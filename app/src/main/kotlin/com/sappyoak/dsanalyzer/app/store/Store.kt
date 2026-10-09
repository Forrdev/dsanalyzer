package com.sappyoak.dsanalyzer.app.store

import com.sappyoak.dsanalyzer.app.runtime.RuntimeMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * A message that arrives frequently, normally because its on a clock
 * rather than because of an action. This interface allows us to reduce the
 * amount these messages are logged
 */

public interface FrequentMessage
/**
 * Holds state, reduces message against it one at a time, and runs the effects each
 * reduction asks for.
 *
 * Messages are queued and processed by a single coroutine, so [reduce] mever runs concurrently
 * with itself and never needs a lock. Effects run concurrently and feed their results back
 * as messages
 *
 * The store lives for as long as [scope]. Cancelling the scope stops the message loop and every
 * effect in flight.
 *
 * Subclasses supply [reduce] as a top level function rather than a member reference because the
 * message loop starts during this constructor, so a subclass's own fields are not yet initialized
 */
public open class Store<S : Any, M : Any, E : Any>(
    private val scope: CoroutineScope,
    name: String,
    initial: S,
    private val reduce: (S, M) -> Transition<S, E>,
    private val effects: EffectRunner<E, M>
) {
    private val logger = KotlinLogging.logger("com.sappyoak.dsanalyzer.app.store.$name")
    private val mutableState = MutableStateFlow(initial)

    public val state: StateFlow<S> = mutableState.asStateFlow()

    private val inbox = Channel<M>(Channel.UNLIMITED)
    private val running = mutableMapOf<Any, Job>()

    init {
        scope.launch {
            for (message in inbox) {
                try {
                    process(message)
                } catch (cancellation: CancellationException) {
                    throw cancellation
                } catch (err: Throwable) {
                    // A reducer threw which is always a bug. State is only assigned once reduce returns,
                    // so there is no partial application here and the loop can carry on.
                    logger.error(err) { "Reducing ${message.describe()} failed" }
                }
            }
        }
    }

    /** Queues [message]. Never suspends and never blocks the caller */
    public fun dispatch(message: M) {
        if (inbox.trySend(message).isFailure) {
            logger.warn { "Dropped ${message.describe()}: store is no longer accepting messages" }
        }
    }

    private fun process(message: M) {
        val transition = reduce(mutableState.value, message)
        mutableState.value = transition.state

        val report = { "${message.describe()} -> ${transition.effects.map { it.describe() }} "}
        if (message is FrequentMessage) logger.trace(report) else logger.debug(report)
        transition.effects.forEach(::launchEffect)
    }

    private fun launchEffect(effect: E) {
        val key = effects.keyOf(effect)
        if (key != null) {
            running.remove(key)?.cancel()
        }

        val job = scope.launch {
            try {
                effects.execute(effect).collect(::dispatch)
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (err: Throwable) {
                logger.error(err) { "Effect: ${effect.describe()} failed" }
                effects.onFailure(effect, err)?.let(::dispatch)
            }
        }

        if (key != null) {
            // Completed jobs are left in place. Cancelling one is a no-op and the map is bounded
            // by the number of distinct keys
            running[key] = job
        }
    }
}

private fun Any.describe(): String = this::class.simpleName ?: this::class.toString()