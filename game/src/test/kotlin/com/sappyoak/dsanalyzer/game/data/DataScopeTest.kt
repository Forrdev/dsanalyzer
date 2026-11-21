package com.sappyoak.dsanalyzer.game.data

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

class DataScopeTest : FunSpec({
    test("a memo loads each key once") {
        runTest {
            val memo = DataScope(installation(), noFiles(backgroundScope), backgroundScope).memo<Int, Int>("test")
            var loads = 0
            repeat(3) { memo.get(1) { ++loads } shouldBe 1 }
            loads shouldBe 1
        }
    }

    test("a failed load is tried again on the next get") {
        runTest {
            val memo = DataScope(installation(), noFiles(backgroundScope), backgroundScope).memo<Int, Int>("test")
            shouldThrow<IllegalStateException> { memo.get(1) { error("broken") } }
            memo.get(1) { 2 } shouldBe 2
        }
    }

    test("closing cancels a load still running") {
        runTest {
            val data = DataScope(installation(), noFiles(backgroundScope), backgroundScope)
            val never = CompletableDeferred<Int>()
            val waiting = async { runCatching { data.memo<Int, Int>("test").get(1) { never.await() } } }
            runCurrent()
            data.close()
            (waiting.await().exceptionOrNull() is CancellationException) shouldBe true
        }
    }
})
