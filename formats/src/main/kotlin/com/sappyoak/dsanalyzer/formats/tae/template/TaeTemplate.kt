package com.sappyoak.dsanalyzer.formats.tae.template

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private val taeTemplateSerializer = Json { ignoreUnknownKeys = true }

public class TaeTemplate(
    public val bank: EventBank,
    private val byType: Map<Int, EventDefinition>
) {
    public val definitions: List<EventDefinition> = byType.values.sortedBy { it.type }
    public val size: Int get() = byType.size

    public operator fun get(type: Int): EventDefinition? = byType[type]

    override fun toString(): String = "TaeTemplate(${bank.name}, $size events)"

    public companion object {
        public val Empty: TaeTemplate = TaeTemplate(EventBank.Character, emptyMap())

        public fun of(bank: EventBank, definitions: Iterable<EventDefinition>): TaeTemplate =
            TaeTemplate(bank, definitions.associateBy { it.type })
    }
}

public fun readTaeTemplate(text: String): TaeTemplate {
    val document = taeTemplateSerializer.decodeFromString<TemplateDocument>(text)
    val bank = EventBank.entries.firstOrNull { it.key == document.bank }
        ?: throw IllegalArgumentException("Unknown TAE event bank '${document.bank}'")

    return TaeTemplate.of(bank, document.events.map { it.toDefinition() } )
}

@Serializable
private class TemplateDocument(
    val bank: String,
    val events: List<TemplateEvent> = emptyList()
)

@Serializable
private class TemplateEvent(
    val type: Int,
    val name: String,
    val params: List<TemplateParam> = emptyList()
)

@Serializable
private class TemplateParam(
    val type: String,
    val name: String? = null,
    val assert: String? = null,
    val values: List<TemplateValue> = emptyList()
)

@Serializable
private class TemplateValue(
    val value: Long,
    val name: String,
    val note: String? = null
)

private fun TemplateEvent.toDefinition(): EventDefinition = EventDefinition(
    type = type,
    name = name,
    params = params.map { it.toDefinition(type) }
)

private fun TemplateParam.toDefinition(eventType: Int): ParamDefinition = ParamDefinition(
    type = ParamType.of(type)
        ?: throw IllegalArgumentException("Unknown TAE parameter type '$type' on $eventType"),
    name = name,
    asserted = assert,
    values = values.map { KnownValue(it.value, it.name, it.note) }
)