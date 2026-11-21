package com.sappyoak.dsanalyzer.runtime.layout

public enum class CallRegister(internal val arch: CallArch, internal val offset: Int) {
    Eax(CallArch.X86, 0x18),
    Ecx(CallArch.X86, 0x20),
    Edx(CallArch.X86, 0x28),
    Ebx(CallArch.X86, 0x30),
    Esi(CallArch.X86, 0x38),
    Edi(CallArch.X86, 0x40),
    Rcx(CallArch.X64, 0x18),
    Rdx(CallArch.X64, 0x20),
    R8(CallArch.X64, 0x28),
    R9(CallArch.X64, 0x30)
}
