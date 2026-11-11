package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object DrawHandles {
    public const val RetargetPlan: Int = 0x80000048.toInt()
    public const val LayerOneScene: Int = 0x80000049.toInt()
    public const val LayerZeroScene: Int = 0x80000046.toInt()
    public const val LayerZeroCamera: Int = 0x80000047.toInt()
    public const val SceneSurface: Int = 0x8000000C.toInt()
    public const val SceneDepth: Int = 0x80000010.toInt()

    /** The low 24 bits of a handle index the registry */
    public const val INDEX_MASK: Int = 0xFFFFFF
}

/** A command in an 'HGDrawPlanEntity': vtable, opcode, two arguments */
public object PlanCommand {
    public const val BeginSurface: Int = 0x10002
    public const val EndSurface: Int = 0x20002
    public const val Viewport: Int = 0x30014
    public const val Scissor: Int = 0x30017
    public const val BeginCamera: Int = 0x10004
    public const val CullingCamera: Int = 0x3000B
    public const val AddPlan: Int = 0x30001

    public const val Type: Int = 0x04
    public const val First: Int = 0x08
    public const val Second: Int = 0x0C
    public const val SIZE: Int = 0x10
}

public object PlanWalker {
    public const val Registry: Int = 0x04
    public const val Stack: Int = 0x0C
    public const val ENTRY_SIZE: Int = 12
}

/** The renderer global's object */
public object Graphics {
    public val Pointer: GamePointer = GamePointer(
        name = "Graphics",
        base = PTDESignatures.GraphicsMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Process,
        size = 0x58
    )

    /** Points at the object whose first field is the draw registry */
    public const val Registry: Int = 0x08
    public const val EzDrawZero: Int = 0x50
    public const val EzDrawOne: Int = 0x54

    /** In the registry: the slot table's holder, with the table at [Table] and its capacity at [Capacity] */
    public const val Slots: Int = 0x18
    public const val Table: Int = 0x04
    public const val Capacity: Int = 0x0C

    /** In a plan entity: begin and end of its command pointers */
    public const val CommandsBegin: Int = 0x24
    public const val CommandsEnd: Int = 0x28

    /** In an EzDraw layer: the scene and camera handles it draws through */
    public const val EzDrawScene: Int = 0x04
    public const val EzDrawCamera: Int = 0x08
}
