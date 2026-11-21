package com.sappyoak.dsanalyzer.runtime.write.debugdraw.dsr

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.dsr.DSRHookSites
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate.Hole
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey

private const val STATE = "state"

/**
 * The flush's own D3D11 state, made once and kept in [DrawControl.Edition]:
 * - `+0x00` setup: 0 not yet, 1 made, 2 failed (the game's states are used)
 * - `+0x08` set while the fill's blend is bound, so the restore knows to run
 * - `+0x10` a zero blend factor, `+0x20` the blend state
 * - `+0x28`, `+0x58` stand-in wrappers: the flush reads only a wrapper's `+0x28`, which is the
 *   biased wire state at `+0x50` and fill state at `+0x80`
 *
 * The game caches the wrapper it last bound, so a stand-in it sees once is not bound again.
 * Without the bias the fill fights the map it was built from
 */
internal object DSRDrawState {
    const val SIZE: Int = 0x88
}

/**
 * At the wire draw's rasterizer load. The first time, it makes the two biased rasterizer states
 * from the game's own (DepthBias −100, slope −1) and a blend state from the one the game has bound
 * (source alpha over, destination alpha kept), on the device the context belongs to. After that it
 * hands the flush the wire stand-in
 */
internal class WireRaster(private val state: Address) : TemplateCave(
    WriteKey("debugdraw:wire-raster"),
    DSRHookSites.FlushWireRaster,
    TEMPLATE
) {
    override fun values(at: Address): Map<String, Long> = mapOf(STATE to state.value)

    private companion object {
        val TEMPLATE = CaveTemplate(
            CaveTemplate.hex(
                """
                48 8B 99 70 02 00 00 48 B8 00 00 00 00 00 00 00
                00 8B 10 83 FA 01 0F 84 69 01 00 00 85 D2 0F 85
                80 01 00 00 48 81 EC 80 01 00 00 48 89 4C 24 28
                48 89 44 24 30 48 C7 44 24 20 00 00 00 00 48 8B
                4E 20 48 8D 54 24 20 48 8B 01 FF 50 18 48 83 7C
                24 20 00 0F 84 39 01 00 00 48 8B 44 24 28 48 8B
                88 70 02 00 00 48 8B 49 28 48 8D 54 24 40 48 8B
                01 FF 50 38 C7 44 24 4C 9C FF FF FF C7 44 24 54
                00 00 80 BF 48 8B 4C 24 20 48 8D 54 24 40 4C 8B
                44 24 30 49 83 C0 50 48 8B 01 FF 90 B0 00 00 00
                48 8B 44 24 28 48 8B 88 78 02 00 00 48 8B 49 28
                48 8D 54 24 40 48 8B 01 FF 50 38 C7 44 24 4C 9C
                FF FF FF C7 44 24 54 00 00 80 BF 48 8B 4C 24 20
                48 8D 54 24 40 4C 8B 44 24 30 49 81 C0 80 00 00
                00 48 8B 01 FF 90 B0 00 00 00 48 8B 86 38 01 00
                00 48 85 C0 74 72 48 8B 48 28 48 85 C9 74 69 48
                8D 54 24 70 48 8B 01 FF 50 38 48 8D 44 24 78 B9
                08 00 00 00 C7 00 01 00 00 00 C7 40 04 05 00 00
                00 C7 40 08 06 00 00 00 C7 40 0C 01 00 00 00 C7
                40 10 01 00 00 00 C7 40 14 02 00 00 00 C7 40 18
                01 00 00 00 48 83 C0 20 FF C9 75 C8 48 8B 4C 24
                20 48 8D 54 24 70 4C 8B 44 24 30 49 83 C0 20 48
                8B 01 FF 90 A0 00 00 00 48 8B 4C 24 20 48 8B 01
                FF 50 10 48 8B 44 24 30 C7 00 01 00 00 00 48 81
                C4 80 01 00 00 48 83 78 50 00 74 18 48 8D 58 28
                EB 12 48 8B 44 24 30 C7 00 02 00 00 00 48 81 C4
                80 01 00 00 E9 00 00 00 00
                """
            ),
            mapOf(STATE to Hole.Abs64(9), BACK to Hole.Rel32(421))
        )
    }
}

/** At the fill draw's rasterizer load: the fill stand-in, and the blend state bound until [FillDrawn] */
internal class FillRaster(private val state: Address) : TemplateCave(
    WriteKey("debugdraw:fill-raster"),
    DSRHookSites.FlushFillRaster,
    TEMPLATE
) {
    override fun values(at: Address): Map<String, Long> = mapOf(STATE to state.value)

    private companion object {
        val TEMPLATE = CaveTemplate(
            CaveTemplate.hex(
                """
                48 8B 99 78 02 00 00 48 B8 00 00 00 00 00 00 00
                00 83 38 01 75 3D 48 83 B8 80 00 00 00 00 74 04
                48 8D 58 58 48 8B 50 20 48 85 D2 74 26 C7 40 08
                01 00 00 00 4C 8D 40 10 41 B9 FF FF FF 0F 48 8B
                4E 20 48 83 EC 20 48 8B 01 FF 90 18 01 00 00 48
                83 C4 20 E9 00 00 00 00
                """
            ),
            mapOf(STATE to Hole.Abs64(9), BACK to Hole.Rel32(84))
        )
    }
}

/**
 * After the fill draw: binds again the blend state the game believes is bound, the one in its cache,
 * so nothing after the flush sees ours
 */
internal class FillDrawn(private val state: Address) : TemplateCave(
    WriteKey("debugdraw:fill-drawn"),
    DSRHookSites.FlushFillDrawn,
    TEMPLATE
) {
    override fun values(at: Address): Map<String, Long> = mapOf(STATE to state.value)

    private companion object {
        val TEMPLATE = CaveTemplate(
            CaveTemplate.hex(
                """
                48 B8 00 00 00 00 00 00 00 00 83 78 08 00 74 38
                C7 40 08 00 00 00 00 4C 8D 40 10 31 D2 48 8B 8E
                38 01 00 00 48 85 C9 74 04 48 8B 51 28 41 B9 FF
                FF FF 0F 48 8B 4E 20 48 83 EC 20 48 8B 01 FF 90
                18 01 00 00 48 83 C4 20 4C 8B 47 20 48 8B 57 10
                E9 00 00 00 00
                """
            ),
            mapOf(STATE to Hole.Abs64(2), BACK to Hole.Rel32(81))
        )
    }
}
