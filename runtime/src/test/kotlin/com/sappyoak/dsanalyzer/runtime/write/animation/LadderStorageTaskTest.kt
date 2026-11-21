package com.sappyoak.dsanalyzer.runtime.write.animation

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.runtime.ptde.ChrIns
import com.sappyoak.dsanalyzer.runtime.sampling.CHARACTER_AT
import com.sappyoak.dsanalyzer.runtime.sampling.GameSession
import com.sappyoak.dsanalyzer.runtime.sampling.PTDEFixture
import com.sappyoak.dsanalyzer.runtime.snapshot.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteOutcome
import com.sappyoak.dsanalyzer.runtime.write.request.WriteRequest
import com.sappyoak.dsanalyzer.runtime.write.request.WriteRequests

/** Where the player's event-action record lives; the ChrIns only points at it */
private const val RECORD_AT = 0x30000

private fun GameSession.turn(requests: WriteRequests): RuntimeSnapshot {
    sample()
    requests.drain(::apply)
    afterSample()
    return sample()
}

/** Type, rung and count, as the record holds them */
private fun PTDEFixture.record(): List<Int> {
    val bytes = ByteBuffer.wrap(checkNotNull(game.read(game.address(RECORD_AT), 12))).order(ByteOrder.LITTLE_ENDIAN)
    return List(3) { bytes.getInt(it * Int.SIZE_BYTES) }
}

class LadderStorageTaskTest : FunSpec({
    test("storage writes a ladder climb into the record the player points at, and leaving clears it") {
        val fixture = PTDEFixture().apply {
            populate()
            game.pointer(CHARACTER_AT + ChrIns.EventActionPointer, RECORD_AT)
        }
        val session = fixture.session()
        val requests = WriteRequests()

        requests.submit(WriteRequest.SetLadderStorage(rung = 5, count = 10))
        val stored = session.turn(requests).writeResults.single()
        val inStorage = fixture.record()

        requests.submit(WriteRequest.ClearLadderStorage)
        session.turn(requests)

        assertSoftly {
            stored.outcome shouldBe WriteOutcome.Done(0)
            inStorage shouldBe listOf(1, 5, 10)
            fixture.record() shouldBe listOf(0, 0, 0)
        }
    }

    test("a player with no record is refused rather than written through a null pointer") {
        val session = PTDEFixture().apply { populate() }.session()
        val requests = WriteRequests()

        requests.submit(WriteRequest.SetLadderStorage(rung = 5, count = 10))
        session.turn(requests).writeResults.single().outcome.shouldBeInstanceOf<WriteOutcome.Failed>()
    }
})
