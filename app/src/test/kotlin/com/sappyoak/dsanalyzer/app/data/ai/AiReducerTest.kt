package com.sappyoak.dsanalyzer.app.data.ai

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.ai.LuaSource
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

private val INSTALLATION = Installation(
    id = InstallationId("dsr"),
    root = Path.of("game"),
    executable = Path.of("game", "DarkSoulsRemastered.exe"),
    build = GameBuild(GameEdition.Remastered)
)

private val OPEN = AiState(installation = INSTALLATION)
private val DEFINE = LuaScriptId(LuaBinder.Common, "ai_define.lua")
private val GOALS = LuaScriptId(LuaBinder.Common, "goal_list.lua")

private fun source(text: String) = LuaSource(text, emptyList())

class AiReducerTest : FunSpec({
    test("opening an installation lists the binders, starts indexing and finds the characters") {
        val (state, effects) = reduceAi(AiState(), AiMessage.Opened(INSTALLATION))

        state.installation shouldBe INSTALLATION
        effects shouldContainExactly listOf(
            AiEffect.LoadCatalog(INSTALLATION),
            AiEffect.Index(INSTALLATION),
            AiEffect.FindCharacters(INSTALLATION)
        )
    }

    test("found characters are kept by think row") {
        val taurus = mapOf(225000 to setOf(AnimationSetId.Character(2250)))

        reduceAi(OPEN, AiMessage.CharactersFound(taurus)).state.thinkCharacters shouldBe taurus
    }

    test("a script is known once its binder lists it") {
        val listed = reduceAi(OPEN, AiMessage.CatalogLoaded(mapOf(LuaBinder.Common to listOf(DEFINE.name)))).state

        listed.has(DEFINE) shouldBe true
        listed.has(GOALS) shouldBe false
    }

    test("choosing a script loads it, and choosing it again does not reload") {
        val (loading, effects) = reduceAi(OPEN, AiMessage.ScriptSelected(DEFINE))
        loading.shown shouldBe DEFINE
        effects shouldContainExactly listOf(AiEffect.Load(INSTALLATION, DEFINE))

        reduceAi(loading, AiMessage.ScriptSelected(DEFINE)).effects.shouldBeEmpty()
    }

    test("a script that loads after another was chosen is dropped") {
        val shown = OPEN.copy(shown = GOALS, loading = true)

        reduceAi(shown, AiMessage.Loaded(DEFINE, source("x = 1"))).state.source.shouldBeNull()
        reduceAi(shown, AiMessage.Loaded(GOALS, source("y = 2"))).state.source?.source shouldBe "y = 2"
    }
})
