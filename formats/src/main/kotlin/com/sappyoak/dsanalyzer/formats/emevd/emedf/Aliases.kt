package com.sappyoak.dsanalyzer.formats.emevd.emedf

import kotlinx.serialization.Serializable


public data class InstructionAlias(
    public val opcode: Opcode,
    public val name: String,
    public val summary: String?,
    public val args: List<ArgAlias>
)

public data class ArgAlias(
    public val name: String,
    public val reference: ArgReference?
)

/**
 * Lays readable names and argument kinds over the definitions
 */
public fun Emedf.withAliases(aliases: Iterable<InstructionAlias>): Emedf {
    val byOpcode = aliases.associateBy { it.opcode }
    return mapDefinitions { definition ->
        val alias = byOpcode[definition.opcode] ?: return@mapDefinitions definition
        definition.copy(
            alias = alias.name,
            summary = alias.summary,
            args = definition.args.mapIndexed { index, arg ->
                val named = alias.args.getOrNull(index) ?: return@mapIndexed arg
                arg.copy(
                    name = arg.name.ifBlank { named.name },
                    reference = named.reference ?: arg.reference
                )
            }
        )
    }
}

public fun readAliases(text: String): List<InstructionAlias> =
    emedfJsonSerializer.decodeFromString<AliasDocument>(text).instructions.map { it.toAlias() }


@Serializable
private class AliasDocument(val instructions: List<AliasEntry> = emptyList())

@Serializable
private class AliasEntry(
    val bank: Int,
    val id: Int,
    val name: String,
    val summary: String? = null,
    val args: List<AliasArg> = emptyList()
)

@Serializable
private class AliasArg(val name: String = "", val reference: String? = null)

private fun AliasEntry.toAlias(): InstructionAlias = InstructionAlias(
    opcode = Opcode(bank, id),
    name = name,
    summary = summary,
    args = args.map { ArgAlias(it.name, it.reference?.let(ArgReference::named))}
)