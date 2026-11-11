package com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.ptde.Graphics
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEHookSites
import com.sappyoak.dsanalyzer.runtime.ptde.WorldChrMan
import com.sappyoak.dsanalyzer.runtime.read.debugdraw.PTDEDrawPlanReader
import com.sappyoak.dsanalyzer.runtime.sampling.SessionContext
import com.sappyoak.dsanalyzer.runtime.session.DrawMode
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawBackend
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawEdit
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawRoute
import com.sappyoak.dsanalyzer.shared.binary.toLittleEndian

/**
 * PTDE's route to the screen: layer 1's plan retargeted onto the scene and spliced in after the
 * post chain, EzDraw layer 0 routed through it, and caves for depth bias, which parts draw, their
 * colors and the draw distance
 */
internal object PTDEDrawBackend : DrawBackend {
    override fun route(context: SessionContext, control: Address): DrawRoute {
        val characters = context.memory.baseOf(WorldChrMan.Pointer)
        if (characters.isNull) return DrawRoute.Unavailable("WorldChrMan not found")
        val plan = PTDEDrawPlanReader(context.memory.memory).read(context.memory.addressOf(Graphics.Pointer))
            ?: return DrawRoute.Unavailable("the main draw plan could not be read")

        return DrawRoute.Ready(
            edits = plan.edits.map { DrawEdit(it.address, it.value.toLittleEndian()) },
            caves = listOf(
                PlanSplice(plan.postChain),
                DepthBiasOn(control),
                DepthBiasOff(control),
                CollisionGate(PTDEHookSites.LoDisplay, DrawMode.Lo, control, characters),
                CollisionGate(PTDEHookSites.HiDisplay, DrawMode.Hi, control, characters),
                FilterColor(PTDEHookSites.LoColor, "lo", control),
                FilterColor(PTDEHookSites.HiColor, "hi", control),
                DistanceCull(control)
            )
        )
    }
}
