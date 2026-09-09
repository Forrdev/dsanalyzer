package com.sappyoak.dsanalyzer.app.logging

import io.github.oshai.kotlinlogging.KotlinLogging
import java.nio.file.Path

/**
 * Logback resolves '${dsanalyzer.logDir}' when it loads its configuration, which happens
 * lazily on the first logger lookup anywhere. Because of this [setupLogging] needs to run before
 * anything obtains a logger, including a top level value holding one, which initializes at class load
 * rather than first use
 */

private const val LOG_DIRECTORY_PROPERTY = "dsanalyzer.logDir"

public fun setupLogging(path: Path) {
    System.setProperty(LOG_DIRECTORY_PROPERTY, path.toString())

    val logger = KotlinLogging.logger("com.sappyoak.dsanalyzer.app.CrashHandler")
    Thread.setDefaultUncaughtExceptionHandler { thread, error ->
        logger.error(error) { "Uncaught exception on thread: ${thread.name}" }
    }
}