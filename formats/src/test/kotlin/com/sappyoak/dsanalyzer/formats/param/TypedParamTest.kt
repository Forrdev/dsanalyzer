package com.sappyoak.dsanalyzer.formats.param

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TYPE = "TEST_PARAM_ST"
private const val ROW_SIZE = 0x10
private const val PADDED = 0x12

private class TestRow(row: ParamRowData) : ParamRow(row)

private val TEST_ROW = ParamRowType(TYPE, ROW_SIZE, 2, emptyList(), ::TestRow)

private fun param(rowSize: Int, rows: Int, version: Int = 2) =
    Param(TYPE, version, rowSize, List(rows) { ParamRowData(it, "", RowBytes(ByteArray(rowSize))) })

class TypedParamTest : FunSpec({
    test("a single row's size takes the file's trailing padding, so it is not held against the def") {
        param(PADDED, rows = 1).typed(TEST_ROW).rows.size shouldBe 1
    }

    test("with two rows the size is the file's own, and one that disagrees is refused") {
        shouldThrow<IllegalArgumentException> { param(PADDED, rows = 2).typed(TEST_ROW) }
    }

    test("another data version is refused whatever the size") {
        shouldThrow<IllegalArgumentException> { param(ROW_SIZE, rows = 2, version = 1).typed(TEST_ROW) }
    }
})
