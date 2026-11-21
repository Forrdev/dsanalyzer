package com.sappyoak.dsanalyzer.runtime.read.target

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.snapshot.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.FocusRequest
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val KNIGHT = 0x10030035
private val ON = FocusRequest(enabled = true, range = 10f)
private val PLAYER = Vec3.Zero

private fun knight(at: Float = 5f, health: Int = 100) = CharacterSnapshot(
    handle = KNIGHT,
    modelId = 2410,
    npcParamId = 241000,
    position = Vec3(at, 0f, 0f),
    health = health,
    healthMax = 100,
    stamina = 100,
    characterType = 5,
    teamType = 6,
    targetHandle = null,
    effects = null
)

class FocusTrackerTest : FunSpec({
    test("the lock target is followed, and kept after the lock drops") {
        val tracker = FocusTracker()

        assertSoftly {
            tracker.resolve(ON, KNIGHT, listOf(knight()), PLAYER) shouldBe Focus(KNIGHT, locked = true)
            tracker.resolve(ON, null, listOf(knight()), PLAYER) shouldBe Focus(KNIGHT, locked = false)
        }
    }

    test("a kept target is let go once it dies, unloads or leaves the range, and does not come back") {
        listOf(listOf(knight(health = 0)), emptyList(), listOf(knight(at = 11f))).forEach { characters ->
            val tracker = FocusTracker().apply { resolve(ON, KNIGHT, listOf(knight()), PLAYER) }

            assertSoftly {
                tracker.resolve(ON, null, characters, PLAYER).shouldBeNull()
                tracker.resolve(ON, null, listOf(knight()), PLAYER).shouldBeNull()
            }
        }
    }

    test("nothing is followed while the request is off, and nothing is kept through it") {
        val tracker = FocusTracker()

        assertSoftly {
            tracker.resolve(FocusRequest.Off, KNIGHT, listOf(knight()), PLAYER).shouldBeNull()
            tracker.resolve(ON, null, listOf(knight()), PLAYER).shouldBeNull()
        }
    }
})
