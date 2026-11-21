package com.sappyoak.dsanalyzer.runtime.write

import io.github.oshai.kotlinlogging.KotlinLogging

import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.inject.SiteLookup
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.inject.CodeCave
import com.sappyoak.dsanalyzer.runtime.inject.locate

private val logger = KotlinLogging.logger {  }

/** One page holds every cave of a set */
public const val CAVE_BLOCK_SIZE: Int = 0x1000

public sealed interface CaveResult {
    /** Each cave's jump is journaled on the writer under the cave's own key */
    public data object Installed : CaveResult
    public data class Failed(public val reason: String) : CaveResult
}


/**
 * Installs [caves] as one unit: every site is located and checked first, every cave is assembled
 * into one fresh block, and every jump goes in under a single suspension. Nothing is patched unless
 * all of it can be.
 *
 * The block is never freed. Once its jumps are reverted a game thread may still be inside it, or
 * on its way back to it from a call it made, and nothing can tell when the last one has left
 */
@GameMutation
public fun GameWriter.installCaves(memory: ProcessMemory, module: AddressRange, caves: List<CodeCave>): CaveResult =
    install(memory, module, caves).also { result ->
        val keys = caves.joinToString { it.key.name }
        when (result) {
            CaveResult.Installed -> logger.atInfo {
                message = "Code caves installed"
                payload = mapOf("caves" to keys)
            }
            is CaveResult.Failed -> logger.atWarn {
                message = "Code caves not installed"
                payload = mapOf("caves" to keys, "reason" to result.reason)
            }
        }
    }

@GameMutation
private fun GameWriter.install(
    memory: ProcessMemory,
    module: AddressRange,
    caves: List<CodeCave>
): CaveResult {
    val sites = caves.map { cave ->
        when (val lookup = reclaim(memory, module, cave)) {
            is SiteLookup.Found -> lookup.address
            is SiteLookup.Missing -> return CaveResult.Failed("${cave.key}: ${lookup.reason}")
        }
    }

    val block = allocate(CAVE_BLOCK_SIZE, module.start) ?: return CaveResult.Failed("No memory could be allocated in the game")
    val code = ArrayList<Byte>(CAVE_BLOCK_SIZE)
    val edits = caves.mapIndexed { i, cave ->
        code += CAVE_MARK.asList()
        val origin = block + code.size.toLong()
        code += cave.assemble(origin, sites[i]).asList()
        CodeEdit(cave.key, sites[i], cave.site.expected, X86.jumpPatch(sites[i], origin, cave.site.displaced))
    }
    check(code.size <= CAVE_BLOCK_SIZE) { "${caves.size} caves outgrew their block (${code.size} bytes)" }

    return when {
        !fill(block, code.toByteArray()) -> CaveResult.Failed("The caves could not be written")
        !patchCode(edits) -> CaveResult.Failed("The cave sites could not be patched")
        else -> CaveResult.Installed
    }
}

/** [cave]'s site, first putting back the original bytes where an earlier session's jump is still in place */
@GameMutation
private fun GameWriter.reclaim(memory: ProcessMemory, module: AddressRange, cave: CodeCave): SiteLookup {
    val lookup = memory.locate(cave.site, module)
    if (lookup is SiteLookup.Found) return lookup

    val left = memory.leftoverCave(cave.site, module) ?: return lookup
    if (!restoreCode(left, cave.site.expected)) return lookup
    logger.atInfo {
        message = "Removed a cave an earlier session left in the game"
        payload = mapOf("cave" to cave.key.name, "site" to left.toString())
    }
    return memory.locate(cave.site, module)
}
