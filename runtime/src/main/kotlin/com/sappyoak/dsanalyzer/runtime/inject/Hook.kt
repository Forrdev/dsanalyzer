package com.sappyoak.dsanalyzer.runtime.inject

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureScan
import com.sappyoak.dsanalyzer.native.memory.resolve
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.WriteKey

/** One page holds any stub written so far */
private const val BLOCK_SIZE = 0x1000

public class HookSite(
    public val signature: Signature,
    public val offset: Int,
    public val displaced: Int
) {
    init {
        require(displaced >= JMP_SIZE) { "A hook displaces at least the $JMP_SIZE of its bytes" }
    }
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
