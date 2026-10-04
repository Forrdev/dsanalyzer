package com.sappyoak.dsanalyzer.app.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.slf4j.LoggerFactory

private fun recorded(name: String, log: () -> Unit): List<ILoggingEvent> {
    val backing = LoggerFactory.getLogger(name) as Logger
    val appender = ListAppender<ILoggingEvent>().apply { start() }

    backing.addAppender(appender)
    backing.level = Level.DEBUG
    try {
        log()
    } finally {
        backing.detachAppender(appender)
    }

    return appender.list.toList()
}

/**
 * The main purpose of this test is not really testing anything about the actual logging, but rather ensuring
 * that test that *need* to log can do so by using their own appender and not falling back to the logback-test.xml
 * that is provided by the convention plugin
 */
class StructuredLoggingTest : FunSpec({
    test("a payload reaches the log as key-value pairs rather than as message text") {
        val name = "com.sappyoak.dsanalyzer.test.payload"

        val events = recorded(name) {
            KotlinLogging.logger(name).atInfo {
                message = "attached"
                payload = mapOf("pid" to 4321, "build" to "Steam")
            }
        }

        val event = events.single()
        assertSoftly {
            event.formattedMessage shouldBe "attached"
            event.keyValuePairs.associate { it.key to it.value } shouldBe
                    mapOf("pid" to 4321, "build" to "Steam")
        }
    }

    test("a cause travels with the line") {
        val name = "com.sappyoak.dsanalyzer.test.cause"
        val failure = IllegalStateException("no")

        val events = recorded(name) {
            KotlinLogging.logger(name).atError {
                message = "it went wrong"
                cause = failure
            }
        }

        events.single().throwableProxy?.message shouldBe "no"
    }
})