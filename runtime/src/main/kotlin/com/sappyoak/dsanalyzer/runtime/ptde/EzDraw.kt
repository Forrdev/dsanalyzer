package com.sappyoak.dsanalyzer.runtime.ptde


public object EzDrawFlush {
    public val DevicePath: List<Int> = listOf(0x08, 0x84, 0x10)
}

/** 'IDirect3DDevice9' vtable slots */
public object D3D9Device {
    public const val SetRenderState: Int = 0xE4
    public const val GetRenderState: Int = 0xE8
}

public object HitResDraw {
    /** In the 'HitRes' (ESI): the 'HitIns' that owns it */
    public const val Owner: Int = 0x08

    /** In the EzDraw context (EBX): the state block, which the color setters find through EDI */
    public const val StateBlock: Int = 0x28

    public const val Wire: Int = 0x10
    public const val Fill: Int = 0x20
}

public object AddTriangleArgs {
    public const val V0: Int = 0x08
    public const val V1: Int = 0x0C
    public const val V2: Int = 0x10
}
