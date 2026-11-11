package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.native.memory.Address

/**
 * Code at [address] that must read [expected] before it is replaced with [bytes]. A mismatch means
 * another build, another tool's patch, or a stale address, and the edit is refused
 */
public class CodeEdit(
    public val key: WriteKey,
    public val address: Address,
    public val expected: ByteArray,
    public val bytes: ByteArray
) {
    init {
        require(bytes.isNotEmpty()) { "$key writes no bytes" }
        require(expected.size == bytes.size) { "$key expects ${expected.size} bytes but writes ${bytes.size}" }
    }
}
