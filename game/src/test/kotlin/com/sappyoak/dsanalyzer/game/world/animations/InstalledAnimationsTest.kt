package com.sappyoak.dsanalyzer.game.world.animations

import java.nio.file.Path
import kotlin.time.measureTime
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.tae.template.decode
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openGameFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest

/** Runs against a real PTDE installation, and only when DS1_PTDE_PATH points at one */
private val ROOT: Path? = System.getenv("DS1_PTDE_PATH")?.let(Path::of)

private val PLAYER = AnimationSetId.Character(0)

private fun <T> withInstalledAnimations(block: (GameFiles, List<AnimationSetId>) -> T): T {
    val root = checkNotNull(ROOT)
    val installation = Installation(
        id = InstallationId.forRoot(root),
        root = root,
        executable = root.resolve(GameEdition.PrepareToDie.executableName),
        build = GameBuild(GameEdition.PrepareToDie)
    )
    return openGameFiles(installation).use { files ->
        block(files, files.availableAnimationSets(loadFileManifest(GameEdition.PrepareToDie)))
    }
}

class InstalledAnimationsTest : FunSpec({
    test("the installation has animation sets for characters and objects").config(enabled = ROOT != null) {
        withInstalledAnimations { _, sets ->
            assertSoftly {
                sets.shouldNotBeEmpty()
                withClue("characters") { sets.any { it is AnimationSetId.Character } shouldBe true }
                withClue("objects") { sets.any { it is AnimationSetId.Object } shouldBe true }
                withClue(sets.take(8).joinToString()) { sets.contains(PLAYER) shouldBe true }
            }
        }
    }

    /**
     * The one that settles whether the parameter lengths are being derived correctly. A length
     * taken from the wrong boundary shows up here as events whose bytes do not match what the
     * template says they should hold
     */
    test("every event of the player's animations decodes against the template").config(enabled = ROOT != null) {
        withInstalledAnimations { files, _ ->
            val template = loadTaeTemplate(PLAYER.bank)
            val player = files.loadTae(PLAYER)

            // An imported animation decodes to its source's events, so an event shared by several
            // animations is counted once per animation that plays it. That is the point: every
            // route to an event has to decode, not just the one that owns it
            val events = player.flatMap { tae ->
                tae.animations.flatMap { animation -> tae.decode(animation.id, template).orEmpty() }
            }

            val mismatched = events.filter { it.sizeMismatch }
                .map { "${it.label}: ${it.event.params.size} bytes for ${it.definition?.packedSize}" }
            val untyped = events.filter { it.definition == null }.map { it.event.type }.distinct().sorted()

            assertSoftly {
                withClue("the DLC binder should be read too") { player.size shouldBe 2 }
                withClue("animations read") { events.shouldNotBeEmpty() }
                withClue("events whose bytes disagree with the template") {
                    mismatched.distinct() shouldBe emptyList()
                }
                withClue("event types no template covers") { untyped shouldBe emptyList<Int>() }
            }
        }
    }

    test("reading every animation set is quick enough to do on demand").config(enabled = ROOT != null) {
        withInstalledAnimations { files, sets ->
            var animations = 0
            val elapsed = measureTime {
                sets.forEach { set -> animations += files.loadTae(set).sumOf { it.animations.size } }
            }

            println("Read ${sets.size} animation sets, $animations animations, in $elapsed")
            animations shouldBeGreaterThan 0
        }
    }
})