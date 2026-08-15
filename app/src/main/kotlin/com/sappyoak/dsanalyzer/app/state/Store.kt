package com.sappyoak.dsanalyzer.app.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.effects.AppEffects
/**
 * Holds the application state and routes actions to [reduce] and to effects.
 *
 * [reduce] is pure and stays that way by design. An action needing work done produces its
 * state change here and its side effects in an effect class. The two are dispatched together,
 * but never mixed, so a reducer can always be reasoned about without asking what it might
 * have triggered
 */
class Store(
    initialState: AppState,
    private val scope: CoroutineScope,
    private val effects: AppEffects,
    private val logSize: Int = DEFAULT_LOG_SIZE
) {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<AppState> = _state.asStateFlow()

    @PublishedApi
    internal val log = ArrayDeque<LoggedAction<*>>(logSize)

    fun dispatch(action: Action) {
        val before = _state.value
        val after = reduce(before, action)

        record(action, changed = after != before)

        _state.value = after
        effects.handle(action, after, scope, ::dispatch)
    }

    fun actionLog(): List<LoggedAction<*>> = log.toList()

    fun noOpActions(): Map<String, Int> =
        log.filterNot { it.changed }.groupingBy { it.type }.eachCount()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Action> actionsOfType(): List<LoggedAction<T>> {
        val result = log.filter { it.type == T::class.simpleName }
        return result as List<LoggedAction<T>>
    }

    private fun record(action: Action, changed: Boolean) {
        if (log.size >= logSize) log.removeFirst()
        log.addLast(LoggedAction(
            type = action::class.simpleName ?: "unknown",
            changed = changed,
            at = System.currentTimeMillis(),
            action = action
        ))
    }

    companion object {
        private const val DEFAULT_LOG_SIZE = 500
    }
}

@Serializable
data class LoggedAction<T : Action>(
    val type: String,
    val changed: Boolean,
    val at: Long,
    val action: T
)