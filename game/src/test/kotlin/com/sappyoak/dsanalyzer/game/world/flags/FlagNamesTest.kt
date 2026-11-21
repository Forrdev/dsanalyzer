package com.sappyoak.dsanalyzer.game.world.flags

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.ref.DataAddress
import com.sappyoak.dsanalyzer.game.ref.scriptEvent

private val SOURCE = DataAddress.scriptEvent(ScriptId.Common, 50)

private fun evidence(kind: FlagKind, direct: Boolean, label: String = kind.label) =
    FlagEvidence(kind, label, SOURCE, direct)

class FlagNamesTest : FunSpec({
    test("direct evidence leads, then a telling trigger, then the event's name, then a generic trigger") {
        val names = FlagNames.of(
            listOf(
                evidence(FlagKind.Region, direct = false),
                evidence(FlagKind.EventState, direct = false),
                evidence(FlagKind.CharacterDead, direct = false),
                evidence(FlagKind.ObjectState, direct = true)
            ).map { 100 namedBy it }
        )

        names[100]!!.evidence.map { it.kind } shouldContainExactly
                listOf(FlagKind.ObjectState, FlagKind.CharacterDead, FlagKind.EventState, FlagKind.Region)
    }

    test("repeated evidence is kept once, and coverage counts each flag by its best kind") {
        val names = FlagNames.of(
            listOf(
                1 namedBy evidence(FlagKind.ItemPickup, direct = true),
                1 namedBy evidence(FlagKind.ItemPickup, direct = true),
                2 namedBy evidence(FlagKind.EventState, direct = false)
            )
        )

        assertSoftly {
            names[1]!!.evidence.size shouldBe 1
            names.coverage() shouldBe mapOf(FlagKind.ItemPickup to 1, FlagKind.EventState to 1)
        }
    }

    test("a direct reading of another kind is reported as a conflict") {
        val name = FlagNames.of(
            listOf(
                7 namedBy evidence(FlagKind.ItemPickup, direct = true),
                7 namedBy evidence(FlagKind.ShopStock, direct = true),
                7 namedBy evidence(FlagKind.Region, direct = false)
            )
        )[7]!!

        name.conflicts.map { it.kind } shouldContainExactly listOf(FlagKind.ShopStock)
    }
})
