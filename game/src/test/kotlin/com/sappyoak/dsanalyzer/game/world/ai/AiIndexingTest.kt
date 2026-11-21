package com.sappyoak.dsanalyzer.game.world.ai

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.luainfo.LuaInfo
import com.sappyoak.dsanalyzer.formats.luainfo.LuaInfoGoal
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.FunctionBody
import com.sappyoak.dsanalyzer.lua.ir.Statement

private val GOAL_LIST = LuaScriptId(LuaBinder.Common, "goal_list.lua")
private val LOGIC_LIST = LuaScriptId(LuaBinder.Common, "logic_list.lua")
private val UNDEAD_BURG = LuaBinder.Map(checkNotNull(MapId.parse("m10_01_00_00")))
private val PARISH = LuaBinder.Map(checkNotNull(MapId.parse("m10_00_00_00")))

private fun main(vararg statements: Statement) = FunctionBody.Decompiled(emptyList(), false, statements.toList())
private fun constant(name: String, id: Int) =
    Statement.Assign(Expression.Global(name), Expression.Number(id.toDouble()))
private fun parameter(goal: String, index: Int, name: String) = Statement.CallStatement(
    Expression.Call(
        Expression.Global("REGISTER_DBG_GOAL_PARAM"),
        listOf(
            Expression.Global(goal), Expression.Number(index.toDouble()), Expression.Text(name), Expression.Number(0.0)
        )
    )
)
private fun function(name: String) = Statement.Assign(Expression.Global(name), Expression.Function(main()))

private fun battle(binder: LuaBinder) = LuaScriptId(binder, "006540_battle.lua") to main(
    function("SunKnight6540Battle_Activate"),
    function("SunKnight6540Battle_Update")
)

private val BATTLE_GOAL = LuaInfoGoal(6540, "SunKnight6540Battle", noUpdate = true, logicInterrupt = false, null)

/** A logic that shares its id with the battle goal, as real maps' logic and goals do */
private val LOGIC = LuaInfoGoal(6540, "sunKnight6540_Logic", false, logicInterrupt = true, "sunKnight6540_Interupt")

private val INDEX = indexAi(
    mapOf(
        GOAL_LIST to main(constant("GOAL_SunKnight6540_Battle", 6540), constant("GOAL_COMMON_Wait", 2000)),
        LOGIC_LIST to main(constant("LOGIC_ID_SunKnight6540", 6540)),
        battle(UNDEAD_BURG),
        battle(PARISH),
        LuaScriptId(LuaBinder.Common, "attack.lua") to main(
            parameter("GOAL_SunKnight6540_Battle", 1, "target"),
            parameter("GOAL_SunKnight6540_Battle", 0, "EzStateID")
        ),
        LuaScriptId(UNDEAD_BURG, "006540_logic.lua") to main(function("sunKnight6540_Logic"))
    ),
    mapOf(UNDEAD_BURG to LuaInfo(listOf(BATTLE_GOAL, LOGIC)), PARISH to LuaInfo(listOf(BATTLE_GOAL)))
)

class AiIndexingTest : FunSpec({
    test("a registered goal takes its own constant and finds the functions that exist") {
        val goal = checkNotNull(INDEX.goals[6540])

        goal.constant shouldBe "GOAL_SunKnight6540_Battle"
        goal.name shouldBe "SunKnight6540Battle"
        goal.functions shouldContainExactly listOf("SunKnight6540Battle_Activate", "SunKnight6540Battle_Update")
    }

    test("a goal keeps its flag and its parameter names in order, knowing which are EzStates") {
        val goal = INDEX.goals.getValue(6540)

        goal.noUpdate shouldBe true
        goal.parameters shouldContainExactly listOf(GoalParameter(0, "EzStateID"), GoalParameter(1, "target"))
        goal.parameters.map { it.isEzState } shouldContainExactly listOf(true, false)
    }

    test("a goal registered in several maps lists every binder") {
        INDEX.goals.getValue(6540).registeredIn shouldContainExactly listOf(PARISH, UNDEAD_BURG)
    }

    test("a constant no binder registers is not a goal") {
        INDEX.goals[2000].shouldBeNull()
    }

    test("a logic takes its own constant and keeps its logic and interrupt functions") {
        val logic = checkNotNull(INDEX.logics[6540])

        logic.constant shouldBe "LOGIC_ID_SunKnight6540"
        logic.functions shouldContainExactly listOf("sunKnight6540_Logic", "sunKnight6540_Interupt")
        logic.registeredIn shouldContainExactly listOf(UNDEAD_BURG)
    }

    test("every global function points at the scripts that declare it") {
        INDEX.definitionsOf("SunKnight6540Battle_Update") shouldContainExactly listOf(
            LuaScriptId(UNDEAD_BURG, "006540_battle.lua"),
            LuaScriptId(PARISH, "006540_battle.lua")
        )
        INDEX.definitionsOf("Missing") shouldBe emptyList()
    }
})
