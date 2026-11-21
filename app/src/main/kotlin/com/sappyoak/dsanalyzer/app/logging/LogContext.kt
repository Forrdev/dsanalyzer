package com.sappyoak.dsanalyzer.app.logging

import ch.qos.logback.classic.LoggerContext
import org.slf4j.LoggerFactory

// Kept as logback context properties rather than MDC to no have to worry about needing coroutine
//  friendly logging extensions
public enum class LogContextKey(public val key: String, public val label: String) {
    Edition("edition", "Edition"),
    Installation("installation", "Installation"),
    GameBuild("gameBuild", "Attached Build"),
    View("view", "View");
}

public object LogContext {
    public fun set(key: LogContextKey, value: String?) {
        val context = LoggerFactory.getILoggerFactory() as? LoggerContext ?: return
        if (context.getProperty(key.key).orEmpty() != value.orEmpty()) context.putProperty(key.key, value.orEmpty())
    }
}
