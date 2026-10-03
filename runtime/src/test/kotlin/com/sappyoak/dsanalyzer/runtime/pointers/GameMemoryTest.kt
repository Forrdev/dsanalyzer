package com.sappyoak.dsanalyzer.runtime.pointers

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.memory.Address

private const val SIGNATURE_AT = 0x100
private const val GLOBAL_AT = 0x800
private const val FIRST_AT = 0x900
private const val SECOND_AT = 0xA00

private fun game(): FakeGame = FakeGame().apply {
    plantSignature(SIGNATURE_AT, GLOBAL_AT)
    pointer(GLOBAL_AT, FIRST_AT)
    pointer(FIRST_AT + 0x10, SECOND_AT)
}

private fun memoryOf(game: FakeGame, vararg pointers: GamePointer): GameMemory =
    GameMemory(game, game.resolvePointers(pointers.toList(), game.module))

class GameMemoryTest : FunSpec({
    test("a walk follows every offset from where the signature landed") {
        val game = game()
        val nested = pointer("Nested", Lifetime.World, listOf(0L, 0x10L))

        memoryOf(game, nested).addressOf(nested) shouldBe game.address(SECOND_AT)
    }

    test("a walk already made is not made again") {
        val game = game()
        val target = pointer("Target", Lifetime.World, listOf(0L))
        val memory = memoryOf(game, target)
        val afterScan = game.reads

        memory.addressOf(target)
        val afterWalk = game.reads
        memory.addressOf(target)

        assertSoftly {
            afterWalk - afterScan shouldBe 1
            game.reads shouldBe afterWalk
        }
    }

    test("a volatile walk is made every time it is asked for") {
        val game = game()
        val target = pointer("Moving", Lifetime.Volatile, listOf(0L))
        val memory = memoryOf(game, target)
        val afterScan = game.reads

        memory.addressOf(target)
        memory.addressOf(target)

        assertSoftly {
            game.reads - afterScan shouldBe 2
            memory.cached shouldBe 0
        }
    }

    test("a world reload forgets the world but keeps the session") {
        val game = game()
        val world = pointer("Character", Lifetime.World, listOf(0L))
        val session = pointer("Singleton", Lifetime.Session, listOf(0L))
        val memory = memoryOf(game, world, session)

        memory.addressOf(world)
        memory.addressOf(session)
        memory.invalidate(Lifetime.World)

        val afterReload = game.reads
        memory.addressOf(session)
        val sessionCost = game.reads - afterReload
        memory.addressOf(world)

        assertSoftly {
            sessionCost shouldBe 0
            game.reads - afterReload shouldBe 1
        }
    }

    test("returning to the menu forgets the session and the world with it") {
        val game = game()
        val session = pointer("Singleton", Lifetime.Session, listOf(0L))
        val world = pointer("Character", Lifetime.World, listOf(0L))
        val memory = memoryOf(game, session, world)

        memory.addressOf(session)
        memory.addressOf(world)
        memory.invalidate(Lifetime.Session)

        memory.cached shouldBe 0
    }

    test("a pointer whose signature was not found has no address") {
        val game = game()
        val missing = pointer("Missing", Lifetime.World, listOf(0L), base = ABSENT)

        memoryOf(game, missing).addressOf(missing) shouldBe Address.Null
    }

    test("a null link gives no address, and is not remembered as one") {
        val game = game()
        val broken = pointer("Broken", Lifetime.World, listOf(0L, 0x20L))
        val memory = memoryOf(game, broken)

        assertSoftly {
            memory.addressOf(broken) shouldBe Address.Null
            memory.cached shouldBe 0
        }
    }
})