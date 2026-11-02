package com.sappyoak.dsanalyzer.formats.esd.expr

import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.esd.Bytecode

public fun Bytecode.decode(): Decoded = Decoder(bytes).run()

/** In Prepare to Die's handler table this byte's slot is null, so the game would call through nothing */
public const val NULL_HANDLER_REASON: String = "byte 0xBB, which dispatches through a null handler in PTDE"

private const val SMALL_INT_LAST = 0x7F
private const val SMALL_INT_BIAS = 64
private const val CALL_FIRST = 0x84
private const val CALL_LAST = 0x8A
private const val SET_REGISTER_FIRST = 0xA7
private const val GET_REGISTER_FIRST = 0xAF
private const val REGISTER_COUNT = 8

private val BINARY = mapOf(
    0x8C to Op.Add, 0x8E to Op.Subtract, 0x8F to Op.Multiply, 0x90 to Op.Divide,
    0x91 to Op.LessOrEqual, 0x92 to Op.GreaterOrEqual, 0x93 to Op.Less, 0x94 to Op.Greater,
    0x95 to Op.Equal, 0x96 to Op.NotEqual, 0x98 to Op.And, 0x99 to Op.Or
)
private val UNARY = mapOf(0x8D to Op.Negate, 0x9A to Op.Not)

private class Decoder(private val bytes: ByteArray) {
    private val stack = ArrayDeque<EsdExpression>()
    private val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
    private var at = 0

    fun run(): Decoded {
        while (at < bytes.size) {
            val start = at
            val op = bytes[at++].toInt() and 0xFF
            if (op == 0xA1) break
            step(op)?.let { reason ->
                return Decoded.Undecodable(reason, start)
            }
        }

        return if (stack.size == 1) Decoded.Expression(stack.single())
        else Decoded.Undecodable("leaves ${stack.size} values where one is the result", at)
    }

    private fun step(op: Int): String? = when {
        op <= SMALL_INT_LAST -> push(EsdExpression.IntValue(op - SMALL_INT_BIAS))
        op == 0x80 -> read(4) { push(EsdExpression.FloatValue(buffer.getFloat(it).toDouble())) }
        op == 0x81 -> read(8) { push(EsdExpression.FloatValue(buffer.getDouble(it))) }
        op == 0x82 -> read(4) { push(EsdExpression.IntValue(buffer.getInt(it))) }
        op == 0xA5 -> text()
        op in CALL_FIRST..CALL_LAST -> call(op - CALL_FIRST)
        op in BINARY -> pop(2) { (left, right) -> push(EsdExpression.Binary(BINARY.getValue(op), left, right)) }
        op in UNARY -> pop(1) { (operand) -> push(EsdExpression.Unary(UNARY.getValue(op), operand)) }
        op == 0xA6 -> mark(IfFalse.Continue)
        op == 0xB7 -> mark(IfFalse.Abort)
        op in SET_REGISTER_FIRST until SET_REGISTER_FIRST + REGISTER_COUNT ->
            pop(1) { (value) -> push(EsdExpression.SetRegister(op - SET_REGISTER_FIRST, value)) }
        op in GET_REGISTER_FIRST until GET_REGISTER_FIRST + REGISTER_COUNT ->
            push(EsdExpression.GetRegister(op - GET_REGISTER_FIRST))
        op == 0xB8 -> pop(1) { (index) -> push(EsdExpression.GroupArg(index)) }
        op == 0xB9 -> push(EsdExpression.CallResult)
        op == 0xBA -> push(EsdExpression.CallOngoing)
        op == 0xBB -> NULL_HANDLER_REASON
        else -> "unknown opcode 0x%02X".format(op)
    }

    private fun push(expression: EsdExpression): String? {
        stack.addLast(expression)
        return null
    }

    private fun pop(count: Int, then: (List<EsdExpression>) -> String?): String? {
        if (stack.size < count) return "needs $count values and the stack hold ${stack.size}"
        return then(List(count) { stack.removeLast() }.reversed())
    }

    private fun read(width: Int, then: (Int) -> String?): String? {
        if (at + width > bytes.size) return "a $width-byte constant runs past the end"
        val from = at
        at += width
        return then(from)
    }

    private fun call(argCount: Int): String? = pop(argCount + 1) { values ->
        val id = values.first() as? EsdExpression.IntValue ?: return@pop "calls a function whose id is not a constant"
        push(EsdExpression.Call(id.value, values.drop(1)))
    }

    private fun mark(ifFalse: IfFalse): String? = pop(1) { (top) ->
        push(EsdExpression.Marked(top, ifFalse))
    }

    private fun text(): String? {
        var end = at
        while (end + 1 < bytes.size && (bytes[end].toInt() != 0 || bytes[end + 1].toInt() != 0)) {
            end += 2
        }

        if (end + 1 >= bytes.size) return "a string runs past the end"

        val value = String(bytes, at, end - at, Charsets.UTF_16LE)
        at = end + 2
        return push(EsdExpression.Text(value))
    }
}
