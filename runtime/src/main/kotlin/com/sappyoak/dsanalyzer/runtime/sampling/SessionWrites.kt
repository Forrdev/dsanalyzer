package com.sappyoak.dsanalyzer.runtime.sampling

import java.util.concurrent.atomic.AtomicLong
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

import com.sappyoak.dsanalyzer.game.world.items.ItemCategory
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.Stat
import com.sappyoak.dsanalyzer.runtime.layout.WriteLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.session.SavedPosition
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.UndoResult
import com.sappyoak.dsanalyzer.runtime.write.flags.EventFlagTask
import com.sappyoak.dsanalyzer.runtime.write.items.SpawnItemTask
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallOutcome
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Mailbox
import com.sappyoak.dsanalyzer.runtime.write.mailbox.RemoteMailbox
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission
import com.sappyoak.dsanalyzer.runtime.write.position.RestorePositionTask
import com.sappyoak.dsanalyzer.runtime.write.stats.LevelUpTask
import com.sappyoak.dsanalyzer.runtime.write.stats.StatsEdit
import com.sappyoak.dsanalyzer.runtime.write.talk.TalkCommand
import com.sappyoak.dsanalyzer.runtime.write.talk.TalkTask
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskOutcome
import com.sappyoak.dsanalyzer.runtime.write.task.TaskRunner
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.runtime.write.warp.WarpDestination
import com.sappyoak.dsanalyzer.runtime.write.warp.WarpTask

private const val OUTCOME_BUFFER = 256

public class SessionWrites internal constructor(
    private val layout: WriteLayout,
    private val player: PlayerGameDataLayout,
    private val writer: GameWriter
) {
    private val callIds = AtomicLong(1)
    private val mailbox = Mailbox(layout.mailbox, writer, callIds)
    private val remote = RemoteMailbox(layout.mailbox.arch, writer, callIds)
    private val tasks = TaskRunner()
    private val calls = MutableSharedFlow<CallOutcome>(extraBufferCapacity = OUTCOME_BUFFER, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val taskSteps = MutableSharedFlow<TaskOutcome>(extraBufferCapacity = OUTCOME_BUFFER, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    public val callOutcomes: SharedFlow<CallOutcome> = calls.asSharedFlow()
    public val taskOutcomes: SharedFlow<TaskOutcome> = taskSteps.asSharedFlow()

    private val stats = StatsEdit(layout.stats, player)

    /** Queues a call for the game's logic thread */
    public fun call(call: GameCall): Submission = mailbox.submit(call)

    public fun run(task: WriteTask): Submission = tasks.start(task)

    /** An undo whose inverse is a task starts that task  */
    public fun undo(id: Long, force: Boolean = false): UndoResult {
        val result = writer.undo(id, force)
        if (result is UndoResult.Started) tasks.start(result.task)
        return result
    }

    public fun restorePosition(saved: SavedPosition): Submission = tasks.start(RestorePositionTask(saved, layout.position))
    public fun setSouls(souls: Int): Submission = tasks.start(stats.souls(souls))
    public fun setHumanity(humanity: Int): Submission = tasks.start(stats.humanity((humanity)))
    public fun setNewGame(cycle: Int): Submission = tasks.start(stats.newGame(cycle))
    public fun setCovenant(covenant: Int): Submission = tasks.start(stats.covenant(covenant))

    public fun setCovenantPoints(covenant: Int, points: Int): Submission =
        tasks.start(stats.covenantPoints(covenant, points))

    public fun setAttributes(attributes: Map<Stat, Int>): Submission =
        tasks.start(LevelUpTask(attributes, layout.stats.levelUp, player))

    public fun warpToBonfire(bonfireId: Int): Submission =
        tasks.start(WarpTask(WarpDestination.Bonfire(bonfireId), layout.warp))

    /** Loads [destination]'s map if it is another one, and spawns the player at its point */
    public fun warpToPoint(destination: SavedPosition): Submission =
        tasks.start(WarpTask(WarpDestination.Point(destination), layout.warp))

    /** Runs [command] as an NPC's talk script would, such as `TalkCommands.Repair` */
    public fun talk(command: TalkCommand): Submission = tasks.start(TalkTask(command, layout.talk, layout.mailbox.arch))

    /** Adds [count] of item [id] to the inventory and shows the game's item-get popup for it */
    public fun spawnItem(category: ItemCategory, id: Int, count: Int): Submission =
        tasks.start(SpawnItemTask(category, id, count, layout.items, layout.mailbox.arch))

    /** Sets event flag [flag] through the game's own call, so what the game ties to it follows */
    public fun setFlag(flag: Int, value: Boolean): Submission =
        tasks.start(EventFlagTask(flag, value, layout.flags, layout.mailbox.arch))

    /** Reads event flag [flag] through the game's own call; the task is done with 1 or 0 */
    public fun readFlag(flag: Int): Submission = tasks.start(EventFlagTask(flag, null, layout.flags, layout.mailbox.arch))

    internal fun afterSample(memory: GameMemory, module: AddressRange, inWorld: Boolean, place: WorldPlace) {
        val arrived = mailbox.poll(memory.memory, module, inWorld) + remote.poll(memory.memory, module, inWorld)
        val context = TaskContext(memory, writer, inWorld, place, mailbox::submit, remote::submit, arrived.associateBy { it.id })
        tasks.step(context).forEach(taskSteps::tryEmit)
        arrived.forEach(calls::tryEmit)
    }

    internal fun cancelAll(memory: ProcessMemory) {
        tasks.cancelAll()
        mailbox.cancelAll(memory)
        remote.cancelAll(memory)
    }
}
