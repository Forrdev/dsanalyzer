package com.sappyoak.dsanalyzer.formats.emevd.emedf

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Reads the community EMEDF definitions
 *
 * Only main_classes is read. The document is JSON with trailing commas, which a strict parser rejects,
 * so the readeris configured to accept them rather than the text being patched before hand
 */
public fun readEmedf(text: String): Emedf {
    val document = emedfJsonSerializer.decodeFromString<EmedfDocument>(text)
    val definitions = document.banks.flatMap { bank ->
        bank.instructions.map { instruction ->
            val opcode = Opcode(bank.index, instruction.index)
            opcode to InstructionDefinition(
                opcode = opcode,
                name = instruction.name,
                args = instruction.args.map { it.toDefinition() }
            )
        }
    }

    return Emedf(
        definitions = definitions.toMap(),
        enums = document.enums.associate { enum ->
            enum.name to enum.values.mapNotNull { (value, name) -> value.toIntOrNull()?.let { it to name } }.toMap()
        }
    )
}

@OptIn(ExperimentalSerializationApi::class)
private val emedfJsonSerializer = Json {
    allowComments = true
    allowTrailingComma = true
    ignoreUnknownKeys = true
    isLenient = true
}

@Serializable
private class EmedfDocument(
    @SerialName("main_classes") val banks: List<EmedfBank> = emptyList(),
    val enums: List<EmedfEnum> = emptyList()
)

@Serializable
private class EmedfBank(
    val index: Int,
    @SerialName("instrs") val instructions: List<EmedfInstruction> = emptyList()
)

@Serializable
private class EmedfInstruction(
    val index: Int,
    val name: String = "",
    val args: List<EmedfArg> = emptyList()
)

@Serializable
private class EmedfArg(
    val name: String = "",
    val type: Int,
    @SerialName("enum_name") val enumName: String? = null,
    val default: Double = 0.0
)

@Serializable
private class EmedfEnum(
    val name: String,
    val values: Map<String, String> = emptyMap()
)

private fun EmedfArg.toDefinition() = ArgDefinition(
    name = name,
    type = ArgType.entries.getOrNull(type)
        ?: throw IllegalArgumentException("Unknown EMEDF argument type $type for $name"),
    enumName = enumName,
    default = default
)