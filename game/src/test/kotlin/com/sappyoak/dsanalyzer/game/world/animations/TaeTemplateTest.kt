package com.sappyoak.dsanalyzer.game.world.animations

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.tae.template.EventBank
import com.sappyoak.dsanalyzer.formats.tae.template.ParamType

private val CHARACTERS = loadTaeTemplate(EventBank.Character)
private val OBJECTS = loadTaeTemplate(EventBank.Object)

/** The event the flag windows live on, and the value this whole slice exists to be able to find */
private const val CHR_ACTION_FLAG = 0
private const val INVINCIBLE = 8L

class TaeTemplatesTest : FunSpec({
    test("both banks load and know which they are") {
        assertSoftly {
            CHARACTERS.bank shouldBe EventBank.Character
            CHARACTERS.size shouldBeGreaterThan 100
            OBJECTS.bank shouldBe EventBank.Object
            OBJECTS.size shouldBeGreaterThan 0
        }
    }

    test("the flag event is named, and its parameters account for their bytes") {
        val definition = checkNotNull(CHARACTERS[CHR_ACTION_FLAG])

        assertSoftly {
            definition.name shouldBe "ChrActionFlag"
            definition.params.map { it.type } shouldBe listOf(
                ParamType.S32,
                ParamType.F32,
                ParamType.S32,
                ParamType.U8,
                ParamType.U8,
                ParamType.S16
            )
            definition.packedSize shouldBe 16
        }
    }

    /**
     * The windows a glitch is usually hiding in are values of this one parameter rather than
     * events of their own, so losing the value names would lose most of what the file is for
     */
    test("the flag values name the windows worth looking for") {
        val flagType = checkNotNull(CHARACTERS[CHR_ACTION_FLAG]).params.first()

        assertSoftly {
            flagType.name shouldBe "FlagType"
            flagType.nameOf(INVINCIBLE)?.name shouldBe "Invincible"
            flagType.nameOf(19L)?.name shouldBe "Disable Map/Object Collision"
            flagType.nameOf(27L)?.name shouldBe "Ignore Gravity"
            flagType.values.size shouldBeGreaterThan 50
        }
    }

    test("a value whose meaning depends on the others says so") {
        val flagType = checkNotNull(CHARACTERS[CHR_ACTION_FLAG]).params.first()

        flagType.nameOf(3L)?.note shouldBe "ArgB:Guard BehaviorJudgeID"
    }

    /**
     * A parameter the templates only assert a value for has no name but still takes its bytes, so
     * dropping it would misalign everything after it
     */
    test("unnamed parameters are kept, because their bytes are real") {
        val unnamed = CHARACTERS.definitions.flatMap { it.params }.filter { it.name == null }

        assertSoftly {
            unnamed.shouldNotBeEmpty()
            unnamed.all { it.type.size > 0 } shouldBe true
        }
    }

    /**
     * Pinned exactly rather than loosely, because these are committed files: a count that moves
     * means either a regeneration worth looking at, or two events collapsing onto one type
     */
    test("every bundled event is reachable by its own type") {
        assertSoftly {
            CHARACTERS.definitions.size shouldBe 114
            OBJECTS.definitions.size shouldBe 7
            CHARACTERS.definitions.all { CHARACTERS[it.type] === it } shouldBe true
            CHARACTERS[9_999] shouldBe null
        }
    }
})