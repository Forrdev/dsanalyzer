package com.sappyoak.dsanalyzer.native.process

import com.sappyoak.dsanalyzer.native.memory.Address

public interface CodeMemory {
    public fun allocate(size: Int): Address?
    public fun free(address: Address): Boolean
    public fun patch(address: Address, bytes: ByteArray): Boolean
}
