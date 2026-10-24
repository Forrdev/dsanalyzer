package com.sappyoak.dsanalyzer.runtime.read.attack

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.AttackHit
import com.sappyoak.dsanalyzer.runtime.ptde.AttackMan
import com.sappyoak.dsanalyzer.runtime.ptde.BulletIns
import com.sappyoak.dsanalyzer.runtime.ptde.BulletMan
import com.sappyoak.dsanalyzer.runtime.ptde.HkpShapeType
import com.sappyoak.dsanalyzer.runtime.ptde.PhysShapePhantom
import com.sappyoak.dsanalyzer.runtime.ptde.havok.HkpCapsuleShape
import com.sappyoak.dsanalyzer.runtime.session.attack.HitShape
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val NODE_LIMIT = 128

internal data class HitKey(
    val instance: Address,
    val id: Int
)

internal data class HitRead(
    val key: HitKey,
    val owner: Int,
    val atkParamId: Int,
    val shape: HitShape
)

internal class AttackReader {
    private val manager = StructView(AttackMan.Pointer)
    private val bullets = StructView(BulletMan.Pointer)
    private val bullet = StructView(BulletIns.Owner + Int.SIZE_BYTES)
    private val hit = StructView(AttackHit.SIZE)
    private val pointer = StructView(Int.SIZE_BYTES)
    private val shape = StructView(HkpCapsuleShape.ByteSize)

    fun read(game: GameMemory, owners: Set<Int>): List<HitRead> {
        if (owners.isEmpty() || !manager.refresh(game)) return emptyList()
        val pool = if (bullets.refresh(game)) bullets.pointer(BulletMan.PoolPointer) else Address.Null
        return walk(game.memory, manager.pointer(AttackMan.HeadPointer), pool, owners)
    }

    fun walk(
        memory: ProcessMemory,
        head: Address,
        pool: Address,
        owners: Set<Int>
    ): List<HitRead> {
        val hits = ArrayList<HitRead>()
        var at = head

        for (step in 0 until NODE_LIMIT) {
            if (at.isNull || !hit.refresh(memory, at)) break

            val owner = attacker(memory, pool, hit.int(AttackHit.Owner))
            val transform = hit.mat4(AttackHit.Transform)
            if (owner in owners && transform.isPlaced) {
                shapeOf(memory, transform)?.let { shape ->
                    hits.add(HitRead(
                        key = HitKey(at, hit.int(AttackHit.Id)),
                        owner = owner,
                        atkParamId = hit.int(AttackHit.AtkParamId),
                        shape = shape
                    ))
                }
            }
            at = hit.pointer(AttackHit.NextPointer)
        }
        return hits
    }

    /** The character behind [owner]. Itself, or the shooter when it is a bullet in [pool] */
    private fun attacker(
        memory: ProcessMemory,
        pool: Address,
        owner: Int
    ): Int {
        if (owner ushr 28 != BulletIns.HANDLE_TAG || pool.isNull) return owner
        val index = owner and BulletIns.INDEX_MASK
        if (index >= BulletMan.COUNT) return owner

        val at = pool + index.toLong() * BulletIns.SIZE
        // the pool slot is the handle's own only while it still holds that handle
        if (!bullet.refresh(memory, at) || bullet.int(BulletIns.Handle) != owner) return owner
        return bullet.int(BulletIns.Owner)
    }

    private fun shapeOf(memory: ProcessMemory, transform: Mat4): HitShape? {
        val phantom = hit.pointer(AttackHit.PhantomPointer)
        if (phantom.isNull) return null
        val at = pointerAt(memory, phantom + PhysShapePhantom.ShapePointer.toLong())
        if (!shape.refresh(memory, at)) return null

        val radius = shape.float(HkpCapsuleShape.radius)
        return when (shape.int(HkpCapsuleShape.type)) {
            HkpShapeType.SPHERE -> HitShape.Sphere(transform.r3.xyz, radius)
            HkpShapeType.CAPSULE -> HitShape.Capsule(
                a = transform.place(shape.vec3(HkpCapsuleShape.vertexA)),
                b = transform.place(shape.vec3(HkpCapsuleShape.vertexB)),
                radius = radius
            )
            else -> null
        }
    }

    private fun pointerAt(memory: ProcessMemory, at: Address): Address =
        if (pointer.refresh(memory, at)) pointer.pointer(0) else Address.Null
}

/**
 * Whether the game has placed the hit yet. A hit can be listed a tick before, with its rotation
 * written and its translation still zero, and some never are (the knight's atk 241010). Either would
 * draw it at the world origin, and stretch its sweep across the map
 */
private val Mat4.isPlaced: Boolean get() = r0.xyz != Vec3.Zero && r3.xyz != Vec3.Zero


/** [local] in the frame of this row-major matrix, whose last row is its translation */
private fun Mat4.place(local: Vec3): Vec3 = Vec3(
    r3.x + local.x * r0.x + local.y * r1.x + local.z * r2.x,
    r3.y + local.x * r0.y + local.y * r1.y + local.z * r2.y,
    r3.z + local.x * r0.z + local.y * r1.z + local.z * r2.z
)
