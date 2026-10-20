package com.sappyoak.dsanalyzer.runtime.dsr

public enum class DSRVersion(public val modSize: Int) {
    V1_01(0x4869400),
    V1_01_1(0x496BE00),
    V1_01_2(0x37CB400),
    V1_03(0x3817800),
    Unknown(-1);

    public companion object {
        public fun from(size: Int): DSRVersion = entries.firstOrNull { it.modSize == size } ?: Unknown
    }
}