package com.sappyoak.dsanalyzer.runtime.write.position

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.LivePositionLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.session.SavedPosition
import com.sappyoak.dsanalyzer.runtime.session.loadedMap
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

/** How close the player must land for the restore to count */
private const val ARRIVED: Float = 0.5f
private const val RETRIES: Int = 1
private const val VECTOR: Int = 4 * Float.SIZE_BYTES
private val KEY = WriteKey("position:restore")

private enum class Phase { Request, Settle, Verify }

/**
 * Moves the player to [saved] in the same map and stops their momentum. The game's
 * own warp request moves the character but keeps its velocity, so after the game takes the rqeuest
 * the proxy velocity, the ChrPosData velocity and the in-air timer are set to zero. The write is
 * recorded with a restore to the previous position as its undo
 */
@OptIn(GameMutation::class)
public class RestorePositionTask internal constructor(
    private val saved: SavedPosition,
    private val layout: LivePositionLayout,
    private val locate: (GameMemory) -> PlayerBody? = { locateBody(it, layout) }
) : WriteTask {
    override val label: String get() = "Restore position"
    override val mergeKey: String get() = KEY.name

    private var phase = Phase.Request
    private var retries = 0
    private var recorded = false

    override fun step(context: TaskContext): TaskStep {
        if (!context.inWorld) return TaskStep.Failed("The player is not in the world")
        if (context.place.loadedMap != saved.map) return TaskStep.Failed("The saved position is in another map")
        val body = locate(context.memory) ?: return TaskStep.Failed("The player could not be found")
        val writer = context.writer
        return when (phase) {
            Phase.Request -> request(context.memory, writer, body)
            Phase.Settle -> settle(context.memory, writer, body)
            Phase.Verify -> verify(context.memory, body)
        }
    }

    private fun request(memory: GameMemory, writer: GameWriter, body: PlayerBody): TaskStep {
        val before = readPlace(memory, layout, body) ?: return TaskStep.Failed("The position could not be read")
        val position = littleEndian(3 * Float.SIZE_BYTES)
            .putFloat(saved.position.x).putFloat(saved.position.y).putFloat(saved.position.z).array()
        val written = writer.fill(body.chrCtrl + layout.warpPosition.toLong(), position) &&
                writer.fill(body.chrCtrl + layout.warpAngle.toLong(), float(saved.angle)) &&
                writer.fill(body.chrCtrl + layout.warp.toLong(), byteArrayOf(1))
        if (!written) return TaskStep.Failed("The warp request could not be written")
        if (!recorded) {
            val previous = SavedPosition(saved.map, before.first, before.second)
            writer.recordEdit(KEY, label) { RestorePositionTask(previous, layout, locate) }
            recorded = true
        }
        phase = Phase.Settle
        return TaskStep.Continue
    }

    /** Waits until the game has taken the request, then stops the momentum it kept */
    private fun settle(memory: GameMemory, writer: GameWriter, body: PlayerBody): TaskStep {
        val pending = memory.memory.read(body.chrCtrl + layout.warp.toLong(), 1)?.get(0) ?: return TaskStep.Failed("The warp request could not be read")
        if (pending.toInt() != 0) return TaskStep.Continue
        val zeroed = zero(writer, body.proxy + layout.proxyVelocity.toLong(), VECTOR) &&
                zero(writer, body.posData + layout.velocity.toLong(), VECTOR) &&
                zero(writer, body.posData + layout.airTimer.toLong(), 2 * Float.SIZE_BYTES)
        if (!zeroed) return TaskStep.Failed("The momentum could not be cleared")
        phase = Phase.Verify
        return TaskStep.Continue
    }

    private fun verify(memory: GameMemory, body: PlayerBody): TaskStep {
        val (now, _) = readPlace(memory, layout, body) ?: return TaskStep.Failed("The position could not be read")
        val dx = now.x - saved.position.x
        val dy = now.y - saved.position.y
        val dz = now.z - saved.position.z
        if (dx * dx + dy * dy + dz * dz <= ARRIVED * ARRIVED) return TaskStep.Done()
        if (retries++ >= RETRIES) return TaskStep.Failed("The player did not arrive at the saved position")
        phase = Phase.Request
        return TaskStep.Continue
    }

    private fun zero(writer: GameWriter, at: Address, size: Int): Boolean = writer.fill(at, ByteArray(size))

    private fun float(value: Float): ByteArray = littleEndian(Float.SIZE_BYTES).putFloat(value).array()
}
