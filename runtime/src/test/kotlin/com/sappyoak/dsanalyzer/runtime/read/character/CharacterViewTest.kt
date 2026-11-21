package com.sappyoak.dsanalyzer.runtime.read.character

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.ptde.AnimData
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.ptde.ChrPosData
import com.sappyoak.dsanalyzer.runtime.ptde.ptdeLayout
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val CHARACTER = 0x1000
private const val CONTROLLER = 0x2000
private const val POSE = 0x3000
private const val ANIM = 0x4000

private val CHARACTERS = ptdeLayout().characters

/** A character standing at its transform's (1, 2, 3), posed at (4, 5, 6), with a controller when [controlled] */
private fun knight(controlled: Boolean = true): FakeGame = FakeGame().apply {
    int(CHARACTER + ChrIns.Handle, 0x10030035)
    float(CHARACTER + ChrIns.PositionX, 1f)
    float(CHARACTER + ChrIns.PositionX + 0x10, 2f)
    float(CHARACTER + ChrIns.PositionX + 0x20, 3f)
    if (controlled) pointer(CHARACTER + ChrIns.ChrCtrlPointer, CONTROLLER)
    pointer(CONTROLLER + ChrCtrl.PositionPointer, POSE)
    pointer(CONTROLLER + ChrCtrl.AnimDataPointer, ANIM)
    float(POSE + ChrPosData.Position, 4f)
    float(POSE + ChrPosData.Position + 4, 5f)
    float(POSE + ChrPosData.Position + 8, 6f)
    float(ANIM + AnimData.PlaySpeed, 1.5f)
}

class CharacterViewTest : FunSpec({
    test("each structure behind the character is read once, however often it is asked for") {
        val game = knight()
        val view = CharacterView(CHARACTERS)
        view.refresh(game, game.address(CHARACTER)) shouldBe true

        val before = game.reads
        repeat(3) {
            view.control()
            view.pose()
            view.animData()
        }

        assertSoftly {
            game.reads - before shouldBe 3
            view.position(posed = true) shouldBe Vec3(4f, 5f, 6f)
            view.animData().shouldNotBeNull().float(AnimData.PlaySpeed) shouldBe 1.5f
        }
    }

    test("a refresh reads the structures behind the character again") {
        val game = knight()
        val view = CharacterView(CHARACTERS)
        view.refresh(game, game.address(CHARACTER))
        view.control()

        view.refresh(game, game.address(CHARACTER))
        val before = game.reads
        view.control()
        game.reads - before shouldBe 1
    }

    test("a character with no controller yet stands where its transform says") {
        val game = knight(controlled = false)
        val view = CharacterView(CHARACTERS)
        view.refresh(game, game.address(CHARACTER))

        assertSoftly {
            view.control().shouldBeNull()
            view.pose().shouldBeNull()
            view.position(posed = true) shouldBe Vec3(1f, 2f, 3f)
        }
    }
})
