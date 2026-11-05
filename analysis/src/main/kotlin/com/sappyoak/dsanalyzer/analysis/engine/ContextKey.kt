package com.sappyoak.dsanalyzer.analysis.engine

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.data.GameData
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.title

public enum class Scope { Game, PerScript; }

public sealed interface RunUnit {
    public val label: String

    public data object Game : RunUnit {
        override val label: String get() = "the game"
    }

    public data class Script(public val script: ScriptId) : RunUnit {
        override val label: String get() = script.title
    }
}

public interface GameSource {
    public val build: GameBuild
    public val scripts: List<ScriptId>

    public fun lacks(requirement: Requirement): Boolean
    public suspend fun <T> data(block: suspend (GameData) -> T): T
}

public abstract class ContextKey<C : Any>(
    public val name: String,
    public val scope: Scope,
    public val requires: Set<Requirement> = emptySet()
) {
    public abstract suspend fun load(source: GameSource, unit: RunUnit): C?
}

internal fun Scope.unitsOf(source: GameSource): List<RunUnit> = when (this) {
    Scope.Game -> listOf(RunUnit.Game)
    Scope.PerScript -> source.scripts.map(RunUnit::Script)
}
