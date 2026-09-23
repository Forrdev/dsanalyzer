package com.sappyoak.dsanalyzer.formats.emevd

/** A numbered routine of instructions, with the arguments its callers pass in */
public data class ScriptEvent(
    public val id: Long,
    public val restBehavior: RestBehavior,
    public val instructions: List<Instruction>,
    /**
     * Where are caller's arguments land inside this event's instructions. Each one copies bytes
     * from the event's own argument block into one instruction's argument
     */
    public val parameters: List<Parameter>
)

/** What happens to a running event when the player rests at a bonfire */
public enum class RestBehavior {
    Default,
    Restart,
    End;
}