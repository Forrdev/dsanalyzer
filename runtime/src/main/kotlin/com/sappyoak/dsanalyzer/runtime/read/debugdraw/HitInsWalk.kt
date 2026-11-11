package com.sappyoak.dsanalyzer.runtime.read.debugdraw

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.HitInsLayout
import com.sappyoak.dsanalyzer.runtime.pointers.TableView

/** Every collision instance loaded under one 'WorldHitManImp' */
internal class HitInsWalk(layout: HitInsLayout) {
    private val areas = TableView(layout.areas)
    private val groups = TableView(layout.groups)
    private val instances = TableView(layout.instances)

    fun instancesOf(memory: ProcessMemory, manager: Address): List<Address> {
        if (manager.isNull || !areas.refresh(memory, manager)) return emptyList()
        return buildList {
            for (area in areas.elements.toList()) {
                if (!groups.refresh(memory, area)) continue
                for (group in groups.elements.toList()) {
                    if (instances.refresh(memory, group)) addAll(instances.elements)
                }
            }
        }
    }
}
