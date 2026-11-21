package com.sappyoak.dsanalyzer.game.world.ai

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly

import com.sappyoak.dsanalyzer.game.world.maps.MapId

private val BURG = LuaBinder.Map(checkNotNull(MapId.parse("m10_01_00_00")))
private val PARISH = LuaBinder.Map(checkNotNull(MapId.parse("m10_00_00_00")))
private val HERE = LuaScriptId(BURG, "006540_battle.lua")
private val COMMON = LuaScriptId(LuaBinder.Common, "common_battle_func.lua")
private val PARISH_COPY = LuaScriptId(PARISH, "006540_battle.lua")

/** Parameter 0 of an attack is its EzState; an approach's guard EzState is parameter 4 */
private val ATTACK = listOf(GoalParameter(0, "EzStateID"), GoalParameter(1, "target"))
private val GUARD = listOf(GoalParameter(0, "target"), GoalParameter(4, "ガードEzState番号"))

private val LINKS = SourceLinks(
    AiIndex(
        goals = mapOf(
            6540 to AiGoal(6540, "GOAL_SunKnight6540_Battle", "SunKnight6540Battle", emptyList(), listOf(BURG)),
            2109 to AiGoal(2109, "GOAL_COMMON_Attack", "Attack", emptyList(), listOf(LuaBinder.Common), false, ATTACK),
            2100 to AiGoal(
                2100, "GOAL_COMMON_Approach", "Approach", emptyList(), listOf(LuaBinder.Common), false, GUARD
            )
        ),
        logics = mapOf(6540 to AiLogic(6540, "LOGIC_ID_SunKnight6540", emptyList(), listOf(BURG))),
        functions = mapOf(
            "Approach_Act" to listOf(PARISH_COPY, COMMON),
            "SunKnight6540_Act01" to listOf(PARISH_COPY, HERE),
            "OnlyInParish" to listOf(PARISH_COPY)
        )
    )
)

private fun targets(line: String) = LINKS.linksIn(line, HERE).map { line.substring(it.start, it.end) to it.target }

class SourceLinksTest : FunSpec({
    test("goal and logic constants open their goal and logic") {
        targets("REGISTER(GOAL_SunKnight6540_Battle, LOGIC_ID_SunKnight6540)") shouldContainExactly listOf(
            "GOAL_SunKnight6540_Battle" to SourceTarget.Goal(6540),
            "LOGIC_ID_SunKnight6540" to SourceTarget.Logic(6540)
        )
    }

    test("a function opens the copy in the same binder, then the common one, then any") {
        targets("    SunKnight6540_Act01(ai, goal) Approach_Act(ai) OnlyInParish()") shouldContainExactly listOf(
            "SunKnight6540_Act01" to SourceTarget.Function(HERE, "SunKnight6540_Act01"),
            "Approach_Act" to SourceTarget.Function(COMMON, "Approach_Act"),
            "OnlyInParish" to SourceTarget.Function(PARISH_COPY, "OnlyInParish")
        )
    }

    test("names in strings and comments, fields, methods and declarations are not links") {
        targets("""x = "Approach_Act \" GOAL_SunKnight6540_Battle" -- Approach_Act""").shouldBeEmpty()
        targets("ai:Approach_Act() t.Approach_Act = 1").shouldBeEmpty()
        targets("function SunKnight6540_Act01(ai, goal)").shouldBeEmpty()
    }

    test("a literal id in an EzState parameter of a goal call opens that animation") {
        targets("goal:AddSubGoal(GOAL_COMMON_Attack, 10, 3000, TARGET_ENE_0)") shouldContainExactly listOf(
            "GOAL_COMMON_Attack" to SourceTarget.Goal(2109),
            "3000" to SourceTarget.Animation(3000)
        )
        targets("ai:AddTopGoal(GOAL_COMMON_Approach, 5, F(1, 2), 3, 4, 5, 9910)").map { it.second }
            .filterIsInstance<SourceTarget.Animation>() shouldContainExactly listOf(SourceTarget.Animation(9910))
    }

    test("none, a value not written out and a call that does not close are not animations") {
        targets("goal:AddSubGoal(GOAL_COMMON_Attack, 10, -1, TARGET_ENE_0)").map { it.first } shouldContainExactly
                listOf("GOAL_COMMON_Attack")
        targets("goal:AddSubGoal(GOAL_COMMON_Attack, 10, NPC_ATK_NormalR)").map { it.first } shouldContainExactly
                listOf("GOAL_COMMON_Attack")
        targets("goal:AddSubGoal(GOAL_COMMON_Attack, 10, 3000").map { it.first } shouldContainExactly
                listOf("GOAL_COMMON_Attack")
    }

    test("a name after a string on the same line still links") {
        targets("""Print('-- not a comment', Approach_Act)""") shouldContainExactly listOf(
            "Approach_Act" to SourceTarget.Function(COMMON, "Approach_Act")
        )
    }
})
