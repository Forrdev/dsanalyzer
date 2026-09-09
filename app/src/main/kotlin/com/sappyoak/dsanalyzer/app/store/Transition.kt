package com.sappyoak.dsanalyzer.app.store

/**
 * The result of perform reduce on one message: the next state and the effects to launch
 *
 * Effects are values rather than calls, so a reducer stays pure and a transition can be asserted
 * whome in a test
 */
public data class Transition<out S : Any, out E : Any>(
    public val state: S,
    public val effects: List<E> = emptyList()
)

/** Builds a [Transition]. Call with no argument when the message launches no effects */
public fun <S : Any, E : Any> S.with(vararg effects: E): Transition<S, E> =
    Transition(this, effects.toList())

/** Builds a [Transition] from an already assembled list of effects */
public fun <S : Any, E : Any> S.with(effects: List<E>): Transition<S, E> =
    Transition(this, effects)