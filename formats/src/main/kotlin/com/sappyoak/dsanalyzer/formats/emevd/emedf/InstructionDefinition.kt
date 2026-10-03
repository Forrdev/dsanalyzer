package com.sappyoak.dsanalyzer.formats.emevd.emedf

/** What  an instruction is called and what its arguments are for one bank and id */
public data class InstructionDefinition(
    public val opcode: Opcode,
    public val name: String,
    public val args: List<ArgDefinition>,
    public val alias: String? = null,
    public val summary: String? = null
) {
    public val label: String get() = alias ?: name

    /** How many bytes the argument takes once each is aligned to its own width */
    public val packedSize: Int
        get() = args.fold(0) { at, arg -> arg.type.align(at) + arg.type.size }
}