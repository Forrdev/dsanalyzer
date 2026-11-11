package com.sappyoak.dsanalyzer.tas

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrPosData
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val VEC3_BYTES = 3 * Float.SIZE_BYTES

/** Where a run starts: the player's map, position and facing */
public data class StartState(public val place: WorldPlace, public val position: Vec3, public val angle: Float)

/**
 * Captures and restores [StartState]s
 */
internal class StartStates(private val memory: GameMemory, private val writer: GameWriter) {
    fun capture(place: WorldPlace): StartState? {
        val at = memory.addressOf(ChrPosData.Pointer).takeUnless { it.isNull } ?: return null
        val bytes = memory.memory.read(at, ChrPosData.Position + VEC3_BYTES) ?: return null
        val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
        val position = Vec3(
            buffer.getFloat(ChrPosData.Position),
            buffer.getFloat(ChrPosData.Position + Float.SIZE_BYTES),
            buffer.getFloat(ChrPosData.Position + 2 * Float.SIZE_BYTES)
        )
        return StartState(place, position, buffer.getFloat(ChrPosData.Angle))
    }

    /** Refused on another map: a warp only moves the player within the loaded world */
    fun restore(start: StartState, place: WorldPlace): Boolean {
        if (start.place != place) return false
        val ctrl = memory.addressOf(ChrCtrl.Pointer).takeUnless { it.isNull } ?: return false
        val target = ByteBuffer.allocate(VEC3_BYTES).order(ByteOrder.LITTLE_ENDIAN)
            .putFloat(start.position.x).putFloat(start.position.y).putFloat(start.position.z).array()
        val angle = ByteBuffer.allocate(Float.SIZE_BYTES).order(ByteOrder.LITTLE_ENDIAN).putFloat(start.angle).array()
        // The flag last, so the warp never fires on a half-written target
        return writer.perform(ctrl + ChrCtrl.WarpPosition.toLong(), target) &&
                writer.perform(ctrl + ChrCtrl.WarpAngle.toLong(), angle) &&
                writer.perform(ctrl + ChrCtrl.Warp.toLong(), byteArrayOf(1))
    }
}
