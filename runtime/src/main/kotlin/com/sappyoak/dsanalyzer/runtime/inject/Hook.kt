package com.sappyoak.dsanalyzer.runtime.inject

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureScan
import com.sappyoak.dsanalyzer.native.memory.resolve
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.write.CodeEdit
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.WriteKey

/** One page holds any stub written so far */
private const val BLOCK_SIZE = 0x1000

public class HookSite(
    public val signature: Signature,
    public val offset: Int,
    public val expected: ByteArray
) {
    init {
        require(displaced >= JMP_SIZE) { "A hook displaces at least the $JMP_SIZE of its bytes" }
    }

    public val displaced: Int get() = expected.size
}

/** Bytes written as a listing reads: `code(0xE8, 0x57, ...)` */
public fun code(vararg values: Int): ByteArray = ByteArray(values.size) { values[it].toByte() }

public sealed interface SiteLookup {
    public data class Found(public val address: Address) : SiteLookup
    public data class Missing(public val reason: String) : SiteLookup
}



/** Where [site] is in [module], provided it still holds the bytes it is expected to */
public fun ProcessMemory.locate(site: HookSite, module: AddressRange): SiteLookup {
    val match = when (val scan = resolve(site.signature, module)) {
        is SignatureScan.Resolved -> scan.address
        else -> return SiteLookup.Missing("${site.signature} not found: $scan")
    }
    val at = match + site.offset.toLong()
    val found = read(at, site.displaced)
        ?: return SiteLookup.Missing("${site.signature}'s bytes could not be read")
    if (!found.contentEquals(site.expected)) {
        return SiteLookup.Missing("${site.signature} does not hold its expected bytes (another build, or already hooked)")
    }
    return SiteLookup.Found(at)
}

public class Hook internal constructor(
    private val writer: GameWriter,
    public val key: WriteKey,
    public val site: Address
) {
    public fun remove(): Boolean = writer.revert(key)
}

public sealed interface HookResult {
    public data class Installed(public val hook: Hook) : HookResult
    public data class Failed(public val reason: String) : HookResult
}

@GameMutation
public fun installHook(
    writer: GameWriter,
    memory: ProcessMemory,
    module: AddressRange,
    site: HookSite,
    body: X86.() -> Unit
): HookResult {
    val at = when (val lookup = memory.locate(site, module)) {
        is SiteLookup.Found -> lookup.address
        is SiteLookup.Missing -> return HookResult.Failed(lookup.reason)
    }

    val block = writer.allocate(BLOCK_SIZE) ?: return HookResult.Failed("No memory could be allocated in the game")
    val stub = X86(block).apply {
        body()
        raw(site.expected)
        jmp(at + site.displaced.toLong())
    }.assemble()
    check(stub.size <= BLOCK_SIZE) { "Stub for ${site.signature} outgrew its block" }

    val key = WriteKey("hook:${site.signature}")
    val jump = X86.jumpPatch(at, block, site.displaced)
    val failure = when {
        !writer.fill(block, stub) -> "The stub could not be written"
        !writer.patchCode(CodeEdit(key, at, site.expected, jump)) -> "${site.signature} could not be patched"
        else -> return HookResult.Installed(Hook(writer, key, at))
    }
    writer.free(block)
    return HookResult.Failed(failure)
}
