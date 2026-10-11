package com.sappyoak.dsanalyzer.runtime.ptde

/** MenuMan's "which menus are open" bitmask, as the game's own `FUN_00C29CB0` computes it. */
public object MenuOpenMask {
    public const val Box: Int = 0x08

    /** OutMenu_MenuBrake. A single-frame pulse, zeroed every tick by Menuman's updates */
    public const val MenuBrake: Int = 0x100

    public const val Shop: Int = 0x1000

    public const val Repair: Int = 0x2000

    /**
     * The third menu in the NPC family, by position between [Repair] and
     * fullscreen bits.
     *
     * Almost certainly modify-equipment as the family sits on consecutively slots and bits,
     * which matches TalkESD shop param lineups, but needs to be observed
     */
    public const val NpcThird: Int = 0x4000

    /**
     * The NPC talk menu, in the one value the player can act through
     *
     * Deliberately outside of [Blocking]. Presumed to be the ambient dialogue box,
     * with an NPC talking while the player is free to be walking, which is consistent with it being
     * the only one of the talk menu's four values the input gates permit
     */
    public const val AmbientTalk: Int = 0x80

    /** The NPC talk menu in its three blocking values */
    public const val Talk: Int = 0x80000

    /** A load or a comparable world-not-ready state */
    public const val Loading: Int = 0x8000000

    public const val FullScreenOnly: Int = 0x20000

    /**
     * The game's own "a menu is covering the world": every bit except [AmbientTalk], `+0x98`, and
     * bit 31, which is never set.
     *
     * Taken from the player input gates `FUN_00F77C20` and `FUN_00F77D20` rather than invented.
     * `FUN_00F77920` is stricter and demands the whole mask be zero, so "blocking" is per-action in
     * the game and this is the middle of three answers.
     */
    public const val Blocking: Int = 0x7FFF_F77F

    private val PLAIN: Map<Int, Int> = mapOf(
        0x28 to 0x1, 0x11C to 0x2, 0x64 to 0x4, 0x60 to Box,
        0x9C to 0x10, 0xA0 to 0x20, 0x3C to 0x40, 0x2C4 to MenuBrake,
        0x24 to 0x200, 0x88 to 0x400, 0x98 to 0x800,
        0x48 to Shop, 0x4C to Repair, 0x50 to NpcThird, 0x120 to 0x8000,
        0xAC to 0x10000, 0xBC to 0x40000, 0xEC to 0x100000,
        0x78 to 0x200000, 0x70 to 0x400000, 0x80 to 0x800000, 0x84 to 0x1000000,
        0xFC to 0x2000000, 0x5C to 0x4000000, 0xD8 to 0x10000000, 0xE8 to 0x40000000,
        0x124 to FullScreenOnly
    )

    /** `+0x40`, the talk menu's kind rather than a flag */
    private const val TALK_KIND: Int = 0x40

    /** `+0x40`'s non-blocking value */
    private const val AMBIENT_TALK_KIND: Int = 4

    /** `+0x40`'s values that do block */
    private val BLOCKING_TALK_KINDS: IntRange = 1..3

    /** The one box kind the input gates act through */
    private const val PERMISSIVE_BOX_KIND: Int = 9

    /** Folds [fields] into the mask */
    public fun of(field: (Int) -> Int): Int {
        var mask = 0

        for ((offset, bit) in PLAIN) {
            if (field(offset) != 0) {
                mask = mask or bit
            }
        }

        when (field(TALK_KIND)) {
            AMBIENT_TALK_KIND -> mask = mask or AmbientTalk
            in BLOCKING_TALK_KINDS -> mask = mask or Talk
        }

        if (field(0xC4) != 0 || field(0x68) != 0 || field(0x108) != 0 || field(0x2E0) != 0) {
            mask = mask or Loading
        }

        return mask
    }

    /** Whether [mask] means the world is covered, including the game's own exception */
    public fun blocks(mask: Int, boxKind: Int): Boolean {
        if (mask and Blocking == 0) return false
        val onlyAPermissiveBox = mask == Box && (boxKind == PERMISSIVE_BOX_KIND || boxKind == 0)
        return !onlyAPermissiveBox
    }
}