package com.sappyoak.dsanalyzer.game.world.scripts.index

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.emevd.ArgData
import com.sappyoak.dsanalyzer.formats.emevd.EventCall
import com.sappyoak.dsanalyzer.formats.emevd.emedf.ArgType
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAddressing
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagTarget
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagUse
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

private const val TEMPLATE = 900L
private const val CALLER = 50L
private const val HEADER = 8

/** An 'InitializeEvent' of [TEMPLATE] in [slot], handing it [flag] */
private fun call(slot: Int, flag: Int): FoundCall {
    val args = ByteBuffer.allocate(HEADER + 4).order(ByteOrder.LITTLE_ENDIAN)
        .putInt(slot).putInt(TEMPLATE.toInt()).putInt(flag)
    return FoundCall(ScriptId.Common, CALLER, EventCall(slot, TEMPLATE, ArgData(args.array())))
}

private val CALLS = EventCallIndex.of(
    found = listOf(call(0, 11010100), call(1, 11010200)),
    needs = mapOf(EventKey(ScriptId.Common, TEMPLATE) to 4),
    scripts = 1
)

private val PASSED = FlagTarget.FromCaller(0, ArgType.UInt, FlagAddressing.Absolute)

private fun uses(target: FlagTarget) =
    listOf(FoundFlagUse(ScriptId.Common, TEMPLATE, FlagUse(target, FlagAccess.Writes, "SetFlagState")))

class CallerFlagsTest : FunSpec({
    test("a flag the caller hands in is found under each caller's flag, naming the caller") {
        val index = FlagIndex.of(uses(PASSED), scripts = 1, calls = CALLS)

        assertSoftly {
            index[11010100].single().caller shouldBe EventKey(ScriptId.Common, CALLER)
            index[11010200].single().access shouldBe FlagAccess.Writes
            index.parameterized shouldBe 0
        }
    }

    test("a slot-relative flag counts from the event's id and each caller's slot") {
        val index = FlagIndex.of(uses(FlagTarget.SlotRelative(5)), scripts = 1, calls = CALLS)

        index.flagIds shouldBe setOf((TEMPLATE + 5).toInt(), (TEMPLATE + 6).toInt())
    }

    test("with no caller to follow the reference is only counted") {
        val index = FlagIndex.of(uses(PASSED), scripts = 1)

        assertSoftly {
            index.flagIds.shouldBeEmpty()
            index.parameterized shouldBe 1
        }
    }
})
