package com.sappyoak.dsanalyzer.game.world.flags

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.esd.Bytecode
import com.sappyoak.dsanalyzer.formats.esd.CommandCall
import com.sappyoak.dsanalyzer.formats.esd.EsdCondition
import com.sappyoak.dsanalyzer.formats.esd.EsdState
import com.sappyoak.dsanalyzer.formats.esd.StateGroup

private const val END = 0xA1
private const val BIAS = 64
private const val CALL_NO_ARGS = 0x84
private const val SET_R0 = 0xA7
private const val GET_R0 = 0xAF
private const val EQUAL = 0x95
private const val PICKED_ENTRY = 23
private const val PURCHASE = 15000010

private fun code(vararg bytes: Int) = Bytecode(ByteArray(bytes.size) { bytes[it].toByte() })

private fun int(value: Int): Bytecode =
    Bytecode(ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN).put(0x82.toByte()).putInt(value).put(END.toByte()).array())

/** `r0 = GetTalkListEntryResult()`, folded into a test as the game does */
private val PICK = code(PICKED_ENTRY + BIAS, CALL_NO_ARGS, SET_R0, BIAS, EQUAL, END)
private val PICKED_SLOT_2 = code(GET_R0, 2 + BIAS, EQUAL, END)
private val ALWAYS = code(1 + BIAS, END)

private fun state(id: Long, vararg to: Pair<Long, Bytecode>, entry: List<CommandCall> = emptyList()) =
    EsdState(id, to.map { (target, test) -> EsdCondition(target, test, emptyList(), emptyList()) }, entry, emptyList(), emptyList())

private val GROUP = StateGroup(
    1,
    listOf(
        state(1, 2L to PICK, entry = listOf(CommandCall(TALK_BANK, ADD_TALK_LIST, listOf(code(2 + BIAS, END), int(PURCHASE))))),
        state(2, 3L to PICKED_SLOT_2, 4L to ALWAYS),
        state(3),
        state(4)
    ).associateBy { it.id }
)

class TalkGraphTest : FunSpec({
    test("a state reached by testing the picked slot is named by the option added under that slot") {
        TalkGraph(GROUP).choiceInto(3) shouldBe PURCHASE
    }

    test("a state no choice leads to has no option, but its predecessors are known") {
        val graph = TalkGraph(GROUP)

        assertSoftly {
            graph.choiceInto(4).shouldBeNull()
            graph.before(4) shouldContainExactly listOf(2L)
        }
    }
})
