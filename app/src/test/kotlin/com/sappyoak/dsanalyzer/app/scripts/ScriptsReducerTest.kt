package com.sappyoak.dsanalyzer.app.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Path

import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

private val INSTALLATION = Installation(
    id = InstallationId("ptde"),
    root = Path.of("game"),
    executable = Path.of("game", "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

private val PARISH = ScriptId.Of(checkNotNull(MapId.parse("m10_02_00_00")))

private fun contents(script: ScriptId, vararg events: Pair<Long, String?>) = ScriptContents(
    script = script,
    events = events.map { (id, name) ->
        EventSummary(WorldRef.ScriptEvent(script, id), id, name, instructionCount = 2)
    },
    linkedFiles = emptyList(),
    definitions = Emedf.Empty,
    decoded = emptyMap()
)

private val COMMON_CONTENTS = contents(ScriptId.Common, 0L to null, 90005L to "Bonfire")

private fun opened(): ScriptsState = reduceScripts(ScriptsState(), ScriptsMessage.Opened(INSTALLATION)).state

private fun loaded(): ScriptsState {
    val catalog = reduceScripts(opened(), ScriptsMessage.CatalogLoaded(listOf(ScriptId.Common, PARISH))).state
    return reduceScripts(catalog, ScriptsMessage.ScriptLoaded(COMMON_CONTENTS)).state
}

class ScriptsReducerTest : FunSpec({
    test("opening a workspace loads the script catalog") {
        reduceScripts(ScriptsState(), ScriptsMessage.Opened(INSTALLATION)).effects shouldBe
                listOf(ScriptsEffect.LoadCatalog(INSTALLATION))
    }

    test("a loaded catalog selects the common script first") {
        val transition = reduceScripts(opened(), ScriptsMessage.CatalogLoaded(listOf(ScriptId.Common, PARISH)))
        assertSoftly {
            transition.state.selected shouldBe ScriptId.Common
            transition.effects shouldBe listOf(ScriptsEffect.LoadScript(INSTALLATION, ScriptId.Common))
        }
    }

    test("selecting an event in the open script focuses it without reloading") {
        val transition = reduceScripts(loaded(), ScriptsMessage.Navigated(WorldRef.ScriptEvent(ScriptId.Common, 90005)))
        assertSoftly {
            transition.state.focused shouldBe 90005L
            transition.effects shouldBe emptyList()
        }
    }

    test("an event in another script loads it and focuses once it arrives") {
        val target = WorldRef.ScriptEvent(PARISH, 11810000)
        val navigated = reduceScripts(loaded(), ScriptsMessage.Navigated(target))
        val arrived = reduceScripts(
            navigated.state,
            ScriptsMessage.ScriptLoaded(contents(PARISH, 11810000L to "Gwyn"))
        )

        assertSoftly {
            navigated.effects shouldBe listOf(ScriptsEffect.LoadScript(INSTALLATION, PARISH))
            navigated.state.pending shouldBe 11810000L
            navigated.state.focused shouldBe null
            arrived.state.focused shouldBe 11810000L
            arrived.state.selected shouldBe PARISH
        }
    }

    test("the query narrows events by id and by name") {
        val state = loaded()
        assertSoftly {
            reduceScripts(state, ScriptsMessage.QueryChanged("9000")).state.visible.map { it.id } shouldBe listOf(90005L)
            reduceScripts(state, ScriptsMessage.QueryChanged("bonf")).state.visible.map { it.id } shouldBe listOf(90005L)
            reduceScripts(state, ScriptsMessage.QueryChanged("zzz")).state.visible shouldBe emptyList()
        }
    }

    test("a focus that the newly loaded script does not have is dropped") {
        val focused = reduceScripts(loaded(), ScriptsMessage.Navigated(WorldRef.ScriptEvent(ScriptId.Common, 90005))).state
        val reloaded = reduceScripts(focused, ScriptsMessage.ScriptLoaded(contents(ScriptId.Common, 1L to null))).state
        reloaded.focused shouldBe null
    }
})