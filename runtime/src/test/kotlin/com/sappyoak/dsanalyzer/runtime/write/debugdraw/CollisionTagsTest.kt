package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.ptde.HitIns
import com.sappyoak.dsanalyzer.runtime.ptde.MapPart
import com.sappyoak.dsanalyzer.runtime.ptde.MapPartType
import com.sappyoak.dsanalyzer.runtime.ptde.MapParts
import com.sappyoak.dsanalyzer.runtime.ptde.MsbResCap
import com.sappyoak.dsanalyzer.runtime.ptde.PTDELayout
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter

private const val MANAGER = 0x5000
private const val AREAS = 0x5100
private const val GROUPS = 0x5200
private const val INSTANCES = 0x6000
private const val MSB = 0x7000
private const val PARTS = 0x7100
private const val PART_DATA = 0x8000
private const val PART_STRIDE = 0x100

/** One area, one group, three instances placed from one MSB whose parts mix collision and enemies */
private fun world(): FakeGame = FakeGame().apply {
    int(MANAGER + 0x18, 1)
    pointer(MANAGER + 0x1C, AREAS)
    int(AREAS + 0x08, 1)
    pointer(AREAS + 0x0C, GROUPS)
    int(GROUPS + 0x3C, 3)
    pointer(GROUPS + 0x40, INSTANCES)
    for (i in 0 until 3) {
        val at = INSTANCES + i * HitIns.SIZE
        int(at + HitIns.PartIndex, 0x00100000 or i)
        pointer(at + HitIns.Msb, MSB)
    }

    pointer(MSB + MsbResCap.PartsList, PARTS)
    // (type, index in type, filter); the last collision part's filter does not fit the tag
    val parts = listOf(Triple(MapPartType.Collision, 0, 8), Triple(MapPartType.Enemy, 0, 13), Triple(MapPartType.Collision, 1, 13), Triple(MapPartType.Collision, 2, 40))
    int(PARTS + MapParts.LengthAt, parts.size + 1)
    parts.forEachIndexed { i, (type, index, filter) ->
        val part = PART_DATA + i * PART_STRIDE
        int(part + MapPart.Type, type)
        int(part + MapPart.IndexInType, index)
        pointer(part + MapPart.TypeParams, part + 0xF0)
        write(part + 0xF0, filter)
        pointer(PARTS + MapParts.FirstSlot + i * 4, part)
    }
}

private fun FakeGame.tagOf(instance: Int): Int =
    read(address(INSTANCES + instance * HitIns.SIZE + HitIns.Tag), 1)!![0].toInt() and 0xFF

@OptIn(GameMutation::class)
class CollisionTagsTest : FunSpec({
    test("each collision instance is tagged with its own part's filter; one that does not fit is left alone") {
        val game = world()
        CollisionTags(checkNotNull(PTDELayout.debugDraw)).tag(game, GameWriter(game, null), game.address(MANAGER)) shouldBe 2
        (0 until 3).map(game::tagOf) shouldBe listOf(0x88, 0x8D, 0x00)
    }

    test("clearing puts every tag back to zero") {
        val game = world()
        val tags = CollisionTags(checkNotNull(PTDELayout.debugDraw))
        tags.tag(game, GameWriter(game, null), game.address(MANAGER))
        tags.clear(game, GameWriter(game, null), game.address(MANAGER)) shouldBe 3
        (0 until 3).map(game::tagOf) shouldBe listOf(0, 0, 0)
    }
})
