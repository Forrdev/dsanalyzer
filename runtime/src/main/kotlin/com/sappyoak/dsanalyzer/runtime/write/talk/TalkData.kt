package com.sappyoak.dsanalyzer.runtime.write.talk

import java.nio.ByteBuffer

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.CallArch
import com.sappyoak.dsanalyzer.runtime.layout.TalkLayout
import com.sappyoak.dsanalyzer.runtime.write.mailbox.CallRegister
import com.sappyoak.dsanalyzer.runtime.write.mailbox.GameCall
import com.sappyoak.dsanalyzer.shared.binary.littleEndian

/** Where each structure sits in the call's data block; each has room for the larger edition */
internal const val EVENT_AT: Int = 0x000
internal const val HANDLER_AT: Int = 0x200
internal const val OWNER_AT: Int = 0x280
internal const val LINK_AT: Int = 0x380
internal const val DATA_SIZE: Int = 0x3C0

/** The type the game gives an int param */
private const val INT_PARAM: Int = 2

/** The game's addresses one talk call needs */
internal class TalkTargets(
    val dispatcher: Address,
    val ownerLookup: Address,
    val eventVtable: Address,
    val handlerVtable: Address?
)

/**
 * The event, a handler pointing at an owner holding [ownerId], and the owner's link where the edition
 * has one. Nothing in it is game code: the dispatcher reads it as the structures it expects
 */
internal fun talkData(command: TalkCommand, layout: TalkLayout, targets: TalkTargets, ownerId: Int, arch: CallArch): ByteArray {
    val event = layout.event
    require(EVENT_AT + event.size <= HANDLER_AT) { "The talk event does not fit before the handler" }
    require(command.params.size <= event.maxParams) { "Talk command ${command.id} takes at most ${event.maxParams} params" }
    val data = littleEndian(DATA_SIZE)
    data.putPointer(EVENT_AT, targets.eventVtable, arch)
    data.putInt(EVENT_AT + event.command, command.id)
    command.params.forEachIndexed { i, value ->
        val at = EVENT_AT + event.firstParam + i * event.paramStride
        data.putInt(at, value)
        data.putInt(at + event.paramType, INT_PARAM)
    }
    data.putInt(EVENT_AT + event.highestIndex, command.params.size)
    targets.handlerVtable?.let { data.putPointer(HANDLER_AT, it, arch) }
    data.putInt(OWNER_AT + layout.ownerEntity, ownerId)
    return data.array()
}

/** The pointer fields inside the block, which the mailbox fills in once it knows where the block is */
internal fun talkLinks(layout: TalkLayout): Map<Int, Int> = buildMap {
    put(HANDLER_AT + layout.handlerOwner, OWNER_AT)
    layout.ownerLink?.let { link ->
        put(OWNER_AT + link.link, LINK_AT)
        put(LINK_AT + link.back, OWNER_AT)
    }
}

/** Resolves the owner the block holds; the call returns the character, or 0 */
internal fun ownerLookupCall(data: ByteArray, layout: TalkLayout, targets: TalkTargets, arch: CallArch): GameCall =
    GameCall(
        target = targets.ownerLookup,
        label = "Resolve the talk owner",
        data = data,
        dataPointers = mapOf((if (arch == CallArch.X86) CallRegister.Eax else CallRegister.Rcx) to OWNER_AT),
        dataLinks = talkLinks(layout)
    )

/** PTDE is thiscall with the event then the machine on the stack; DSR passes the same in RCX, RDX and R8 */
internal fun dispatchCall(command: TalkCommand, data: ByteArray, layout: TalkLayout, targets: TalkTargets, arch: CallArch): GameCall =
    if (arch == CallArch.X86) {
        GameCall(
            target = targets.dispatcher,
            label = "Talk command ${command.id}",
            stack = listOf(0L, 0L),
            data = data,
            dataPointers = mapOf(CallRegister.Ecx to HANDLER_AT),
            dataLinks = talkLinks(layout),
            stackData = mapOf(0 to EVENT_AT)
        )
    } else {
        GameCall(
            target = targets.dispatcher,
            label = "Talk command ${command.id}",
            registers = mapOf(CallRegister.R8 to 0L),
            data = data,
            dataPointers = mapOf(CallRegister.Rcx to HANDLER_AT, CallRegister.Rdx to EVENT_AT),
            dataLinks = talkLinks(layout)
        )
    }

private fun ByteBuffer.putPointer(at: Int, address: Address, arch: CallArch) {
    if (arch == CallArch.X86) putInt(at, address.value.toInt()) else putLong(at, address.value)
}
