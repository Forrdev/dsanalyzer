package com.sappyoak.dsanalyzer.runtime.read.animation

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.layout.AnimRequestLayout
import com.sappyoak.dsanalyzer.runtime.layout.AnimationLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimSlotSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationTarget

/** Where one character keeps its animation */
internal data class AnimationSource(val pool: Address, val requests: Address)

internal class AnimationReader(
    private val pool: AnimationLayout,
    private val requests: AnimRequestLayout
) {
    private val pools = AnimationPoolReader(pool)
    private val managers = AnimationRequestReader(requests)
    private val pointer = StructView(Long.SIZE_BYTES)

    /** The player's, through the edition's own pointers, which every edition declares */
    fun player(game: GameMemory): AnimationSource? {
        val source = AnimationSource(game.addressOf(pool.pointer), game.addressOf(requests.pointer))
        return source.takeUnless { it.pool.isNull || it.requests.isNull }
    }

    fun forgetPlayer(game: GameMemory) {
        game.forget(pool.pointer)
        game.forget(requests.pointer)
    }

    /** A character's, from its controller */
    fun locate(memory: ProcessMemory, controller: Address): AnimationSource? {
        if (controller.isNull) return null
        val at = pointer.pointerAt(memory, controller + pool.fromController.toLong())
        val requestsAt = pointer.pointerAt(memory, controller + requests.fromController.toLong())
        return if (at.isNull || requestsAt.isNull) null else AnimationSource(at, requestsAt)
    }

    fun read(memory: ProcessMemory, source: AnimationSource, target: AnimationTarget): AnimationSnapshot? {
        val read = managers.read(memory, source.requests) ?: return null
        // The manager names its pool; one that disagrees means one of the two addresses is stale
        if (read.pool != source.pool) return null
        val slots = pools.read(memory, source.pool) ?: return null

        return AnimationSnapshot(
            target = target,
            playSpeed = slots.playSpeed,
            slots = slots.slots.map { it.joined(read) },
            requests = read.requests,
            channels = read.channels
        )
    }
}

private fun AnimSlotSnapshot.joined(read: AnimationRequests): AnimSlotSnapshot {
    if (handle.isNone) return this
    val channel = read.channels.firstOrNull { it.slot == handle } ?: return this

    val asking = read.requests.filter { it.channel == channel.handle }
    val request = asking.firstOrNull { it.live } ?: asking.firstOrNull()
    return copy(channel = channel.index, animationId = request?.animationId)
}
