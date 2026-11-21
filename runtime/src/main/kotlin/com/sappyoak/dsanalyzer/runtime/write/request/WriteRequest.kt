package com.sappyoak.dsanalyzer.runtime.write.request

import com.sappyoak.dsanalyzer.game.world.items.ItemCategory
import com.sappyoak.dsanalyzer.runtime.layout.Stat
import com.sappyoak.dsanalyzer.runtime.snapshot.CheatFlag
import com.sappyoak.dsanalyzer.runtime.snapshot.SavedPosition
import com.sappyoak.dsanalyzer.runtime.write.talk.TalkCommand
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.runtime.write.warp.WarpDestination

/** Every change the tool can ask of the game. Each is submitted through [WriteRequests.submit] */
public sealed interface WriteRequest {
    public val label: String

    public data class SetCheat(public val flag: CheatFlag, public val on: Boolean) : WriteRequest {
        override val label: String get() = "${if (on) "Turn on" else "Turn off"} $flag"
    }

    /** [id] is a history record's; [force] undoes an edit even after the game has changed it again */
    public data class Undo(public val id: Long, public val force: Boolean = false) : WriteRequest {
        override val label: String get() = "Undo $id"
    }

    /** Puts back every change the tool made, except the edits kept on purpose */
    public data object RevertAll : WriteRequest {
        override val label: String get() = "Revert all"
    }

    public data class Warp(public val destination: WarpDestination) : WriteRequest {
        override val label: String get() = "Warp to $destination"
    }

    /** Runs [command] as an NPC's talk script would, such as `TalkCommands.Repair` */
    public data class Talk(public val command: TalkCommand) : WriteRequest {
        override val label: String get() = "Talk command ${command.id}"
    }

    /** Adds [count] of item [id] to the inventory and shows the game's item-get popup for it */
    public data class SpawnItem(public val category: ItemCategory, public val id: Int, public val count: Int) :
        WriteRequest {
        override val label: String get() = "Spawn ${category.name.lowercase()} $id x$count"
    }

    /** Sets a flag through the game's own call, so what the game ties to it follows */
    public data class SetFlag(public val flag: Int, public val on: Boolean) : WriteRequest {
        override val label: String get() = "Set flag $flag to $on"
    }

    /** Reads a flag through the game's own call; done with 1 or 0 */
    public data class ReadFlag(public val flag: Int) : WriteRequest {
        override val label: String get() = "Read flag $flag"
    }

    public data class RestorePosition(public val saved: SavedPosition) : WriteRequest {
        override val label: String get() = "Restore position"
    }

    public data class SetSouls(public val souls: Int) : WriteRequest {
        override val label: String get() = "Set souls to $souls"
    }

    public data class SetHumanity(public val humanity: Int) : WriteRequest {
        override val label: String get() = "Set humanity to $humanity"
    }

    public data class SetNewGame(public val cycle: Int) : WriteRequest {
        override val label: String get() = "Set NG+$cycle"
    }

    public data class SetCovenant(public val covenant: Int) : WriteRequest {
        override val label: String get() = "Join covenant $covenant"
    }

    public data class SetCovenantPoints(public val covenant: Int, public val points: Int) : WriteRequest {
        override val label: String get() = "Set covenant $covenant points to $points"
    }

    public data class SetAttributes(public val attributes: Map<Stat, Int>) : WriteRequest {
        override val label: String get() = "Set attributes"
    }

    /** Holds the player in a ladder climb at [rung] of [count] with no ladder: ladder storage */
    public data class SetLadderStorage(public val rung: Int, public val count: Int) : WriteRequest {
        override val label: String get() = "Ladder storage, rung $rung of $count"
    }

    public data object ClearLadderStorage : WriteRequest {
        override val label: String get() = "Leave ladder storage"
    }

    /** Waits up to ten seconds for the player to start a one-shot animation, then orphans its slot */
    public data object InducePoopwalk : WriteRequest {
        override val label: String get() = "Induce a poopwalk"
    }

    /** Releases every orphaned animation slot on the player; done with how many */
    public data object CurePoopwalk : WriteRequest {
        override val label: String get() = "Cure the poopwalk"
    }

    /** A multi-sample write built outside this module */
    public data class Run(public val task: WriteTask) : WriteRequest {
        override val label: String get() = task.label
    }
}
