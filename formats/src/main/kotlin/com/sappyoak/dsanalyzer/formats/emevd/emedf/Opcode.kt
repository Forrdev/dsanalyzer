package com.sappyoak.dsanalyzer.formats.emevd.emedf

import com.sappyoak.dsanalyzer.formats.emevd.Instruction

/** Where an instruction sits: its bank, and its id within that bank */
public data class Opcode(public val bank: Int, public val id: Int) {
    override fun toString(): String = "$bank[$id]"
}

public val Instruction.opcode: Opcode get() = Opcode(bank, id)