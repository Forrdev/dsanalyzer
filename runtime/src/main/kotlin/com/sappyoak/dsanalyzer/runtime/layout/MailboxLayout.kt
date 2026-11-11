package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.inject.HookSite

public enum class CallArch { X86, X64; }

/** How the cave runs the instruction its jump replaced */
public sealed interface Displaced {
    /** Position-independent, so it runs unchanged */
    public data object Verbatim : Displaced
    /** `mov rax,[rip+disp32]`, which the cave rewrites as `mov rax,[abs64]` */
    public data object RipLoadRax : Displaced
}

/** The per-frame site on the game logic thread where the mailbox makes its calls */
public data class MailboxLayout(
    public val site: HookSite,
    public val displaced: Displaced,
    public val arch: CallArch
)
