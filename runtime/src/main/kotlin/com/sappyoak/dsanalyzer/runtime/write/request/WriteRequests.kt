package com.sappyoak.dsanalyzer.runtime.write.request

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicLong

private val logger = KotlinLogging.logger { }

private class Ticketed(val ticket: Long, val request: WriteRequest)

/**
 * The one way into the game's memory. [submit] is safe from any thread and answers at once with a
 * ticket; the request is applied on the sampling thread before the next sample, and its outcome comes
 * back in a later snapshot's `writeResults` under the same ticket
 */
public class WriteRequests {
    private val tickets = AtomicLong(1)
    private val queue = ConcurrentLinkedQueue<Ticketed>()

    public fun submit(request: WriteRequest): Long =
        tickets.getAndIncrement().also { queue.add(Ticketed(it, request)) }

    /** Hands each waiting request to [apply], oldest first; called on the sampling thread */
    internal fun drain(apply: (ticket: Long, request: WriteRequest) -> Unit) {
        while (true) {
            val next = queue.poll() ?: return
            logger.atInfo {
                message = "Write submitted"
                payload = mapOf("ticket" to next.ticket, "request" to next.request.label)
            }
            apply(next.ticket, next.request)
        }
    }
}
