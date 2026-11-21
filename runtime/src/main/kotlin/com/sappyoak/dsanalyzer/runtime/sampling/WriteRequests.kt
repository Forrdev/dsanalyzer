package com.sappyoak.dsanalyzer.runtime.sampling

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentLinkedQueue

import com.sappyoak.dsanalyzer.runtime.session.CheatFlag
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask

private val logger = KotlinLogging.logger {  }

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
            val write = queue.poll() ?: return
            val outcome = when (write) {
                is WriteRequest.SetCheat -> session.setCheat(write.flag, write.on)
                is WriteRequest.Undo -> session.writes.undo(write.id, write.force)
                is WriteRequest.Run -> session.writes.run(write.task)
                WriteRequest.RevertAll -> session.revertAll()
            }
            logger.atInfo {
                message = "Write applied"
                payload = mapOf("request" to write.describe(), "outcome" to outcome.toString())
            }
        }
    }
}

private fun WriteRequest.describe(): String = when (this) {
    is WriteRequest.Run -> "Run ${task::class.simpleName}"
    else -> toString()
}
