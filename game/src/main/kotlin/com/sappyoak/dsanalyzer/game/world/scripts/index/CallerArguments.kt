package com.sappyoak.dsanalyzer.game.world.scripts.index

/** What an event's callers hand it, ready to fill in one of its parameters */
public sealed interface CallerArguments {
    public data object Unknown : CallerArguments
    public data object NoCaller : CallerArguments

    public data class Sites(
        public val sites: List<CallSite>,
        public val offset: Int
    ) : CallerArguments
}

public fun EventCallIndex.argumentsFor(event: EventKey): CallerArguments {
    if (!built) return CallerArguments.Unknown

    val sites = sitesFor(event)
    if (sites.isEmpty()) return CallerArguments.NoCaller

    val offset = (base as? BlockBase.Derived)?.offset ?: return CallerArguments.Unknown
    return CallerArguments.Sites(sites, offset)
}
