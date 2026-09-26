package com.sappyoak.dsanalyzer.runtime

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

private val PTDE = ProcessInfo(10, "DARKSOULS.exe")
private val DSR = ProcessInfo(11, "DarkSoulsRemastered.exe")
private val OTHER = ProcessInfo(12, "explorer.exe")

class GameConnectionTest : FunSpec({
    test("finds only the requested editions, ignoring executable case") {
        val processes = FakeProcesses().apply { running.addAll(listOf(PTDE, DSR, OTHER)) }
        processes.findGames(setOf(GameEdition.PrepareToDie)).shouldContainExactly(GameProcess(PTDE, GameEdition.PrepareToDie))
    }

    test("connects when the pointer width matches the edition") {
        val processes = FakeProcesses(PointerSize.IntPointer)
        processes.connect(GameProcess(PTDE, GameEdition.PrepareToDie))
            .shouldBeInstanceOf<ConnectResult.Connected>()
    }

    test("refuses a process whose own module cannot be found") {
        val processes = FakeProcesses(loaded = emptyList())
        val result = processes.connect(GameProcess(PTDE, GameEdition.PrepareToDie))

        assertSoftly {
            result.shouldBeInstanceOf<ConnectResult.Refused>()
            processes.attached.single().closed shouldBe true
        }
    }

    test("refuses and closes a process whose pointer size does not match") {
        val processes = FakeProcesses(PointerSize.LongPointer)
        val result = processes.connect(GameProcess(PTDE, GameEdition.PrepareToDie))

        assertSoftly {
            result.shouldBeInstanceOf<ConnectResult.Refused>()
            processes.attached.single().closed shouldBe true
        }
    }

    test("turns an access failure into a refusal") {
        FakeProcesses(denied = setOf(PTDE.pid))
            .connect(GameProcess(PTDE, GameEdition.PrepareToDie))
            .shouldBeInstanceOf<ConnectResult.Refused>()
    }
})