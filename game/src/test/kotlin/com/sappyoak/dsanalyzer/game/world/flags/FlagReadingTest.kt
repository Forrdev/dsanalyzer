package com.sappyoak.dsanalyzer.game.world.flags

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.ref.DataAddress
import com.sappyoak.dsanalyzer.game.ref.map

private const val FLAG = 11010700
private val MAP = DataAddress.map(MapId.of(10, 1))
private val PICKUP = FlagEvidence(FlagKind.ItemPickup, "Picked up a ring", MAP, direct = true)
private val EVENT = FlagEvidence(FlagKind.EventState, "Some event", MAP, direct = false)
private val SITE = FlagSite(FlagAccess.Writes, "turns it on", FlagSource.World(MAP))

class FlagReadingTest : FunSpec({
    test("every origin's readings are ranked together, and a site two origins found is listed once") {
        val reading = FlagReading.of(
            listOf(
                OriginReading(names = listOf(FLAG namedBy EVENT), sites = listOf(FLAG to SITE)),
                OriginReading(names = listOf(FLAG namedBy PICKUP), sites = listOf(FLAG to SITE)),
                OriginReading()
            )
        )

        assertSoftly {
            reading.names[FLAG]?.best shouldBe PICKUP
            reading.names[FLAG]?.evidence?.size shouldBe 2
            reading.sites[FLAG] shouldBe listOf(SITE)
        }
    }

    test("a direct reading is also the place the game sets its flag") {
        val reading = listOf(FLAG namedBy PICKUP).withSites()

        reading.sites shouldBe listOf(FLAG to FlagSite(FlagAccess.Writes, "Picked up a ring", FlagSource.World(MAP)))
    }
})
