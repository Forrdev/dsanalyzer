package com.sappyoak.dsanalyzer.runtime.session

public data class MenuSnapshot(
    /** MenuMan's open-menu bitmask */
    public val mask: Int,
    public val boxKind: Int,
    /**
     * The pause-menu family's screen, or 0 when that family is closed
     */
    public val screen: Int,
    public val defaultQuantity: Int,
    public val isBlocking: Boolean,
    /** From the fade to the loading screen until the fade-in ends */
    public val fading: Boolean = false,
    public val resting: Boolean = false
)

/** Menu-script entries by index */
public object MenuScript {
    public const val InventoryScreen: Int = 2
    /** The pause menu family's screen */
    public const val Screen: Int = 3
    public const val PauseFamily: Int = 8
    /** '1'..'3' while talking or in the bonfire menu, '4' the ambient talk box */
    public const val TalkKind: Int = 9
    public const val Shop: Int = 11
    public const val Repair: Int = 12
    public const val Upgrade: Int = 13
    /** '1' while sitting at a bonfire */
    public const val Resting: Int = 15
    /** Which modal box is up: '2' quitout, '7' not enough humanity, '8' item-use yes/no, '9' non-blocking */
    public const val BoxKind: Int = 17
    public const val LevelUp: Int = 23
    public const val Attune: Int = 25
    public const val BottomlessBox: Int = 26
    /** '1' from the fade to the loading screen until the fade-in ends */
    public const val Fading: Int = 34
    public const val Warp: Int = 36
    public const val Feed: Int = 64
    /** How many of a stackable item the current box can take, and its default */
    public const val CurrentMaxQuantity: Int = 122
    public const val DefaultQuantity: Int = 124
}

/** MenuMan's "which menus are open" bitmask, by script index */
public object MenuOpenMask {
    public const val Box: Int = 0x08
    /** OutMenu_MenuBrake. A single-frame pulse, zeroed every tick by MenuMan's updates */
    public const val MenuBrake: Int = 0x100
    public const val Shop: Int = 0x1000
    public const val Repair: Int = 0x2000
    public const val Upgrade: Int = 0x4000
    public const val Warp: Int = 0x10000
    public const val LevelUp: Int = 0x200000
    public const val Attune: Int = 0x800000
    public const val BottomlessBox: Int = 0x1000000

    /** The talk menu in the one value the player can act through, outside of [Blocking] */
    public const val AmbientTalk: Int = 0x80
    /** The talk menu in its three blocking values */
    public const val Talk: Int = 0x80000

    /**
     * Four entries the fold treats as one bit. Once called "loading", but none changes in a PTDE warp,
     * and in DSR they change in bonfire and shop menus. [MenuScript.Fading] is the load signal
     */
    public const val MenuTransition: Int = 0x8000000
    public const val FullScreenOnly: Int = 0x20000

    /**
     * The game's own "a menu is covering the world": every bit except [AmbientTalk], `+0x98`, and
     * bit 31, which is never set. From PTDE's input gates `FUN_00F77C20` and `FUN_00F77D20`
     */
    public const val Blocking: Int = 0x7FFF_F77F

    private val PLAIN: Map<Int, Int> = mapOf(
        MenuScript.Screen to 0x1, MenuScript.Feed to 0x2, 18 to 0x4, MenuScript.BoxKind to Box,
        32 to 0x10, 33 to 0x20, MenuScript.PauseFamily to 0x40, 170 to MenuBrake,
        MenuScript.InventoryScreen to 0x200, 27 to 0x400, 31 to 0x800,
        MenuScript.Shop to Shop, MenuScript.Repair to Repair, MenuScript.Upgrade to Upgrade, 65 to 0x8000,
        MenuScript.Warp to Warp, 40 to 0x40000, 52 to 0x100000,
        MenuScript.LevelUp to LevelUp, 21 to 0x400000, MenuScript.Attune to Attune,
        MenuScript.BottomlessBox to BottomlessBox,
        56 to 0x2000000, 16 to 0x4000000, 47 to 0x10000000, 51 to 0x40000000,
        66 to FullScreenOnly
    )

    private val TRANSITION: List<Int> = listOf(42, 19, 59, 177)

    /** [MenuScript.TalkKind]'s non-blocking value */
    private const val AMBIENT_TALK_KIND: Int = 4

    /** [MenuScript.TalkKind]'s values that do block */
    private val BLOCKING_TALK_KINDS: IntRange = 1..3

    /** The one box kind the input gates act through */
    private const val PERMISSIVE_BOX_KIND: Int = 9

    /** Folds the entries [entry] reads, by script index, into the mask */
    public fun of(entry: (Int) -> Int): Int {
        var mask = 0
        for ((index, bit) in PLAIN) {
            if (entry(index) != 0) mask = mask or bit
        }
        when (entry(MenuScript.TalkKind)) {
            AMBIENT_TALK_KIND -> mask = mask or AmbientTalk
            in BLOCKING_TALK_KINDS -> mask = mask or Talk
        }
        if (TRANSITION.any { entry(it) != 0 }) mask = mask or MenuTransition
        return mask
    }

    /** Whether [mask] means the world is covered, including the game's own exception */
    public fun blocks(mask: Int, boxKind: Int): Boolean {
        if (mask and Blocking == 0) return false
        val onlyAPermissiveBox = mask == Box && (boxKind == PERMISSIVE_BOX_KIND || boxKind == 0)
        return !onlyAPermissiveBox
    }
}
