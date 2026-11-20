package com.sappyoak.dsanalyzer.app.data.animations

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.nio.file.Path

import com.sappyoak.dsanalyzer.formats.tae.template.TaeTemplate
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

private val INSTALLATION = Installation(
    id = InstallationId("ptde"),
    root = Path.of("game"),
    executable = Path.of("game", "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

private val PLAYER = AnimationSetId.Character(0)
private val SKELETON = AnimationSetId.Character(2810)
private val DOOR = AnimationSetId.Object(1000)

private val OPEN = AnimationsState(installation = INSTALLATION)

private fun animationsOf(set: AnimationSetId) = SetAnimations(set, emptyList(), TaeTemplate.Empty, tickHz = 30)

class AnimationsReducerTest : FunSpec({
    test("opening an installation reads its catalog") {
        val (state, effects) = reduceAnimations(AnimationsState(), AnimationsMessage.Opened(INSTALLATION))

        state.installation shouldBe INSTALLATION
        effects shouldContainExactly listOf(AnimationsEffect.LoadCatalog(INSTALLATION))
    }

    test("the catalog lists characters before objects") {
        val (state, effects) = reduceAnimations(
            OPEN,
            AnimationsMessage.CatalogLoaded(listOf(DOOR, PLAYER, SKELETON), OPEN.models)
        )

        state.sets shouldContainExactly listOf(PLAYER, SKELETON, DOOR)
        effects.shouldBeEmpty()
    }

    test("choosing a set loads it, and choosing it again does not reload") {
        val (loading, effects) = reduceAnimations(OPEN, AnimationsMessage.SetSelected(SKELETON))
        loading.shown shouldBe SKELETON
        loading.loading shouldBe true
        effects shouldContainExactly listOf(AnimationsEffect.Load(INSTALLATION, SKELETON))

        reduceAnimations(loading, AnimationsMessage.SetSelected(SKELETON)).effects.shouldBeEmpty()
    }

    test("a load for a set no longer shown is dropped") {
        val shown = OPEN.copy(shown = PLAYER, loading = true)

        reduceAnimations(shown, AnimationsMessage.Loaded(animationsOf(SKELETON))).state.animations.shouldBeNull()
        reduceAnimations(shown, AnimationsMessage.Loaded(animationsOf(PLAYER))).state.loading shouldBe false
    }

    test("a set is titled with the character's name where it is known") {
        OPEN.title(DOOR) shouldBe "o1000"
        OPEN.title(SKELETON) shouldBe "c2810"
    }
})
