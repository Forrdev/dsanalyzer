package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.chunk.Opcode
import com.sappyoak.dsanalyzer.lua.chunk.isConstant
import com.sappyoak.dsanalyzer.lua.ir.BinaryOperator
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.Statement
import com.sappyoak.dsanalyzer.lua.ir.UnaryOperator

/** An `if` test: true falls through to [thenStart], false jumps to [falseTarget] */
internal class Condition(val expression: Expression, val thenStart: Int, val falseTarget: Int)

/**
 * One test and its JMP: [test] true takes [onTrue], false takes [onFalse]. [start] is where the code that
 * computes its operands begins, which is where other tests jump to reach it
 */
private data class Fork(val start: Int, val test: Expression, val onTrue: Int, val onFalse: Int) {
    fun negated(): Fork = copy(test = negate(test), onTrue = onFalse, onFalse = onTrue)
}

private class Candidate(val fork: Fork, val jumpPc: Int, val registers: Registers)

internal fun isTest(opcode: Opcode): Boolean =
    opcode == Opcode.EQ || opcode == Opcode.LT || opcode == Opcode.LE || opcode == Opcode.TEST

/**
 * Reads the run of tests starting at [pc] as one condition. Code between tests may only compute their
 * operands; the run is cut back until every jump lands on a later test, the then-block or the false target.
 * The false target may be [follow], where a jump to the end of the enclosing block was threaded to
 */
internal fun readCondition(
    frame: Frame,
    executor: Executor,
    pc: Int,
    end: Int,
    follow: Int,
    registers: Registers
): Condition {
    val candidates = mutableListOf(forkAt(frame, pc, pc, registers))
    while (true) candidates += nextFork(frame, executor, candidates.last(), end) ?: break

    for (count in candidates.size downTo 1) {
        val run = candidates.subList(0, count)
        val last = run.last()
        val thenStart = last.jumpPc + 1
        val falseTarget = last.fork.onTrue
        val exits = falseTarget <= end || falseTarget == follow
        if (falseTarget < thenStart || !exits || !landsInside(run, thenStart, falseTarget)) continue
        val reduced = reduce(run.map { it.fork }, thenStart, falseTarget) ?: continue
        leftOver(frame, last.registers, pc)
        registers.adopt(last.registers)
        return Condition(reduced, thenStart, falseTarget)
    }
    unsupported("a condition whose jumps do not nest")
}

/** Values a condition left waiting were locals declared before it, which without debug info shows only here */
private fun leftOver(frame: Frame, registers: Registers, pc: Int) {
    val waiting = registers.pending
    if (waiting.isEmpty() && registers.openTop == null) return
    if (!frame.scope.recovering) unsupported("values left over after a condition")
    Access(frame, pc, registers).found(waiting.max())
}

private fun landsInside(run: List<Candidate>, thenStart: Int, falseTarget: Int): Boolean =
    run.withIndex().all { (index, candidate) ->
        val target = candidate.fork.onTrue
        target == thenStart || target == falseTarget || run.drop(index + 1).any { it.fork.start == target }
    }

/** The next test, when only operand-computing code stands between it and [previous] */
private fun nextFork(frame: Frame, executor: Executor, previous: Candidate, end: Int): Candidate? {
    val function = frame.function
    val start = previous.jumpPc + 1
    val registers = previous.registers.copy()
    var pc = start
    while (pc < end) {
        val opcode = function.code[pc].opcode
        if (frame.scope.startsAt(pc)) return null
        if (isTest(opcode)) return attempt { forkAt(frame, start, pc, registers) }
        if (opcode == Opcode.JMP || opcode == Opcode.FORLOOP || opcode == Opcode.RETURN) return null
        val out = mutableListOf<Statement>()
        pc = attempt { executor.execute(pc, registers, out) } ?: return null
        if (out.isNotEmpty()) return null
    }
    return null
}

