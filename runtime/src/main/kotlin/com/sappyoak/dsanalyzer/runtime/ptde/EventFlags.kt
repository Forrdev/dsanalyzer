package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Equal to `EventFlagIds.BLOCK_SIZE` */
private const val BLOCK_SIZE: Int = 0x16D00

private const val MANAGER_SIZE: Int = 0x120

public object EventFlags {
    public val Pointer: GamePointer = GamePointer(
        name = "EventFlags",
        base = PTDESignatures.EventFlags,
        offsets = listOf(0L, 0L),
        lifetime = Lifetime.Session,
        size = BLOCK_SIZE
    )

    /**
     * The flag manager itself, one deref short of [Pointer]
     *
     * The maanger keeps two banks. Bank A is the local world and bank B is the host's world while
     * you are in it, selected on every map by the session state
     */
    public val Manager: GamePointer = GamePointer(
        name = "EventFlagMan",
        base = PTDESignatures.EventFlags,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = MANAGER_SIZE
    )

    /** Bank A's bit buffer, which is what [Pointer] resolves to */
    public const val BufferA: Int = 0x00
    /** Bank B's bit buffer, `SizeB` bytes and sparsely populated */
    public const val BufferB: Int = 0x04

    public const val SizeA: Int = 0x08
    public const val SizeB: Int = 0x0C

    public const val BankA: Int = 0x14
    public const val BankB: Int = 0x90

    public const val GroupRecordStride: Int = 0x0C

    public const val CurrentBank: Int = 0x10C
    /** `0` for bank A, `1` for bank B */
    public const val BankIndex: Int = 0x110

    public const val Ready: Int = 0x114
}
