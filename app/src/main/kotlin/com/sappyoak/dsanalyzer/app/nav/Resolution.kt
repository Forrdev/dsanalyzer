package com.sappyoak.dsanalyzer.app.nav

import com.sappyoak.dsanalyzer.game.ref.Address

public sealed interface Resolution {
    public data class Resolved(public val view: View) : Resolution

    public data class NoView(public val address: Address) : Resolution

    public data class Absent(
        public val address: Address,
        public val missing: String
    ) : Resolution

    public data class Malformed(public val text: String) : Resolution
}
