package com.sappyoak.dsanalyzer.runtime.write

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.CodeMemory
import com.sappyoak.dsanalyzer.native.process.CodePatch
import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

private const val DATA = 0x100
private const val SITE_A = 0x200
private const val SITE_B = 0x300

private val HOOK = code(0x51, 0xA1, 0x64, 0x55, 0x37)
private val JUMP = code(0xE9, 0x00, 0x00, 0x00, 0x00)

private fun code(vararg values: Int): ByteArray = ByteArray(values.size) { values[it].toByte() }

/** Code memory that writes the batch straight into the fake game; there are no threads to stop */
private class FakeCode(private val game: FakeGame) : CodeMemory {
    var batches = 0
        private set

    override fun allocate(size: Int): Address? = null
    override fun free(address: Address): Boolean = false
    override fun patch(patches: List<CodePatch>): Boolean {
        batches++
        return patches.all { game.write(it.address, it.bytes) }
    }
}

private class Fixture {
    val game = FakeGame().apply {
        write(DATA, 1, 2, 3, 4)
        write(SITE_A, 0x51, 0xA1, 0x64, 0x55, 0x37)
        write(SITE_B, 0x51, 0xA1, 0x64, 0x55, 0x37)
    }
    val code = FakeCode(game)
    val writer = GameWriter(game, code)

    fun bytes(offset: Int, length: Int): List<Byte> = game.read(game.address(offset), length)!!.toList()
    fun edit(name: String, offset: Int, expected: ByteArray = HOOK) =
        CodeEdit(WriteKey(name), game.address(offset), expected, JUMP)
}

@OptIn(GameMutation::class)
class GameWriterTest : FunSpec({
    test("a data write keeps the bytes it first found, through every rewrite") {
        with(Fixture()) {
            val key = WriteKey("data")
            writer.writeBytes(key, game.address(DATA), Lifetime.World, code(9, 9, 9, 9)) shouldBe true
            writer.writeBytes(key, game.address(DATA), Lifetime.World, code(8, 8, 8, 8)) shouldBe true
            writer.revert(key) shouldBe true
            bytes(DATA, 4) shouldBe code(1, 2, 3, 4).toList()
        }
    }

    test("a batch lands in one suspension and reverts to the expected bytes") {
        with(Fixture()) {
            writer.patchCode(listOf(edit("a", SITE_A), edit("b", SITE_B))) shouldBe true
            code.batches shouldBe 1
            bytes(SITE_A, 5) shouldBe JUMP.toList()
            writer.revertAll() shouldBe 2
            bytes(SITE_B, 5) shouldBe HOOK.toList()
        }
    }

    test("one site that does not hold its expected bytes refuses the whole batch") {
        with(Fixture()) {
            val stale = edit("b", SITE_B, expected = code(0xE9, 0x10, 0x20, 0x30, 0x40))
            writer.patchCode(listOf(edit("a", SITE_A), stale)) shouldBe false
            code.batches shouldBe 0
            bytes(SITE_A, 5) shouldBe HOOK.toList()
            writer.active.shouldBeEmpty()
        }
    }

    test("a failed step puts back only what the steps changed, newest first") {
        with(Fixture()) {
            val before = WriteKey("before")
            writer.writeBytes(before, game.address(DATA + 8), Lifetime.World, code(7)) shouldBe true

            val done = writer.allOrNothing {
                writeBytes(WriteKey("data"), game.address(DATA), Lifetime.World, code(9, 9, 9, 9)) &&
                        patchCode(edit("a", SITE_A)) &&
                        patchCode(edit("b", SITE_B, expected = code(0, 0, 0, 0, 0)))
            }

            done shouldBe false
            bytes(DATA, 4) shouldBe code(1, 2, 3, 4).toList()
            bytes(SITE_A, 5) shouldBe HOOK.toList()
            writer.active shouldBe setOf(before)
        }
    }
})
