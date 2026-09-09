package com.sappyoak.dsanalyzer.app.store

import kotlinx.coroutines.flow.Flow

/**
 * Executes effects. An implementation holds the services its effects need and nothing else.
 * State should never be stored because the reducer packs everything an effect needs into the
 * effect itself at the moment the decision is made
 */
public interface EffectRunner<in E : Any, out M : Any> {
    /**
     * Optional identity for an effect. Launching an effect cancels any running effects sharing its
     * key, so a feature cancels work by emitting a keyed effect that returns an empty flow. Keys must
     * come from a small fixed set: one entry is retained per distinct key for the store's lifetime
     */
    public fun keyOf(effect: E): Any? = null

    /** Runs [effect] emitting a message for each result it produces */
    public fun execute(effect: E): Flow<M>

    /** Turns a failure of [effect] into a message, or null to log it and carry on */
    public fun onFailure(effect: E, failure: Throwable): M?
}