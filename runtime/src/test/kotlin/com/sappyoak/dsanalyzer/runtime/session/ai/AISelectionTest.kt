package com.sappyoak.dsanalyzer.runtime.session.ai

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

private const val PLAYER = 0x10044000
private const val KNIGHT = 0x10030035
private const val ARCHER = 0x10030036
private const val GRACE = 3

class AISelectionTest : FunSpec({
    test("off reads no one, and all reads every character an AI drives") {
        val selection = AISelection(GRACE)
        val targets = mapOf(KNIGHT to PLAYER, ARCHER to null)

        assertSoftly {
            selection.select(AIScope.Off, targets, PLAYER).shouldBeEmpty()
            selection.select(AIScope.All, targets, PLAYER) shouldBe setOf(KNIGHT, ARCHER)
        }
    }

    test("only the characters targeting the player are read") {
        val selection = AISelection(GRACE)

        selection.select(AIScope.TargetingPlayer, mapOf(KNIGHT to PLAYER, ARCHER to KNIGHT), PLAYER) shouldBe
                setOf(KNIGHT)
    }

    test("a character that stops targeting stays selected for the grace period, then drops") {
        val selection = AISelection(GRACE)
        selection.select(AIScope.TargetingPlayer, mapOf(KNIGHT to PLAYER), PLAYER)

        val after = List(GRACE + 1) { selection.select(AIScope.TargetingPlayer, mapOf(KNIGHT to null), PLAYER) }

        assertSoftly {
            after.take(GRACE).forEach { it shouldBe setOf(KNIGHT) }
            after.last().shouldBeEmpty()
        }
    }

    test("targeting again inside the grace period starts it over") {
        val selection = AISelection(GRACE)
        selection.select(AIScope.TargetingPlayer, mapOf(KNIGHT to PLAYER), PLAYER)
        repeat(GRACE) { selection.select(AIScope.TargetingPlayer, mapOf(KNIGHT to null), PLAYER) }
        selection.select(AIScope.TargetingPlayer, mapOf(KNIGHT to PLAYER), PLAYER)

        selection.select(AIScope.TargetingPlayer, mapOf(KNIGHT to null), PLAYER) shouldBe setOf(KNIGHT)
    }

    test("a character gone from the world is dropped at once, grace or not") {
        val selection = AISelection(GRACE)
        selection.select(AIScope.TargetingPlayer, mapOf(KNIGHT to PLAYER), PLAYER)

        selection.select(AIScope.TargetingPlayer, mapOf(ARCHER to null), PLAYER).shouldBeEmpty()
    }

    test("with no player, nothing counts as targeting one") {
        val selection = AISelection(GRACE)

        selection.select(AIScope.TargetingPlayer, mapOf(KNIGHT to -1, ARCHER to null), player = null).shouldBeEmpty()
    }
})
