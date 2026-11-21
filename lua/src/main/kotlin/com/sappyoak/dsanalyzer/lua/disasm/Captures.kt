package com.sappyoak.dsanalyzer.lua.disasm

import com.sappyoak.dsanalyzer.lua.chunk.Instruction
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.chunk.Opcode

/** One of the instructions after a CLOSURE, which name what an upvalue captures rather than run */
internal class Capture(val function: Int, val upvalue: String)

/** The capture pseudo-instructions of [this] function, by pc */
internal fun LuaFunction.captures(): Map<Int, Capture> = buildMap {
    code.forEachIndexed { pc, instruction ->
        if (instruction.opcode != Opcode.CLOSURE) return@forEachIndexed
        val child = functions[instruction.bx]
        repeat(child.upvalueCount) { index ->
            put(pc + 1 + index, Capture(instruction.bx, child.upvalueNames.getOrNull(index) ?: "u$index"))
        }
    }
}

/** A MOVE captures a local of this function, a GETUPVAL passes on one of its own upvalues */
internal fun describeCapture(function: LuaFunction, pc: Int, instruction: Instruction, capture: Capture): String {
    val o = Operands(function, pc)
    val from = if (instruction.opcode == Opcode.GETUPVAL) "upvalue ${o.u(instruction.b)}" else o.r(instruction.b)
    val alias = if (capture.upvalue == from) "" else " as ${capture.upvalue}"
    return "function ${capture.function} captures $from$alias"
}
