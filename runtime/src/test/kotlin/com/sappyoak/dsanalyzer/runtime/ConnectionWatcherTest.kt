package com.sappyoak.dsanalyzer.runtime

import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration.Companion.seconds

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.process.ProcessInfo

private val GAME = ProcessInfo(10, "DARKSOULS.exe")
private val EDITIONS = setOf(GameEdition.PrepareToDie)
private val INTERVAL = 1.seconds

/** Collects the watch in the background of [runTest], so tests step time and inspect what arrived */
private fun TestScope.watch(processes: FakeProcesses): List<ConnectionEvent> {
    val events = mutableListOf<ConnectionEvent>()
    backgroundScope.launch {
        processes.watchForGame(EDITIONS, INTERVAL, EmptyCoroutineContext).collect(events::add)
    }
    runCurrent()
    return events
}

class ConnectionWatcherTest : FunSpec({
    test("searches until the game starts, then connects") {
        runTest {
            val processes = FakeProcesses()
            val events = watch(processes)

            assertSoftly {
                events.shouldContainExactly(ConnectionEvent.Searching)

                processes.running.add(GAME)
                advanceTimeBy(INTERVAL * 2)

                events.last().shouldBeInstanceOf<ConnectionEvent.Connected>()
            }
        }
    }

    test("closes the connection and searches again when the game exists") {
        runTest {
            val processes = FakeProcesses().apply { running.add(GAME) }
            val events = watch(processes)

            processes.running.clear()
            advanceTimeBy(INTERVAL * 2)

            assertSoftly {
                events.last() shouldBe ConnectionEvent.Searching
                processes.attached.single().closed shouldBe true
            }
        }
    }

    test("does not retry a refused game until it exists") {
        runTest {
            val processes = FakeProcesses(denied = setOf(GAME.pid)).apply { running.add(GAME) }
            val events = watch(processes)
            advanceTimeBy(INTERVAL * 2)

            assertSoftly {
                events.filterIsInstance<ConnectionEvent.Refused>().size shouldBe 1

                processes.running.clear()
                advanceTimeBy(INTERVAL * 2)
                events.last() shouldBe ConnectionEvent.Searching
            }
        }
    }

    test("Cancelling the watch closes the connection") {
        runTest {
            val processes = FakeProcesses().apply { running.add(GAME) }
            val events = mutableListOf<ConnectionEvent>()
            val job = launch {
                processes.watchForGame(EDITIONS, INTERVAL, EmptyCoroutineContext).collect(events::add)
            }

            runCurrent()

            job.cancel()
            runCurrent()

            processes.attached.single().closed shouldBe true
        }
    }
})