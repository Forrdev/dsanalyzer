package com.sappyoak.dsanalyzer.app.settings

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

import com.sappyoak.dsanalyzer.app.connection.ConnectionSettings

private val DEBOUNCE_INTERVAL = 100.milliseconds
private val MOMENT = 20.milliseconds

private val EDITED = Settings(connection = ConnectionSettings(autoConnect = true))

class SettingsSaverTest : FunSpec({
    test("a save waits until settings stop changing, and only the newest is written") {
        val written = CopyOnWriteArrayList<Settings>()
        val saver = SettingsSaver({ written += it }, DEBOUNCE_INTERVAL)

        val first = launch { saver.save(Settings()) }
        delay(MOMENT)
        first.cancel()
        val second = launch { saver.save(EDITED) }
        delay(MOMENT)

        assertSoftly {
            written.shouldBeEmpty()
            second.join()
            written shouldBe listOf(EDITED)
        }
    }

    test("a flush writes a waiting save at once, and the wait then writes nothing else") {
        val written = CopyOnWriteArrayList<Settings>()
        val saver = SettingsSaver({ written += it }, DEBOUNCE_INTERVAL)

        val waiting = launch { saver.save(EDITED) }
        delay(MOMENT)
        saver.flush()

        assertSoftly {
            written shouldBe listOf(EDITED)
            waiting.join()
            written shouldBe listOf(EDITED)
        }
    }

    test("A flush waits for a write already under way rather than returning before it lands") {
        val written = CopyOnWriteArrayList<Settings>()
        val release = CompletableDeferred<Unit>()
        val saver = SettingsSaver({
            release.await()
            written += it
        }, Duration.ZERO)

        launch { saver.save(EDITED) }
        delay(MOMENT)
        val flushing = launch { saver.flush() }
        delay(MOMENT)

        assertSoftly {
            flushing.isCompleted shouldBe false
            release.complete(Unit)
            flushing.join()
            written shouldBe listOf(EDITED)
        }
    }
})
