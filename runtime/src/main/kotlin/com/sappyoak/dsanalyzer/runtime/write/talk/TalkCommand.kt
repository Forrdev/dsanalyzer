package com.sappyoak.dsanalyzer.runtime.write.talk

/** The cases of PTDE's dispatcher that read the talk owner (`talk-and-point-warp.md` §3) */
private val OWNER_COMMANDS: Set<Int> = setOf(1, 4, 6, 8, 11, 15, 17, 33, 37, 38, 46, 51, 52)

/** The ids both editions share. DSR's 71 to 77 have not been surveyed */
private val SURVEYED: IntRange = 0..70

/**
 * One talk-script command, as an NPC's ESD runs it, with its int [params].
 * A command that reads its owner runs only once a loaded character resolves as that owner,
 * because those cases call through the owner without checking it
 */
public data class TalkCommand(public val id: Int, public val params: List<Int> = emptyList()) {
    public val needsOwner: Boolean get() = id in OWNER_COMMANDS || id !in SURVEYED
}

/** The commands tested in both editions (`talk-and-point-warp.md` §6 and §8) */
public object TalkCommands {
    public val Repair: TalkCommand = TalkCommand(23)
    public val ReinforceWeapon: TalkCommand = TalkCommand(24, listOf(0))
    public val ReinforceArmor: TalkCommand = TalkCommand(24, listOf(10))
    public val StorageBox: TalkCommand = TalkCommand(30)
    public val LevelUp: TalkCommand = TalkCommand(31)

    /** Frampt's feed in DS1, the only buyer the game has */
    public val Sell: TalkCommand = TalkCommand(46, listOf(-1, -1))
    public val Ascension: TalkCommand = TalkCommand(48)

    /** A shop selling the `ShopLineupParam` rows [first] through [last] */
    public fun shop(first: Int, last: Int): TalkCommand = TalkCommand(22, listOf(first, last))

    /** Attunement over the rows [first] through [last] */
    public fun attunement(first: Int, last: Int): TalkCommand = TalkCommand(28, listOf(first, last))
}
