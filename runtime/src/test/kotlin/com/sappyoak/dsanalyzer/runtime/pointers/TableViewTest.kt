package com.sappyoak.dsanalyzer.runtime.pointers

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.memory.Address

private const val OWNER_AT = 0x1000
private const val SLOTS_AT = 0x2000
private const val ELEMENTS_AT = 0x3000

/** Far enough apart that an element overrunning into the next one would be obvious */
private const val ELEMENT_STRIDE = 0x100

private fun FakeGame.element(index: Int): Int = ELEMENTS_AT + index * ELEMENT_STRIDE

/** A `begin`/`end` pair at the owner, with [count] pointer slots behind it */
private fun FakeGame.layOutRange(count: Int) {
    pointer(OWNER_AT + 0x04, SLOTS_AT)
    pointer(OWNER_AT + 0x08, SLOTS_AT + count * Int.SIZE_BYTES)
    repeat(count) { pointer(SLOTS_AT + it * Int.SIZE_BYTES, element(it)) }
}

/** A chain from the owner's head, each node pointing at the next and the last at nothing */
private fun FakeGame.layOutChain(count: Int) {
    pointer(OWNER_AT + 0x04, element(0))
    repeat(count) { index ->
        if (index == count - 1) nullPointer(element(index) + 0x30)
        else pointer(element(index) + 0x30, element(index + 1))
    }
}

private val RANGE_OF_POINTERS = GameTable(
    name = "RangeOfPointers",
    shape = TableShape.Range(beginAt = 0x04, endAt = 0x08),
    elements = TableElements.Pointers
)

private val CHAIN = GameTable(
    name = "Chain",
    shape = TableShape.Linked(headAt = 0x04, nextAt = 0x30),
    elements = TableElements.Inline(ELEMENT_STRIDE)
)

private val PARAM_LIST = GameTable(
    name = "ParamList",
    shape = TableShape.ParamList(lengthAt = 0x08, firstAt = 0x0C),
    elements = TableElements.Pointers
)

/**
 * A param list stating [stated] slots: [stated] - 1 elements, then a trailing slot that points at
 * the next list rather than at an element. [trailing] is what that last slot holds, a live
 * pointer for every list but the file's last one, which holds zero
 */
private fun FakeGame.layOutParamList(stated: Int, trailing: Int) {
    int(OWNER_AT + 0x08, stated)
    repeat(stated - 1) { pointer(OWNER_AT + 0x0C + it * Int.SIZE_BYTES, element(it)) }
    int(OWNER_AT + 0x0C + (stated - 1) * Int.SIZE_BYTES, trailing)
}


