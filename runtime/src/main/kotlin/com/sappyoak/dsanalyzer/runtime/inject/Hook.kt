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
    val match = when (val scan = memory.resolve(site.signature, module)) {
        is SignatureScan.Resolved -> scan.address
        else -> return HookResult.Failed("${site.signature} not found: $scan")
    }
    val at = match + site.offset.toLong()
    val found = memory.read(at, site.displaced)
        ?: return HookResult.Failed("${site.signature}'s bytes could not be read")
    if (!found.contentEquals(site.expected)) {
        return HookResult.Failed("${site.signature} does not hold its expected bytes (another build, or already hooked)")
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
