package com.sappyoak.dsanalyzer.app.data.animations

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.formats.tae.AnimationSource
import com.sappyoak.dsanalyzer.formats.tae.Tae
import com.sappyoak.dsanalyzer.formats.tae.TaeAnimation
import com.sappyoak.dsanalyzer.formats.tae.template.DecodedParam
import com.sappyoak.dsanalyzer.formats.tae.template.ParamDefinition
import com.sappyoak.dsanalyzer.formats.tae.template.ParamType
import com.sappyoak.dsanalyzer.formats.tae.template.ParamValue
import com.sappyoak.dsanalyzer.formats.tae.template.TaeTemplate
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

private val SET = AnimationSetId.Character(0)

private fun tae(id: Int, vararg animationIds: Long) = Tae(
    id = id,
    bigEndian = false,
    skeletonName = null,
    sibName = null,
    animations = animationIds.map { TaeAnimation(it, AnimationSource.Own(false, false, false, 0), null, emptyList()) }
)

/** The player's animations split across two files, sharing one id */
private val ANIMATIONS = SetAnimations(SET, listOf(tae(0, 3000, 200), tae(1, 3000, 250100)), TaeTemplate.Empty, 60)

private fun param(name: String, raw: Long) = DecodedParam(ParamDefinition(ParamType.S32, name), ParamValue.Number(raw))

class SetAnimationsTest : FunSpec({
    test("ids are gathered from every file of the set, once each and in order") {
        ANIMATIONS.ids shouldContainExactly listOf(200L, 3000L, 250100L)
    }

    test("an id is labelled by its category and number") {
        animationLabel(3000) shouldBe "a00_3000"
        animationLabel(250100) shouldBe "a25_0100"
    }

    test("event times become ticks of the edition's logic rate") {
        ticksAt(0.4333333f, 30) shouldBe 13
        ticksAt(0.4333333f, 60) shouldBe 26
    }

    test("an unknown animation has no events") {
        ANIMATIONS.events(9999).shouldBeNull()
        ANIMATIONS.events(200) shouldBe emptyList()
    }

    test("a SpEffect id links to its row") {
        ANIMATIONS.linkOf(param("SpEffectID", 5520)) shouldBe View.Data.ParamRow("SpEffectParam", 5520)
    }

    test("an animation id links only to an animation the set has") {
        ANIMATIONS.linkOf(param("AnimID", 3000)) shouldBe View.Data.Animation(SET, 3000)
        ANIMATIONS.linkOf(param("AnimID", 4000)).shouldBeNull()
    }

    test("empty and unrelated values link nowhere") {
        ANIMATIONS.linkOf(param("SpEffectID", -1)).shouldBeNull()
        ANIMATIONS.linkOf(param("DummyPolyID", 100)).shouldBeNull()
    }
})