class TableViewTest : FunSpec({
    test("a param list drops the trailing slot, which is the next list and not an element") {
        val game = FakeGame().apply { layOutParamList(stated = 4, trailing = 0x9999) }
        val view = TableView(PARAM_LIST)

        assertSoftly {
            view.refresh(game, game.address(OWNER_AT)) shouldBe true
            view.length shouldBe 3
            view.elements shouldBe listOf(0, 1, 2).map { game.address(game.element(it)) }
        }
    }

    test("a param list ending the file drops its null trailing slot too") {
        val game = FakeGame().apply { layOutParamList(stated = 3, trailing = 0) }
        val view = TableView(PARAM_LIST)

        assertSoftly {
            view.refresh(game, game.address(OWNER_AT)) shouldBe true
            view.length shouldBe 2
            view.elements shouldHaveSize 2
        }
    }

    test("a param list stating no slots is refused") {
        val game = FakeGame().apply { int(OWNER_AT + 0x08, 0) }
        TableView(PARAM_LIST).refresh(game, game.address(OWNER_AT)) shouldBe false
    }

    test("a begin and end pair bounds the slots, and each slot names an element") {
        val game = FakeGame().apply { layOutRange(3) }
        val view = TableView(RANGE_OF_POINTERS)

        assertSoftly {
            view.refresh(game, game.address(OWNER_AT)) shouldBe true
            view.length shouldBe 3
            view.elements shouldBe listOf(0, 1, 2).map { game.address(game.element(it)) }
        }
    }

    /**
     * A null slot inside the length is an absent element rather than a failed read — the game
     * leaves holes — so it is dropped while the length still reports what the game said
     */
    test("a null slot is a hole rather than a failure") {
        val game = FakeGame().apply {
            layOutRange(3)
            nullPointer(SLOTS_AT + Int.SIZE_BYTES)
        }
        val view = TableView(RANGE_OF_POINTERS)

        assertSoftly {
            view.refresh(game, game.address(OWNER_AT)) shouldBe true
            view.length shouldBe 3
            view.elements shouldBe listOf(0, 2).map { game.address(game.element(it)) }
        }
    }

    test("a counted table reads its length from the owner and its elements inline after it") {
        val game = FakeGame().apply { int(OWNER_AT + 0x08, 4) }
        val table = GameTable(
            name = "CountedInline",
            shape = TableShape.Counted(lengthAt = 0x08, firstAt = 0x0C),
            elements = TableElements.Inline(ELEMENT_STRIDE)
        )
        val view = TableView(table)

        assertSoftly {
            view.refresh(game, game.address(OWNER_AT)) shouldBe true
            view.elements shouldHaveSize 4
            view.elements.first() shouldBe game.address(OWNER_AT + 0x0C)
            view.elements.last() shouldBe game.address(OWNER_AT + 0x0C + 3 * ELEMENT_STRIDE)
        }
    }

    test("an indirect table finds its elements through a pointer rather than beside it") {
        val game = FakeGame().apply {
            int(OWNER_AT + 0x08, 2)
            pointer(OWNER_AT + 0x0C, ELEMENTS_AT)
        }
        val table = GameTable(
            name = "CountedIndirect",
            shape = TableShape.Counted(lengthAt = 0x08, firstAt = 0x0C, indirect = true),
            elements = TableElements.Inline(ELEMENT_STRIDE)
        )
        val view = TableView(table)

        assertSoftly {
            view.refresh(game, game.address(OWNER_AT)) shouldBe true
            view.elements shouldBe listOf(0, 1).map { game.address(game.element(it)) }
        }
    }

    test("a fixed table takes its length from the declaration") {
        val game = FakeGame().apply { pointer(OWNER_AT + 0x0C, ELEMENTS_AT) }
        val table = GameTable(
            name = "Fixed",
            shape = TableShape.Fixed(length = 3, firstAt = 0x0C, indirect = true),
            elements = TableElements.Inline(ELEMENT_STRIDE)
        )

        TableView(table).apply {
            refresh(game, game.address(OWNER_AT)) shouldBe true
            elements shouldHaveSize 3
        }
    }

    test("a chain is walked to its end, in order") {
        val game = FakeGame().apply { layOutChain(4) }
        val view = TableView(CHAIN)

        assertSoftly {
            view.refresh(game, game.address(OWNER_AT)) shouldBe true
            view.length shouldBe 4
            view.elements shouldBe (0 until 4).map { game.address(game.element(it)) }
        }
    }

    /**
     * A chain that turns back on itself would otherwise be an unbounded read on the sampling
     * thread, which is a hung tool rather than a failed one
     */
    test("a chain that points at itself stops rather than running forever") {
        val game = FakeGame().apply {
            pointer(OWNER_AT + 0x04, ELEMENTS_AT)
            pointer(ELEMENTS_AT + 0x30, ELEMENTS_AT)
        }
        val view = TableView(CHAIN)

        assertSoftly {
            view.refresh(game, game.address(OWNER_AT)) shouldBe true
            view.elements shouldHaveSize 1
            view.truncated shouldBe true
        }
    }

    test("a length past the limit is taken as far as the limit and reported as truncated") {
        val game = FakeGame().apply { int(OWNER_AT + 0x08, 50) }
        val table = GameTable(
            name = "Overlong",
            shape = TableShape.Counted(lengthAt = 0x08, firstAt = 0x0C),
            elements = TableElements.Inline(0x10),
            limit = 8
        )
        val view = TableView(table)

        assertSoftly {
            view.refresh(game, game.address(OWNER_AT)) shouldBe true
            view.length shouldBe 50
            view.elements shouldHaveSize 8
            view.truncated shouldBe true
        }
    }

    test("a negative length is a failed read rather than an empty table") {
        val game = FakeGame().apply { int(OWNER_AT + 0x08, -1) }
        val table = GameTable(
            name = "Negative",
            shape = TableShape.Counted(lengthAt = 0x08, firstAt = 0x0C),
            elements = TableElements.Inline(0x10)
        )

        TableView(table).refresh(game, game.address(OWNER_AT)) shouldBe false
    }

    test("an owner that resolved to nothing leaves the table empty rather than reading") {
        val game = FakeGame()
        val view = TableView(RANGE_OF_POINTERS)

        assertSoftly {
            view.refresh(game, Address.Null) shouldBe false
            view.elements.shouldBeEmpty()
            game.reads shouldBe 0
        }
    }

    /**
     * The cost of a table has to be set by its shape and not by its size, or sampling a map block's
     * 1178 parts becomes 1178 crossings into the kernel
     */
    test("reading a table costs the same whether it holds three elements or thirty") {
        fun readsFor(count: Int): Int {
            val game = FakeGame().apply { layOutRange(count) }
            val before = game.reads
            TableView(RANGE_OF_POINTERS).refresh(game, game.address(OWNER_AT))
            return game.reads - before
        }

        readsFor(30) shouldBe readsFor(3)
    }
})