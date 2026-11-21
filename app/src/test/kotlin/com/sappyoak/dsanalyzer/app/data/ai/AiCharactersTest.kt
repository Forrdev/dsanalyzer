package com.sappyoak.dsanalyzer.app.data.ai

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly

import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.game.world.ai.AiGoal
import com.sappyoak.dsanalyzer.game.world.ai.AiIndex
import com.sappyoak.dsanalyzer.game.world.ai.AiLogic
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId
import com.sappyoak.dsanalyzer.game.world.maps.MapId

private val BURG = LuaBinder.Map(checkNotNull(MapId.parse("m10_01_00_00")))
private val TAURUS_SCRIPT = LuaScriptId(BURG, "225000_battle.lua")
private val LOGIC_SCRIPT = LuaScriptId(BURG, "010100_logic.lua")
private val TAURUS = AnimationSetId.Character(2250)
private val CAPRA = AnimationSetId.Character(2240)
private val PLAYER = AnimationSetId.Character(0)

private val INDEX = AiIndex(
    goals = mapOf(225000 to AiGoal(225000, null, "Hammer", listOf("Hammer_Activate"), listOf(BURG))),
    logics = mapOf(10100 to AiLogic(10100, null, listOf("Gitai_Logic", "Gitai_Interupt"), listOf(BURG))),
    functions = mapOf("Hammer_Activate" to listOf(TAURUS_SCRIPT), "Gitai_Logic" to listOf(LOGIC_SCRIPT))
)

class AiCharactersTest : FunSpec({
    test("a script runs the goals and logic it defines functions for") {
        INDEX.entriesDefinedIn(TAURUS_SCRIPT) shouldContainExactly listOf(View.Data.AiGoal(225000))
        INDEX.entriesDefinedIn(LOGIC_SCRIPT) shouldContainExactly listOf(View.Data.AiLogic(10100))
    }

    test("think rows give their placed characters once each, by label, without the human model") {
        val byThink = mapOf(1 to setOf(TAURUS, PLAYER), 2 to setOf(CAPRA, TAURUS))

        charactersOfRows(listOf(1, 2, 3), byThink) shouldContainExactly listOf(CAPRA, TAURUS)
    }
})
