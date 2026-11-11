package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.dsr.DSRVersion
import com.sappyoak.dsanalyzer.runtime.ptde.ImageHeader
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEVersion
import com.sappyoak.dsanalyzer.native.memory.intAt

internal fun readBuild(edition: GameEdition, memory: ProcessMemory, module: AddressRange): String? = when (edition) {
    GameEdition.PrepareToDie -> memory.ptdeVersion(module)?.name
    GameEdition.Remastered -> DSRVersion.from(module.size.toInt()).name
}

internal fun ProcessMemory.ptdeVersion(module: AddressRange): PTDEVersion? {
    val peOffset = intAt(module.start + ImageHeader.PeOffset)?.toUInt() ?: return null
    val timestamp = intAt(module.start + peOffset.toLong() + ImageHeader.TimeDateStamp)?.toUInt() ?: return null
    val richMarker = intAt(module.start + ImageHeader.RichMarker)?.toUInt() ?: return null
    return PTDEVersion.of(timestamp, richMarker)
}
