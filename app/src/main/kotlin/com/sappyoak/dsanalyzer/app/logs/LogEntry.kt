package com.sappyoak.dsanalyzer.app.logs

import java.time.Instant

import com.sappyoak.dsanalyzer.app.logging.LAUNCH_MESSAGE
import com.sappyoak.dsanalyzer.app.logging.LogContextKey

private const val PACKAGE = "com.sappyoak.dsanalyzer."

public enum class LogLevel { Trace, Debug, Info, Warn, Error }

public data class LogFrame(
    public val className: String,
    public val method: String,
    public val file: String?,
    public val line: Int
) {
    public val ours: Boolean get() = className.startsWith(PACKAGE)

    override fun toString(): String = "$className.$method(${file ?: "?"}${if (line >= 0) ":$line" else ""})"
}

public data class LogError(
    public val type: String,
    public val message: String?,
    public val frames: List<LogFrame>,
    public val cause: LogError?
) {
    /** Where in our own code it surfaced; what tells one problem from another with the same type */
    public val origin: LogFrame? get() = frames.firstOrNull { it.ours } ?: cause?.origin
}

/** One line of the log file; [sequence] is its place in the file, which no two lines share */
public data class LogEntry(
    public val sequence: Long,
    public val time: Instant,
    public val level: LogLevel,
    public val logger: String,
    public val thread: String,
    public val message: String,
    public val fields: Map<String, String>,
    public val context: Map<LogContextKey, String>,
    public val error: LogError?
) {
    /** The module or feature a logger belongs to: 'app', 'game', 'runtime' and so on */
    public val area: String get() = logger.removePrefix(PACKAGE).substringBefore('.')

    /** The line a launch of the tool opens with */
    public val launch: Boolean get() = message == LAUNCH_MESSAGE
}
