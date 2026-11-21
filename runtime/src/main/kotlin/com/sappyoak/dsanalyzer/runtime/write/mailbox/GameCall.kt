package com.sappyoak.dsanalyzer.runtime.write.mailbox

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.layout.CallRegister

/** Where the mailbox's request block keeps [this] register's value; each architecture's start at 0x18 */
internal val CallRegister.slot: Int get() = when (this) {
    CallRegister.Eax, CallRegister.Rcx -> 0x18
    CallRegister.Ecx, CallRegister.Rdx -> 0x20
    CallRegister.Edx, CallRegister.R8 -> 0x28
    CallRegister.Ebx, CallRegister.R9 -> 0x30
    CallRegister.Esi -> 0x38
    CallRegister.Edi -> 0x40
}

/**
 * One call into the game. [stack] holds the stack arguments, first argument first.
 * On x86 the cave restores ESP after the call, so cdecl and stdcall both work.
 * On x64 the cave adds the shadow space
 */
public class GameCall(
    public val target: Address,
    public val label: String,
    public val registers: Map<CallRegister, Long> = emptyMap(),
    public val stack: List<Long> = emptyList(),
    /**
     * Copied into the mailbox before the call. [dataPointers] put the address of this block, plus an
     * offset into it, in a register, which is how a call that wants a structure gets one
     */
    public val data: ByteArray = ByteArray(0),
    public val dataPointers: Map<CallRegister, Int> = emptyMap(),
    /**
     * Pointer fields inside [data]. The field at each key gets the address of [data] plus the value,
     * which is how a structure in the block points at another one in the same block
     */
    public val dataLinks: Map<Int, Int> = emptyMap(),
    /** Stack arguments, by index, that get the address of [data] plus the value in place of their own */
    public val stackData: Map<Int, Int> = emptyMap()
) {
    init {
        require(data.size <= MailboxSlot.DATA_LIMIT) {
            "$label carries ${data.size} bytes of data. The mailbox takes ${MailboxSlot.DATA_LIMIT}"
        }
        require(dataPointers.values.all { it >= 0 && it < data.size }) { "$label points a register outside its own data" }
        require(dataPointers.keys.none { it in registers }) { "$label sets the same register twice" }
        require(dataLinks.all { (at, to) -> at >= 0 && at + Int.SIZE_BYTES <= data.size && to in data.indices }) {
            "$label links a field outside its own data"
        }
        require(stackData.all { (index, to) -> index in stack.indices && to in data.indices }) {
            "$label points a stack argument outside its stack or its data"
        }
        require(stack.size <= MailboxSlot.MAX_ARGS) {
            "$label passes ${stack.size} stack arguments. The mailbox takes ${MailboxSlot.MAX_ARGS}"
        }
    }
}

/** The x64 registers the first four arguments go in */
private val X64_ARGUMENTS = listOf(CallRegister.Rcx, CallRegister.Rdx, CallRegister.R8, CallRegister.R9)

/**
 * A call in the edition's own convention: on x86 every argument on the stack, first argument first; on
 * x64 the first four in RCX RDX R8 R9 and the rest on the stack. [registers] adds implicit ones, such as EAX
 */
public fun conventionalCall(
    arch: CallArch,
    target: Address,
    label: String,
    arguments: List<Long>,
    registers: Map<CallRegister, Long> = emptyMap()
): GameCall {
    if (arch == CallArch.X86) return GameCall(target, label, registers, arguments)
    val inRegisters = X64_ARGUMENTS.zip(arguments).toMap()
    return GameCall(target, label, registers + inRegisters, arguments.drop(X64_ARGUMENTS.size))
}

/** The request slot both caves share. Register fields are 8 bytes wide in both */
internal object MailboxSlot {
    const val STATE: Int = 0x00
    const val FRAMES: Int = 0x04
    const val TARGET: Int = 0x10
    const val RETURN: Int = 0x70
    /** The remote thread's control word: the tool writes [STOP], the thread answers [STOPPED] as it ends */
    const val CONTROL: Int = 0x78
    const val SIZE: Int = 0x80

    /** A call's data block, from the slot. It sits past the slot in the same allocation */
    const val DATA: Int = 0x100
    const val DATA_LIMIT: Int = 0x700
    const val MAX_ARGS: Int = 6

    const val FREE: Int = 0
    const val REQUESTED: Int = 1
    const val RUNNING: Int = 2
    const val DONE: Int = 3

    const val STOP: Int = 1
    const val STOPPED: Int = 2

    fun argCount(arch: CallArch): Int = if (arch == CallArch.X86) 0x48 else 0x38
    fun firstArg(arch: CallArch): Int = if (arch == CallArch.X86) 0x50 else 0x40
    fun argStride(arch: CallArch): Int = if (arch == CallArch.X86) Int.SIZE_BYTES else Long.SIZE_BYTES
}
