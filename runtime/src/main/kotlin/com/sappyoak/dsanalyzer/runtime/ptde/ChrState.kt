package com.sappyoak.dsanalyzer.runtime.ptde

public object ChrState {
    public const val Kind: Int = 0x08

    public const val Flags: Int = 0x14
    public const val ParentPointer: Int = 0x1C
}

public object ChrStateFlags {
    public const val Dead: Int = 0x01
}
