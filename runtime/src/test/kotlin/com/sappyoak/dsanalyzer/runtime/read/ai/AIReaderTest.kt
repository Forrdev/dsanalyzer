package com.sappyoak.dsanalyzer.runtime.read.ai

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.ptde.ActionCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.AIThink
import com.sappyoak.dsanalyzer.runtime.ptde.ChrCtrl
import com.sappyoak.dsanalyzer.runtime.ptde.ComManipulator
import com.sappyoak.dsanalyzer.runtime.ptde.TargetNode
import com.sappyoak.dsanalyzer.runtime.ptde.TargetingSystem
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterRef
import com.sappyoak.dsanalyzer.runtime.session.ai.AISnapshot
import com.sappyoak.dsanalyzer.runtime.session.ai.AITarget

private const val MANIPULATOR = 0x1000
private const val THINK = 0x2000
private const val TARGETING = 0x3000
private const val CONTROLLER = 0x4000
private const val ACTIONS = 0x5000
private const val NODES = 0x6000
private const val NODE_STRIDE = 0x80

private const val KNIGHT = 0x10030035
private const val PLAYER = 0x10044000
private const val ALLY = 0x10030030

/** One knight's AI, idle unless a test says otherwise */
private fun knight(): FakeGame = FakeGame().apply {
    pointer(MANIPULATOR + ComManipulator.AIThinkPointer, THINK)
    pointer(THINK + AIThink.OwnerPointer, MANIPULATOR + ComManipulator.ThinkOwner)
    pointer(THINK + AIThink.TargetingSystemPointer, TARGETING)
    int(THINK + AIThink.ThinkId, 241000)
    pointer(CONTROLLER + ChrCtrl.ActionCtrlPointer, ACTIONS)
    int(ACTIONS + ActionCtrl.RequestedAction, -1)
    int(ACTIONS + ActionCtrl.PlayingAction, -1)
}

/** One entry of the known list: a character's handle, or [SOUND] for a sound's place */
private data class Entry(val kind: Int, val weight: Float, val handle: Int)

private const val SOUND = 0

/** The known list, built from [entries] in order */
private fun FakeGame.known(vararg entries: Entry) {
    if (entries.isEmpty()) return nullPointer(TARGETING + TargetingSystem.Known)
    pointer(TARGETING + TargetingSystem.Known, NODES)
    entries.forEachIndexed { i, (kind, weight, handle) ->
        val at = NODES + i * NODE_STRIDE
        // The kind byte carries a flag in its top bit, which the game masks off
        write(at + TargetNode.Kind, kind or 0x80)
        if (i + 1 < entries.size) pointer(at + TargetNode.Next, at + NODE_STRIDE) else nullPointer(at + TargetNode.Next)
        int(at + TargetNode.Shape, if (handle == SOUND) TargetNode.PLACE else TargetNode.CHARACTER)
        float(at + TargetNode.Weight, weight)
        // What a sound's place leaves where a handle would be, which is no handle at all
        int(at + TargetNode.Handle, if (handle == SOUND) 0xFFFF0000.toInt() else handle)
    }
}

private fun FakeGame.ref() = CharacterRef(KNIGHT, address(MANIPULATOR), address(CONTROLLER))

private fun FakeGame.readOnce(): AISnapshot? {
    val reader = AIReader()
    val source = reader.locate(this, ref()) ?: return null
    return reader.read(this, source)
}

private fun FakeGame.target(): AITarget? {
    val reader = AIReader()
    return reader.locate(this, ref())?.let { reader.targetOf(this, it) }
}

class AIReaderTest : FunSpec({
    test("an attacking knight reads its think, actions and script state") {
        val game = knight().apply {
            int(ACTIONS + ActionCtrl.RequestedAction, 3002)
            int(ACTIONS + ActionCtrl.PlayingAction, 3001)
            float(THINK + AIThink.Timers + 4, 2.5f)
            int(THINK + AIThink.Numbers + 7 * 4, 3)
            known(Entry(TargetNode.COMBAT, 1f, PLAYER))
        }

        val ai = checkNotNull(game.readOnce())

        assertSoftly {
            ai.thinkId shouldBe 241000
            ai.target shouldBe AITarget.Character(PLAYER)
            ai.requestedAction shouldBe 3002
            ai.playingAction shouldBe 3001
            ai.timers shouldBe listOf(0f, 2.5f, 0f, 0f)
            ai.numbers.last() shouldBe 3
        }
    }

    test("an idle AI reads no action and no target rather than -1") {
        val ai = checkNotNull(knight().readOnce())

        assertSoftly {
            ai.target shouldBe AITarget.None
            ai.requestedAction shouldBe null
            ai.playingAction shouldBe null
        }
    }

    test("an AI that no longer points back at its owner is refused, found or already in hand") {
        val game = knight()
        val reader = AIReader()
        val source = checkNotNull(reader.locate(game, game.ref()))

        // What a freed and reused allocation looks like: the same address, someone else's contents
        game.pointer(THINK + AIThink.OwnerPointer, 0x7000)

        assertSoftly {
            reader.read(game, source) shouldBe null
            reader.locate(game, game.ref()) shouldBe null
        }
    }

    test("the heaviest combat entry is the target, and known characters are passed over") {
        val game = knight().apply {
            known(
                Entry(TargetNode.KNOWN, 9f, ALLY),
                Entry(TargetNode.COMBAT, 0.2f, ALLY),
                Entry(TargetNode.COMBAT, 0.9f, PLAYER)
            )
        }

        game.target() shouldBe AITarget.Character(PLAYER)
    }

    test("an urgent sound wins from anywhere in the list, and is a place rather than a character") {
        val game = knight().apply {
            known(Entry(TargetNode.COMBAT, 0.9f, PLAYER), Entry(TargetNode.ALERT_SOUND, 0f, ALLY), Entry(TargetNode.URGENT_SOUND, 0f, SOUND))
        }

        game.target() shouldBe AITarget.Sound
    }

    test("an alert sound comes before combat, and a plain sound only when there is no combat entry") {
        val alerted = knight().apply {
            known(Entry(TargetNode.COMBAT, 0.9f, PLAYER), Entry(TargetNode.ALERT_SOUND, 0f, SOUND))
        }
        val fighting = knight().apply {
            known(Entry(TargetNode.SOUND, 0f, SOUND), Entry(TargetNode.COMBAT, 0.1f, PLAYER))
        }
        val listening = knight().apply {
            known(Entry(TargetNode.KNOWN, 0f, ALLY), Entry(TargetNode.SOUND, 0f, SOUND))
        }

        assertSoftly {
            alerted.target() shouldBe AITarget.Sound
            fighting.target() shouldBe AITarget.Character(PLAYER)
            listening.target() shouldBe AITarget.Sound
            knight().target() shouldBe AITarget.None
        }
    }

    test("a combat entry not yet filled in is no one rather than a character") {
        val game = knight().apply { known(Entry(TargetNode.COMBAT, 0.5f, -1)) }

        game.target() shouldBe AITarget.None
    }

    test("a character no AI drives is not located") {
        val game = knight().apply { nullPointer(MANIPULATOR + ComManipulator.AIThinkPointer) }

        AIReader().locate(game, game.ref()) shouldBe null
        knight().readOnce() shouldNotBe null
    }
})
