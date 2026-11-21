package com.sappyoak.dsanalyzer.lua.chunk

import java.nio.charset.Charset

/** A local's name and the instructions it is live over: from [startPc] up to, not including, [endPc] */
public data class LocalVariable(
    public val name: String,
    public val startPc: Int,
    public val endPc: Int
) {
    public fun isLiveAt(pc: Int): Boolean = pc >= startPc && pc < endPc
}

/** One compiled function, with its debug info and the functions defined inside it */
public class LuaFunction(
    /** The chunk name, inherited from the parent when a nested function does not carry its own */
    public val source: String?,
    public val lineDefined: Int,
    public val upvalueCount: Int,
    public val paramCount: Int,
    public val isVararg: Boolean,
    public val maxStackSize: Int,
    /** The source line of each instruction; empty when the chunk was stripped */
    public val lines: List<Int>,
    public val locals: List<LocalVariable>,
    public val upvalueNames: List<String>,
    public val constants: List<LuaConstant>,
    public val functions: List<LuaFunction>,
    public val code: List<Instruction>
) {
    /**
     * The name of the local held in [register] at [pc], when debug info has one.
     * Live locals take registers in declaration order, which is how Lua itself names them.
     */
    public fun localName(register: Int, pc: Int): String? {
        var remaining = register
        for (local in locals) {
            if (local.startPc > pc) break
            if (local.isLiveAt(pc)) {
                if (remaining == 0) return local.name
                remaining--
            }
        }
        return null
    }

    public fun lineAt(pc: Int): Int? = lines.getOrNull(pc)

    /** This function with [locals] in place of its own, as when names are borrowed for a stripped copy */
    public fun withLocals(locals: List<LocalVariable>): LuaFunction = LuaFunction(
        source, lineDefined, upvalueCount, paramCount, isVararg, maxStackSize,
        lines, locals, upvalueNames, constants, functions, code
    )

    override fun toString(): String = "LuaFunction(${source ?: "?"}:$lineDefined, ${code.size} instructions)"
}

/** A whole dumped chunk: the header it was written with, its main function, and the encoding of its strings */
public class LuaChunk(
    public val header: ChunkHeader,
    public val main: LuaFunction,
    /** What source rebuilt from this chunk must be saved in to compile back to the same strings */
    public val charset: Charset
)
