package com.sappyoak.dsanalyzer.game.world.animations

import com.sappyoak.dsanalyzer.formats.binder.OpenBinder
import com.sappyoak.dsanalyzer.formats.tae.Tae
import com.sappyoak.dsanalyzer.formats.tae.readTae
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openBinder
import com.sappyoak.dsanalyzer.game.verification.FileManifest

private const val TAE_EXTENSION = ".tae"

private val CHARACTER_BINDER =
    Regex("""^\Q$CHARACTER_DIRECTORY\Ec(\d+)\Q$CHARACTER_BINDER_EXTENSION\E$""")

private val OBJECT_BINDER =
    Regex("""^\Q$OBJECT_DIRECTORY\Eo(\d+)\Q$OBJECT_BINDER_EXTENSION\E$""")

/** The animation timings for one character or object */
public fun GameFiles.loadTae(set: AnimationSetId): List<Tae> =
    set.animationPaths.flatMap { path ->
        openBinder(path)?.allTae().orEmpty()
    }

/**
 * The characters and objects this installation has animations for
 */
public fun GameFiles.availableAnimationSets(manifest: FileManifest): List<AnimationSetId> = manifest.paths
    .asSequence()
    .mapNotNull { it.value.toAnimationSet() }
    .filter { set -> set.animationPaths.any { exists(it) } }
    .sortedBy { it.label }
    .toList()

private fun OpenBinder.allTae(): List<Tae> = binder.entries
    .filter { entry -> entry.name?.endsWith(TAE_EXTENSION, ignoreCase = true) == true }
    .map { entry -> readTae(open(entry)) }

private fun String.toAnimationSet(): AnimationSetId? {
    CHARACTER_BINDER.matchEntire(this)?.let { match ->
        return match.groupValues[1].toIntOrNull()?.let(AnimationSetId::Character)
    }

    return OBJECT_BINDER.matchEntire(this)?.let { match ->
        match.groupValues[1].toIntOrNull()?.let(AnimationSetId::Object)
    }
}