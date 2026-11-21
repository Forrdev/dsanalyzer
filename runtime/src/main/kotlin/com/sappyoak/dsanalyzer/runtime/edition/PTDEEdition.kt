package com.sappyoak.dsanalyzer.runtime.edition

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.intAt
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.ptde.ImageHeader
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEPointers
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEVersion
import com.sappyoak.dsanalyzer.runtime.ptde.ptdeLayout
import com.sappyoak.dsanalyzer.runtime.snapshot.RuntimeFeature
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.ptde.PTDEDrawBackend

public val PTDEEdition: Edition = Edition(
    game = GameEdition.PrepareToDie,
    pointers = PTDEPointers,
    layoutFor = { ptdeLayout() },
    build = { memory, module -> memory.ptdeVersion(module)?.name },
    draw = PTDEDrawBackend,
    supports = RuntimeFeature.entries.toSet()
)

/** The build, told apart by the PE timestamp and the Rich header's marker */
private fun ProcessMemory.ptdeVersion(module: AddressRange): PTDEVersion? {
    val peOffset = intAt(module.start + ImageHeader.PeOffset)?.toUInt() ?: return null
    val timestamp = intAt(module.start + peOffset.toLong() + ImageHeader.TimeDateStamp)?.toUInt() ?: return null
    val richMarker = intAt(module.start + ImageHeader.RichMarker)?.toUInt() ?: return null
    return PTDEVersion.of(timestamp, richMarker)
}
