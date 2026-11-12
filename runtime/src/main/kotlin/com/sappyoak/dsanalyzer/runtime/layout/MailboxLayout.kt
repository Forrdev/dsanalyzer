package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.PlayerGate
import com.sappyoak.dsanalyzer.runtime.inject.StepTableSite

public enum class CallArch { X86, X64; }

public sealed interface MailboxLayout {
    public val arch: CallArch

    public data class Code(public val site: HookSite, override val arch: CallArch = CallArch.X86) : MailboxLayout
    public data class StepTable(
        public val site: StepTableSite,
        public val gate: PlayerGate,
        override val arch: CallArch = CallArch.X64
    ) : MailboxLayout
}
