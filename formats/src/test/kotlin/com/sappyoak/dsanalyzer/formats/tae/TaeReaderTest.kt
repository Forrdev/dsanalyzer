package com.sappyoak.dsanalyzer.formats.tae

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

private val TAE = readTae(ptdeTae())

class TaeReaderTest : FunSpec({
    test("the header names the file and what it was authored against") {
        assertSoftly {
            TAE.id shouldBe TAE_ID
            TAE.bigEndian shouldBe false
            TAE.skeletonName shouldBe SKELETON
            TAE.sibName shouldBe SIB
            TAE.animations.map { it.id } shouldBe listOf(OWN_ANIMATION, IMPORTING_ANIMATION)
        }
    }

    test("an event covers the window the times table gives it") {
        val events = checkNotNull(TAE[OWN_ANIMATION]).events

        assertSoftly {
            events.map { it.type } shouldBe listOf(FLAG_EVENT, WEAPON_STYLE_EVENT)
            events[0].startTime shouldBe 0.0f
            events[0].endTime shouldBe 0.5f
            events[0].duration shouldBe 0.5f
            events[1].startTime shouldBe 0.5f
            events[1].endTime shouldBe 1.0f
        }
    }

    /**
     * Nothing in the file says how long an event's parameters are, so the reader works it out from
     * whatever block comes next. This animation has no event groups, which is the case where the
     * last event has to be bounded by something else entirely
     */
    test("parameter lengths are derived from the next block along") {
        val events = checkNotNull(TAE[OWN_ANIMATION]).events

        assertSoftly {
            events[0].params.size shouldBe FLAG_PARAM_BYTES
            events[1].params.size shouldBe WEAPON_STYLE_PARAM_BYTES
        }
    }

    test("an animation with its own motion carries its flags and the file it plays") {
        val animation = checkNotNull(TAE[OWN_ANIMATION])
        val source = animation.source.shouldBeInstanceOf<AnimationSource.Own>()

        assertSoftly {
            animation.fileName shouldBe MOTION_FILE
            source.loopsByDefault shouldBe true
            source.importsHkx shouldBe false
            source.allowsDelayLoad shouldBe false
            source.hkxSourceAnimationId shouldBe 0
        }
    }

    test("an importing animation lists no events of its own and names no file") {
        val animation = checkNotNull(TAE[IMPORTING_ANIMATION])
        val source = animation.source.shouldBeInstanceOf<AnimationSource.Imported>()

        assertSoftly {
            animation.events shouldBe emptyList()
            animation.fileName shouldBe null
            source.fromAnimationId shouldBe OWN_ANIMATION.toInt()
        }
    }

    test("the events an animation plays are the ones it imports") {
        assertSoftly {
            TAE.eventsFor(IMPORTING_ANIMATION) shouldBe TAE.eventsFor(OWN_ANIMATION)
            TAE.eventsFor(IMPORTING_ANIMATION)?.map { it.type } shouldBe
                    listOf(FLAG_EVENT, WEAPON_STYLE_EVENT)
            TAE.eventsFor(9_999L) shouldBe null
        }
    }

    test("a file that is not a Dark Souls TAE is refused by name rather than misread") {
        assertSoftly {
            shouldThrow<BinaryFormatException> { readTae(ptdeTae(magic = "BND3")) }
                .message shouldBe "Not a TAE file, found 'BND3' (at 0x0"
            shouldThrow<BinaryFormatException> { readTae(ptdeTae(version = 0x1000C)) }
                .message shouldBe "TAE version 0x1000c is not Dark Souls (at 0x8"
            shouldThrow<BinaryFormatException> { readTae(ptdeTae(wide = true)) }
                .message shouldBe "TAE version 0x1000b is not Dark Souls (at 0x8"
        }
    }
})