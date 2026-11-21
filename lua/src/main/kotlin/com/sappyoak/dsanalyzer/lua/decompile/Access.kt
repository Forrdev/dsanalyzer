package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.chunk.LuaConstant
import com.sappyoak.dsanalyzer.lua.chunk.RK_CONSTANT_BASE
import com.sappyoak.dsanalyzer.lua.chunk.isConstant
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.Statement

/**
 * Reads and writes operands at one pc: a register is a local when the scope names it there, else a temporary.
 * While locals are being recovered, a read or write that luac's allocation could not have produced reports
 * the locals it implies
 */
internal class Access(private val frame: Frame, private val pc: Int, val registers: Registers) {
    private val scope = frame.scope

    private fun localAt(register: Int): String? = scope.nameAt(register, pc)

    fun read(register: Int): Expression {
        localAt(register)?.let { return Expression.Local(it) }
        if (scope.recovering && registers.peek(register) == null) found(register)
        return value(registers.take(register), register)
    }

    /**
     * What a MOVE copies from [register] into [into]. luac only copies a temporary when storing it into a
     * local; one copied to build another temporary was a local
     */
    fun moved(register: Int, into: Int): Expression {
        val copiesTemporary = localAt(register) == null && registers.peek(register) != null
        if (scope.recovering && copiesTemporary && localAt(into) == null) found(register)
        return read(register)
    }

    /** The local in [register], which a closure is capturing */
    fun localName(register: Int): String =
        localAt(register) ?: if (scope.recovering) found(register) else unsupported("a capture of a temporary")

    /**
     * Checks an instruction's register or constant [operands]. luac frees a temporary only from the top, so
     * one read from under other waiting values was a local
     */
    fun operands(vararg operands: Int) {
        if (!scope.recovering) return
        val temporaries = operands.filter { !isConstant(it) && localAt(it) == null && registers.peek(it) != null }
        val buried = temporaries.filter { register -> registers.pending.any { it > register && it !in temporaries } }
        buried.maxOrNull()?.let { found(it) }
    }

    /** The lone register operand of an instruction, checked as [operands] does */
    fun operand(register: Int): Expression {
        operands(register)
        return read(register)
    }

    /** A register or, past [RK_CONSTANT_BASE], a constant */
    fun rk(operand: Int): Expression = if (isConstant(operand)) constant(operand - RK_CONSTANT_BASE) else read(operand)

    fun constant(index: Int): Expression = when (val constant = frame.function.constants[index]) {
        LuaConstant.Nil -> Expression.Nil
        is LuaConstant.Number -> Expression.Number(constant.value)
        is LuaConstant.Text -> Expression.Text(constant.value)
    }

    /** Stores into a local as an assignment, or leaves the value waiting in a temporary */
    fun write(register: Int, value: Expression, out: MutableList<Statement>) {
        val local = localAt(register)
        if (local != null) out += Statement.Assign(Expression.Local(local), value) else put(register, Slot.Value(value))
    }

    /** Leaves a slot that only a temporary can hold */
    fun put(register: Int, slot: Slot) {
        if (localAt(register) != null) unsupported("$slot stored straight into a local")
        if (scope.recovering) {
            // A temporary is only ever taken at the first free register, and never over one still waiting
            val firstFree = registers.firstFree(localCount())
            if (registers.peek(register) != null) found(register)
            if (register > firstFree) found(register - 1)
        }
        registers.put(register, slot, pc)
    }

    /** Changes a waiting slot in place, as a table constructor's items do */
    fun update(register: Int, slot: Slot) = registers.put(register, slot, pc)

    fun registers(start: Int, count: Int): List<Expression> = (start until start + count).map { read(it) }

    /** Everything from [start] up to and including the open call */
    fun upToOpenTop(start: Int): List<Expression> = (start..registers.closeOpenTop()).map { read(it) }

    /** [count] registers from [start], or all of them up to the open call when [count] is negative */
    fun range(start: Int, count: Int): List<Expression> = if (count < 0) upToOpenTop(start) else registers(start, count)

    /** The call at register [a] with B = [b]: a SELF there makes it a method call */
    fun call(a: Int, b: Int): Expression {
        val argumentCount = b - 1
        return when (val callee = registers.peek(a)?.takeIf { localAt(a) == null }) {
            is Slot.Method -> {
                registers.take(a)
                if (registers.take(a + 1) != Slot.Receiver) unsupported("a method call without its receiver")
                val arguments = range(a + 2, if (argumentCount < 0) -1 else argumentCount - 1)
                Expression.MethodCall(callee.receiver, callee.name, arguments)
            }
            else -> {
                val target = read(a)
                Expression.Call(target, range(a + 1, argumentCount))
            }
        }
    }

    /** What a RETURN passes on: a lone call there was a local, since returning a call is a TAILCALL */
    fun returned(start: Int, count: Int): List<Expression> {
        val slot = registers.peek(start)
        val call = (slot as? Slot.Value)?.expression.let { it is Expression.Call || it is Expression.MethodCall }
        if (scope.recovering && count == 1 && call && localAt(start) == null) found(start)
        return range(start, count)
    }

    /** A statement leaves no temporaries behind: any still waiting after one are locals */
    fun statementDone() {
        if (scope.recovering) registers.pending.lastOrNull()?.let { found(it) }
    }

    /** Reports the registers below [register] that are not locals yet, as a for loop's base needs them to be */
    fun requireLocalsBelow(register: Int) {
        if (scope.recovering && localCount() < register) found(register - 1)
    }

    /** How many registers locals take here: every one below the first that is not a local */
    private fun localCount(): Int = generateSequence(0) { it + 1 }.first { localAt(it) == null }

    /**
     * Reports [register], and every register between the locals and it, as locals: luac keeps locals below
     * every temporary. Each comes into scope after the instruction that last filled it, or at the function's
     * start if none did
     */
    fun found(register: Int): Nothing {
        val hints = (localCount()..register).map { LocalHint(it, (registers.filledAt(it) ?: -1) + 1) }
        throw LocalsFound(hints.toSet())
    }

    private fun value(slot: Slot, register: Int): Expression = when (slot) {
        is Slot.Value -> slot.expression
        is Slot.Table -> slot.toExpression()
        else -> unsupported("r$register holds $slot where a value is read")
    }
}
