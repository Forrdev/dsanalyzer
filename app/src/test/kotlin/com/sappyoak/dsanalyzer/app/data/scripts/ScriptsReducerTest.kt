package com.sappyoak.dsanalyzer.app.data.scripts

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
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
        EventSummary(View.Data.ScriptEvent(script, id), id, name, instructionCount = 2)
    },
    linkedFiles = emptyList(),
    definitions = Emedf.Empty,
    decoded = emptyMap()
)

private val COMMON_CONTENTS = contents(ScriptId.Common, 0L to null, 90005L to "Bonfire")

/** The catalog read, nothing loaded -- where a session now starts */
private val CATALOGUED = ScriptsState(
    installation = INSTALLATION,
    scripts = listOf(ScriptId.Common, PARISH)
)

class ScriptsReducerTest : FunSpec({

    test("opening starts the catalog and discards the last workspace") {
        val (state, effects) = reduceScripts(
            CATALOGUED.copy(contents = COMMON_CONTENTS, problem = "stale"),
            ScriptsMessage.Opened(INSTALLATION)
        )

        assertSoftly {
            state.contents shouldBe null
            state.problem shouldBe null
            state.scripts.shouldBeEmpty()
            effects shouldContainExactly listOf(ScriptsEffect.LoadCatalog(INSTALLATION))
        }
    }

    test("the catalog indexes every script and reads none") {
        val catalog = listOf(ScriptId.Common, PARISH)
        val (state, effects) = reduceScripts(
            ScriptsState(installation = INSTALLATION),
            ScriptsMessage.CatalogLoaded(catalog)
        )

        assertSoftly {
            state.scripts shouldContainExactly catalog
            state.indexing shouldBe true
            withClue("a view asks for a script; the catalog no longer reads the first one") {
                state.shown shouldBe null
                state.loading shouldBe false
            }
            effects shouldContainExactly listOf(ScriptsEffect.IndexFlags(INSTALLATION, catalog))
        }
    }

    test("selecting a script reads it, and selecting it again does not") {
        val (reading, effects) = reduceScripts(
            CATALOGUED,
            ScriptsMessage.ScriptSelected(ScriptId.Common)
        )

        assertSoftly {
            reading.loadingScript shouldBe ScriptId.Common
            reading.shown shouldBe ScriptId.Common
            effects shouldContainExactly
                    listOf(ScriptsEffect.LoadScript(INSTALLATION, ScriptId.Common))

            withClue("a repeated view must not restart a read in flight") {
                reduceScripts(reading, ScriptsMessage.ScriptSelected(ScriptId.Common))
                    .effects.shouldBeEmpty()
            }

            val here = CATALOGUED.copy(contents = COMMON_CONTENTS)
            reduceScripts(here, ScriptsMessage.ScriptSelected(ScriptId.Common)).state shouldBe here
        }
    }

    test("a loaded script clears the in-flight marker and exposes its events") {
        val (state, _) = reduceScripts(
            CATALOGUED.copy(loadingScript = ScriptId.Common),
            ScriptsMessage.ScriptLoaded(COMMON_CONTENTS)
        )

        assertSoftly {
            state.contents shouldBe COMMON_CONTENTS
            state.loadingScript shouldBe null
            state.event(90005L)?.name shouldBe "Bonfire"
            withClue("an event the script does not have is absent, not an error") {
                state.event(1L) shouldBe null
            }
        }
    }

    test("a read that finishes after the view moved on is ignored") {
        val onParish = CATALOGUED.copy(contents = contents(PARISH), loadingScript = null)

        val (state, _) = reduceScripts(onParish, ScriptsMessage.ScriptLoaded(COMMON_CONTENTS))

        withClue("otherwise a late read replaces the script you just navigated to") {
            state.contents?.script shouldBe PARISH
        }
    }

    test("selecting a script with no installation open says so instead of reading") {
        val (state, effects) = reduceScripts(
            ScriptsState(scripts = listOf(ScriptId.Common)),
            ScriptsMessage.ScriptSelected(ScriptId.Common)
        )

        assertSoftly {
            state.problem shouldBe "No installation is open"
            state.loadingScript shouldBe null
            effects.shouldBeEmpty()
        }
    }

    test("a failed read stops the spinner; a failed index stops only the indexing") {
        assertSoftly {
            val failedRead = reduceScripts(
                CATALOGUED.copy(loadingScript = ScriptId.Common),
                ScriptsMessage.Failed("unreadable")
            ).state
            failedRead.loading shouldBe false
            failedRead.problem shouldBe "unreadable"

            val failedIndex = reduceScripts(
                CATALOGUED.copy(indexing = true, loadingScript = ScriptId.Common),
                ScriptsMessage.IndexFailed("no emevd")
            ).state
            failedIndex.indexing shouldBe false
            withClue("indexing is background work and must not cancel the script being read") {
                failedIndex.loadingScript shouldBe ScriptId.Common
            }
        }
    }

    test("the event query is kept verbatim and narrows the visible list") {
        val here = CATALOGUED.copy(contents = COMMON_CONTENTS)

        val (state, _) = reduceScripts(here, ScriptsMessage.QueryChanged("Bonf"))

        assertSoftly {
            state.query shouldBe "Bonf"
            state.visible.map { it.id } shouldContainExactly listOf(90005L)
        }
    }
})
