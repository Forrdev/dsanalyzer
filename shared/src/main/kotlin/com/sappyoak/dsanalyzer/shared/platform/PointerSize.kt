package com.sappyoak.dsanalyzer.shared.platform

@JvmInline
public value class PointerSize(public val value: Int) {
    public override fun toString(): String = "$value"

    public val intPointer: Boolean get() = this == IntPointer
    public val longPointer: Boolean get() = this == LongPointer

    companion object {
        public val IntPointer: PointerSize = PointerSize(Int.SIZE_BYTES)
        public val LongPointer: PointerSize = PointerSize(Long.SIZE_BYTES)
    }
}

