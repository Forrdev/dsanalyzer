package com.sappyoak.dsanalyzer.game.world.ai

/**
 * A goal: the id NpcThinkParam names, the `GOAL_` constant for it, and its name in the binders' `.luainfo`.
 * [functions] are the `<name>_Activate`-style functions some script defines; [registeredIn] lists every
 * binder that registers it, since maps carry their own copies. With [noUpdate] the engine never calls its
 * `_Update`. [parameters] are the names `REGISTER_DBG_GOAL_PARAM` gives its parameters
 */
public data class AiGoal(
    public val id: Int,
    public val constant: String?,
    public val name: String,
    public val functions: List<String>,
    public val registeredIn: List<LuaBinder>,
    public val noUpdate: Boolean = false,
    public val parameters: List<GoalParameter> = emptyList()
)

/** A goal parameter by its position after the goal id and lifetime in `AddSubGoal`, and its debug name */
public data class GoalParameter(public val index: Int, public val name: String) {
    /** An EzState request, which for a non-human character plays the animation of the same id */
    public val isEzState: Boolean get() = EZ_STATE in name
}

private const val EZ_STATE = "EzState"

/** A logic: the id NpcThinkParam names, its `LOGIC_ID_` constant, and its logic and interrupt functions */
public data class AiLogic(
    public val id: Int,
    public val constant: String?,
    public val functions: List<String>,
    public val registeredIn: List<LuaBinder>
)

/** Every goal and logic the AI binders register, and which scripts define each global function */
public class AiIndex(
    public val goals: Map<Int, AiGoal>,
    public val logics: Map<Int, AiLogic>,
    /** Global function name to the scripts that define it */
    public val functions: Map<String, List<LuaScriptId>>
) {
    /** The scripts that define [function], or none for one no script declares */
    public fun definitionsOf(function: String): List<LuaScriptId> = functions[function].orEmpty()

    public companion object {
        public val Empty: AiIndex = AiIndex(emptyMap(), emptyMap(), emptyMap())
    }
}
