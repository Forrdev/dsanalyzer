package com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.ptde.DrawHandles
import com.sappyoak.dsanalyzer.runtime.ptde.Graphics
import com.sappyoak.dsanalyzer.runtime.ptde.PlanCommand
import com.sappyoak.dsanalyzer.shared.binary.toInts

/** The registry has room for this many entities in practice; a larger capacity is a misread */
private const val CAPACITY_LIMIT = 0x10000
private const val COMMAND_LIMIT = 1024

/** One dword the overlay writes into the game's draw setup */
internal class PlanEdit(val address: Address, val value: Int)

/**
 * What reaching the screen needs, resolved from the plans' structure every launch: the main plan's
 * handles change between launches. [edits] retarget layer 1's plan onto the scene surface and
 * camera, and route EzDraw layer 0 through layer 1's scene
 */
internal class DrawPlan(val postChain: Int, val edits: List<PlanEdit>)

private class Command(val address: Address, val type: Int, val first: Int, val second: Int)

/** Reads the draw plans hanging off the renderer global's object [graphics] */
internal class PTDEDrawPlanReader(private val memory: ProcessMemory) {
    fun read(graphics: Address): DrawPlan? {
        val slots = pointer(pointer(pointer(graphics + Graphics.Registry.toLong())) + Graphics.Slots.toLong())
        val table = pointer(slots + Graphics.Table.toLong())
        val capacity = int(slots + Graphics.Capacity.toLong())?.takeIf { it in 1..CAPACITY_LIMIT } ?: return null
        val entities = memory.read(table, capacity * Int.SIZE_BYTES)?.toInts() ?: return null

        val retarget = Address(entities.getOrNull(DrawHandles.RetargetPlan and DrawHandles.INDEX_MASK)?.toUInt()?.toLong() ?: 0)
        val planType = int(retarget) ?: return null
        val main = entities.asSequence()
            .map { Address(it.toUInt().toLong()) }
            .filter { !it.isNull && int(it) == planType }
            .firstNotNullOfOrNull { scenePass(commandsOf(it)) } ?: return null

        val layer = commandsOf(retarget)
        fun argOf(type: Int, value: Int) = layer.firstOrNull { it.type == type }?.let { PlanEdit(it.address + PlanCommand.First.toLong(), value) }
        val retargets = listOf(
            argOf(PlanCommand.BeginSurface, DrawHandles.SceneSurface),
            argOf(PlanCommand.Viewport, DrawHandles.SceneSurface),
            argOf(PlanCommand.Scissor, DrawHandles.SceneSurface),
            argOf(PlanCommand.BeginCamera, main.camera),
            argOf(PlanCommand.CullingCamera, main.culling)
        )
        if (retargets.any { it == null }) return null

        val zero = pointer(graphics + Graphics.EzDrawZero.toLong())
        val one = pointer(graphics + Graphics.EzDrawOne.toLong())
        if (zero.isNull || one.isNull) return null
        val routing = listOf(
            PlanEdit(zero + Graphics.EzDrawScene.toLong(), DrawHandles.LayerOneScene),
            PlanEdit(one + Graphics.EzDrawScene.toLong(), DrawHandles.LayerZeroScene),
            PlanEdit(one + Graphics.EzDrawCamera.toLong(), DrawHandles.LayerZeroCamera)
        )
        return DrawPlan(main.postChain, retargets.filterNotNull() + routing)
    }

    private class ScenePass(val camera: Int, val culling: Int, val postChain: Int)

    /**
     * The main plan's pass onto the scene surface: `BeginSurface(scene, depth)` ... `EndSurface`, then
     * an `AddPlan` of the post chain. Other plans target the same surface but are never followed by one
     */
    private fun scenePass(commands: List<Command>): ScenePass? {
        val begin = commands.indexOfFirst {
            it.type == PlanCommand.BeginSurface && it.first == DrawHandles.SceneSurface && it.second == DrawHandles.SceneDepth
        }
        if (begin < 0) return null
        val end = (begin until commands.size).firstOrNull { commands[it].type == PlanCommand.EndSurface } ?: return null
        val post = commands.getOrNull(end + 1)?.takeIf { it.type == PlanCommand.AddPlan } ?: return null
        val pass = commands.subList(begin, end)
        val camera = pass.firstOrNull { it.type == PlanCommand.BeginCamera } ?: return null
        val culling = pass.firstOrNull { it.type == PlanCommand.CullingCamera } ?: return null
        return ScenePass(camera.first, culling.first, post.first)
    }

    private fun commandsOf(plan: Address): List<Command> {
        val begin = pointer(plan + Graphics.CommandsBegin.toLong())
        val end = pointer(plan + Graphics.CommandsEnd.toLong())
        val count = ((end.value - begin.value) / Int.SIZE_BYTES).toInt()
        if (begin.isNull || count !in 1..COMMAND_LIMIT) return emptyList()
        val slots = memory.read(begin, count * Int.SIZE_BYTES)?.toInts() ?: return emptyList()
        return slots.asList().mapNotNull { slot ->
            val at = Address(slot.toUInt().toLong())
            memory.read(at, PlanCommand.SIZE)?.toInts()?.let {
                Command(at, it[PlanCommand.Type / 4], it[PlanCommand.First / 4], it[PlanCommand.Second / 4])
            }
        }
    }

    private fun int(at: Address): Int? = if (at.isNull) null else memory.read(at, Int.SIZE_BYTES)?.toInts()?.first()

    private fun pointer(at: Address): Address = Address(int(at)?.toUInt()?.toLong() ?: 0)
}
