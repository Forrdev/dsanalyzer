package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/** Equal to `EventFlagIds.BLOCK_SIZE`. The manager states it at +0x10 */
private const val BLOCK_SIZE: Int = 0x16D00

/** 'EventFlagMan' and its bank A block.  */
public object EventFlags {
    public val Pointer: GamePointer = GamePointer(
        name = "EventFlags",
        base = DSRSignatures.EventFlagMan,
        offsets = listOf(0L, 0L),
        lifetime = Lifetime.Session,
        size = BLOCK_SIZE
    )

    /** In the manager: the byte GetEvent tests before it reads a flag */
    public const val Ready: Int = 0x224
}
