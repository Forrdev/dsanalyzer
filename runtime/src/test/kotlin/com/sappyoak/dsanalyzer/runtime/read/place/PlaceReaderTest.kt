package com.sappyoak.dsanalyzer.runtime.read.place

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.dsr.DSRVersion
import com.sappyoak.dsanalyzer.runtime.dsr.NO_MAP
import com.sappyoak.dsanalyzer.runtime.dsr.WorldInfoOwner
import com.sappyoak.dsanalyzer.runtime.dsr.dsrLayout
import com.sappyoak.dsanalyzer.runtime.layout.PlaceLayout
import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers
import com.sappyoak.dsanalyzer.runtime.ptde.WorldArea
import com.sappyoak.dsanalyzer.runtime.ptde.ptdeLayout
import com.sappyoak.dsanalyzer.runtime.sampling.plantSignatures
import com.sappyoak.dsanalyzer.runtime.snapshot.WorldPlace

private const val SIGNATURES_AT = 0x1000
private const val STATICS_AT = 0x2000
private const val STRUCT_AT = 0x3000

private val PTDE = ptdeLayout().place

/** The one pointer both layouts are read through here, since the fake process is 32 bit, large enough for either */
private val STRUCT = WorldArea.Pointer.run { GamePointer(name, base, offsets, lifetime, size = 0x1000) }

/** DSR's offsets, read through PTDE's static */
private val DSR = dsrLayout(DSRVersion.V1_03_1).place.copy(pointer = STRUCT)

private fun place(layout: PlaceLayout, fill: FakeGame.() -> Unit): WorldPlace {
    val game = FakeGame().apply {
        val static = plantSignatures(listOf(WorldArea.Pointer.base), SIGNATURES_AT, STATICS_AT).values.single()
        pointer(static, STRUCT_AT)
        fill()
    }
    val reader = PlaceReader(layout)
    reader.refresh(GameMemory(game, game.resolvePointers(listOf(layout.pointer), game.module)))
    return reader.place()
}

class PlaceReaderTest : FunSpec({
    test("PTDE's two bytes are the area then the block, so 10 and 2 is m10_02") {
        place(PTDE) {
            write(STRUCT_AT + WorldArea.World, 10)
            write(STRUCT_AT + WorldArea.Area, 2)
        } shouldBe WorldPlace.InWorld(checkNotNull(MapId.parse("m10_02_00_00")))
    }

    test("DSR's map dword keeps the area in its top byte and the block below it") {
        place(DSR) { int(STRUCT_AT + WorldInfoOwner.CurrentMap, 0x0A020000) } shouldBe
                WorldPlace.InWorld(checkNotNull(MapId.parse("m10_02_00_00")))
    }

    test("DSR's empty map dword is no world rather than a map") {
        place(DSR) { int(STRUCT_AT + WorldInfoOwner.CurrentMap, NO_MAP) } shouldBe WorldPlace.NoWorld
    }

    test("a map the game data lacks is still loading") {
        place(PTDE) {
            write(STRUCT_AT + WorldArea.World, 99)
            write(STRUCT_AT + WorldArea.Area, 9)
        } shouldBe WorldPlace.Loading(MapId.of(area = 99, block = 9))
    }
})
