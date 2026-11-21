package com.sappyoak.dsanalyzer.lua.disasm

import com.sappyoak.dsanalyzer.lua.chunk.Instruction
import com.sappyoak.dsanalyzer.lua.chunk.LuaChunk
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.chunk.OpFormat
import com.sappyoak.dsanalyzer.lua.chunk.Opcode

private const val OPCODE_WIDTH = 10
private const val OPERANDS_WIDTH = 14

/**
 * A listing of every function in [chunk], nested functions after their parent, in the spirit of
 * `luac -l -l`. Positions are 1-based like luac's; each instruction carries what it does in source terms.
 */
public fun disassemble(chunk: LuaChunk): String = disassemble(chunk.main)

/** The listing of [function] and the functions nested in it, [function] named [path] */
public fun disassemble(function: LuaFunction, path: String = "main"): String =
    buildString { listFunction(function, path) }

private fun StringBuilder.listFunction(function: LuaFunction, path: String) {
    appendLine("function $path <${function.source ?: "?"}:${function.lineDefined}>")
    appendLine(
        "; ${function.paramCount}${if (function.isVararg) "+" else ""} params, " +
                "${function.maxStackSize} slots, ${function.upvalueCount} upvalues, ${function.locals.size} locals, " +
                "${function.constants.size} constants, ${function.functions.size} functions"
    )
    val captures = function.captures()
    function.code.forEachIndexed { pc, instruction ->
        val meaning = captures[pc]?.let { describeCapture(function, pc, instruction, it) }
            ?: describe(function, pc, instruction)
        appendLine(line(function, pc, instruction, meaning))
    }

    section("constants", function.constants) { index, constant -> "$index  $constant" }
    section("locals", function.locals) { index, local ->
        "$index  ${local.name}  ${local.startPc + 1}-${local.endPc + 1}"
    }
    section("upvalues", function.upvalueNames) { index, name -> "$index  $name" }

    function.functions.forEachIndexed { index, child ->
        appendLine()
        listFunction(child, "$path/$index")
    }
}

private fun line(function: LuaFunction, pc: Int, instruction: Instruction, meaning: String): String {
    val line = function.lineAt(pc)?.let { "[$it]" } ?: "[-]"
    return "%6d  %-7s %-${OPCODE_WIDTH}s %-${OPERANDS_WIDTH}s ; %s".format(
        pc + 1, line, instruction.opcode.name, operands(instruction), meaning
    )
}

/** The raw operand fields the opcode's format has */
private fun operands(instruction: Instruction): String = when (instruction.opcode.format) {
    OpFormat.ABC -> "${instruction.a} ${instruction.b} ${instruction.c}"
    OpFormat.ABx -> "${instruction.a} ${instruction.bx}"
    OpFormat.AsBx ->
        if (instruction.opcode == Opcode.JMP) "${instruction.sbx}" else "${instruction.a} ${instruction.sbx}"
}

private fun <T> StringBuilder.section(title: String, entries: List<T>, describe: (Int, T) -> String) {
    if (entries.isEmpty()) return
    appendLine("$title (${entries.size})")
    entries.forEachIndexed { index, entry -> appendLine("    ${describe(index, entry)}") }
}
