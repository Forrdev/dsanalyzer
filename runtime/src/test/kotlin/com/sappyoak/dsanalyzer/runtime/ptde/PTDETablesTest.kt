package com.sappyoak.dsanalyzer.runtime.ptde

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape
import io.kotest.matchers.comparables.shouldBeGreaterThan

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
                    listOf("ChrIns", "ChrCtrl", "ChrPosData", "AnimData", "AnimRequestA", "AnimRequestB", "SpecialEffect", "MapData", "MapParts")
            throughCharacter.drop(1).forEach { it.offsets.take(3) shouldBe listOf(0L, 4L, 0L) }
            WorldCharacters.Pointer.offsets shouldBe listOf(0L)
        }
    }

    test("every table's inline element has room for the fields read out of it") {
        val elements = listOf(
            Triple("ActiveSpecialEffects", SpecialEffects.Active, ActiveEffect.Previous)
        )

        assertSoftly {
            elements.forEach { (name, table, last) ->
                val stride = (table.elements as TableElements.Inline).stride
                withClue("$name reads to $last but strides $stride") {
                    stride shouldBeGreaterThan last
                }
            }
        }
    }

    test("a map part is fetched with room for the last field it declares") {
        MAP_PART_SIZE shouldBeGreaterThan MapPart.ThinkParamId
    }

    test("a world block is strided with room for the fields read out of it") {
        assertSoftly {
            WORLD_BLOCK_SIZE shouldBeGreaterThan WorldBlock.MapDataPointer
            (WorldChrMan.Blocks.elements as TableElements.Inline).stride shouldBe WORLD_BLOCK_SIZE
        }
    }

    test("a block's parts are declared as a param list") {
        MapParts.All.shape.shouldBeInstanceOf<TableShape.ParamList>()
    }

    test("no two tables answer to the same name") {
        PTDETables.map { (_, table) -> table.name }.groupBy { it }
            .filterValues { it.size > 1 }.keys.shouldBeEmpty()
    }

    test("every table is owned by a pointer the connection scans for") {
        PTDETables.forEach { (owner, _) -> PTDEPointers shouldContain owner }
    }

    test("The animation channels are one layout at two displacements") {
        assertSoftly {
            AnimRequest.ChannelA.offsets shouldBe AnimRequest.ChannelB.offsets
            AnimRequest.ChannelA.size shouldBe AnimRequest.ChannelA.size
            AnimRequest.ChannelA.displacement shouldNotBe AnimRequest.ChannelB.displacement
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

    test("a requested map slot decodes to the map the player was standing in") {
        assertSoftly {
            WorldRes.mapOf(0x0A010000) shouldBe MapId.parse("m10_01_00_00")
            WorldRes.mapOf(NO_MAP) shouldBe null
        }
    }

    test("the two block nests are kept apart") {
        assertSoftly {
            WORLD_BLOCK_SIZE shouldNotBe WORLD_BLOCK_RES_SIZE
            WORLD_BLOCK_RES_SIZE shouldBeGreaterThan WorldBlockRes.PinLoaded
            (WorldRes.Blocks.elements as TableElements.Inline).stride shouldBe WORLD_BLOCK_RES_SIZE
        }
    }

    test("the load queue's slots sit inside the bytes its pointer fetches") {
        assertSoftly {
            WorldRes.Pointer.size shouldBeGreaterThan WorldRes.WarpTarget
            LoadStage.isPending(LoadStage.Idle) shouldBe false
            LoadStage.isPending(LoadStage.Loaded) shouldBe false
            LoadStage.isPending(LoadStage.Loaded) shouldBe true
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