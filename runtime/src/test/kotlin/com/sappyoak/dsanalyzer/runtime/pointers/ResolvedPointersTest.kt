package com.sappyoak.dsanalyzer.runtime.pointers

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.memory.SignatureScan

private const val SIGNATURE_AT = 0x100
private const val GLOBAL_AT = 0x800

class ResolvedPointersTest : FunSpec({
    test("pointers sharing a signature are found by one scan") {
        val game = FakeGame().apply { plantSignature(SIGNATURE_AT, GLOBAL_AT) }
        val first = pointer("First", Lifetime.World, listOf(0L))
        val second = pointer("Second", Lifetime.World, listOf(0L, 0x10L))

        val resolved = game.resolvePointers(listOf(first, second), game.module)

        assertSoftly {
            resolved.resolved shouldBe 1
            resolved.complete shouldBe true
            resolved[first] shouldBe game.address(GLOBAL_AT)
            resolved[second] shouldBe resolved[first]
        }
    }

    test("a signature that is not in this build names the pointers it leaves unreadable") {
        val game = FakeGame().apply { plantSignature(SIGNATURE_AT, GLOBAL_AT) }
        val here = pointer("Here", Lifetime.Session, listOf(0L))
        val gone = pointer("Gone", Lifetime.Session, listOf(0L), base = ABSENT)

        val resolved = game.resolvePointers(listOf(here, gone), game.module)

        assertSoftly {
            resolved.complete shouldBe false
            resolved[here] shouldBe game.address(GLOBAL_AT)
            resolved[gone] shouldBe null
            resolved.unresolved.single().signature shouldBe "Absent"
            resolved.unresolved.single().outcome shouldBe SignatureScan.NotFound
            resolved.unresolved.single().pointers.shouldContainExactly("Gone")
        }
    }

    test("a signature matching twice is refused rather than guessed at") {
        val game = FakeGame().apply {
            plantSignature(SIGNATURE_AT, GLOBAL_AT)
            plantSignature(SIGNATURE_AT + 0x40, GLOBAL_AT)
        }

        val resolved = game.resolvePointers(listOf(pointer("Twice", Lifetime.World, listOf(0L))), game.module)

        assertSoftly {
            resolved.resolved shouldBe 0
            resolved.unresolved.single().outcome.shouldBe(
                SignatureScan.Ambiguous(listOf(game.address(SIGNATURE_AT), game.address(SIGNATURE_AT + 0x40)))
            )
        }
    }
})