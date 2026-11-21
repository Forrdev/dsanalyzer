package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.CodeMemory
import com.sappyoak.dsanalyzer.native.process.CodePatch
import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.ResolvedPointers
import com.sappyoak.dsanalyzer.runtime.ptde.AttackMan
import com.sappyoak.dsanalyzer.runtime.ptde.PTDESignatures
import com.sappyoak.dsanalyzer.runtime.ptde.ptdeLayout
import com.sappyoak.dsanalyzer.runtime.write.SessionContext
import com.sappyoak.dsanalyzer.runtime.snapshot.DrawInstall
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde.PTDEDrawBackend

private const val ATTACK_GLOBAL = 0x100
private const val ATTACK_MAN = 0x200
private const val SETUP = 0x300
private const val ARENA = 0x30000

/** Hands out the end of the fake game and patches straight into it */
private class ArenaMemory(private val game: FakeGame) : CodeMemory {
    private var next = ARENA

    override fun allocate(size: Int): Address = game.address(next).also { next += size }
    override fun free(address: Address): Boolean = true
    override fun patch(patches: List<CodePatch>): Boolean = patches.all { game.write(it.address, it.bytes) }
}

private class Engine(backend: (FakeGame) -> DrawRoute) {
    val game = FakeGame().apply {
        pointer(ATTACK_GLOBAL, ATTACK_MAN)
        write(SETUP, 1, 2, 3, 4)
    }
    val writer = GameWriter(game, ArenaMemory(game))
    private val pointers = ResolvedPointers(mapOf(PTDESignatures.AttackMan to game.address(ATTACK_GLOBAL)), emptyList())
    private val context = SessionContext(
        GameEdition.PrepareToDie, GameMemory(game, pointers), writer, game.module, ptdeLayout(), PTDEDrawBackend
    ) { null }
    val draw = EngineDrawWriter(context, checkNotNull(ptdeLayout().debugDraw)) { _, _ -> backend(game) }

    fun bytes(offset: Int, length: Int): List<Byte> = game.read(game.address(offset), length)!!.toList()
}

private fun retarget(game: FakeGame): DrawRoute =
    DrawRoute.Ready(listOf(DrawEdit(game.address(SETUP), byteArrayOf(9, 9, 9, 9))), caves = emptyList())

class EngineDrawWriterTest : FunSpec({
    test("a backend's edits go in on install and back on uninstall") {
        with(Engine(::retarget)) {
            draw.install() shouldBe DrawInstall.Installed
            draw.installed shouldBe true
            bytes(SETUP, 4) shouldBe listOf<Byte>(9, 9, 9, 9)

            draw.uninstall() shouldBe true
            bytes(SETUP, 4) shouldBe listOf<Byte>(1, 2, 3, 4)
            writer.active.shouldBeEmpty()
        }
    }

    test("an edition with no route to the screen installs nothing and says why") {
        with(Engine { DrawRoute.Unavailable("no plan") }) {
            draw.install() shouldBe DrawInstall.Failed("no plan")
            draw.installed shouldBe false
            writer.active.shouldBeEmpty()
        }
    }

    test("the hitbox draw is a journaled byte that needs no install") {
        with(Engine(::retarget)) {
            draw.setHitboxes(true) shouldBe true
            bytes(ATTACK_MAN + AttackMan.HitboxDraw, 1) shouldBe listOf<Byte>(1)
            draw.setHitboxes(false) shouldBe true
            bytes(ATTACK_MAN + AttackMan.HitboxDraw, 1) shouldBe listOf<Byte>(0)
        }
    }
})
