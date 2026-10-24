package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/**
 * The Bullet Manager. Every arrow, spell, and thrown item in flight is one [BulletIns] in a fixed pool.
 */
public object BulletMan {
    public val Pointer: GamePointer = GamePointer(
        name = "BulletMan",
        base = PTDESignatures.BulletMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x64
    )

    public const val PoolPointer: Int = 0x00
    public const val COUNT: Int = 0x80
}

/** 'NS_FRPG::BulletIns' */
public object BulletIns {
    public const val Handle: Int = 0x04
    public const val Owner: Int = 0x1EC

    public const val SIZE: Int = 0x2C0
    public const val HANDLE_TAG: Int = 0x3
    public const val INDEX_MASK: Int = 0xFF
}
