package com.sappyoak.dsanalyzer.lua.decompile

import com.sappyoak.dsanalyzer.lua.ir.Expression
import com.sappyoak.dsanalyzer.lua.ir.TableItem

/** What a temporary register holds between the instruction that fills it and the one that uses it */
internal sealed interface Slot {
    data class Value(val expression: Expression) : Slot

    /** SELF's function half: the method looked up on its receiver */
    data class Method(val receiver: Expression, val name: String) : Slot

    /** SELF's other half, the receiver passed as the first argument */
    data object Receiver : Slot

    /** A further result of a call that returns several into consecutive registers */
    data object MoreResults : Slot

    /**
     * A table constructor still being filled. Positional items wait in the registers above it until a
     * SETLIST; keyed items remember how many positional items came before them
     */
    data class Table(val positional: List<Expression>, val keyed: List<Pair<Int, TableItem.Keyed>>) : Slot {
        fun toExpression(): Expression.Table {
            val items = mutableListOf<TableItem>()
            var next = 0
            for ((before, item) in keyed) {
                while (next < before && next < positional.size) items += TableItem.Positional(positional[next++])
                items += item
            }
            while (next < positional.size) items += TableItem.Positional(positional[next++])
            return Expression.Table(items)
        }
    }
}

/** The value a local is declared with from this slot, or none for a bare one or a call's further result */
internal fun Slot?.declaredValue(): Expression? = when (this) {
    null, Slot.MoreResults -> null
    is Slot.Value -> expression
    is Slot.Table -> toExpression()
    else -> unsupported("a local declared from $this")
}

/** A shape this decompiler does not rebuild; the function is then kept as disassembly */
internal class UnsupportedShape(message: String) : Exception(message)

internal fun unsupported(message: String): Nothing = throw UnsupportedShape(message)

/**
 * [block]'s result, or null when it meets a shape it does not rebuild. Used when looking ahead, where code
 * that breaks the allocation rules only means the look went past the statement
 */
internal inline fun <T> attempt(block: () -> T): T? = try {
    block()
} catch (_: UnsupportedShape) {
    null
} catch (_: LocalsFound) {
    null
}
