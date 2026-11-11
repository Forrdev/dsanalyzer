package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.runtime.layout.MenuLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.session.MenuOpenMask
import com.sappyoak.dsanalyzer.runtime.session.MenuScript
import com.sappyoak.dsanalyzer.runtime.session.MenuSnapshot

internal class MenuReader(private val layout: MenuLayout) {
    private val view = StructView(layout.pointer)

    fun read(memory: GameMemory): MenuSnapshot? {
        if (!view.refresh(memory)) return null

        val mask = MenuOpenMask.of(::entry)
        val boxKind = entry(MenuScript.BoxKind)
        return MenuSnapshot(
            mask = mask,
            boxKind = boxKind,
            screen = entry(MenuScript.Screen),
            defaultQuantity = entry(MenuScript.DefaultQuantity),
            isBlocking = MenuOpenMask.blocks(mask, boxKind),
            fading = entry(MenuScript.Fading) != 0,
            resting = entry(MenuScript.Resting) != 0
        )
    }

    private fun entry(index: Int): Int = view.int(layout.offsetOf(index))
}

