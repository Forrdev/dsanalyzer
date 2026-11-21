package com.sappyoak.dsanalyzer.runtime.edition

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.runtime.dsr.DSRPointers
import com.sappyoak.dsanalyzer.runtime.dsr.DSRVersion
import com.sappyoak.dsanalyzer.runtime.dsr.dsrLayout
import com.sappyoak.dsanalyzer.runtime.snapshot.RuntimeFeature
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.dsr.DSRDrawBackend

public val DSREdition: Edition = Edition(
    game = GameEdition.Remastered,
    pointers = DSRPointers,
    layoutFor = { module -> dsrLayout(versionOf(module)) },
    build = { _, module -> versionOf(module).name },
    draw = DSRDrawBackend,
    supports = RuntimeFeature.entries.toSet()
)

/** The build, told apart by the module's size */
private fun versionOf(module: AddressRange): DSRVersion = DSRVersion.from(module.size.toInt())
