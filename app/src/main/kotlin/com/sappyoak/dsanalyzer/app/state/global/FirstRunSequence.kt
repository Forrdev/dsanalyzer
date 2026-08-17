package com.sappyoak.dsanalyzer.app.state.global

import kotlinx.serialization.Serializable
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.identity.GameIdentity

/**
 * The ordered setup a first launch needs
 */
@Serializable
data class FirstRunSequence(
    val completed: Set<FirstRunStep> = emptySet(),
    val dataPath: Path? = null,
    val installationKey: String? = null,
    val identity: GameIdentity? = null,
    val needsExtraction: Boolean = false,
    val workspaceTitle: String? = null
) {
    val steps: List<FirstRunStep>
        get() = buildList {
            add(FirstRunStep.DataPath)
            add(FirstRunStep.Installation)
            if (needsExtraction) add(FirstRunStep.Extraction)
            add(FirstRunStep.Workspace)
        }

    val current: FirstRunStep? get() = steps.firstOrNull { it !in completed }
    val isComplete: Boolean get() = current == null
    val position: Int get() = steps.indexOf(current).takeIf { it >= 0 }?.plus(1) ?: steps.size
    val total: Int get() = steps.size

    fun completed(step: FirstRunStep): FirstRunSequence = copy(completed = completed + step)
    fun isReachable(step: FirstRunStep): Boolean {
        val index = steps.indexOf(step)
        if (index < 0) return false
        return steps.take(index).all { it in completed }
    }
}

@Serializable
enum class FirstRunStep(val title: String, val purpose: String) {
    DataPath(
        title = "Choose where to store tool data",
        purpose = "Extraction caches, definitions, testing and session history and everything else goes here " +
        "the cache can be fairly large, so choose somewhere with room"
    ),
    Installation(
        title = "Point at a DarkSouls installation",
        purpose = "PTDE or Remastered. The tool works out which from the executable and the files " +
        "to determine the exact build"
    ),
    Extraction(
        title = "Extract game files",
        purpose = "The archives store path hashes rather than names, so reading them repeatedly " +
        "is slow. Extracting once makes every later scan much faster. It also lets you use other tools " +
        "on these files if you so choose"
    ),
    Workspace(
        title = "Name your workspace",
        purpose = "A workspace holds an installation, its findings, and what you are currently working on. " +
        "You can open more later -- one or multiple per installation"
    )
}