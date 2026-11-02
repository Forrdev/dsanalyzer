package com.sappyoak.dsanalyzer.formats.esd

import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.binary.assertValue
import com.sappyoak.dsanalyzer.shared.binary.assertValues

/** 32-bit offsets (Prepare to Die) and 64-bit ones (Remastered); everything else is shared */
private const val NARROW_MAGIC = "fSSL"
private const val WIDE_MAGIC = "fsSL"

/** A condition with no target state, and offsets in a group's state table */
private const val NO_STATE = -1L

public fun readEsd(reader: BinaryReader): Esd {
    reader.order = ByteOrder.LITTLE_ENDIAN
    reader.position = 0
    val wide = reader.assertValues(NARROW_MAGIC, WIDE_MAGIC) { readAscii(4) } == WIDE_MAGIC
    val layout = Layout(wide)

    reader.assertValue(1) { readInt() }
    val darkSoulsCount = reader.assertValues(1, 2, 3) { readInt() }
    reader.assertValue(darkSoulsCount) { readInt() }
    reader.assertValue(0x54) { readInt() }
    reader.skip(4L) // data size
    reader.assertValue(6) { readInt() }
    reader.assertValue(layout.headerSize) { readInt() }
    reader.assertValue(1) { readInt() }
    reader.assertValue(layout.groupSize) { readInt() }
    val groupCount = reader.readInt()
    reader.assertValue(layout.stateSize) { readInt() }
    reader.skip(4L) // state count, which includes the repeated first states
    reader.assertValue(layout.conditionSize) { readInt() }
    reader.skip(4L) // condition count
    reader.assertValue(layout.commandSize) { readInt() }
    reader.skip(4L) // command count
    reader.assertValue(layout.argumentSize) { readInt() }
    reader.skip(16L) // argument count, condition offsets offset and count, name block offset
    val nameLength = reader.readInt()
    reader.skip(16L) // two unknown offsets, each followed by a zero

    val body = Body(reader, reader.position, wide)
    reader.assertValue(1) { readInt() }
    reader.skip(16L) // four unknowns
    if (wide) reader.assertValue(0) { readInt() }

    val groupsOffset = body.varint()
    body.varint() // group count again
    val nameOffset = body.varint()
    body.varint() // name length again

    val name = if (nameLength > 0) reader.at(body.start + nameOffset) { readUTF16() } else null
    val groups = if (groupCount == 0) emptyList() else reader.at(body.start + groupsOffset) {
        List(groupCount) { body.group(layout.stateSize) }
    }
    return Esd(name, groups.sortedBy { it.id }.associateBy { it.id })
}


private class Layout(wide: Boolean) {
    val headerSize = if (wide) 0x48 else 0x2C
    val groupSize = if (wide) 0x20 else 0x10
    val stateSize = if (wide) 0x48 else 0x24
    val conditionSize = if (wide) 0x38 else 0x1C
    val commandSize = if (wide) 0x18 else 0x10
    val argumentSize = if (wide) 0x10 else 0x08
}

private class Body(private val reader: BinaryReader, val start: Long, private val wide: Boolean) {
    fun varint(): Long = if (wide) reader.readLong() else reader.readInt().toLong()

    fun group(stateSize: Int): StateGroup {
        val id = varint()
        val statesOffset = varint()
        val count = varint().toInt()
        if (varint() != statesOffset) {
            throw BinaryFormatException("State group $id gives two different state offsets", reader.position)
        }

        val offsets = List(count) { statesOffset + it * stateSize }
        val ids = offsets.associateWith { offset -> reader.at(start + offset) { varint() } }
        val states = offsets.map { offset -> reader.at(start + offset) { state(ids) } }
        return StateGroup(id, states.associateBy { it.id })
    }

    private fun state(ids: Map<Long, Long>): EsdState {
        val id = varint()
        val conditions = list { varint() }.map { condition(it, ids) }
        // The file's order is entry, exit, while; named arguments evaluate in the order written
        return EsdState(
            id = id,
            conditions = conditions,
            entryCommands = commands(),
            exitCommands = commands(),
            whileCommands = commands()
        )
    }

    private fun condition(offset: Long, ids: Map<Long, Long>): EsdCondition = reader.at(start + offset) {
        val stateOffset = varint()
        val passCommands = commands()
        val subconditions = list { varint() }.map { condition(it, ids) }
        val evaluator = bytecode()

        val target = if (stateOffset == NO_STATE) null else ids[stateOffset]
            ?: throw BinaryFormatException("A condition leads to no state of its group", start + offset)
        EsdCondition(target, evaluator, passCommands, subconditions)
    }

    private fun commands(): List<CommandCall> = list {
        val bank = reader.assertValues(1, 5, 6, 7) { readInt() }
        val id = reader.readInt()
        CommandCall(bank, id, list { bytecode() })
    }

    /** An offset and a count, then that many items read where the offset points */
    private fun <T> list(item: () -> T): List<T> {
        val offset = varint()
        val count = varint().toInt()
        return reader.at(start + offset) { List(count) { item() } }
    }

    private fun bytecode(): Bytecode {
        val offset = varint()
        val length = varint().toInt()
        return Bytecode(reader.at(start + offset) { readBytes(length) })
    }
}
