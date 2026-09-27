package com.sappyoak.dsanalyzer.runtime.ptde

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers

private const val SIGNATURE_AT = 0x200
private const val STATIC_AT = 0x1000
private const val HOLDER_AT = 0x1100
private const val BLOCK_AT = 0x20000

private const val SAMPLE = 11_010_902
private const val OTHER = 11_020_000

private fun FakeGame.plantFlagBlock() {
    write(SIGNATURE_AT, 0x56, 0x8B, 0xF1, 0x8B, 0x46, 0x1C, 0x50, 0xA1)
    pointer(SIGNATURE_AT + 8, STATIC_AT)
    write(SIGNATURE_AT + 12, 0x32, 0xC9)
    pointer(STATIC_AT, HOLDER_AT)
    pointer(HOLDER_AT, BLOCK_AT)
}

private fun FakeGame.setFlag(flagId: Int, set: Boolean) {
    val at = checkNotNull(EventFlags.locate(flagId))
    int(BLOCK_AT + at.byteOffset, if (set) at.mask.toInt() else 0)
}

private fun connected(game: FakeGame): GameMemory =
    GameMemory(game, game.resolvePointers(listOf(EventFlags.Pointer), game.module))

class EventFlagBlockTest : FunSpec({
    test("the first fetch records the block rather than reporting all of it as new") {
        val game = FakeGame().apply { plantFlagBlock(); setFlag(SAMPLE, true) }
        val block = EventFlagBlock()

        val changes = block.refresh(connected(game))

        assertSoftly {
            changes.shouldBeEmpty()
            block.isPresent shouldBe true
            block.isSet(SAMPLE) shouldBe true
            block.isSet(OTHER) shouldBe false
        }
    }

    test("a flag that turns on is reported by id") {
        val game = FakeGame().apply { plantFlagBlock() }
        val memory = connected(game)
        val block = EventFlagBlock().apply { refresh(memory) }

        game.setFlag(SAMPLE, true)

        block.refresh(memory).shouldContainExactly(FlagChange(SAMPLE, true))
    }

    test("a flag that turns off again is reported too") {
        val game = FakeGame().apply { plantFlagBlock(); setFlag(SAMPLE, true) }
        val memory = connected(game)
        val block = EventFlagBlock().apply { refresh(memory) }

        game.setFlag(SAMPLE, false)

        assertSoftly {
            block.refresh(memory).shouldContainExactly(FlagChange(SAMPLE, false))
            block.isSet(SAMPLE) shouldBe false
        }
    }

    test("two flags changing in one tick are both reported") {
        val game = FakeGame().apply { plantFlagBlock() }
        val memory = connected(game)
        val block = EventFlagBlock().apply { refresh(memory) }

        game.setFlag(SAMPLE, true)
        game.setFlag(OTHER, true)

        block.refresh(memory).map { it.flagId }.toSet() shouldBe setOf(SAMPLE, OTHER)
    }

    test("a bit that belongs to no flag is not reported as one") {
        val game = FakeGame().apply { plantFlagBlock() }
        val memory = connected(game)
        val block = EventFlagBlock().apply { refresh(memory) }

        // The tail of a section addresses numbers past 999, which no id can name
        game.int(BLOCK_AT + 0x57C, 0x00800000)

        block.refresh(memory).shouldBeEmpty()
    }

    test("a block that cannot be reached reports nothing and says so") {
        val game = FakeGame()
        val block = EventFlagBlock()

        assertSoftly {
            block.refresh(connected(game)).shouldBeEmpty()
            block.isPresent shouldBe false
            block.isSet(SAMPLE) shouldBe null
        }
    }
})