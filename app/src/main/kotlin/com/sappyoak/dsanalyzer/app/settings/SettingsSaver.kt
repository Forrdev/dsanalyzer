package com.sappyoak.dsanalyzer.app.settings

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicReference
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private val DEBOUNCE_INTERVAL = 300.milliseconds

public class SettingsSaver(
    private val write: suspend (Settings) -> Unit,
    private val interval: Duration = DEBOUNCE_INTERVAL
) {
    private val unwritten = AtomicReference<Settings?>(null)
    private val writing = Mutex()

    public suspend fun save(settings: Settings) {
        unwritten.set(settings)
        delay(interval)
        flush()
    }

    public suspend fun flush(): Unit = writing.withLock {
        unwritten.getAndSet(null)?.let { write(it) }
    }
}
