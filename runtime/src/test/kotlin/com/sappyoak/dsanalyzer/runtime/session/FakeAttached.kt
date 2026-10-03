package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.native.process.AttachedProcess
import com.sappyoak.dsanalyzer.native.process.ModuleInfo
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame

/** A laid-out [FakeGame] presented as something a connection can be opened on */
internal class FakeAttached(
    override val info: ProcessInfo,
    private val game: FakeGame
) : AttachedProcess, ProcessMemory by game {
    override val isRunning: Boolean = true

    override fun modules(): List<ModuleInfo> = listOf(ModuleInfo(info.executableName, game.module))

    override fun close(): Unit = Unit
}