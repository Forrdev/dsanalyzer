package com.sappyoak.dsanalyzer.runtime.sampling

import java.util.concurrent.ConcurrentLinkedQueue

import com.sappyoak.dsanalyzer.runtime.session.CheatFlag
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask


public sealed interface WriteRequest {
    public data class SetCheat(
        public val flag: CheatFlag,
        public val on: Boolean
    ) : WriteRequest

    public data class Undo(
        public val id: Long,
        public val force: Boolean = false
    ) : WriteRequest

    /** Starts a multi-sample write */
    public data class Run(public val task: WriteTask) : WriteRequest

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
                is WriteRequest.Undo -> session.writes.undo(write.id, write.force)
                is WriteRequest.Run -> session.writes.run(write.task)
                WriteRequest.RevertAll -> session.revertAll()
            }
        }
    }
}
