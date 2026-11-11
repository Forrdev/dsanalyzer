package com.sappyoak.dsanalyzer.native.process

import com.sappyoak.dsanalyzer.native.memory.Address

public class CodePatch(public val address: Address, public val bytes: ByteArray) {
    init {
        require(bytes.isNotEmpty()) { "A patch writes at least one byte" }
    }

    public val end: Address get() = address + bytes.size.toLong()

    public operator fun contains(pointer: Long): Boolean = pointer >= address.value && pointer < end.value
}

public interface CodeMemory {
    public fun allocate(size: Int): Address?
    public fun free(address: Address): Boolean
    public fun patch(patches: List<CodePatch>): Boolean
}

public fun CodeMemory.patch(
    address: Address,
    bytes: ByteArray
): Boolean = patch(listOf(CodePatch(address, bytes)))
