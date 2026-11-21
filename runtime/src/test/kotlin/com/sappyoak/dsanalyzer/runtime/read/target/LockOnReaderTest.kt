package com.sappyoak.dsanalyzer.runtime.read.target

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers
import com.sappyoak.dsanalyzer.runtime.ptde.LockOnMan
import com.sappyoak.dsanalyzer.runtime.ptde.LockOnNode
import com.sappyoak.dsanalyzer.runtime.ptde.ptdeLayout
import com.sappyoak.dsanalyzer.runtime.sampling.plantSignatures

private const val SIGNATURES_AT = 0x1000
private const val STATICS_AT = 0x2000
private const val MANAGER_AT = 0x3000
private const val NODES_AT = 0x5000
private const val LOCKABLES_AT = 0x6000

private const val CHOSEN = 0x10030035

/** Three candidates, the [chosen] one marked, with [locked] set on the manager */
private fun lockedOn(chosen: Int?, locked: Boolean = true): FakeGame = FakeGame().apply {
    val static = plantSignatures(listOf(LockOnMan.Pointer.base), SIGNATURES_AT, STATICS_AT).values.single()
    pointer(static, MANAGER_AT)
    write(MANAGER_AT + LockOnMan.Locked, if (locked) 1 else 0)
    pointer(MANAGER_AT + LockOnMan.Candidates, NODES_AT)

    for (i in 0 until 3) {
        val node = NODES_AT + i * LockOnNode.SIZE
        val lockable = LOCKABLES_AT + i * 0x20
        if (i < 2) pointer(node + LockOnNode.Next, node + LockOnNode.SIZE) else nullPointer(node + LockOnNode.Next)
        pointer(node + LockOnNode.Lockable, lockable)
        write(node + LockOnNode.Flags, if (i == chosen) LockOnNode.Chosen else 0)
        int(lockable + LockOnNode.Handle, CHOSEN - 2 + i)
    }
}

private fun read(game: FakeGame): Int? =
    LockOnReader(ptdeLayout().lockOn).read(GameMemory(game, game.resolvePointers(listOf(LockOnMan.Pointer), game.module)), null)

class LockOnReaderTest : FunSpec({
    test("the chosen candidate's handle is the lock target") {
        read(lockedOn(chosen = 2)) shouldBe CHOSEN
    }

    test("nothing is read while the manager is not locked") {
        read(lockedOn(chosen = 2, locked = false)).shouldBeNull()
    }

    test("a list with no chosen candidate gives nothing") {
        read(lockedOn(chosen = null)).shouldBeNull()
    }
})
