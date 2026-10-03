package com.sappyoak.dsanalyzer.app.logging

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineExceptionHandler

private const val CRASH_LOGGER = "com.sappyoak.dsanalyzer.app.CrashHandler"
private const val COROUTINE_LOGGER = "com.sappyoak.dsanalyzer.app.CoroutineErrors"

public fun installCrashLogging() {
    val logger = KotlinLogging.logger(CRASH_LOGGER)
    Thread.setDefaultUncaughtExceptionHandler { thread, error ->
        logger.atError {
            message = "Uncaught exception, thread died"
            cause = error
            payload = mapOf("thread" to thread.name)
        }
    }
}

fun coroutineErrorLogging(loggerName: String = COROUTINE_LOGGER): CoroutineExceptionHandler {
    val logger = KotlinLogging.logger(loggerName)
    return CoroutineExceptionHandler { context, error ->
        logger.atError {
            message = "Unhandled coroutine failure"
            cause = error
            payload = mapOf("context" to context.toString())
        }
    }
}