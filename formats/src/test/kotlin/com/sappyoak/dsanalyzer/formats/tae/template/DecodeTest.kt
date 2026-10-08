package com.sappyoak.dsanalyzer.formats.tae.template

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.tae.EventParams
import com.sappyoak.dsanalyzer.formats.tae.TaeEvent

private fun bytes(size: Int, fill: ByteBuffer.() -> Unit): EventParams =
    EventParams(ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN).apply(fill).array())

private fun event(params: EventParams, type: Int = 1) =
    TaeEvent(type = type, startTime = 0.25f, endTime = 0.75f, params = params)

private fun definition(vararg params: ParamDefinition) =
    EventDefinition(type = 1, name = "Tested", params = params.toList())

private fun param(type: ParamType, name: String? = "value", values: List<KnownValue> = emptyList()) =
    ParamDefinition(type = type, name = name, values = values)

private val FLAG_VALUES = listOf(
    KnownValue(8L, "Invincible"),
    KnownValue(3L, "Guarding", note = "ArgB:Guard BehaviorJudgeID")
)

class DecodeTest : FunSpec({
    test("parameters are packed with no alignment") {
        val packed = bytes(5) {
            put(0, 2)
            putInt(1, 0x0BADF00D)
        }

        val decoded = event(packed).decode(definition(param(ParamType.U8, "first"), param(ParamType.S32, "second")))

        assertSoftly {
            decoded.params.map { it.value } shouldBe listOf(ParamValue.Number(2L), ParamValue.Number(0x0BADF00DL))
            decoded.sizeMismatch shouldBe false
        }
    }

    test("a gradient is two floats, not one") {
        val packed = bytes(12) {
            putFloat(0, 1.0f)
            putFloat(4, 4.0f)
            putInt(8, 42)
        }

        val decoded = event(packed).decode(definition(param(ParamType.F32Grad, "ramp"), param(ParamType.S32, "after")))

        assertSoftly {
            decoded.params[0].value shouldBe ParamValue.Gradient(1.0f, 4.0f)
            decoded.params[1].value shouldBe ParamValue.Number(42L)
        }
    }

    test("each type reads the width it says it does") {
        val packed = bytes(11) {
            put(0, 1)
            put(1, 0xFF.toByte())
            put(2, 0xFF.toByte())
            putShort(3, 0xFFFF.toShort())
            putShort(5, (-2).toShort())
            putFloat(7, 0.5f)
        }

        val decoded = event(packed).decode(
            definition(
                param(ParamType.B, "flag"),
                param(ParamType.U8, "unsignedByte"),
                param(ParamType.S8, "signedByte"),
                param(ParamType.U16, "unsignedShort"),
                param(ParamType.S16, "signedShort"),
                param(ParamType.F32, "decimal")
            )
        )

        decoded.params.map { it.value } shouldBe listOf(
            ParamValue.Flag(true),
            ParamValue.Number(255L),
            ParamValue.Number(-1L),
            ParamValue.Number(65_535L),
            ParamValue.Number(-2L),
            ParamValue.Decimal(0.5f)
        )
    }

    test("a parameter the file has no bytes for is absent rather than wrong") {
        val decoded = event(bytes(4) { putInt(0, 1) })
            .decode(definition(param(ParamType.S32, "present"), param(ParamType.S32, "past the end")))

        assertSoftly {
            decoded.params[0].value shouldBe ParamValue.Number(1L)
            decoded.params[1].value shouldBe ParamValue.Missing
            decoded.sizeMismatch shouldBe true
        }
    }

    test("a known value is named, and says what it makes the other parameters mean") {
        val decoded = event(bytes(4) { putInt(0, 3) })
            .decode(definition(param(ParamType.S32, "FlagType", FLAG_VALUES)))

        assertSoftly {
            decoded.params.single().known?.name shouldBe "Guarding"
            decoded.params.single().known?.note shouldBe "ArgB:Guard BehaviorJudgeID"
            decoded.label shouldBe "Tested"
        }
    }

    test("a value nothing names still reads as itself") {
        val decoded = event(bytes(4) { putInt(0, 77) })
            .decode(definition(param(ParamType.S32, "FlagType", FLAG_VALUES)))

        assertSoftly {
            decoded.params.single().known shouldBe null
            decoded.params.single().value shouldBe ParamValue.Number(77L)
        }
    }

    /** The window is in the times, so an event no template covers is still worth showing */
    test("an event with no definition keeps its window") {
        val decoded = event(bytes(4) { putInt(0, 1) }, type = 9_999).decode(null)

        assertSoftly {
            decoded.label shouldBe "Event 9999"
            decoded.params shouldBe emptyList()
            decoded.event.startTime shouldBe 0.25f
            decoded.sizeMismatch shouldBe false
        }
    }
})