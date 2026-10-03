package com.sappyoak.dsanalyzer.formats.emevd.emedf

import com.sappyoak.dsanalyzer.formats.emevd.Instruction

/**
 * The instruction definitions, which is what makes argument bytes mean anything.
 *
 * An instruction with no definitions here decodes to nothing rather than guesses, widths
 * are what split the bytes, so inventing them shifts every argument
 * after the first mistake
 */
public class Emedf(
    private val definitions: Map<Opcode, InstructionDefinition>,
    private val enums: Map<String, Map<Int, String>>
) {
    public val size: Int get() = definitions.size

    public operator fun get(opcode: Opcode): InstructionDefinition? = definitions[opcode]
    public operator fun get(instruction: Instruction): InstructionDefinition? = definitions[instruction.opcode]

    /** The name a definition gives [value] for this argument's enum when there is one */
    public fun enumValue(enumName: String?, value: Long): String? = enums[enumName]?.get(value.toInt())

    public fun mapDefinitions(change: (InstructionDefinition) -> InstructionDefinition): Emedf =
        Emedf(definitions.mapValues { (_, definition) -> change(definition) }, enums)

    public companion object {
        public val Empty: Emedf = Emedf(emptyMap(), emptyMap())
    }
}
