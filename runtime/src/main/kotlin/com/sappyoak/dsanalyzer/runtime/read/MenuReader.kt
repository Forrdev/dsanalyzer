package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.MenuMan
import com.sappyoak.dsanalyzer.runtime.ptde.MenuOpenMask
import com.sappyoak.dsanalyzer.runtime.session.MenuSnapshot

internal class MenuReader {
    private val view = StructView(MenuMan.Pointer)

    fun read(memory: GameMemory): MenuSnapshot? {
        if (!view.refresh(memory)) return null

        val mask = MenuOpenMask.of(view::int)
        val boxKind = view.int(MenuMan.BoxKind)

        return MenuSnapshot(
            mask = mask,
            boxKind = boxKind,
            screen = view.int(MenuMan.Screen),
            // Applied here rather than on the snapshot: which masks cover the world is this
            // edition's rule, and the snapshot has to mean the same thing for one that differs
            defaultQuantity = view.int(MenuMan.DefaultQuantity),
            isBlocking = MenuOpenMask.blocks(mask, boxKind)
        )
    }
}