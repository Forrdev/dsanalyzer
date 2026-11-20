package com.sappyoak.dsanalyzer.app.data.flags

import com.sappyoak.dsanalyzer.app.runtime.RuntimeState

public enum class FlagLive(public val label: String) {
    Detached("No game attached"),
    Unreadable("Not readable yet"),
    Set("ON in game"),
    Clear("OFF in game");
}

/** [flagId]'s live value, from the sample that watched it */
public fun RuntimeState.flagLive(flagId: Int): FlagLive = when {
    !live -> FlagLive.Detached
    shownFlag != flagId -> FlagLive.Unreadable
    else -> when (shownFlagSet) {
        true -> FlagLive.Set
        false -> FlagLive.Clear
        null -> FlagLive.Unreadable
    }
}
