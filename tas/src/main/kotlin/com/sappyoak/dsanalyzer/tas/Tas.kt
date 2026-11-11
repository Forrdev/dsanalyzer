package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureScan
import com.sappyoak.dsanalyzer.native.memory.resolve
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.inject.HookResult
import com.sappyoak.dsanalyzer.runtime.inject.Mem
import com.sappyoak.dsanalyzer.runtime.inject.installHook
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.ptde.FollowCam
import com.sappyoak.dsanalyzer.runtime.ptde.InputMan
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEHookSites
import com.sappyoak.dsanalyzer.runtime.ptde.PTDESignatures
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.tas.rng.RngTools
import com.sappyoak.dsanalyzer.native.memory.intAt

/** Each sync point as a chain the gate can watch: the follow cam resolving is "a world is loaded" */
private val SYNC_CHAINS: Map<SyncPoint, GamePointer> = mapOf(SyncPoint.LoadFinished to FollowCam.Pointer)

/**
 * The frame gate and the pad override, sharing one mailbox
 *
 * Everything is placed through the session's writer, so the hooks are journaled with every other
 * change and a revert-all removes them
 */
public class Tas private constructor(
    public val gate: FrameGate,
    public val pad: PadOverride,
    public val rng: RngTools,
    /** Null when the load sites were not found; the rest of the tools work without it */
    public val loads: LoadSync?,
    private val syncPoints: Map<SyncPoint, GateTrigger>
) : AutoCloseable {
    public val runs: RunPlayer = RunPlayer(gate, pad, syncPoints, rng::pin)

    /** Holds the game the first frame [point] passes; false when it is not mapped */
    public fun pauseAt(point: SyncPoint): Boolean = syncPoints[point]?.let(gate::pauseWhen) ?: false

    /** Once per sample: the pad's ring first, then whatever the run does with the result */
    public fun pump() {
        pad.pump()
        runs.pump()
    }

    /** The pad is handed back before the gate lets go, so no frame runs a half-stopped movie */
    override fun close() {
        loads?.disable()
        runs.stop()
        pad.close()
        gate.close()
    }

    public sealed interface Result {
        public data class Installed(public val tas: Tas) : Result
        public data class Unavailable(public val reason: String) : Result
    }

    public companion object {
        public fun install(
            edition: GameEdition,
            memory: ProcessMemory,
            module: AddressRange,
            writer: GameWriter
        ): Result {
            if (edition != GameEdition.PrepareToDie) return Result.Unavailable("TAS sites are only mapped for Prepare to Die")
            if (!writer.canPlaceCode) return Result.Unavailable("This platform cannot place code in the game")
            val sleep = memory.find(PTDESignatures.SleepImport, module) ?: return missing(PTDESignatures.SleepImport)
            val inputMan = memory.find(PTDESignatures.InputMan, module) ?: return missing(PTDESignatures.InputMan)
            val gameMan = memory.find(PTDESignatures.GameMan, module) ?: return missing(PTDESignatures.GameMan)
            val syncPoints = SYNC_CHAINS.mapNotNull { (point, chain) ->
                memory.find(chain.base, module)?.let { point to GateTrigger.present(it, chain.offsets.map(Long::toInt)) }
            }.toMap()
            val loadSites = memory.loadSites(module)
            val budget = memory.find(PTDESignatures.LoadBudgetWrite, module)
                ?.let { it + PTDEHookSites.LoadBudgetImmediate.toLong() }
            val mailbox = Mailbox.allocate(memory, writer)
                ?: return Result.Unavailable("No memory could be allocated in the game")

            val gateHook = when (val hooked = installHook(writer, memory, module, PTDEHookSites.GameLoopStep) {
                gate(mailbox, Mem.Absolute(sleep), Mem.Absolute(gameMan), loadSites.takeIf { budget != null })
            }) {
                is HookResult.Installed -> hooked.hook
                is HookResult.Failed -> {
                    writer.free(mailbox.base)
                    return Result.Unavailable(hooked.reason)
                }
            }
            return when (val hooked = installHook(writer, memory, module, PTDEHookSites.PadPolled) { padOverride(mailbox) }) {
                is HookResult.Installed -> {
                    val gate = FrameGate(mailbox, gateHook)
                    val pad = PadOverride(mailbox, hooked.hook) { memory.walk(inputMan, InputMan.PlayerPad) }
                    val loads = if (loadSites != null && budget != null) LoadSync(mailbox, writer, budget) else null
                    Result.Installed(Tas(gate, pad, RngTools(memory, writer, gate, mailbox, gameMan), loads, syncPoints))
                }
                // The gate's stub stays in place, and with it the mailbox it reads
                is HookResult.Failed -> {
                    gateHook.remove()
                    Result.Unavailable(hooked.reason)
                }
            }
        }
    }
}

private fun ProcessMemory.loadSites(module: AddressRange): LoadSites? {
    val fileMan = find(PTDESignatures.FileMan, module) ?: return null
    val poll = find(PTDESignatures.FileManPoll, module) ?: return null
    val rings = find(PTDESignatures.FileRings, module) ?: return null
    return LoadSites(fileMan, poll, rings)
}

private fun missing(signature: Signature): Tas.Result = Tas.Result.Unavailable("$signature not found")

private fun ProcessMemory.find(signature: Signature, module: AddressRange): Address? =
    (resolve(signature, module) as? SignatureScan.Resolved)?.address

/** Reads the pointer at [from], then follows each of [offsets] from it; null on any unreadable step */
private fun ProcessMemory.walk(from: Address, offsets: List<Int>): Int? {
    var value = intAt(from) ?: return null
    for (offset in offsets) value = intAt(Address(value.toLong() and 0xFFFFFFFFL) + offset.toLong()) ?: return null
    return value
}
