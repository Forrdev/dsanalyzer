package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallRegister

public sealed interface LevelUpLayout {
    public val function: GamePointer

    /** PTDE: EAX and ECX both hold one block carrying the stats, humanity, the level and the souls */
    public data class OneBlock(
        override val function: GamePointer,
        public val size: Int = 0x17C,
        public val humanity: Int = 0x20,
        public val soulLevel: Int = 0x16C,
        public val souls: Int = 0x178
    ) : LevelUpLayout

    /** DSR: RCX holds a level block and RDX the eight stats and humanity as nine ints */
    public data class SplitBlock(
        override val function: GamePointer,
        public val size: Int = 0x2A4,
        public val newLevel: Int = 0x294,
        public val requiredSouls: Int = 0x29C,
        public val currentSouls: Int = 0x2A0
    ) : LevelUpLayout
}

public data class StatWriteLayout(
    public val newGame: FieldLayout,
    public val covenant: Int,
    public val covenantPoints: Int,
    public val levelUp: LevelUpLayout
)
