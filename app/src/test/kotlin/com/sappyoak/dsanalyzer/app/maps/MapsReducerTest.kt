package com.sappyoak.dsanalyzer.app.maps

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import java.nio.file.Path

import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.world.EntryKind
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.game.world.maps.MapId

private val INSTALLATION = Installation(
    id = InstallationId("ptde"),
    root = Path.of("game"),
    executable = Path.of("game", "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

private val DEPTHS = checkNotNull(MapId.parse("m10_00_00_00"))
private val PARISH = checkNotNull(MapId.parse("m10_02_00_00"))

private fun entry(map: MapId, index: Int, name: String, entityId: Int? = null) = EntrySummary(
    ref = WorldRef.Entry(map, EntryKind.Part, index),
    name = name,
    subtype = "Collision",
    entityId = entityId
)

private fun contents(map: MapId, vararg entries: EntrySummary) = MapContents(
    map = map,
    msb = MSB(emptyList(), emptyList(), emptyList(), emptyList()),
    entries = entries.toList(),
    collisionIndices = emptyList(),
    environmentIndices = emptyList()
)

private val PARISH_CONTENTS = contents(
    PARISH,
    entry(PARISH, 0, "h0000B1"),
    entry(PARISH, 1, "h0001B1", entityId = 1200)
)

private fun opened(): MapsState = reduceMaps(MapsState(), MapsMessage.Opened(INSTALLATION)).state

private fun loaded(): MapsState {
    val catalog = reduceMaps(opened(), MapsMessage.CatalogLoaded(listOf(PARISH, DEPTHS))).state
    return reduceMaps(catalog, MapsMessage.MapLoaded(PARISH_CONTENTS)).state
}

class MapsReducerTest : FunSpec({
    test("opening a workspace loads the catalog") {
        reduceMaps(MapsState(), MapsMessage.Opened(INSTALLATION)).effects shouldBe
                listOf(MapsEffect.LoadCatalog(INSTALLATION))
    }

    test("a loaded catalog selects the first map and indexes entities") {
        val transition = reduceMaps(opened(), MapsMessage.CatalogLoaded(listOf(PARISH, DEPTHS)))

        assertSoftly {
            transition.state.selected shouldBe PARISH
            transition.effects shouldBe listOf(
                MapsEffect.LoadMap(INSTALLATION, PARISH),
                MapsEffect.IndexEntities(INSTALLATION, listOf(PARISH, DEPTHS))
            )
        }
    }

    test("following a link in the loaded map focuses it without reloading") {
        val target = WorldRef.Entry(PARISH, EntryKind.Part, 1)
        val transition = reduceMaps(loaded(), MapsMessage.Navigated(target))

        assertSoftly {
            transition.state.focused shouldBe target
            transition.state.focusedEntry?.name shouldBe "h0001B1"
            transition.effects shouldBe emptyList()
        }
    }

    test("following a link into another map loads it and focuses once it arrives") {
        val target = WorldRef.Entry(DEPTHS, EntryKind.Region, 3)
        val navigated = reduceMaps(loaded(), MapsMessage.Navigated(target))
        val arrived = reduceMaps(navigated.state, MapsMessage.MapLoaded(contents(DEPTHS, entry(DEPTHS, 3, "zone"))))

        assertSoftly {
            navigated.effects shouldBe listOf(MapsEffect.LoadMap(INSTALLATION, DEPTHS))
            navigated.state.focused shouldBe null
            navigated.state.pending shouldBe target
            arrived.state.focused shouldBe target
            arrived.state.selected shouldBe DEPTHS
        }
    }

    test("back returns to where a link was followed from, and forward returns again") {
        val first = WorldRef.Entry(PARISH, EntryKind.Part, 0)
        val second = WorldRef.Entry(PARISH, EntryKind.Part, 1)
        val browsed = reduceMaps(
            reduceMaps(loaded(), MapsMessage.Navigated(first)).state,
            MapsMessage.Navigated(second)
        ).state

        val back = reduceMaps(browsed, MapsMessage.BackRequested).state
        val forward = reduceMaps(back, MapsMessage.ForwardRequested).state

        assertSoftly {
            back.focused shouldBe first
            back.canGoForward shouldBe true
            forward.focused shouldBe second
            forward.canGoBack shouldBe true
        }
    }

    test("an entity id resolves to the entry the index put it at") {
        val indexed = reduceMaps(
            loaded(),
            MapsMessage.EntitiesIndexed(EntityIndex.of(listOf(PARISH_CONTENTS)))
        ).state

        reduceMaps(indexed, MapsMessage.Navigated(WorldRef.Entity(1200))).state.focused shouldBe
                WorldRef.Entry(PARISH, EntryKind.Part, 1)
    }

    test("an entity id nothing carries is reported rather than ignored") {
        checkNotNull(reduceMaps(loaded(), MapsMessage.Navigated(WorldRef.Entity(9999))).state.problem)
            .shouldContain("9999")
    }

    test("a map with no layout is named rather than failing the whole index") {
        val missing = checkNotNull(MapId.parse("m99_00_00_00"))
        val indexed = reduceMaps(
            loaded(),
            MapsMessage.EntitiesIndexed(EntityIndex.of(listOf(PARISH_CONTENTS)), skipped = listOf(missing))
        ).state

        assertSoftly {
            indexed.entities[1200] shouldBe listOf(WorldRef.Entry(PARISH, EntryKind.Part, 1))
            checkNotNull(indexed.problem).shouldContain("m99_00_00_00")
        }
    }

    test("the query and kind filters narrow what the list shows") {
        val state = loaded()

        assertSoftly {
            reduceMaps(state, MapsMessage.QueryChanged("1200")).state.visible.map { it.name } shouldBe listOf("h0001B1")
            reduceMaps(state, MapsMessage.QueryChanged("h0000")).state.visible.map { it.name } shouldBe listOf("h0000B1")
            reduceMaps(state, MapsMessage.KindToggled(EntryKind.Part)).state.visible.shouldBeEmpty()
        }
    }

    test("the visible list is one instance per state, so composition can skip it") {
        val state = reduceMaps(loaded(), MapsMessage.Navigated(WorldRef.Entry(PARISH, EntryKind.Part, 1))).state

        assertSoftly {
            (state.visible === state.visible) shouldBe true
            (state.focusedEntry === state.focusedEntry) shouldBe true
            state.visible shouldNotBeSameInstanceAs
                    reduceMaps(state, MapsMessage.QueryChanged("h0001")).state.visible
        }
    }
})