/** The test at [pc] with its JMP, as a fork whose true side is the jump */
private fun forkAt(frame: Frame, start: Int, pc: Int, registers: Registers): Candidate {
    val instruction = frame.function.code[pc]
    val jump = frame.function.code.getOrNull(pc + 1)?.takeIf { it.opcode == Opcode.JMP }
        ?: unsupported("a test without its JMP")
    val access = Access(frame, pc, registers)
    val test = when (instruction.opcode) {
        Opcode.TEST -> {
            if (instruction.a != instruction.b) unsupported("and/or used as a value")
            access.read(instruction.b).let { if (instruction.c != 0) it else negate(it) }
        }
        else -> {
            val operator = when (instruction.opcode) {
                Opcode.EQ -> BinaryOperator.EQ
                Opcode.LT -> BinaryOperator.LT
                else -> BinaryOperator.LE
            }
            // The jump is taken when the comparison's result equals A
            val left = Operand(access.rk(instruction.b), instruction.b.takeUnless(::isConstant))
            val right = Operand(access.rk(instruction.c), instruction.c.takeUnless(::isConstant))
            comparison(operator, left, right).let { if (instruction.a != 0) it else negate(it) }
        }
    }
    return Candidate(Fork(start, test, onTrue = pc + 2 + jump.sbx, onFalse = pc + 2), pc + 1, registers)
}

/**
 * Folds the forks into one expression that is true for [thenStart] and false for [falseTarget], merging a
 * fork into the one before it with `and` or `or`. Each merge picks the orientation that leaves the fewest
 * `not`s, so `not (a and b)` only appears when nothing simpler matches
 */
private fun reduce(forks: List<Fork>, thenStart: Int, falseTarget: Int): Expression? {
    val nodes = forks.toMutableList()
    while (nodes.size > 1) {
        val index = (0 until nodes.size - 1).firstOrNull { canMerge(nodes, it) } ?: return null
        nodes[index] = merge(nodes[index], nodes[index + 1])
        nodes.removeAt(index + 1)
    }
    val root = nodes.single()
    // Checked in this order because an empty then-block makes both targets the same pc, and the last test's
    // jump always leaves for the false target
    return when {
        root.onTrue == falseTarget && root.onFalse == thenStart -> negate(root.test)
        root.onTrue == thenStart && root.onFalse == falseTarget -> root.test
        else -> null
    }
}

private fun canMerge(nodes: List<Fork>, index: Int): Boolean {
    val first = nodes[index]
    val second = nodes[index + 1]
    val reachesSecond = first.onTrue == second.start || first.onFalse == second.start
    val onlyFromFirst = nodes.filterIndexed { other, _ -> other != index }
        .none { it.onTrue == second.start || it.onFalse == second.start }
    val other = if (first.onTrue == second.start) first.onFalse else first.onTrue
    return reachesSecond && onlyFromFirst && (other == second.onTrue || other == second.onFalse)
}

private fun merge(first: Fork, second: Fork): Fork {
    val asAnd = orient(first, towards = second.start, onTrue = true).let { x ->
        orient(second, towards = x.onFalse, onTrue = false).let { y -> Merge(x, y, BinaryOperator.AND) }
    }
    val asOr = orient(first, towards = second.start, onTrue = false).let { x ->
        orient(second, towards = x.onTrue, onTrue = true).let { y -> Merge(x, y, BinaryOperator.OR) }
    }
    val chosen = if (asOr.cost < asAnd.cost) asOr else asAnd
    val test = Expression.Binary(chosen.operator, chosen.x.test, chosen.y.test)
    return Fork(first.start, test, chosen.y.onTrue, chosen.y.onFalse)
}

/** [fork] turned so that its true side (or false side) goes [towards] */
private fun orient(fork: Fork, towards: Int, onTrue: Boolean): Fork =
    if ((if (onTrue) fork.onTrue else fork.onFalse) == towards) fork else fork.negated()

private class Merge(val x: Fork, val y: Fork, val operator: BinaryOperator) {
    /** How many `not`s this orientation leaves, so the reading closest to the source wins */
    val cost: Int get() = notCount(x.test) + notCount(y.test)
}

private fun notCount(expression: Expression): Int = when (expression) {
    is Expression.Unary -> notCount(expression.operand) + if (expression.operator == UnaryOperator.NOT) 1 else 0
    is Expression.Binary -> notCount(expression.left) + notCount(expression.right)
    else -> 0
}
