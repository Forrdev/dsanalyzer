package com.sappyoak.dsanalyzer.runtime.write.request

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.runtime.sampling.GameSession
import com.sappyoak.dsanalyzer.runtime.sampling.PTDEFixture
import com.sappyoak.dsanalyzer.runtime.snapshot.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteOutcome

/** One turn of the sampling loop: sample, apply what was submitted, step the tasks, sample again */
private fun GameSession.turn(requests: WriteRequests): RuntimeSnapshot {
    sample()
    requests.drain(::apply)
    afterSample()
    return sample()
}

class WriteRequestsTest : FunSpec({
    test("each submission gets its own ticket, and its result comes back once under it") {
        val session = PTDEFixture().apply { populate() }.session()
        val requests = WriteRequests()

        val first = requests.submit(WriteRequest.RevertAll)
        val second = requests.submit(WriteRequest.Undo(id = 404))
        val results = session.turn(requests).writeResults

        assertSoftly {
            (second > first) shouldBe true
            results.map { it.ticket } shouldBe listOf(first, second)
            results[0].outcome shouldBe WriteOutcome.Done(0)
            results[1].outcome shouldBe WriteOutcome.Failed("No such write in the history")
            session.sample().writeResults.shouldBeEmpty()
        }
    }

    test("a task the game cannot take settles as failed under its ticket") {
        val fixture = PTDEFixture().apply { populate() }
        val session = fixture.session()
        fixture.quitToMenu()
        val requests = WriteRequests()

        val ticket = requests.submit(WriteRequest.SetFlag(flag = 11_010_902, on = true))
        val result = session.turn(requests).writeResults.single()

        assertSoftly {
            result.ticket shouldBe ticket
            result.label shouldBe "Set flag 11010902 to true"
            result.outcome.shouldBeInstanceOf<WriteOutcome.Failed>()
        }
    }
})
