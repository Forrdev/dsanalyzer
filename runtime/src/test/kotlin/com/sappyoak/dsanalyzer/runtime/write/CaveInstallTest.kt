package com.sappyoak.dsanalyzer.runtime.write

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.process.CodeMemory
import com.sappyoak.dsanalyzer.native.process.CodePatch
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.X86
import com.sappyoak.dsanalyzer.runtime.inject.X86Cave
import com.sappyoak.dsanalyzer.runtime.inject.callTarget
import com.sappyoak.dsanalyzer.runtime.inject.code
import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey

private const val SITE_A = 0x100
private const val SITE_B = 0x200
private const val ARENA = 0x30000

private val PROLOGUE = code(0x55, 0x8B, 0xEC, 0x83, 0xEC, 0x10)

/** The mark ahead of every cave, and the first cave's mark and code together: the prologue then a jump back */
private val MARK = CAVE_MARK.size
private val MARKED_A = MARK + PROLOGUE.size + 5

/** Allocates from one arena at the end of the fake game, and patches straight into it */
private class ArenaCode(private val game: FakeGame) : CodeMemory {
    private var next = ARENA
    var batches = 0
        private set

    override fun allocate(size: Int): Address = game.address(next).also { next += size }
    override fun free(address: Address): Boolean = true
    override fun patch(patches: List<CodePatch>): Boolean {
        batches++
        return patches.all { game.write(it.address, it.bytes) }
    }
}

/** Re-runs the prologue and returns: the smallest cave there is */
private class Passthrough(name: String, marker: Int, expected: ByteArray = PROLOGUE) : X86Cave() {
    override val key = WriteKey(name)
    override val site = HookSite(Signature.parse(name, "AA BB %02X 55 8B EC".format(marker)), 3, expected)

    override fun X86.emit(at: Address) {
        raw(site.expected)
        jmp(at + site.displaced.toLong())
    }
}

private class CaveFixture {
    val game = FakeGame().apply {
        write(SITE_A, 0xAA, 0xBB, 0x01, 0x55, 0x8B, 0xEC, 0x83, 0xEC, 0x10)
        write(SITE_B, 0xAA, 0xBB, 0x02, 0x55, 0x8B, 0xEC, 0x83, 0xEC, 0x10)
    }
    val code = ArenaCode(game)
    val writer = GameWriter(game, code)

    fun bytes(offset: Int, length: Int): List<Byte> = game.read(game.address(offset), length)!!.toList()
}

@OptIn(GameMutation::class)
class CaveInstallTest : FunSpec({
    test("every cave goes in under one suspension, each jump into its own code") {
        with(CaveFixture()) {
            writer.installCaves(game, game.module, listOf(Passthrough("a", 1), Passthrough("b", 2))) shouldBe CaveResult.Installed
            code.batches shouldBe 1
            bytes(SITE_A + 3, 1) shouldBe listOf(0xE9.toByte())
            val caveB = game.address(ARENA + MARKED_A + MARK)
            bytes(SITE_B + 3, 6) shouldBe X86.jumpPatch(game.address(SITE_B + 3), caveB, 6).toList()
            writer.revertAll() shouldBe 2
            bytes(SITE_A + 3, 6) shouldBe PROLOGUE.toList()
        }
    }

    test("one site that does not hold its bytes leaves every site alone") {
        with(CaveFixture()) {
            val stale = Passthrough("b", 2, expected = code(0x90, 0x90, 0x90, 0x90, 0x90, 0x90))
            writer.installCaves(game, game.module, listOf(Passthrough("a", 1), stale))
                .shouldBeInstanceOf<CaveResult.Failed>()
            code.batches shouldBe 0
            bytes(SITE_A + 3, 6) shouldBe PROLOGUE.toList()
            writer.active.shouldBeEmpty()
        }
    }

    test("caves a session left in the game are taken back by the next, which installs its own") {
        with(CaveFixture()) {
            val caves = listOf(Passthrough("a", 1), Passthrough("b", 2))
            writer.installCaves(game, game.module, caves) shouldBe CaveResult.Installed
            val left = bytes(SITE_A + 3, 6)

            val next = GameWriter(game, code)
            next.installCaves(game, game.module, caves) shouldBe CaveResult.Installed
            bytes(SITE_A + 3, 1) shouldBe listOf(0xE9.toByte())
            bytes(SITE_A + 3, 6) shouldNotBe left

            next.revertAll() shouldBe 2
            bytes(SITE_A + 3, 6) shouldBe PROLOGUE.toList()
        }
    }

    test("another tool's hook at a site is left where it is") {
        with(CaveFixture()) {
            val foreign = X86.jumpPatch(game.address(SITE_A + 3), game.address(ARENA + 0x800), 6)
            game.write(game.address(SITE_A + 3), foreign)

            writer.installCaves(game, game.module, listOf(Passthrough("a", 1)))
                .shouldBeInstanceOf<CaveResult.Failed>()
            bytes(SITE_A + 3, 6) shouldBe foreign.toList()
        }
    }

    test("the target of a relative call") {
        callTarget(Address(0x01020A54), code(0xE8, 0x57, 0x0C, 0x52, 0xFF)) shouldBe Address(0x005416B0)
    }
})
