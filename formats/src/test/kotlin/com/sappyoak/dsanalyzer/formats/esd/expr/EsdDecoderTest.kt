package com.sappyoak.dsanalyzer.formats.esd.expr

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.esd.Bytecode

private fun decode(vararg bytes: Int): Decoded = Bytecode(ByteArray(bytes.size) { bytes[it].toByte() }).decode()
private fun expr(vararg bytes: Int): EsdExpression = (decode(*bytes) as Decoded.Expression).expression

private val ONE = EsdExpression.IntValue(1)

class ExprDecoderTest : FunSpec({
    test("a call pops its arguments and then the id beneath them") {
        assertSoftly {
            // GetHP() <= 0: push 1000, call with none, push 0, <=, end
            expr(0x82, 0xE8, 0x03, 0, 0, 0x84, 0x40, 0x91, 0xA1) shouldBe
                    EsdExpression.Binary(Op.LessOrEqual, EsdExpression.Call(1000, emptyList()), EsdExpression.IntValue(0))
            // f5(1, 1.5): push 5, push 1, push 1.5f, call with two
            expr(0x45, 0x41, 0x80, 0x00, 0x00, 0xC0, 0x3F, 0x86) shouldBe
                    EsdExpression.Call(5, listOf(ONE, EsdExpression.FloatValue(1.5)))
        }
    }

    test("constants, strings, registers, group arguments and the marks") {
        assertSoftly {
            expr(0x82, 0xFF, 0xFF, 0xFF, 0xFF) shouldBe EsdExpression.IntValue(-1)
            expr(0xA5, 0x68, 0, 0x69, 0, 0, 0) shouldBe EsdExpression.Text("hi")
            expr(0x41, 0xA7) shouldBe EsdExpression.SetRegister(0, ONE)
            expr(0xB6) shouldBe EsdExpression.GetRegister(7)
            expr(0x40, 0xB8) shouldBe EsdExpression.GroupArg(EsdExpression.IntValue(0))
            expr(0x41, 0xA6) shouldBe EsdExpression.Marked(ONE, IfFalse.Continue)
            expr(0x41, 0x9A, 0xB7) shouldBe EsdExpression.Marked(EsdExpression.Unary(Op.Not, ONE), IfFalse.Abort)
            expr(0xB9) shouldBe EsdExpression.CallResult
        }
    }

    test("anything it cannot follow is undecodable at the byte where it stopped, never an exception") {
        assertSoftly {
            decode(0x41, 0xBB) shouldBe Decoded.Undecodable(NULL_HANDLER_REASON, 1)
            decode(0xFF) shouldBe Decoded.Undecodable("unknown opcode 0xFF", 0)
            decode(0x91) shouldBe Decoded.Undecodable("needs 2 values and the stack holds 0", 0)
            decode(0x41, 0x41, 0xA1) shouldBe Decoded.Undecodable("leaves 2 values where one is the result", 3)
            decode(0x82, 0x01) shouldBe Decoded.Undecodable("a 4-byte constant runs past the end", 0)
            decode(0xAF, 0x84) shouldBe Decoded.Undecodable("calls a function whose id is not a constant", 1)
        }
    }
})
