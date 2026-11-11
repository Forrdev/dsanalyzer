package com.sappyoak.dsanalyzer.runtime.write.debugdraw.dsr

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.dsr.DisplayTest
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate.Hole
import com.sappyoak.dsanalyzer.runtime.session.DrawMode
import com.sappyoak.dsanalyzer.runtime.write.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.bits

private const val CONTROL = "control"
private const val WIRE = "wire"
private const val FILL = "fill"
private const val CHARACTERS = "characters"
private const val BITS = "bits"
private const val MODE = "mode"
private const val DRAW = "draw"
private const val SKIP = "skip"

/**
 * Decides which parts draw at one mesh's [test] */
internal class CollisionGate(
    private val test: DisplayTest,
    private val mode: DrawMode,
    private val control: Address,
    private val wire: Address,
    private val fill: Address,
    /** The global holding 'WorldChrManImp' */
    private val characters: Address
) : TemplateCave(WriteKey("debugdraw:gate-${mode.name.lowercase()}"), test.site, TEMPLATE) {
    override fun values(at: Address): Map<String, Long> = mapOf(
        CONTROL to control.value,
        WIRE to wire.value,
        FILL to fill.value,
        CHARACTERS to characters.value,
        BITS to test.bits.toLong(),
        MODE to mode.bits.toLong(),
        DRAW to (at + test.draw.toLong()).value,
        SKIP to (at + test.skip.toLong()).value
    )

    private companion object {
        val TEMPLATE = CaveTemplate(
            CaveTemplate.hex(
                """
                0F B6 83 C1 00 00 00 48 B9 00 00 00 00 00 00 00
                00 89 C2 A9 80 00 00 00 75 05 BA 1F 00 00 00 83
                E2 1F C1 E2 05 0F 10 84 11 80 00 00 00 0F 10 8C
                11 90 00 00 00 49 B8 00 00 00 00 00 00 00 00 41
                0F 11 00 49 B8 00 00 00 00 00 00 00 00 41 0F 11
                08 BA 00 00 00 00 84 93 C0 00 00 00 75 19 A9 80
                00 00 00 74 56 BA 00 00 00 00 84 51 04 74 4C 83
                E0 1F 0F A3 01 73 44 48 B8 00 00 00 00 00 00 00
                00 48 8B 00 48 85 C0 74 2D 48 8B 40 68 48 85 C0
                74 24 48 8B 40 68 48 85 C0 74 1B 48 8B 40 28 48
                85 C0 74 12 8B 50 10 89 51 10 8B 50 14 89 51 14
                8B 50 18 89 51 18 E9 00 00 00 00 E9 00 00 00 00
                """
            ),
            mapOf(
                CONTROL to Hole.Abs64(9),
                WIRE to Hole.Abs64(55),
                FILL to Hole.Abs64(69),
                BITS to Hole.Imm32(82),
                MODE to Hole.Imm32(102),
                CHARACTERS to Hole.Abs64(121),
                DRAW to Hole.Rel32(183),
                SKIP to Hole.Rel32(188)
            )
        )
    }
}
