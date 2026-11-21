package com.sappyoak.dsanalyzer.runtime.sampling

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.time.Duration.Companion.milliseconds

private val PERIOD = 30.milliseconds

/** One tick: [work] long, then the wait for the next */
private suspend fun TestScope.tick(pacer: TickPacer, work: Long): Long {
    delay(work)
    pacer.awaitNext()
    return currentTime
}

class TickPacerTest : FunSpec({
    test("ticks stay on the clock: a slow one is made up by the next, not pushed along") {
        runTest {
            val pacer = TickPacer(PERIOD, testScheduler.timeSource)
            tick(pacer, work = 0) shouldBe 30
            tick(pacer, work = 40) shouldBe 70
            tick(pacer, work = 0) shouldBe 90
        }
    }

    test("a tick more than a whole period behind starts the clock again rather than catching up") {
        runTest {
            val pacer = TickPacer(PERIOD, testScheduler.timeSource)
            tick(pacer, work = 100) shouldBe 100
            tick(pacer, work = 0) shouldBe 130
        }
    }
})
