package com.sappyoak.dsanalyzer.runtime.write.debugdraw.dsr

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.dsr.DSRHookSites
import com.sappyoak.dsanalyzer.runtime.dsr.Graphics
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate.Hole
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey

private const val GRAPHICS = "graphics"
private const val EMIT = "emit"
private const val LAYER = "layer"
private const val SCENE = "scene"

/**
 * Adds EzDraw layer 0's scene to the world plan's first pass, where the game's own scene was just
 * emitted, so the debug draws share its camera and depth. The builder runs on every load screen,
 * so a plan already built is unchanged until the next one.
 *
 * RDI is the plan stream and ESI the plan's handle; both survive the call. [emitScene] is the
 * builder's own `emit(stream, plan, scene)`
 */
internal class SceneInsert(private val graphics: Address, private val emitScene: Address) : TemplateCave(
    WriteKey("debugdraw:scene-insert"),
    DSRHookSites.WorldPlanSceneEnd,
    TEMPLATE
) {
    override fun values(at: Address): Map<String, Long> = mapOf(
        GRAPHICS to graphics.value,
        EMIT to emitScene.value,
        LAYER to Graphics.EzDrawZero.toLong(),
        SCENE to Graphics.EzDrawScene.toLong()
    )

    private companion object {
        val TEMPLATE = CaveTemplate(
            CaveTemplate.hex(
                """
                48 A1 00 00 00 00 00 00 00 00
                48 85 C0
                0F 84 1B 00 00 00
                48 8B 40 00
                48 85 C0
                0F 84 0E 00 00 00
                44 8B 40 00
                8B D6
                48 8B CF
                E8 00 00 00 00
                44 8B C6
                BA 16 00 00 00
                E9 00 00 00 00
                """
            ),
            mapOf(
                GRAPHICS to Hole.Abs64(2),
                LAYER to Hole.Disp8(22),
                SCENE to Hole.Disp8(35),
                EMIT to Hole.Rel32(42),
                BACK to Hole.Rel32(55)
            )
        )
    }
}
