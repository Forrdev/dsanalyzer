package com.sappyoak.dsanalyzer.runtime.write.stats

import com.sappyoak.dsanalyzer.runtime.layout.LevelUpLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.Stat
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.write.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallOutcome
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallRegister
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.runtime.write.mailbox.Submission
import com.sappyoak.dsanalyzer.runtime.write.task.TaskContext
import com.sappyoak.dsanalyzer.runtime.write.task.TaskStep
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

public val LEVEL_UP_ORDER: List<Stat> = listOf(
    Stat.Vitality,
    Stat.Attunement,
    Stat.Endurance,
    Stat.Strength,
    Stat.Dexterity,
    Stat.Resistance,
    Stat.Intelligence,
    Stat.Faith
)

/** Where the nine ints sit in a split call's data, past the level block and aligned */
internal const val SPLIT_STATS: Int = 0x2B0

private const val MIN_STAT = 1
private const val MAX_STAT = 99
private val KEY = WriteKey("stats:attributes")

internal data class Attributes(
    val stats: Map<Stat, Int>,
    val humanity: Int,
    val soulLevel: Int,
    val souls: Int
)

/**
 * Sets attributes through the game's own level-up. The soul level moves by the sum of the stat changes, and the
 * souls and the humanity the player has passed back unchanged. The write is recorded with a level-up back to
 * the previous attributes as its undo
 */
public class LevelUpTask internal constructor(
    target: Map<Stat, Int>,
    private val layout: LevelUpLayout,
    private val player: PlayerGameDataLayout
) : WriteTask {
    override val label: String get() = "Set attributes"
    override val mergeKey: String get() = KEY.name

    private val target = target.mapValues { (_, value) -> value.coerceIn(MIN_STAT, MAX_STAT) }
    private var sent: Long? = null

    override fun step(context: TaskContext): TaskStep {
        val id = sent ?: return send(context)
        return when (val outcome = context.outcome(id)) {
            null -> TaskStep.Continue
            is CallOutcome.Done -> TaskStep.Done(outcome.value)
            is CallOutcome.TimedOut -> TaskStep.Failed("The level up was not run in time")
            is CallOutcome.Failed -> TaskStep.Failed(outcome.reason)
        }
    }

    private fun send(context: TaskContext): TaskStep {
        if (!context.inWorld) return TaskStep.Failed("The player is not in the world")
        val function = context.memory.addressOf(layout.function)
        if (function.isNull) return TaskStep.Failed("${layout.function} could not be found")
        val now = read(context.memory) ?: return TaskStep.Failed("The attributes could not be read")
        val wanted = LEVEL_UP_ORDER.associateWith { target[it] ?: now.stats.getValue(it) }

        val call = GameCall(
            target = function,
            label = label,
            data = levelUpBlock(layout, wanted, now, newSoulLevel(now, wanted)),
            dataPointers = levelUpPointers(layout)
        )
        return when (val submission = context.call(call)) {
            is Submission.Refused -> TaskStep.Failed(submission.reason)
            is Submission.Queued -> {
                sent = submission.id
                context.writer.recordEdit(KEY, label) { LevelUpTask(now.stats, layout, player) }
                TaskStep.Continue
            }
        }
    }

    private fun read(memory: GameMemory): Attributes? {
        val view = StructView(player.pointer)
        if (!view.refresh(memory)) return null
        return Attributes(
            stats = LEVEL_UP_ORDER.associateWith { view.int(player.stats.offsetOf(it)) },
            humanity = view.int(player.progress.humanity),
            soulLevel = view.int(player.progress.soulLevel),
            souls = view.int(player.progress.souls)
        )
    }
}

/** The soul level the game lands on: one level for every point the stats move by */
internal fun newSoulLevel(now: Attributes, wanted: Map<Stat, Int>): Int =
    now.soulLevel + wanted.entries.sumOf { (stat, value) -> value - now.stats.getValue(stat) }

/** The block the call reads its stats out of, laid out as the edition's own level-up expects */
internal fun levelUpBlock(layout: LevelUpLayout, wanted: Map<Stat, Int>, now: Attributes, level: Int): ByteArray =
    when (layout) {
        is LevelUpLayout.OneBlock -> littleEndian(layout.size).apply {
            LEVEL_UP_ORDER.forEachIndexed { i, stat -> putInt(i * Int.SIZE_BYTES, wanted.getValue(stat)) }
            putInt(layout.humanity, now.humanity)
            putInt(layout.soulLevel, level)
            putInt(layout.souls, now.souls)
        }.array()

        is LevelUpLayout.SplitBlock -> littleEndian(SPLIT_STATS + (LEVEL_UP_ORDER.size + 1) * Int.SIZE_BYTES).apply {
            putInt(layout.newLevel, level)
            putInt(layout.requiredSouls, 0)
            putInt(layout.currentSouls, now.souls)
            LEVEL_UP_ORDER.forEachIndexed { i, stat -> putInt(SPLIT_STATS + i * Int.SIZE_BYTES, wanted.getValue(stat)) }
            putInt(SPLIT_STATS + LEVEL_UP_ORDER.size * Int.SIZE_BYTES, now.humanity)
        }.array()
    }

/** Which registers the call wants pointed into that block */
internal fun levelUpPointers(layout: LevelUpLayout): Map<CallRegister, Int> = when (layout) {
    is LevelUpLayout.OneBlock -> mapOf(CallRegister.Eax to 0, CallRegister.Ecx to 0)
    is LevelUpLayout.SplitBlock -> mapOf(CallRegister.Rcx to 0, CallRegister.Rdx to SPLIT_STATS)
}
