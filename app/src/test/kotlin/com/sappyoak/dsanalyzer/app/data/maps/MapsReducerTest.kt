package com.sappyoak.dsanalyzer.app.data.maps

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.nio.file.Path

import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId

private val INSTALLATION = Installation(
    id = InstallationId("ptde"),
    root = Path.of("game"),
    executable = Path.of("game", "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

private val DEPTHS = checkNotNull(MapId.parse("m10_00_00_00"))
private val PARISH = checkNotNull(MapId.parse("m10_02_00_00"))

private val CATALOGUED = MapsState(installation = INSTALLATION, maps = listOf(PARISH, DEPTHS))

private fun contentsFor(map: MapId, vararg entries: EntrySummary) = MapContents(
    map = map,
    msb = MSB(emptyList(), emptyList(), emptyList(), emptyList()),
    entries = entries.toList(),
    collisionIndices = emptyList(),
    environmentIndices = emptyList()
)


class MapsReducerTest : FunSpec({
    test("the catalog populates the navigator and reads no map on its own") {
        val (state, effects) = reduceMaps(
            MapsState(installation = INSTALLATION),
            MapsMessage.CatalogLoaded(listOf(PARISH, DEPTHS))
        )

        assertSoftly {
            state.maps shouldContainExactly listOf(PARISH, DEPTHS)
            withClue("a view asks for a map; the catalog no longer picks the first one") {
                state.shown shouldBe null
                state.loading shouldBe false
            }
            withClue("indexing still starts, since it covers every map rather than a chosen one") {
                effects shouldContainExactly
                        listOf(MapsEffect.IndexEntities(INSTALLATION, listOf(PARISH, DEPTHS)))
            }
            withClue("and an empty catalog asks for nothing at all") {
                reduceMaps(
                    MapsState(installation = INSTALLATION),
                    MapsMessage.CatalogLoaded(emptyList())
                ).effects.shouldBeEmpty()
            }
        }
    }

    test("selecting a map reads it") {
        val (state, effects) = reduceMaps(CATALOGUED, MapsMessage.MapSelected(PARISH))

        assertSoftly {
            state.loadingMap shouldBe PARISH
            state.loading shouldBe true
            withClue("shown covers the map being read, not only one already here") {
                state.shown shouldBe PARISH
            }
            effects shouldContainExactly listOf(MapsEffect.LoadMap(INSTALLATION, PARISH))
        }
    }

    test("selecting the map already here, or already on its way, does nothing") {
        val loaded = CATALOGUED.copy(contents = contentsFor(PARISH))
        val inFlight = CATALOGUED.copy(loadingMap = PARISH)

        assertSoftly {
            reduceMaps(loaded, MapsMessage.MapSelected(PARISH)).state shouldBe loaded
            reduceMaps(loaded, MapsMessage.MapSelected(PARISH)).effects.shouldBeEmpty()

            withClue("a repeated view must not restart a read in flight") {
                reduceMaps(inFlight, MapsMessage.MapSelected(PARISH)).state shouldBe inFlight
                reduceMaps(inFlight, MapsMessage.MapSelected(PARISH)).effects.shouldBeEmpty()
            }
        }
    }

    test("a loaded map clears the in-flight marker") {
        val contents = contentsFor(PARISH)
        val (state, _) = reduceMaps(
            CATALOGUED.copy(loadingMap = PARISH),
            MapsMessage.MapLoaded(contents)
        )

        assertSoftly {
            state.contents shouldBe contents
            state.loadingMap shouldBe null
            state.loading shouldBe false
        }
    }

    test("a read that finishes after the view moved on is ignored") {
        val movedOn = CATALOGUED.copy(contents = contentsFor(DEPTHS), loadingMap = null)

        val (state, _) = reduceMaps(movedOn, MapsMessage.MapLoaded(contentsFor(PARISH)))

        withClue("otherwise a late read replaces the map you just navigated to") {
            state.contents?.map shouldBe DEPTHS
        }
    }

    test("a stale read arriving while another is in flight is ignored too") {
        val loadingDepths = CATALOGUED.copy(loadingMap = DEPTHS)

        val (state, _) = reduceMaps(loadingDepths, MapsMessage.MapLoaded(contentsFor(PARISH)))

        assertSoftly {
            state.contents shouldBe null
            withClue("the read that was asked for is still the one being waited on") {
                state.loadingMap shouldBe DEPTHS
            }
        }
    }

    test("selecting a map with no installation open says so instead of reading") {
        val (state, effects) = reduceMaps(
            MapsState(installation = null, maps = listOf(PARISH)),
            MapsMessage.MapSelected(PARISH)
        )

        assertSoftly {
            state.problem shouldBe "No installation is open"
            state.loadingMap shouldBe null
            effects.shouldBeEmpty()
        }
    }

    test("a failed read stops the spinner and keeps the reason") {
        val (state, _) = reduceMaps(
            CATALOGUED.copy(loadingMap = PARISH),
            MapsMessage.Failed("unreadable")
        )

        assertSoftly {
            state.loading shouldBe false
            state.problem shouldBe "unreadable"
        }
    }

    test("the kind filter toggles both ways and the query is kept verbatim") {
        assertSoftly {
            val off = reduceMaps(CATALOGUED, MapsMessage.KindToggled(EntryKind.Part)).state
            (EntryKind.Part in off.kinds) shouldBe false

            val backOn = reduceMaps(off, MapsMessage.KindToggled(EntryKind.Part)).state
            (EntryKind.Part in backOn.kinds) shouldBe true

            withClue("trimming a query here would fight the user mid-word") {
                reduceMaps(CATALOGUED, MapsMessage.QueryChanged("  gargoyle ")).state.query shouldBe
                        "  gargoyle "
            }
        }
    }

    test("skipped maps are reported without disturbing a read in flight") {
        val (state, _) = reduceMaps(
            CATALOGUED.copy(loadingMap = PARISH),
            MapsMessage.EntitiesIndexed(EntityIndex.Empty, skipped = listOf(DEPTHS))
        )

        assertSoftly {
            state.problem shouldBe "No layout file for $DEPTHS, so entity search will not cover it"
            withClue("indexing is background work and must not cancel the map being read") {
                state.loadingMap shouldBe PARISH
            }
        }
    }

    test("opening a workspace discards whatever the last one had read") {
        val (state, effects) = reduceMaps(
            CATALOGUED.copy(contents = contentsFor(PARISH), problem = "stale"),
            MapsMessage.Opened(INSTALLATION)
        )

        assertSoftly {
            state.contents shouldBe null
            state.problem shouldBe null
            state.maps.shouldBeEmpty()
            effects shouldContainExactly listOf(MapsEffect.LoadCatalog(INSTALLATION))
        }
    }
})
