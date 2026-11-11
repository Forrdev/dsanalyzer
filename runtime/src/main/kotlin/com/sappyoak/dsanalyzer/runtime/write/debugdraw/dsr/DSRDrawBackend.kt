package com.sappyoak.dsanalyzer.runtime.write.debugdraw.dsr

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.SignatureScan
import com.sappyoak.dsanalyzer.native.memory.resolve
import com.sappyoak.dsanalyzer.runtime.dsr.DSRHookSites
import com.sappyoak.dsanalyzer.runtime.dsr.DSRSignatures
import com.sappyoak.dsanalyzer.runtime.dsr.Graphics
import com.sappyoak.dsanalyzer.runtime.dsr.HitColors
import com.sappyoak.dsanalyzer.runtime.dsr.WorldChrMan
import com.sappyoak.dsanalyzer.runtime.inject.SiteLookup
import com.sappyoak.dsanalyzer.runtime.inject.cave.callTarget
import com.sappyoak.dsanalyzer.runtime.inject.locate
import com.sappyoak.dsanalyzer.runtime.sampling.SessionContext
import com.sappyoak.dsanalyzer.runtime.session.DrawMode
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawBackend
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawControl
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawEdit
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawRoute

private const val CALL_SIZE = 5

/**
 * DSR's route to the screen: EzDraw layer 0's scene added to the world plan's first pass, the
 * flush's own depth bias and fill blend, the gates that pick parts and write their colors, and the
 * distance cull. Every cave goes in at once; with nothing tagged and an empty mask they draw nothing
 * the game would not.
 *
 * The world plan is built on a load screen, so the scene appears from the next one
 */
internal object DSRDrawBackend : DrawBackend {
    override fun route(context: SessionContext, control: Address): DrawRoute {
        val memory = context.memory.memory
        val graphics = context.memory.baseOf(Graphics.Pointer)
        val characters = context.memory.baseOf(WorldChrMan.Pointer)
        if (graphics.isNull || characters.isNull) return DrawRoute.Unavailable("the renderer or WorldChrMan was not found")

        val scene = when (val found = memory.locate(DSRHookSites.WorldPlanSceneEnd, context.module)) {
            is SiteLookup.Found -> found.address
            is SiteLookup.Missing -> return DrawRoute.Unavailable(found.reason)
        }
        val call = scene + DSRHookSites.SceneEmitCall.toLong()
        val emit = memory.read(call, CALL_SIZE)?.let { callTarget(call, it) }
            ?: return DrawRoute.Unavailable("the scene emitter's call could not be read")

        val colors = when (val found = memory.resolve(DSRSignatures.HitColors, context.module)) {
            is SignatureScan.Resolved -> found.address
            else -> return DrawRoute.Unavailable("the collision colors were not found: $found")
        }
        val span = AddressRange(colors + HitColors.HiFill.toLong(), HitColors.SPAN)
        val original = memory.read(span.start, span.size.toInt())
            ?: return DrawRoute.Unavailable("the collision colors could not be read")

        val state = control + DrawControl.Edition.toLong()
        fun color(offset: Int) = colors + offset.toLong()
        return DrawRoute.Ready(
            // Journals the game's colors, which the gates overwrite, so uninstalling puts them back
            edits = listOf(DrawEdit(span.start, original)),
            caves = listOf(
                SceneInsert(graphics, emit),
                WireRaster(state),
                FillRaster(state),
                FillDrawn(state),
                CollisionGate(DSRHookSites.LoDisplay, DrawMode.Lo, control, color(HitColors.LoWire), color(HitColors.LoFill), characters),
                CollisionGate(DSRHookSites.HiDisplay, DrawMode.Hi, control, color(HitColors.HiWire), color(HitColors.HiFill), characters),
                DistanceCull(control)
            ),
            writable = listOf(span)
        )
    }
}
