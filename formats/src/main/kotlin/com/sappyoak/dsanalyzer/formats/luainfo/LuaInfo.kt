package com.sappyoak.dsanalyzer.formats.luainfo

/**
 * The goals a Lua binder declares to the engine, which registers them from this table when the binder
 * loads. The same in both editions; Remastered's scripts repeat it as `REGISTER_GOAL` calls
 */
public class LuaInfo(public val goals: List<LuaInfoGoal>)

/**
 * One goal. A logic has [logicInterrupt] set: [name] is its logic function and [interruptName] the
 * function run when it is interrupted. [noUpdate] is what `REGISTER_GOAL_NO_UPDATE` sets: the engine never
 * calls the goal's `_Update` (SoulsFormats calls this flag BattleInterrupt)
 */
public data class LuaInfoGoal(
    public val id: Int,
    public val name: String,
    public val noUpdate: Boolean,
    public val logicInterrupt: Boolean,
    public val interruptName: String?
)
