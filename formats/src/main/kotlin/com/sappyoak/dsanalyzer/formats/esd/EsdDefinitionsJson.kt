package com.sappyoak.dsanalyzer.formats.esd

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

private val json = Json { ignoreUnknownKeys = true }


public fun readEsdDefinitions(
    names: String,
    readings: String,
    edition: String,
    kind: EsdKind
): EsdDefinitions {
    val named = section<NamesSection>(names, edition, kind) ?: return EsdDefinitions.None
    val read = section<ReadingsSection>(readings, edition, kind) ?: ReadingsSection()

    val commandReadings = read.commands.associateBy { it.bank to it.id }
    val functionReadings = read.functions.associateBy { it.id }
    return EsdDefinitions(
        commands = named.commands.associate { entry ->
            (entry.bank to entry.id) to entry.toDefinition(commandReadings[entry.bank to entry.id])
        },
        functions = named.functions.associate { it.id to it.toDefinition(functionReadings[it.id]) }
    )
}

private inline fun <reified  T> section(text: String, edition: String, kind: EsdKind): T? {
    val root = json.parseToJsonElement(text).jsonObject
    val byKind = root[edition] as? JsonObject ?: return null
    return byKind[kind.key]?.let { json.decodeFromJsonElement<T>(it) }
}

@Serializable
private class NamesSection(
    val commands: List<NamedEntry> = emptyList(),
    val functions: List<NamedEntry> = emptyList()
)

@Serializable
private class NamedEntry(
    val bank: Int = 0,
    val id: Int,
    val name: String? = null,
    val formerly: List<String> = emptyList(),
    val args: List<String> = emptyList(),
    val used: Boolean = false
) {
    fun toDefinition(reading: ReadingEntry?): EsdDefinition =
        EsdDefinition(
            name = name,
            formerly = formerly,
            args = args,
            used = used,
            reading = reading?.let { Reading(it.reading, it.toVerdict()) }
        )
}

@Serializable
private class ReadingsSection(
    val commands: List<ReadingEntry> = emptyList(),
    val functions: List<ReadingEntry> = emptyList()
)

@Serializable
private class ReadingEntry(
    val bank: Int = 0,
    val id: Int,
    val reading: String,
    val verdict: String
) {
    fun toVerdict(): Verdict = when (verdict) {
        "agrees" -> Verdict.Agrees
        "conflicts" -> Verdict.Conflicts
        else -> Verdict.Unjudged
    }
}
