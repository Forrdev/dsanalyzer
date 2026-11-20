package com.sappyoak.dsanalyzer.app.data.flags

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagTarget
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagUse
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.world.flags.FlagEvidence
import com.sappyoak.dsanalyzer.game.world.flags.FlagKind
import com.sappyoak.dsanalyzer.game.world.flags.FlagNames
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.index.FlagIndex
import com.sappyoak.dsanalyzer.game.world.scripts.index.FoundFlagUse

private const val BOSS = 14
private const val DOOR = 11010700
private const val READ_ONLY = 11010800
private const val PICKUP = 50000520

private fun named(flagId: Int, kind: FlagKind) =
    flagId to FlagEvidence(kind, kind.label, WorldRef.ScriptEvent(ScriptId.Common, 0), direct = true)

private val NAMES = FlagNames.of(
    listOf(named(BOSS, FlagKind.BossDefeated), named(DOOR, FlagKind.ObjectState), named(PICKUP, FlagKind.ItemPickup))
)

private val INDEX = FlagIndex.of(
    listOf(FoundFlagUse(ScriptId.Common, 0, FlagUse(FlagTarget.One(READ_ONLY), FlagAccess.Reads, "IF Event Flag"))),
    scripts = 1
)

class FlagCatalogTest : FunSpec({
    test("every flag the scripts use or the data names is listed once, by kind, by map and unnamed") {
        val catalog = FlagCatalog.of(NAMES, INDEX)
        val undeadBurg = FlagGroup.Map(MapId.of(10, 1))

        assertSoftly {
            catalog[FlagGroup.All] shouldContainExactly listOf(BOSS, DOOR, READ_ONLY, PICKUP)
            catalog[FlagGroup.Kind(FlagKind.ObjectState)] shouldContainExactly listOf(DOOR)
            catalog[FlagGroup.Unnamed] shouldContainExactly listOf(READ_ONLY)
            catalog[undeadBurg] shouldContainExactly listOf(DOOR, READ_ONLY)
        }
    }

    test("groups run all, kinds in order, unnamed, then maps, leaving empty ones out") {
        FlagCatalog.of(NAMES, INDEX).groups shouldContainExactly listOf(
            FlagGroup.All,
            FlagGroup.Kind(FlagKind.ItemPickup),
            FlagGroup.Kind(FlagKind.ObjectState),
            FlagGroup.Kind(FlagKind.BossDefeated),
            FlagGroup.Unnamed,
            FlagGroup.Map(MapId.of(10, 1))
        )
    }

    test("only ids in the map range carry a map") {
        assertSoftly {
            mapOfFlag(DOOR) shouldBe MapId.of(10, 1)
            mapOfFlag(11_510_000) shouldBe MapId.of(15, 1)
            mapOfFlag(BOSS) shouldBe null
            mapOfFlag(PICKUP) shouldBe null
        }
    }
})
