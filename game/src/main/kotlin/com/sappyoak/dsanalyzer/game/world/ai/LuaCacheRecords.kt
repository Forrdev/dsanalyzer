package com.sappyoak.dsanalyzer.game.world.ai

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationFingerprint
import com.sappyoak.dsanalyzer.game.fingerprint
import com.sappyoak.dsanalyzer.lua.decompile.DECOMPILER_REVISION

/** What a Lua cache was made from: the installation's files, the decompiler, the indexing and the bundled names */
@Serializable
internal data class LuaCacheStamp(
    val fingerprint: InstallationFingerprint,
    val revision: Int,
    val indexRevision: Int,
    val names: String
) {
    companion object {
        fun of(installation: Installation): LuaCacheStamp =
            LuaCacheStamp(installation.fingerprint(), DECOMPILER_REVISION, AI_INDEX_REVISION, luaNamesDigest())
    }
}

@Serializable
internal class SourceRecord(val source: String, val failures: List<String>)

@Serializable
internal class GoalRecord(
    val id: Int,
    val constant: String?,
    val name: String,
    val functions: List<String>,
    val registeredIn: List<String>,
    val noUpdate: Boolean,
    val parameters: List<ParameterRecord>
) {
    fun toGoal(): AiGoal = AiGoal(
        id, constant, name, functions, registeredIn.map(::binder), noUpdate,
        parameters.map { GoalParameter(it.index, it.name) }
    )

    companion object {
        fun of(goal: AiGoal): GoalRecord = GoalRecord(
            goal.id, goal.constant, goal.name, goal.functions, goal.registeredIn.map(LuaBinder::label), goal.noUpdate,
            goal.parameters.map { ParameterRecord(it.index, it.name) }
        )
    }
}

@Serializable
internal class ParameterRecord(val index: Int, val name: String)

@Serializable
internal class LogicRecord(
    val id: Int,
    val constant: String?,
    val functions: List<String>,
    val registeredIn: List<String>
)

/** An [AiIndex] with binders written as their labels and scripts as `<binder>/<file>` */
@Serializable
internal class AiIndexRecord(
    val goals: List<GoalRecord>,
    val logics: List<LogicRecord>,
    val functions: Map<String, List<String>>
) {
    /** The index back, or null if a label no longer names a binder */
    fun toIndex(): AiIndex? = runCatching {
        AiIndex(
            goals.map(GoalRecord::toGoal).associateBy { it.id }.toSortedMap(),
            logics.map { AiLogic(it.id, it.constant, it.functions, it.registeredIn.map(::binder)) }
                .associateBy { it.id }.toSortedMap(),
            functions.mapValues { (_, scripts) -> scripts.map(::script) }
        )
    }.getOrNull()

    companion object {
        fun of(index: AiIndex): AiIndexRecord = AiIndexRecord(
            index.goals.values.map(GoalRecord::of),
            index.logics.values.map {
                LogicRecord(it.id, it.constant, it.functions, it.registeredIn.map(LuaBinder::label))
            },
            index.functions.mapValues { (_, scripts) -> scripts.map(LuaScriptId::toString) }
        )
    }
}

private fun binder(label: String): LuaBinder = LuaBinder.of(label) ?: error("No binder is labelled $label")

private fun script(written: String): LuaScriptId =
    LuaScriptId(binder(written.substringBefore('/')), written.substringAfter('/'))
