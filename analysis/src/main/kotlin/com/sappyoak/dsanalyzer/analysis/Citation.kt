package com.sappyoak.dsanalyzer.analysis

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.game.ref.Address

/**
 * One thing a finding points at: an address, a label, and nothing about whether it resolves
 */
@Serializable
public data class Citation(
    public val text: String,
    public val label: String? = null
) {
    public companion object {
        public fun of(address: Address, label: String? = null): Citation =
            Citation(address.toAddress(), label)
    }
}
