package com.sappyoak.dsanalyzer.runtime.ptde

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.world.maps.MapId

/** The last field each structure declares, which its size has to reach past */
private val LAST_FIELDS = listOf(
    Triple("ChrIns", ChrIns.Pointer, ChrIns.StoredItem),
    Triple("ChrCtrl", ChrCtrl.Pointer, ChrCtrl.NpcParamPairPointer),
    Triple("ChrPosData", ChrPosData.Pointer, ChrPosData.Position + 8),
    Triple("AnimData", AnimData.Pointer, AnimData.PlaySpeed),
    Triple("AnimRequestA", AnimRequest.ChannelA, AnimRequest.Times + 8),
    Triple("AnimRequestB", AnimRequest.ChannelB, AnimRequest.Times + 8),
    Triple("CharData2", PlayerStats.Pointer, PlayerStats.Stance),
    Triple("WorldState", WorldState.Pointer, WorldState.StableAngle),
    Triple("WorldArea", WorldArea.Pointer, WorldArea.World),
    Triple("DeathCam", DeathCam.Pointer, DeathCam.Active),
    Triple("GameDataMan", GameDataMan.Pointer, GameDataMan.InGameTimeMillis)
)

class PTDETablesTest : FunSpec({
    test("every structure is fetched with room for the last field it declares") {
        assertSoftly {
            LAST_FIELDS.forEach { (name, pointer, last) ->
                withClue("$name reads to $last but fetches ${pointer.size}") {
                    pointer.size shouldBeGreaterThan last
                }
            }
        }
    }

    test("the structures reached through the character share its scan") {
        val throughCharacter = PTDEPointers.filter { it.base === ChrIns.Base }

        assertSoftly {
            throughCharacter.map { it.name } shouldBe
                    listOf("ChrIns", "ChrCtrl", "ChrPosData", "AnimData", "AnimRequestA", "AnimRequestB")
            throughCharacter.forEach { it.offsets.take(3) shouldBe listOf(0L, 4L, 0L) }
        }
    }

    test("The animation channels are one layout at two displacements") {
        assertSoftly {
            AnimRequest.ChannelA.offsets shouldBe AnimRequest.ChannelB.offsets
            AnimRequest.ChannelA.size shouldBe AnimRequest.ChannelA.size
            AnimRequest.ChannelA.displacement shouldBe AnimRequest.ChannelB.displacement
        }
    }

    test("no pointer but the animation channels displaces") {
        PTDEPointers.filter { it.displacement != 0L }.map { it.name } shouldBe listOf("AnimRequestA", "AnimRequestB")
    }

    test("no two pointers answer to the same name") {
        PTDEPointers.groupBy { it.name }.filterValues { it.size > 1 }.keys.shouldBeEmpty()
    }

    test("every pointer walks at least one offset from where its signature landed") {
        assertSoftly {
            PTDEPointers.forEach { withClue(it.name) { it.offsets.shouldBeGreaterThanZero() } }
        }
    }

    test("the two map bytes read as the map id the rest of the tool uses") {
        WorldArea.mapId(world = 10, area = 2) shouldBe checkNotNull(MapId.parse("m10_02_00_00"))
    }

    test("a build is named by its version word, and an unknown one is not guessed at") {
        assertSoftly {
            PTDEBuild.of(0xFC293654u) shouldBe PTDEBuild.Steam
            PTDEBuild.of(0xCE9634B4u) shouldBe PTDEBuild.Debug
            PTDEBuild.of(0u) shouldBe null
        }
    }
})

private fun List<Long>.shouldBeGreaterThanZero() {
    size shouldBeGreaterThan 0
}