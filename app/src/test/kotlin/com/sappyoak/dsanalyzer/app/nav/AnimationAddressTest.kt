package com.sappyoak.dsanalyzer.app.nav

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.game.ref.parseAddress
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId

private val TAURUS = AnimationSetId.Character(2690)
private val CHEST = AnimationSetId.Object(1234)

private val WITH_SETS =
    WorkspaceResolveContext(emptySet(), emptySet(), emptySet(), { false }, { false }, setOf(TAURUS, CHEST), { null })

private fun resolve(address: String): Resolution = WITH_SETS.resolve(checkNotNull(parseAddress(address)))

class AnimationAddressTest : FunSpec({
    test("a set and an animation in it are addressed under the anim space by label and id") {
        View.Data.AnimationSet(TAURUS).toDataAddress()?.toAddress() shouldBe "ds:/anim/c2690"
        View.Data.Animation(CHEST, 0).toDataAddress()?.toAddress() shouldBe "ds:/anim/o1234/0"
    }

    test("both views resolve back from their addresses") {
        resolve("ds:/anim/c2690") shouldBe Resolution.Resolved(View.Data.AnimationSet(TAURUS))
        resolve("ds:/anim/c2690/3000") shouldBe Resolution.Resolved(View.Data.Animation(TAURUS, 3000))
    }

    test("a set the installation lacks is absent, and a malformed one has no view") {
        resolve("ds:/anim/c9999/3000").shouldBeInstanceOf<Resolution.Absent>()
        resolve("ds:/anim/x2690").shouldBeInstanceOf<Resolution.NoView>()
        resolve("ds:/anim/c2690/three").shouldBeInstanceOf<Resolution.NoView>()
    }

    test("only a label written the way the set writes it parses") {
        AnimationSetId.parse("c0000") shouldBe AnimationSetId.Character(0)
        AnimationSetId.parse("c02690").shouldBeNull()
        AnimationSetId.parse("c").shouldBeNull()
    }
})
