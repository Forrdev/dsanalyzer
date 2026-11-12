package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object AttackMan {
    public val Pointer: GamePointer = GamePointer(
        name = "AttackMan",
        base = PTDESignatures.AttackMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x20
    )

    /** The first live [AttackHit], linked through [AttackHit.NextPointer] */
    public const val HeadPointer: Int = 0x00

    public const val HitboxDraw: Int = 0x1C
}

public object AttackHit {
    public const val Id: Int = 0x00

    public const val PhantomPointer: Int = 0x04
    public const val Owner: Int = 0x20
    public const val AtkParamId: Int = 0x60

    public const val DummyA: Int = 0x0CC
    public const val DummyB: Int = 0x0D0

    public const val Transform: Int = 0x100
    public const val PreviousPosition: Int = 0x150
    public const val NextPointer: Int = 0x1D0

    public const val SIZE: Int = 0x1D4
}

public object PhysShapePhantom {
    public const val ShapePointer: Int = 0x1C
}

public object HkpShapeType {
    public const val SPHERE: Int = 1
    public const val CAPSULE: Int = 5
}

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
