package com.sappyoak.dsanalyzer.runtime.sampling

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

import com.sappyoak.dsanalyzer.runtime.write.request.WriteRequests

public class SampleRequests {
    private val placedEnemies = AtomicBoolean(false)
    private val characterEffects = AtomicBoolean(false)
    private val chosen = AtomicReference<SampleOptions>(SampleOptions())

    public val writes: WriteRequests = WriteRequests()

    internal val options: SampleOptions get() = chosen.get()

    public fun sampleWith(change: (SampleOptions) -> SampleOptions) {
        chosen.updateAndGet(change)
    }

    public fun requestPlacedEnemies() {
        placedEnemies.set(true)
    }

    internal fun takePlacedEnemies(): Boolean = placedEnemies.getAndSet(false)

    public fun requestCharacterEffects() {
        characterEffects.set(true)
    }

    internal fun takeCharacterEffects(): Boolean = characterEffects.getAndSet(false)
}
