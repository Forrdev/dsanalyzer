package com.sappyoak.dsanalyzer.runtime.write.position

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.LivePositionLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.shared.math.Vec3

// need to make this shared somewhere
private const val POINTER_ROOM = 8

public data class PlayerBody(public val chrCtrl: Address, public val posData: Address, public val proxy: Address)

internal fun locateBody(memory: GameMemory, layout: LivePositionLayout): PlayerBody? {
    val chrCtrl = memory.addressOf(layout.chrCtrl).takeUnless { it.isNull } ?: return null
    val view = StructView(POINTER_ROOM)
    val posData = view.pointerAt(memory.memory, chrCtrl + layout.posData.toLong()).takeUnless { it.isNull } ?: return null
    val proxy = view.pointerAt(memory.memory, posData + layout.proxy.toLong()).takeUnless { it.isNull } ?: return null
    return PlayerBody(chrCtrl, posData, proxy)
}

internal fun readPlace(
    memory: GameMemory,
    layout: LivePositionLayout,
    body: PlayerBody
): Pair<Vec3, Float>? {
    val view = StructView(layout.position + 3 * Float.SIZE_BYTES)
    if (!view.refresh(memory.memory, body.posData)) return null
    return view.vec3(layout.position) to view.float(layout.angle)
}
