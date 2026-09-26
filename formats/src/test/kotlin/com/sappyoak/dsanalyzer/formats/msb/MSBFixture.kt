package com.sappyoak.dsanalyzer.formats.msb

import com.sappyoak.dsanalyzer.formats.FixtureWriter
import com.sappyoak.dsanalyzer.formats.entry

internal fun model(name: String, typeCode: Int): ByteArray = entry {
    val nameSlot = slot()
    int(typeCode).int(0)
    val sibSlot = slot()
    int(1).zeros(3)
    fill(nameSlot).string(name)
    fill(sibSlot).string("").pad()
}

internal fun region(name: String, shapeCode: Int, dimensions: FloatArray, entityId: Int): ByteArray = entry {
    val nameSlot = slot()
    int(0).int(0).int(shapeCode)
    float(1f, 2f, 3f, 0f, 90f, 0f)
    val emptySlots = List(2) { slot() }
    val shapeSlot = slot()
    val entitySlot = slot()
    int(0)
    fill(nameSlot).string(name).pad()
    emptySlots.forEach { fill(it).int(0) }
    if (dimensions.isNotEmpty()) fill(shapeSlot).float(*dimensions)
    fill(entitySlot).int(entityId)
}

internal fun part(
    name: String,
    typeCode: Int,
    modelIndex: Int,
    drawGroups: IntArray = IntArray(4),
    typeData: FixtureWriter.() -> Unit
): ByteArray = entry {
    val nameSlot = slot()
    int(typeCode).int(0).int(modelIndex)
    val sibSlot = slot()
    float(1f, 2f, 3f, 0f, 0f, 0f, 1f, 1f, 1f)
    drawGroups.forEach(::int)
    zeros(4)
    val entitySlot = slot()
    val typeSlot = slot()
    int(0)
    fill(nameSlot).string(name)
    fill(sibSlot).string("").pad()
    fill(entitySlot).int(-1)
    repeat(10) { byte(-1) }
    byte(0)
    repeat(7) { byte(1) }
    byte(0).byte(0)
    fill(typeSlot).typeData()
}

internal fun msbFile(vararg lists: Pair<String, List<ByteArray>>): ByteArray = FixtureWriter().apply {
    lists.forEachIndexed { index, (name, entries) ->
        int(0)
        val nameSlot = slot()
        int(entries.size + 1)
        val entrySlots = entries.map { slot() }
        val nextSlot = slot()
        fill(nameSlot).string(name).pad()
        entries.zip(entrySlots).forEach { (bytes, slot) -> fill(slot).bytes(bytes) }
        if (index < lists.lastIndex) fill(nextSlot)
    }
}.toByteArray()