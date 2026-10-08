package com.sappyoak.dsanalyzer.game.world.animations

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.tae.template.EventBank
import com.sappyoak.dsanalyzer.game.files.GamePath

private fun paths(set: AnimationSetId) = set.animationPaths.map { it.value }

class AnimationPathsTest : FunSpec({
    test("a set is named the way the game names its files") {
        assertSoftly {
            AnimationSetId.Character(0).label shouldBe "c0000"
            AnimationSetId.Character(2230).label shouldBe "c2230"
            AnimationSetId.Object(10).label shouldBe "o0010"
        }
    }

    test("which bank applies is decided by what the set is, since the file does not say") {
        assertSoftly {
            AnimationSetId.Character(2230).bank shouldBe EventBank.Character
            AnimationSetId.Object(10).bank shouldBe EventBank.Object
        }
    }

    test("a character's animations are a binder beside the model") {
        paths(AnimationSetId.Character(2230)) shouldBe listOf("/chr/c2230.anibnd.dcx")
    }

    test("the player's animations span two binders") {
        paths(AnimationSetId.Character(0)) shouldBe
                listOf("/chr/c0000.anibnd.dcx", "/chr/c0000_dlc01.anibnd.dcx")
    }

    test("an object's animations are in its object binder") {
        paths(AnimationSetId.Object(10)) shouldBe listOf("/obj/o0010.objbnd.dcx")
    }

    test("the paths are the ones the archive indexes") {
        AnimationSetId.Character(0).animationPaths.first() shouldBe
                GamePath.of("/CHR/C0000.ANIBND.DCX")
    }
})
