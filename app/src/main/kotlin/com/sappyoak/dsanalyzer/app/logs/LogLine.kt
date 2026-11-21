package com.sappyoak.dsanalyzer.app.logs

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.SerializationException
import java.time.Instant

import com.sappyoak.dsanalyzer.app.logging.LogContextKey

/**
 * Reads one line as logback's JsonEncoder writes it: 'timestamp', 'level', 'loggerName', 'threadName',
 * 'formattedMessage', 'kvpList' (one single-key object per pair), 'context.properties' and 'throwable'.
 * Null for a line that is not one, such as the tail of a line still being written
 */
public fun parseLogLine(line: String, sequence: Long): LogEntry? {
    val json = try {
        Json.parseToJsonElement(line) as? JsonObject
    } catch (_: SerializationException) {
        null
    } ?: return null

    return LogEntry(
        sequence = sequence,
        time = Instant.ofEpochMilli(json.long("timestamp") ?: return null),
        level = levelOf(json.text("level")) ?: return null,
        logger = json.text("loggerName").orEmpty(),
        thread = json.text("threadName").orEmpty(),
        message = json.text("formattedMessage").orEmpty(),
        fields = fieldsOf(json["kvpList"]),
        context = contextOf(json["context"]),
        error = errorOf(json["throwable"])
    )
}

private fun levelOf(level: String?): LogLevel? = when (level) {
    "TRACE" -> LogLevel.Trace
    "DEBUG" -> LogLevel.Debug
    "INFO" -> LogLevel.Info
    "WARN" -> LogLevel.Warn
    "ERROR" -> LogLevel.Error
    else -> null
}

private fun fieldsOf(pairs: JsonElement?): Map<String, String> =
    (pairs as? JsonArray).orEmpty()
        .mapNotNull { it as? JsonObject }
        .flatMap { pair -> pair.entries.map { (key, value) -> key to value.shown() } }
        .toMap()

private fun contextOf(context: JsonElement?): Map<LogContextKey, String> {
    val properties = (context as? JsonObject)?.get("properties") as? JsonObject ?: return emptyMap()
    return LogContextKey.entries
        .mapNotNull { key -> properties.text(key.key)?.takeIf { it.isNotEmpty() }?.let { key to it } }
        .toMap()
}

private fun errorOf(throwable: JsonElement?): LogError? {
    val json = throwable as? JsonObject ?: return null
    return LogError(
        type = json.text("className") ?: return null,
        message = json.text("message"),
        frames = (json["stepArray"] as? JsonArray).orEmpty().mapNotNull { frameOf(it) },
        cause = errorOf(json["cause"])
    )
}

private fun frameOf(step: JsonElement): LogFrame? {
    val json = step as? JsonObject ?: return null
    return LogFrame(
        className = json.text("className") ?: return null,
        method = json.text("methodName").orEmpty(),
        file = json.text("fileName"),
        line = (json["lineNumber"] as? JsonPrimitive)?.intOrNull ?: -1
    )
}

private fun JsonObject.text(key: String): String? = (this[key] as? JsonPrimitive)?.contentOrNull

private fun JsonObject.long(key: String): Long? = (this[key] as? JsonPrimitive)?.longOrNull

private fun JsonElement.shown(): String = when (this) {
    is JsonNull -> "null"
    is JsonPrimitive -> content
    else -> toString()
}
