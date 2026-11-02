package com.sappyoak.dsanalyzer.app.nav

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

public class Navigator(start: View = Section.Data.landing) {
    public var history: History by mutableStateOf(History.of(start))
        private set

    public val current: View get() = history.current
    public val section: Section get() = history.section

    public fun go(view: View) {
        history = history.push(view)
    }

    public fun back() {
        history = history.back()
    }

    public fun forward() {
        history = history.forward()
    }

    public fun open(section: Section) {
        if (section == this.section) return
        go(history.lastIn(section) ?: section.landing)
    }
}

public val Section.landing: View
    get() = when (this) {
        Section.Analysis -> View.Analysis.Leads
        Section.Data -> View.Data.Overview
        Section.Runtime -> View.Live(RuntimePanel.Attachment)
    }
