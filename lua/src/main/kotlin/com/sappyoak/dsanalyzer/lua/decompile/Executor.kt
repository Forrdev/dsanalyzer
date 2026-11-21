package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.chunk.Instruction
import com.sappyoak.dsanalyzer.lua.chunk.LuaConstant
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.chunk.Opcode
import com.sappyoak.dsanalyzer.lua.chunk.RK_CONSTANT_BASE
import com.sappyoak.dsanalyzer.lua.chunk.isConstant
import com.sappyoak.dsanalyzer.lua.ir.BinaryOperator
import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.FunctionBody
import com.sappyoak.dsanalyzer.lua.ir.Statement
import com.sappyoak.dsanalyzer.lua.ir.TableItem
import com.sappyoak.dsanalyzer.lua.ir.UnaryOperator

/** How many SETLIST items one flush covers in 5.0 */
private const val FIELDS_PER_FLUSH = 32

/**
 * Runs the instructions that only move values: they fill temporaries, and a write that lands in a local,
 * a global, a table or an upvalue, or a call whose results are dropped, becomes a statement
 */
internal class Executor(
    private val frame: Frame,
    /** Rebuilds nested function [index], given the names of the locals and upvalues its closure captures */
    private val child: (index: Int, upvalueNames: List<String>) -> FunctionBody
) {
    private val function = frame.function

    /** Runs the instruction at [pc] and returns the pc to continue from */
    fun execute(pc: Int, registers: Registers, out: MutableList<Statement>): Int {
        val instruction = function.code[pc]
        val access = Access(frame, pc, registers)
        val a = instruction.a
        val b = instruction.b
        val c = instruction.c
        when (instruction.opcode) {
            Opcode.MOVE -> access.write(a, access.moved(b, into = a), out)
            Opcode.LOADK -> access.write(a, access.constant(instruction.bx), out)
            Opcode.LOADBOOL -> {
                if (c != 0) unsupported("a comparison used as a value")
                access.write(a, Expression.Bool(b != 0), out)
            }
            Opcode.LOADNIL -> (a..b).forEach { access.write(it, Expression.Nil, out) }
            Opcode.GETUPVAL -> access.write(a, Expression.Upvalue(frame.upvalue(b)), out)
            Opcode.GETGLOBAL -> access.write(a, Expression.Global(name(instruction.bx)), out)
            Opcode.GETTABLE -> {
                access.operands(b, c)
                access.write(a, Expression.Index(access.read(b), access.rk(c)), out)
            }
            Opcode.SETGLOBAL -> out += Statement.Assign(Expression.Global(name(instruction.bx)), access.read(a))
            Opcode.SETUPVAL -> out += Statement.Assign(Expression.Upvalue(frame.upvalue(b)), access.read(a))
            Opcode.SETTABLE -> setTable(access, a, b, c, out)
            Opcode.NEWTABLE -> access.put(a, Slot.Table(emptyList(), emptyList()))
            Opcode.SELF -> self(access, instruction)
            Opcode.ADD -> arithmetic(access, instruction, BinaryOperator.ADD, out)
            Opcode.SUB -> arithmetic(access, instruction, BinaryOperator.SUB, out)
            Opcode.MUL -> arithmetic(access, instruction, BinaryOperator.MUL, out)
            Opcode.DIV -> arithmetic(access, instruction, BinaryOperator.DIV, out)
            Opcode.POW -> arithmetic(access, instruction, BinaryOperator.POW, out)
            Opcode.UNM -> access.write(a, Expression.Unary(UnaryOperator.MINUS, access.operand(b)), out)
            Opcode.NOT -> access.write(a, Expression.Unary(UnaryOperator.NOT, access.operand(b)), out)
            Opcode.CONCAT -> access.write(a, (b..c).map { access.read(it) }.reduceRight { left, right ->
                Expression.Binary(BinaryOperator.CONCAT, left, right)
            }, out)
            Opcode.CALL -> call(access, instruction, registers, out)
            Opcode.TAILCALL -> return tailCall(access, instruction, pc, out)
            Opcode.RETURN -> out += Statement.Return(access.returned(a, b - 1))
            Opcode.SETLIST -> setList(access, a, access.registers(a + 1, instruction.bx % FIELDS_PER_FLUSH + 1))
            Opcode.SETLISTO -> setList(access, a, access.upToOpenTop(a + 1))
            Opcode.CLOSE -> Unit
            Opcode.CLOSURE -> {
                val captures = function.functions[instruction.bx].upvalueCount
                val names = (pc + 1..pc + captures).map { captured(access, function.code[it]) }
                access.write(a, Expression.Function(child(instruction.bx, names)), out)
                return pc + 1 + captures
            }
            Opcode.JMP, Opcode.EQ, Opcode.LT, Opcode.LE, Opcode.TEST,
            Opcode.FORLOOP, Opcode.TFORLOOP, Opcode.TFORPREP -> unsupported("${instruction.opcode} out of place")
        }
        return pc + 1
    }

    private fun setTable(access: Access, a: Int, b: Int, c: Int, out: MutableList<Statement>) {
        val key = access.rk(b)
        val value = access.rk(c)
        val table = access.registers.peek(a) as? Slot.Table
        if (table == null) {
            out += Statement.Assign(Expression.Index(access.read(a), key), value)
            return
        }
        val before = table.positional.size + access.registers.filledAbove(a)
        access.update(a, table.copy(keyed = table.keyed + (before to TableItem.Keyed(key, value))))
    }

    private fun setList(access: Access, a: Int, values: List<Expression>) {
        val table = access.registers.peek(a) as? Slot.Table ?: unsupported("SETLIST into r$a, which holds no table")
        access.update(a, table.copy(positional = table.positional + values))
    }

    /** What a capture after CLOSURE passes on: a local of this function (MOVE) or one of its upvalues */
    private fun captured(access: Access, capture: Instruction): String = when (capture.opcode) {
        Opcode.GETUPVAL -> frame.upvalue(capture.b)
        else -> access.localName(capture.b)
    }

    private fun self(access: Access, instruction: Instruction) {
        val receiver = access.operand(instruction.b)
        val key = instruction.c
        val name = (function.constants.getOrNull(key - RK_CONSTANT_BASE) as? LuaConstant.Text)
            ?.takeIf { isConstant(key) }?.value ?: unsupported("a method looked up by a computed key")
        access.put(instruction.a, Slot.Method(receiver, name))
        access.put(instruction.a + 1, Slot.Receiver)
    }

    private fun arithmetic(
        access: Access,
        instruction: Instruction,
        operator: BinaryOperator,
        out: MutableList<Statement>
    ) {
        access.operands(instruction.b, instruction.c)
        val value = Expression.Binary(operator, access.rk(instruction.b), access.rk(instruction.c))
        access.write(instruction.a, value, out)
    }

    private fun call(access: Access, instruction: Instruction, registers: Registers, out: MutableList<Statement>) {
        val a = instruction.a
        val call = access.call(a, instruction.b)
        when (val results = instruction.c - 1) {
            0 -> out += Statement.CallStatement(call).also { access.statementDone() }
            -1 -> access.put(a, Slot.Value(call)).also { registers.openTop = a }
            else -> {
                access.write(a, call, out)
                (1 until results).forEach { access.put(a + it, Slot.MoreResults) }
            }
        }
    }

    /** A tail call is always followed by the RETURN that would pass its results on */
    private fun tailCall(access: Access, instruction: Instruction, pc: Int, out: MutableList<Statement>): Int {
        out += Statement.Return(listOf(access.call(instruction.a, instruction.b)))
        val next = function.code.getOrNull(pc + 1)
        if (next?.opcode != Opcode.RETURN) unsupported("TAILCALL without its RETURN")
        return pc + 2
    }

    private fun name(index: Int): String =
        (function.constants[index] as? LuaConstant.Text)?.value ?: unsupported("a global named by a non-string")

}
