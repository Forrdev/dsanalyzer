package com.sappyoak.dsanalyzer.runtime.pointers

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.assertFailsWith

import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val SIGNATURE_AT = 0x100
private const val GLOBAL_AT = 0x800
private const val STRUCT_AT = 0x900

private const val HEALTH = 0x00
private const val SPEED = 0x04
private const val POSITION = 0x08
private const val TEAM = 0x14
private const val FLAGS = 0x18
private const val NO_DEAD = 0x00000020

private val CHARACTER = pointer("Character", Lifetime.World, listOf(0L), size = 0x20)

private fun game(): FakeGame = FakeGame().apply {
    plantSignature(SIGNATURE_AT, GLOBAL_AT)
    pointer(GLOBAL_AT, STRUCT_AT)
    int(STRUCT_AT + HEALTH, 837)
    float(STRUCT_AT + SPEED, 1.5f)
    float(STRUCT_AT + POSITION, 10f)
    float(STRUCT_AT + POSITION + 4, -20.5f)
    float(STRUCT_AT + POSITION + 8, 30f)
    write(STRUCT_AT + TEAM, 0xC8)
    int(STRUCT_AT + FLAGS, NO_DEAD)
}

private fun memoryOf(game: FakeGame): GameMemory =
    GameMemory(game, game.resolvePointers(listOf(CHARACTER), game.module))


class StructViewTest : FunSpec({
    test("one fetch serves every field") {
        val game = game()
        val view = StructView(CHARACTER)

        val fetched = view.refresh(memoryOf(game))

        assertSoftly {
            fetched shouldBe true
            view.address shouldBe game.address(STRUCT_AT)
            view.int(HEALTH) shouldBe 837
            view.float(SPEED) shouldBe 1.5f
            view.vec3(POSITION) shouldBe Vec3(10f, -20.5f, 30f)
            view.unsigned(TEAM) shouldBe 0xC8
            view.flag(FLAGS, NO_DEAD) shouldBe true
        }
    }

    test("a fetched structure costs one read however many fields are taken from it") {
        val game = game()
        val memory = memoryOf(game)
        val view = StructView(CHARACTER)

        view.refresh(memory)
        val afterFetch = game.reads
        repeat(10) { view.int(HEALTH) }

        game.reads shouldBe afterFetch
    }

    test("fields come from the moment of the fetch, not from whenever they are read") {
        val game = game()
        val memory = memoryOf(game)
        val view = StructView(CHARACTER)
        view.refresh(memory)

        game.float(STRUCT_AT + POSITION, 999f)

        assertSoftly {
            view.vec3(POSITION) shouldBe Vec3(10f, -20.5f, 30f)
            view.refresh(memory) shouldBe true
            view.vec3(POSITION) shouldBe Vec3(999f, -20.5f, 30f)
        }
    }

    test("a fetch that fails leaves nothing readable behind it") {
        val game = game()
        val memory = memoryOf(game)
        val view = StructView(CHARACTER)
        view.refresh(memory)

        game.nullPointer(GLOBAL_AT)
        memory.invalidate(Lifetime.World)

        assertSoftly {
            view.refresh(memory) shouldBe false
            view.isPresent shouldBe false
            assertFailsWith<IllegalStateException> { view.int(HEALTH) }
        }
    }
})