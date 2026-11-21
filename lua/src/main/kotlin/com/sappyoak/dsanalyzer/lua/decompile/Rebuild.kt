package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.chunk.Opcode
import com.sappyoak.dsanalyzer.lua.disasm.disassemble
import com.sappyoak.dsanalyzer.lua.ir.Branch
import com.sappyoak.dsanalyzer.lua.ir.FunctionBody
import com.sappyoak.dsanalyzer.lua.ir.Statement
import com.sappyoak.dsanalyzer.lua.names.Borrowing
import com.sappyoak.dsanalyzer.lua.names.globalFunctionNames

/**
 * One rebuild of [function] under one guess of its locals. Nested functions are rebuilt by
 * [FunctionDecompiler], with the names their closures capture, generated names starting past [names]'s, and
 * whatever [borrowing] has for them
 */
internal class Rebuild(
    private val function: LuaFunction,
    private val scope: LocalScope,
    upvalues: List<String>,
    private val names: NameSource?,
    private val borrowing: Borrowing
) {
    private val code = function.code
    private val frame = Frame(function, scope, upvalues)
    private val globals by lazy { globalFunctionNames(function) }
    private val executor = Executor(frame) { index, captured ->
        FunctionDecompiler(function.functions[index], captured, names?.position ?: 1, borrowing, globals[index])
            .decompile()
    }

    fun run(): FunctionBody = try {
        val statements = block(0, code.size, Registers(), Flow(follow = code.size, loopExit = null))
        FunctionBody.Decompiled(scope.parameters, function.isVararg, statements)
    } catch (failure: UnsupportedShape) {
        FunctionBody.Failed(scope.parameters, function.isVararg, failure.message.orEmpty(), disassemble(function))
    }

    /**
     * Rebuilds [start] until [end]. luac threads a jump that lands on another jump straight to that jump's
     * target, so a jump to [Flow.follow], where control goes after the block, also means the block's end
     */
    private fun block(start: Int, end: Int, registers: Registers, flow: Flow): List<Statement> {
        val mark = scope.enter()
        val out = mutableListOf<Statement>()
        val starts = mutableListOf<Int>()
        var segment = start
        // Each statement starts where the one before it ended
        val record = { at: Int ->
            while (starts.size < out.size) starts += segment
            segment = at
        }
        var pc = start
        while (pc < end) {
            scope.declare(pc, registers, out)
            if (out.size > starts.size) record(pc)
            val instruction = code[pc]
            pc = when {
                isTest(instruction.opcode) -> ifStatement(pc, end, registers, flow, out)
                instruction.opcode == Opcode.JMP -> {
                    if (pc + 1 + instruction.sbx != flow.loopExit) unsupported("a jump that is not a break or an if")
                    out += Statement.Break
                    pc + 1
                }
                isForPrep(pc) -> numericFor(pc, registers, out)
                instruction.opcode == Opcode.RETURN && pc == code.lastIndex && instruction.b == 1 -> pc + 1
                else -> executor.execute(pc, registers, out)
            }
            if (out.size > starts.size) record(pc)
        }
        scope.declare(end, registers, out, pendingOnly = true)
        record(end)
        leftOver(registers)
        scope.exit(mark)
        // A block's locals end where it does, except the function's, which end at its final RETURN
        return scope.scoped(out, starts, if (end == code.size) code.lastIndex else end)
    }

    private fun ifStatement(pc: Int, end: Int, registers: Registers, flow: Flow, out: MutableList<Statement>): Int {
        val condition = readCondition(frame, executor, pc, end, flow.follow, registers)
        val falseTarget = condition.falseTarget.coerceAtMost(end)
        // A JMP that follows a test belongs to that test; only a lone one jumps over an else
        val elseJump = code[falseTarget - 1].takeIf {
            it.opcode == Opcode.JMP && falseTarget - 1 >= condition.thenStart && !isTest(code[falseTarget - 2].opcode)
        }
        val elseEnd = elseJump?.let { falseTarget + it.sbx }?.let { target ->
            when {
                target == flow.loopExit -> null
                // An empty `else` still jumps over itself
                target in falseTarget..end -> target
                target == flow.follow -> end
                else -> null
            }
        }
        val after = { at: Int -> if (at >= end) flow.follow else at }

        val thenEnd = if (elseEnd != null) falseTarget - 1 else falseTarget
        val thenFlow = flow.copy(follow = after(elseEnd ?: falseTarget))
        val thenBody = block(condition.thenStart, thenEnd, registers.forBlock(), thenFlow)
        val otherwise = elseEnd?.let { block(falseTarget, it, registers.forBlock(), flow.copy(follow = after(it))) }
        val nested = otherwise?.singleOrNull() as? Statement.If
        out += if (nested != null) {
            Statement.If(listOf(Branch(condition.expression, thenBody)) + nested.branches, nested.otherwise)
        } else {
            Statement.If(listOf(Branch(condition.expression, thenBody)), otherwise)
        }
        return elseEnd ?: falseTarget
    }

    /** Values still waiting when a block ends were locals; without debug info that is how the last ones show */
    private fun leftOver(registers: Registers) {
        if (registers.isEmpty) return
        if (!scope.recovering) unsupported("values left over at the end of a block")
        throw LocalsFound(registers.pending.map { LocalHint(it, (registers.filledAt(it) ?: -1) + 1) }.toSet())
    }

    /** `SUB a a a+2; JMP` to a FORLOOP on the same base opens a numeric for */
    private fun isForPrep(pc: Int): Boolean {
        val sub = code[pc]
        val jump = code.getOrNull(pc + 1) ?: return false
        if (sub.opcode != Opcode.SUB || sub.b != sub.a || sub.c != sub.a + 2 || jump.opcode != Opcode.JMP) return false
        val loop = code.getOrNull(pc + 2 + jump.sbx) ?: return false
        return loop.opcode == Opcode.FORLOOP && loop.a == sub.a && pc + 2 + jump.sbx + 1 + loop.sbx == pc + 2
    }

    private fun numericFor(pc: Int, registers: Registers, out: MutableList<Statement>): Int {
        val base = code[pc].a
        val access = Access(frame, pc, registers)
        access.requireLocalsBelow(base)
        val start = access.read(base)
        val limit = access.read(base + 1)
        val step = access.read(base + 2)
        val bodyStart = pc + 2
        val loopPc = bodyStart + code[pc + 1].sbx
        val mark = scope.enter()
        val variable = scope.declareLoop(base, bodyStart)

        val body = block(bodyStart, loopPc, registers.forBlock(), Flow(follow = loopPc, loopExit = loopPc + 1))
        scope.exit(mark)
        out += Statement.NumericFor(variable, start, limit, step, body)
        return loopPc + 1
    }
}

/** Where control goes when a block falls off its end, and where a break inside it lands */
private data class Flow(val follow: Int, val loopExit: Int?)
