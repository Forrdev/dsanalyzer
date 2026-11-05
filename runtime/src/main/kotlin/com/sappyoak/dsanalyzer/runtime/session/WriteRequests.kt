package com.sappyoak.dsanalyzer.runtime.session

import java.util.concurrent.ConcurrentLinkedQueue

public sealed interface WriteRequest {
    public data class SetCheat(
        public val flag: CheatFlag,
        public val on: Boolean
    ) : WriteRequest

    public data object RevertAll : WriteRequest
}

public class WriteRequests {
    private val queue = ConcurrentLinkedQueue<WriteRequest>()

    public fun request(write: WriteRequest) {
        queue.add(write)
    }

    internal fun applyTo(session: GameSession) {
        while (true) {
            when (val write = queue.poll() ?: return) {
                is WriteRequest.SetCheat -> session.setCheat(write.flag, write.on)
                WriteRequest.RevertAll -> session.revertAll()
            }
        }
    }
}
