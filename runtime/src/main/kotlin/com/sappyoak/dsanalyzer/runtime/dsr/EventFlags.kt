package com.sappyoak.dsanalyzer.runtime.dsr

public object EventFlags {
    /** A byte. Both the resolver and the setter return early while it is clear */
    public const val Ready: Int = 0x224
    /** Holds a pointer to the group array, which lives inline at `mgr + 0x28` */
    public const val GroupTable: Int = 0x218
    public const val GroupStride: Int = 0x18
    public const val AreaStride: Int = 0xA8
    public const val SectionStride: Int = 0x10
    public const val SectionsPerArea: Int = 10
    public const val BitArrayBytes: Int = 0x80
}