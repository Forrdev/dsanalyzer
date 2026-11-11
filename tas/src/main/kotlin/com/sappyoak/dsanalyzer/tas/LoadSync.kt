package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.write.CodeEdit
import com.sappyoak.dsanalyzer.runtime.write.GameWriter
import com.sappyoak.dsanalyzer.runtime.write.WriteKey
import com.sappyoak.dsanalyzer.tas.Mailbox.Field

private val BUDGET_PATCH = WriteKey("tas:load-budget")

public class LoadSync internal constructor(
    private val mailbox: Mailbox,
    private val writer: GameWriter,
    private val budgetImmediate: Address
) {
    public val isOn: Boolean get() = (mailbox.read(Field.LoadBarrier) ?: 0) != 0

    public fun enable(): Boolean =
        writer.patchCode(CodeEdit(BUDGET_PATCH, budgetImmediate, expected = byteArrayOf(1), bytes = byteArrayOf(0))) &&
                mailbox.write(Field.LoadBarrier, 1)

    /** The barrier goes first, so no frame is held against a budget that is back on */
    public fun disable(): Boolean {
        val released = mailbox.write(Field.LoadBarrier, 0)
        writer.revert(BUDGET_PATCH)
        return released
    }
}
