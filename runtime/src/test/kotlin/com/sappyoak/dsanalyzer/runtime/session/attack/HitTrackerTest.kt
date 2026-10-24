package com.sappyoak.dsanalyzer.runtime.session.attack

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.read.attack.HitKey
import com.sappyoak.dsanalyzer.runtime.read.attack.HitRead
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val KNIGHT = 0x10030035

private val SWING = HitKey(Address(0x1000), id = 1)
private val OTHER = HitKey(Address(0x2000), id = 2)

/** The swing's capsule with its tip [step] units along x */
private fun at(step: Int, key: HitKey = SWING) =
    HitRead(key, KNIGHT, 241002, HitShape.Capsule(Vec3(0f, 1f, 0f), Vec3(step.toFloat(), 2f, 0f), 0.4f))

class HitTrackerTest : FunSpec({
    test("each tick a hit moves adds its shape to the sweep, and a tick it holds still adds nothing") {
        val tracker = HitTracker(lingerTicks = 2, sweepLimit = 8)
        tracker.update(listOf(at(0)))
        tracker.update(listOf(at(1)))
        val hit = tracker.update(listOf(at(1))).single()

        assertSoftly {
            hit.sweep shouldBe listOf(at(0).shape, at(1).shape)
            hit.live shouldBe true
            hit.atkParamId shouldBe 241002
        }
    }

    test("a closed hit lingers, no longer live, and then goes") {
        val tracker = HitTracker(lingerTicks = 2, sweepLimit = 8)
        tracker.update(listOf(at(0)))

        val lingering = tracker.update(emptyList())
        tracker.update(emptyList())

        assertSoftly {
            lingering.single().live shouldBe false
            lingering.single().sweep shouldBe listOf(at(0).shape)
            tracker.update(emptyList()) shouldBe emptyList()
        }
    }

    test("an AtkParam id written after the hit first listed is the one reported") {
        val tracker = HitTracker(lingerTicks = 2, sweepLimit = 8)
        tracker.update(listOf(at(0).copy(atkParamId = -1)))

        tracker.update(listOf(at(1))).single().atkParamId shouldBe 241002
    }

    test("a long hit keeps only its latest shapes") {
        val tracker = HitTracker(lingerTicks = 2, sweepLimit = 3)
        val hit = (0 until 6).map { tracker.update(listOf(at(it))) }.last().single()

        hit.sweep shouldBe (3 until 6).map { at(it).shape }
    }

    test("two hits at once sweep apart, and a load drops both") {
        val tracker = HitTracker(lingerTicks = 2, sweepLimit = 8)
        tracker.update(listOf(at(0), at(5, OTHER)))
        val both = tracker.update(listOf(at(1), at(6, OTHER)))
        tracker.clear()

        assertSoftly {
            both.map { it.id } shouldBe listOf(1, 2)
            both.map { it.sweep.size } shouldBe listOf(2, 2)
            tracker.update(emptyList()) shouldBe emptyList()
        }
    }
})
