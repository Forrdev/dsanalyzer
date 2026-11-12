package com.sappyoak.dsanalyzer.runtime.write.mailbox

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.CallArch

public enum class CallRegister(internal val arch: CallArch, internal val offset: Int) {
    Eax(CallArch.X86, 0x18),
    Ecx(CallArch.X86, 0x20),
    Edx(CallArch.X86, 0x28),
    Ebx(CallArch.X86, 0x30),
    Esi(CallArch.X86, 0x38),
    Edi(CallArch.X86, 0x40),
    Rcx(CallArch.X64, 0x18),
    Rdx(CallArch.X64, 0x20),
    R8(CallArch.X64, 0x28),
    R9(CallArch.X64, 0x30)
}

/**
 * One call into the game. [stack] holds the stack arguments, first argument first.
 * On x86 the cave restores ESP after the call, so cdecl and stdcall both work.
 * On x64 the cave adds the shadow space
 */
public data class GameCall(
    public val target: Address,
    public val label: String,
    public val registers: Map<CallRegister, Long> = emptyMap(),
    public val stack: List<Long> = emptyList(),
    /**
     * Copied into the mailbox before the call. [dataPointers] put the address of this block, plus an
     * offset into it, in a register, which is how a call that wants a structure gets one
     */
    public val data: ByteArray = ByteArray(0),
    public val dataPointers: Map<CallRegister, Int> = emptyMap()
) {
    init {
        require(data.size <= MailboxSlot.DATA_LIMIT) {
            "$label carries ${data.size} bytes of data. The mailbox takes ${MailboxSlot.DATA_LIMIT}"
        }
        require(dataPointers.values.all { it >= 0 && it < data.size }) { "$label points a register outside its own data" }
        require(dataPointers.keys.none { it in registers }) { "$label sets the same register twice" }
        require(stack.size <= MailboxSlot.MAX_ARGS) {
            "$label passes ${stack.size} stack arguments. The mailbox takes ${MailboxSlot.MAX_ARGS}"
        }
    }
}

/** The request slot both caves share. Register fields are 8 bytes wide in both */
internal object MailboxSlot {
    const val STATE: Int = 0x00
    const val FRAMES: Int = 0x04
    const val TARGET: Int = 0x10
    const val RETURN: Int = 0x70
    const val SIZE: Int = 0x80

    /** A call's data block, from the slot. It sits past the slot in the same allocation */
    const val DATA: Int = 0x100
    const val DATA_LIMIT: Int = 0x700
    const val MAX_ARGS: Int = 6

    const val FREE: Int = 0
    const val REQUESTED: Int = 1
    const val RUNNING: Int = 2
    const val DONE: Int = 3

    fun argCount(arch: CallArch): Int = if (arch == CallArch.X86) 0x48 else 0x38
    fun firstArg(arch: CallArch): Int = if (arch == CallArch.X86) 0x50 else 0x40
    fun argStride(arch: CallArch): Int = if (arch == CallArch.X86) Int.SIZE_BYTES else Long.SIZE_BYTES
}
