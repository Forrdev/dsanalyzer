package com.sappyoak.dsanalyzer.runtime.write.mailbox

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

/** The request fields from [MailboxSlot.TARGET] through the return value, for a call whose data sits at [data] */
internal fun requestBytes(call: GameCall, arch: CallArch, data: Address): ByteArray {
    val request = littleEndian(MailboxSlot.RETURN + Long.SIZE_BYTES - MailboxSlot.TARGET)
    fun at(offset: Int) = offset - MailboxSlot.TARGET
    request.putLong(at(MailboxSlot.TARGET), call.target.value)
    call.registers.forEach { (register, value) -> request.putLong(at(register.offset), value) }
    call.dataPointers.forEach { (register, offset) -> request.putLong(at(register.offset), data.value + offset) }
    request.putInt(at(MailboxSlot.argCount(arch)), call.stack.size)
    call.stack.forEachIndexed { i, value ->
        val argument = call.stackData[i]?.let { data.value + it } ?: value
        val offset = at(MailboxSlot.firstArg(arch) + i * MailboxSlot.argStride(arch))
        if (arch == CallArch.X86) request.putInt(offset, argument.toInt()) else request.putLong(offset, argument)
    }
    return request.array()
}

/**
 * [GameCall.data] with each of its [GameCall.dataLinks] pointing into the block at [data].
 * Null when a link has no room for a pointer of [arch]'s width
 */
internal fun linkedData(call: GameCall, arch: CallArch, data: Address): ByteArray? {
    if (call.dataLinks.isEmpty()) return call.data
    val width = if (arch == CallArch.X86) Int.SIZE_BYTES else Long.SIZE_BYTES
    val bytes = call.data.copyOf()
    val fields = bytes.littleEndian()
    call.dataLinks.forEach { (at, to) ->
        if (at + width > bytes.size) return null
        val target = data.value + to
        if (width == Int.SIZE_BYTES) fields.putInt(at, target.toInt()) else fields.putLong(at, target)
    }
    return bytes
}
