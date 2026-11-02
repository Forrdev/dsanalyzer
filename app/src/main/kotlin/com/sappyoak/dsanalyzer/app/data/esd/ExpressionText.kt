package com.sappyoak.dsanalyzer.app.data.esd

import java.math.BigDecimal
import java.math.MathContext

import com.sappyoak.dsanalyzer.formats.esd.Bytecode
import com.sappyoak.dsanalyzer.formats.esd.EsdDefinitions
import com.sappyoak.dsanalyzer.formats.esd.expr.Decoded
import com.sappyoak.dsanalyzer.formats.esd.expr.EsdExpression
import com.sappyoak.dsanalyzer.formats.esd.expr.IfFalse
import com.sappyoak.dsanalyzer.formats.esd.expr.decode

/** Float32 constants widen to doubles, seven digits is all a float32 ever held */
private val FLOAT_DIGITS = MathContext(7)

public fun Bytecode.rendered(definitions: EsdDefinitions): String = when (val decoded = decode()) {
    is Decoded.Expression -> decoded.expression.render(definitions)
    is Decoded.Undecodable -> "undecodable: ${decoded.reason}, at byte ${decoded.at}"
}

internal fun EsdExpression.render(definitions: EsdDefinitions): String = when (this) {
    is EsdExpression.IntValue -> value.toString()
    is EsdExpression.FloatValue -> BigDecimal(value).round(FLOAT_DIGITS).stripTrailingZeros().toPlainString()
    is EsdExpression.Text -> "\"$value\""
    is EsdExpression.Call ->
        "${definitions.function(function)?.label ?: "f$function"}(${args.joinToString { it.render(definitions) }})"
    is EsdExpression.Binary -> "${left.operand(definitions)} ${op.symbol} ${right.operand(definitions)}"
    is EsdExpression.Unary -> "${op.symbol}${operand.operand(definitions)}"
    is EsdExpression.GetRegister -> "reg$index"
    is EsdExpression.SetRegister -> "reg$index = ${value.render(definitions)}"
    is EsdExpression.GroupArg -> "arg[${index.render(definitions)}]"
    EsdExpression.CallResult -> "result"
    EsdExpression.CallOngoing -> "still running"
    is EsdExpression.Marked ->
        "${expr.render(definitions)} (else ${if (ifFalse == IfFalse.Continue) "continue" else "abort"})"
}

private fun EsdExpression.operand(definitions: EsdDefinitions): String =
    if (this is EsdExpression.Binary) "(${render(definitions)}" else render(definitions)

