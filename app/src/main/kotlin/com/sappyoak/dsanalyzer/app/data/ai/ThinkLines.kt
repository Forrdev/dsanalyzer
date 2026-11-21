package com.sappyoak.dsanalyzer.app.data.ai

import com.sappyoak.dsanalyzer.app.data.params.ParamsState
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.toDataAddress
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.NpcThinkParamRow
import com.sappyoak.dsanalyzer.game.ref.Address
import com.sappyoak.dsanalyzer.game.world.params.GameParam

private const val LOGIC_FIELD = "logicId"

/** NpcThinkParam fields that name a goal */
private val GOAL_FIELDS: Map<String, (NpcThinkParamRow) -> Int> = mapOf(
    "battleGoalID" to { it.battleGoalID },
    "goalID_ToCaution" to { it.goalID_ToCaution },
    "goalID_ToFind" to { it.goalID_ToFind }
)

public fun aiLinksOf(row: ParamRow): Map<String, Address> {
    val think = row as? NpcThinkParamRow ?: return emptyMap()
    return buildMap {
        if (think.logicId >= 0) put(LOGIC_FIELD, address(View.Data.AiLogic(think.logicId)))
        GOAL_FIELDS.forEach { (field, read) ->
            read(think).takeIf { it > 0 }?.let { put(field, address(View.Data.AiGoal(it))) }
        }
    }
}

/** One NpcThinkParam row that names a goal or logic, and the field it names it in */
public data class ThinkUse(public val rowId: Int, public val label: String, public val field: String)

/** The NpcThinkParam rows that run [view]'s goal or logic */
public fun ParamsState.thinkRowsUsing(view: View.Data): List<ThinkUse> {
    val entry = entries[GameParam.NpcThinkParam.fileName] ?: return emptyList()
    return entry.table.rows.filterIsInstance<NpcThinkParamRow>().flatMap { row ->
        val fields = when (view) {
            is View.Data.AiLogic -> listOf(LOGIC_FIELD).filter { row.logicId == view.logicId }
            is View.Data.AiGoal -> GOAL_FIELDS.filter { (_, read) -> read(row) == view.goalId }.keys.toList()
            else -> emptyList()
        }
        fields.map { ThinkUse(row.id, naming.labelOf(entry, row).name, it) }
    }
}

private fun address(view: View.Data): Address = checkNotNull(view.toDataAddress())
