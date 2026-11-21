package com.sappyoak.dsanalyzer.formats.luainfo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly

import com.sappyoak.dsanalyzer.formats.FixtureWriter
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

private val SHIFT_JIS = charset("Shift_JIS")

/** One of each kind of record a real file holds: a common goal, a battle goal and a logic */
private val GOALS = listOf(
    LuaInfoGoal(2000, "Wait", noUpdate = false, logicInterrupt = false, interruptName = null),
    LuaInfoGoal(225000, "Hammer_m10_225000Battle", noUpdate = true, logicInterrupt = false, null),
    LuaInfoGoal(10100, "Gitai10100_Logic", noUpdate = false, logicInterrupt = true, "Gitai10100_Interupt")
)

/** The header, the records with their name offsets filled as each name is written, then the names */
private fun luaInfo(version: Int = 1, flag: Int? = null): ByteArray = FixtureWriter().apply {
    bytes("LUAI".toByteArray())
    int(version)
    int(GOALS.size)
    int(0)
    val slots = GOALS.map { goal ->
        int(goal.id)
        val name = slot()
        val interrupt = slot()
        byte(flag ?: if (goal.noUpdate) 1 else 0)
        byte(if (goal.logicInterrupt) 1 else 0)
        short(0)
        name to interrupt
    }
    GOALS.zip(slots).forEach { (goal, slot) ->
        fill(slot.first)
        bytes(goal.name.toByteArray(SHIFT_JIS) + 0)
        goal.interruptName?.let { fill(slot.second); bytes(it.toByteArray(SHIFT_JIS) + 0) }
    }
}.toByteArray()

class LuaInfoReaderTest : FunSpec({
    test("each record reads back with its names and flags") {
        readLuaInfo(BinaryReader.of(luaInfo())).goals shouldContainExactly GOALS
    }

    test("another version is refused") {
        shouldThrow<BinaryFormatException> { readLuaInfo(BinaryReader.of(luaInfo(version = 2))) }
    }

    test("a flag that is neither 0 nor 1 is refused") {
        shouldThrow<BinaryFormatException> { readLuaInfo(BinaryReader.of(luaInfo(flag = 2))) }
    }
})
