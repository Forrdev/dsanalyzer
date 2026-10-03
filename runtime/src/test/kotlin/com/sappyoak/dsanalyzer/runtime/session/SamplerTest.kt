package com.sappyoak.dsanalyzer.runtime.session

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration.Companion.milliseconds
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.runtime.ConnectionEvent
import com.sappyoak.dsanalyzer.runtime.GameProcess
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEBuild

private val TICK = 100.milliseconds

private val REMASTERED = GameProcess(
    ProcessInfo(11, GameEdition.Remastered.executableName),
    GameEdition.Remastered
)

/** Collects in the background of [runTest] so a test steps time rather than waiting for it */
private fun TestScope.sample(upstream: Flow<ConnectionEvent>): List<RuntimeEvent> {
    val events = mutableListOf<RuntimeEvent>()
    backgroundScope.launch {
        upstream.sampling(period = { TICK }, context = EmptyCoroutineContext).collect(events::add)
    }
    runCurrent()
    return events
}

class SamplerTest : FunSpec({
    test("a connection is attached once, then sampled at the tick it asks for") {
        runTest {
            val fixture = PTDEFixture().apply { populate() }
            val events = sample(flowOf(ConnectionEvent.Connected(fixture.connection())))

            val attached = events.first().shouldBeInstanceOf<RuntimeEvent.Attached>()
            advanceTimeBy(350.milliseconds)

            assertSoftly {
                attached.game shouldBe PTDE_PROCESS
                attached.build shouldBe PTDEBuild.Steam
                attached.pointers.complete shouldBe true
                events.count { it is RuntimeEvent.Attached } shouldBe 1
                events.count { it is RuntimeEvent.Sampled } shouldBe 4
            }
        }
    }

    test("what it samples is the game behind the connection") {
        runTest {
            val fixture = PTDEFixture().apply { populate() }
            val events = sample(flowOf(ConnectionEvent.Connected(fixture.connection())))

            val sampled = events.filterIsInstance<RuntimeEvent.Sampled>().first()

            assertSoftly {
                sampled.snapshot.player?.health shouldBe 837
                sampled.snapshot.cost.complete shouldBe true
            }
        }
    }

    test("the game going away stops the sampling that was reading it") {
        runTest {
            val fixture = PTDEFixture().apply { populate() }
            val events = sample(
                flow {
                    emit(ConnectionEvent.Connected(fixture.connection()))
                    delay(250.milliseconds)
                    emit(ConnectionEvent.Searching)
                }
            )

            advanceTimeBy(250.milliseconds)
            val whenLost = events.count { it is RuntimeEvent.Sampled }
            advanceTimeBy(500.milliseconds)

            assertSoftly {
                events.last() shouldBe RuntimeEvent.Searching
                events.count { it is RuntimeEvent.Sampled } shouldBe whenLost
            }
        }
    }

    test("an edition with no tables is reported rather than sampled") {
        runTest {
            val fixture = PTDEFixture()
            val events = sample(flowOf(ConnectionEvent.Connected(fixture.connection(REMASTERED))))

            advanceTimeBy(350.milliseconds)

            assertSoftly {
                events shouldBe listOf(RuntimeEvent.Unsupported(REMASTERED))
                events.count { it is RuntimeEvent.Sampled } shouldBe 0
            }
        }
    }

    test("a refusal upstream passes straight through") {
        runTest {
            val fixture = PTDEFixture()
            val events = sample(flowOf(ConnectionEvent.Refused(PTDE_PROCESS, "denied")))

            events shouldBe listOf(RuntimeEvent.Refused(PTDE_PROCESS, "denied"))
            fixture.game.reads shouldBe 0
        }
    }
})