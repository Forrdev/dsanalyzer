package com.sappyoak.dsanalyzer.game.world.ai

import com.sappyoak.dsanalyzer.formats.luainfo.LuaInfo
import com.sappyoak.dsanalyzer.formats.luainfo.LuaInfoGoal
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.FunctionBody
import com.sappyoak.dsanalyzer.lua.ir.Statement

private const val GOAL_PREFIX = "GOAL_"
private const val DEBUG_PARAMETER = "REGISTER_DBG_GOAL_PARAM"
private const val LOGIC_PREFIX = "LOGIC_ID_"

/** The suffixes the engine appends to a goal's name to find its functions */
private val GOAL_FUNCTION_SUFFIXES = listOf("_Activate", "_Update", "_Terminate", "_Interupt")

/** Changes whenever the same scripts would index differently, so an index kept from before is not reused */
internal const val AI_INDEX_REVISION: Int = 4

/**
 * Indexes the AI: the goals and logic each binder's `.luainfo` registers ([infos]), named by the `GOAL_` and
 * `LOGIC_ID_` constants the lists in [mains] assign, with the goal parameter names the scripts register,
 * every global function each script declares, and the flags they set
 */
public fun indexAi(mains: Map<LuaScriptId, FunctionBody>, infos: Map<LuaBinder, LuaInfo>): AiIndex {
    val statements = mains.mapValues { (_, body) -> (body as? FunctionBody.Decompiled)?.statements.orEmpty() }
    val constants = statements.values.flatten().mapNotNull(::constant)
    val goalConstants = constants.filter { it.first.startsWith(GOAL_PREFIX) }.associate { (name, id) -> id to name }
    val logicConstants = constants.filter { it.first.startsWith(LOGIC_PREFIX) }.associate { (name, id) -> id to name }
    val functions = statements.flatMap { (script, body) -> body.mapNotNull(::declaredFunction).map { it to script } }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, scripts) -> scripts.distinct() }
    val goalIds = goalConstants.entries.associate { (id, name) -> name to id }
    val parameters = statements.values.flatten().mapNotNull { parameter(it, goalIds) }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, all) -> all.distinctBy { it.index }.sortedBy { it.index } }

    val registrations = infos.entries.sortedBy { it.key.label }
        .flatMap { (binder, info) -> info.goals.map { binder to it } }
    val (logics, goals) = registrations.partition { (_, goal) -> goal.logicInterrupt }
    return AiIndex(
        goals = byId(goals) { id, first, binders ->
            val functionNames = GOAL_FUNCTION_SUFFIXES.map { first.name + it }.filter(functions::containsKey)
            AiGoal(id, goalConstants[id], first.name, functionNames, binders, first.noUpdate, parameters[id].orEmpty())
        },
        logics = byId(logics) { id, first, binders ->
            AiLogic(id, logicConstants[id], listOfNotNull(first.name, first.interruptName), binders)
        },
        functions = functions,
        flagSets = luaFlagSets(mains).groupBy({ it.first }, { it.second })
    )
}

/** One entry per id, from its first registration, with every binder that registers it */
private fun <T> byId(
    registrations: List<Pair<LuaBinder, LuaInfoGoal>>,
    entry: (Int, LuaInfoGoal, List<LuaBinder>) -> T
): Map<Int, T> = registrations.groupBy { it.second.id }
    .mapValues { (id, all) -> entry(id, all.first().second, all.map { it.first }.distinct()) }
    .toSortedMap()

/** `GOAL_X = 6540` or `LOGIC_ID_X = 6540` */
private fun constant(statement: Statement): Pair<String, Int>? {
    val assign = statement as? Statement.Assign ?: return null
    val name = (assign.target as? Expression.Global)?.name ?: return null
    if (!name.startsWith(GOAL_PREFIX) && !name.startsWith(LOGIC_PREFIX)) return null
    return (assign.value as? Expression.Number)?.let { name to it.value.toInt() }
}

/** `REGISTER_DBG_GOAL_PARAM(GOAL_X, 0, "name", type)`, by goal id */
private fun parameter(statement: Statement, goalIds: Map<String, Int>): Pair<Int, GoalParameter>? {
    val call = (statement as? Statement.CallStatement)?.call as? Expression.Call ?: return null
    if ((call.function as? Expression.Global)?.name != DEBUG_PARAMETER) return null
    val goal = when (val first = call.arguments.getOrNull(0)) {
        is Expression.Global -> goalIds[first.name]
        is Expression.Number -> first.value.toInt()
        else -> null
    } ?: return null
    val index = (call.arguments.getOrNull(1) as? Expression.Number)?.value?.toInt() ?: return null
    val name = (call.arguments.getOrNull(2) as? Expression.Text)?.value ?: return null
    return goal to GoalParameter(index, name)
}

/** `function Name(...)`, a function stored straight into a global */
private fun declaredFunction(statement: Statement): String? {
    val assign = statement as? Statement.Assign ?: return null
    return (assign.target as? Expression.Global)?.name?.takeIf { assign.value is Expression.Function }
}
