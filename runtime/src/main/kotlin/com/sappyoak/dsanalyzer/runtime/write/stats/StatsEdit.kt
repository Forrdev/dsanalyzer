package com.sappyoak.dsanalyzer.runtime.write.stats

import com.sappyoak.dsanalyzer.runtime.layout.FieldLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.StatWriteLayout
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.task.FieldEditTask
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.shared.binary.toLittleEndian

/** One covenant byte each, counted from Warriors of Sunlight */
public const val COVENANT_COUNT: Int = 7

private const val MAX_COVENANT = 9
private const val MAX_POINTS = 255

internal class StatsEdit(
    private val layout: StatWriteLayout,
    private val player: PlayerGameDataLayout
) {
    fun souls(value: Int): WriteTask =
        edit("Set souls to $value", "stats:souls", field(player.progress.souls), value.coerceAtLeast(0).toLittleEndian())

    fun humanity(value: Int): WriteTask =
        edit("Set humanity to $value", "stats:humanity", field(player.progress.humanity), value.coerceIn(0, 99).toLittleEndian())

    fun newGame(cycle: Int): WriteTask =
        edit("Set the NG cycle to $cycle", "stats:new-game", layout.newGame, cycle.coerceAtLeast(0).toLittleEndian())

    fun covenant(covenant: Int): WriteTask {
        require(covenant in 0..MAX_COVENANT) { "Covenant $covenant is outside 0 to $MAX_COVENANT" }
        return edit("Set the covenant to $covenant", "stats:covenant", field(layout.covenant), byteArrayOf(covenant.toByte()))
    }

    fun covenantPoints(covenant: Int, points: Int): WriteTask {
        require(covenant in 0 until COVENANT_COUNT) { "Covenant $covenant is outside 0 to ${COVENANT_COUNT - 1}" }
        val value = points.coerceIn(0, MAX_POINTS).toByte()
        return edit(
            "Set covenant $covenant to $points points",
            "stats:covenant-points:$covenant",
            field(layout.covenantPoints + covenant),
            byteArrayOf(value)
        )
    }

    private fun field(offset: Int) = FieldLayout(player.pointer, offset)

    private fun edit(label: String, key: String, field: FieldLayout, bytes: ByteArray) =
        FieldEditTask(label, WriteKey(key), field, bytes)
}
