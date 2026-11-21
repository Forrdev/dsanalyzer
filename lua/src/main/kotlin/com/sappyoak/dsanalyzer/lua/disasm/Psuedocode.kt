package com.sappyoak.dsanalyzer.lua.disasm

import com.sappyoak.dsanalyzer.lua.chunk.Instruction
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.chunk.Opcode

/** How many SETLIST items one flush covers in 5.0 */
private const val FIELDS_PER_FLUSH = 32

/** What [instruction] at [pc] does, in Lua-like terms, following 5.0's lvm.c */
internal fun describe(function: LuaFunction, pc: Int, instruction: Instruction): String {
    val o = Operands(function, pc)
    val a = instruction.a
    val b = instruction.b
    val c = instruction.c
    return when (instruction.opcode) {
        Opcode.MOVE -> "${o.r(a)} = ${o.r(b)}"
        Opcode.LOADK -> "${o.r(a)} = ${o.k(instruction.bx)}"
        Opcode.LOADBOOL -> "${o.r(a)} = ${b != 0}" + if (c != 0) "; skip next" else ""
        Opcode.LOADNIL -> "${o.range(a, b)} = nil"
        Opcode.GETUPVAL -> "${o.r(a)} = ${o.u(b)}"
        Opcode.GETGLOBAL -> "${o.r(a)} = ${o.global(instruction.bx)}"
        Opcode.GETTABLE -> "${o.r(a)} = ${o.index(o.r(b), c)}"
        Opcode.SETGLOBAL -> "${o.global(instruction.bx)} = ${o.r(a)}"
        Opcode.SETUPVAL -> "${o.u(b)} = ${o.r(a)}"
        Opcode.SETTABLE -> "${o.index(o.r(a), b)} = ${o.rk(c)}"
        Opcode.NEWTABLE -> "${o.r(a)} = {}"
        Opcode.SELF -> "${o.r(a + 1)} = ${o.r(b)}; ${o.r(a)} = ${o.index(o.r(b), c)}"
        Opcode.ADD -> arithmetic(o, instruction, "+")
        Opcode.SUB -> arithmetic(o, instruction, "-")
        Opcode.MUL -> arithmetic(o, instruction, "*")
        Opcode.DIV -> arithmetic(o, instruction, "/")
        Opcode.POW -> arithmetic(o, instruction, "^")
        Opcode.UNM -> "${o.r(a)} = -${o.r(b)}"
        Opcode.NOT -> "${o.r(a)} = not ${o.r(b)}"
        Opcode.CONCAT -> "${o.r(a)} = ${(b..c).joinToString(" .. ") { o.r(it) }}"
        Opcode.JMP -> "goto ${o.target(instruction.sbx) + 1}"
        Opcode.EQ -> comparison(o, instruction, "==")
        Opcode.LT -> comparison(o, instruction, "<")
        Opcode.LE -> comparison(o, instruction, "<=")
        Opcode.TEST ->
            if (a == b) "skip next unless ${o.r(b)} is ${truth(c)}"
            else "if ${o.r(b)} is ${truth(c)} then ${o.r(a)} = ${o.r(b)} else skip next"
        Opcode.CALL -> call(o, a, b).let { if (c == 1) it else "${results(o, a, c)} = $it" }
        Opcode.TAILCALL -> "return ${call(o, a, b)}"
        Opcode.RETURN -> ("return " + if (b == 0) "${o.r(a)}..." else o.range(a, a + b - 2)).trimEnd()
        Opcode.FORLOOP -> "${o.r(a)} += ${o.r(a + 2)}; if within ${o.r(a + 1)} goto ${o.target(instruction.sbx) + 1}"
        Opcode.TFORLOOP -> "${o.range(a + 2, a + 2 + c)} = ${o.r(a)}(${o.r(a + 1)}, ${o.r(a + 2)}); " +
                "leave the loop if ${o.r(a + 2)} is nil"
        Opcode.TFORPREP -> "start iterating ${o.r(a)}; goto ${o.target(instruction.sbx) + 1}"
        Opcode.SETLIST -> setList(o, a, instruction.bx, "${o.r(a + 1 + instruction.bx % FIELDS_PER_FLUSH)}")
        Opcode.SETLISTO -> setList(o, a, instruction.bx, "top")
        Opcode.CLOSE -> "close upvalues from ${o.r(a)}"
        Opcode.CLOSURE -> "${o.r(a)} = closure of function ${instruction.bx}"
    }
}

private fun arithmetic(o: Operands, instruction: Instruction, operator: String): String =
    "${o.r(instruction.a)} = ${o.rk(instruction.b)} $operator ${o.rk(instruction.c)}"

/** 5.0 skips the next instruction when the comparison's result differs from A */
private fun comparison(o: Operands, instruction: Instruction, operator: String): String =
    "skip next if (${o.rk(instruction.b)} $operator ${o.rk(instruction.c)}) is ${instruction.a == 0}"

private fun truth(c: Int): String = if (c != 0) "truthy" else "falsy"

/** B counts the arguments plus one, or 0 for every value up to the stack top */
private fun call(o: Operands, a: Int, b: Int): String =
    "${o.r(a)}(" + (if (b == 0) "${o.r(a + 1)}..." else o.range(a + 1, a + b - 1)) + ")"

/** C counts the results plus one, or 0 for as many as the call returns */
private fun results(o: Operands, a: Int, c: Int): String = if (c == 0) "${o.r(a)}..." else o.range(a, a + c - 2)

private fun setList(o: Operands, a: Int, bx: Int, last: String): String {
    val first = bx - bx % FIELDS_PER_FLUSH + 1
    return "${o.r(a)}[$first...] = ${o.r(a + 1)}..$last"
}
