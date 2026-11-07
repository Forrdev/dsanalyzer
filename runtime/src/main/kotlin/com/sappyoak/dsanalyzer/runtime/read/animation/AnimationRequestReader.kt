package com.sappyoak.dsanalyzer.runtime.read.animation

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestLayout
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimChannelSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimHandle
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimRequestSnapshot

internal class AnimationRequests(
    /** The pool the manager points at, which has to be the one read beside it */
    val pool: Address,
    val requests: List<AnimRequestSnapshot>,
    val channels: List<AnimChannelSnapshot>
)


internal class AnimationRequestReader(private val layout: AnimRequestLayout) {
    private val manager = StructView(layout.pointer.size)
    private val table = StructView(layout.channelTableFirst + layout.channel.count * Long.SIZE_BYTES)
    private val channel = StructView(layout.channel.run {
        maxOf(ownHandle, animData, currentSlotHandle, fadeWeight)
    } + Long.SIZE_BYTES)

    private val bandOf: Map<Int, Int> = layout.bands
        .flatMapIndexed { band, range -> range.indices.map { it to band + 1 } }
        .toMap()

    fun read(memory: ProcessMemory, at: Address): AnimationRequests? {
        if (!manager.refresh(memory, at)) return null

        val pool = manager.pointer(layout.animData)
        if (pool.isNull) return null

        return AnimationRequests(pool, requests(), channels(memory, pool))
    }

    private fun requests(): List<AnimRequestSnapshot> = (0 until layout.entryCount).mapNotNull { entry ->
        val base = entry * layout.entryStride
        val id = manager.int(base + layout.entry.animationId)
        if (id < 0) return@mapNotNull null

        AnimRequestSnapshot(
            entry = entry,
            animationId = id,
            enabled = manager.boolean(base + layout.entry.enableFlag),
            band = bandOf[entry],
            channel = AnimHandle(manager.int(base + layout.entry.channelHandle))
        )
    }

    private fun channels(memory: ProcessMemory, pool: Address): List<AnimChannelSnapshot> {
        if (!table.refresh(memory, manager.pointer(layout.channelTable))) return emptyList()

        val width = memory.pointerSize.value
        return (0 until layout.channel.count).map { index ->
            val at = table.pointer(layout.channelTableFirst + index * width)
            if (!channel.refresh(memory, at) || channel.pointer(layout.channel.animData) != pool) {
                return emptyList()
            }

            AnimChannelSnapshot(
                index = index,
                handle = AnimHandle(channel.int(layout.channel.ownHandle)),
                slot = AnimHandle(channel.int(layout.channel.currentSlotHandle)),
                fadeWeight = channel.float(layout.channel.fadeWeight)
            )
        }
    }
}
