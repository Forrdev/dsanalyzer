package com.sappyoak.dsanalyzer.runtime.read.attack

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.ptde.AttackHit
import com.sappyoak.dsanalyzer.runtime.ptde.BulletIns
import com.sappyoak.dsanalyzer.runtime.ptde.HkpShapeType
import com.sappyoak.dsanalyzer.runtime.ptde.PhysShapePhantom
import com.sappyoak.dsanalyzer.runtime.ptde.havok.HkpCapsuleShape
import com.sappyoak.dsanalyzer.runtime.session.attack.HitShape
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val HITS = 0x1000
private const val HIT_STRIDE = 0x200
private const val PHANTOMS = 0x3000
private const val SHAPES = 0x4000
private const val STRIDE = 0x100
private const val POOL = 0x8000

private const val KNIGHT = 0x10030035
private const val ALLY = 0x10030030

/** An arrow in pool slot 2, as the game numbers it */
private const val ARROW = 0x30880002

/** One hit's shape: a capsule from the origin to [tip], or a sphere when [tip] is null */
private data class Hit(val owner: Int, val radius: Float, val tip: Vec3?, val placed: Boolean = true)

/** The live list, built from [hits] in order, each placed by [placeHit] */
private fun hits(vararg hits: Hit): FakeGame = FakeGame().apply {
    hits.forEachIndexed { i, hit ->
        val at = HITS + i * HIT_STRIDE
        val phantom = PHANTOMS + i * STRIDE
        val shape = SHAPES + i * STRIDE

        int(at + AttackHit.Id, i)
        int(at + AttackHit.Owner, hit.owner)
        int(at + AttackHit.AtkParamId, 241000 + i)
        if (hit.placed) placeHit(at + AttackHit.Transform)
        if (i + 1 < hits.size) pointer(at + AttackHit.NextPointer, at + HIT_STRIDE) else nullPointer(at + AttackHit.NextPointer)

        pointer(at + AttackHit.PhantomPointer, phantom)
        pointer(phantom + PhysShapePhantom.ShapePointer, shape)
        int(shape + HkpCapsuleShape.type, if (hit.tip == null) HkpShapeType.SPHERE else HkpShapeType.CAPSULE)
        float(shape + HkpCapsuleShape.radius, hit.radius)
        hit.tip?.let { tip ->
            float(shape + HkpCapsuleShape.vertexB, tip.x)
            float(shape + HkpCapsuleShape.vertexB + 4, tip.y)
            float(shape + HkpCapsuleShape.vertexB + 8, tip.z)
        }
    }
}

/** A quarter turn about up, standing at (10, 2, 5): local x runs along world -z, local z along world x */
private fun FakeGame.placeHit(at: Int, translated: Boolean = true) {
    val rows = listOf(0f, 0f, -1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 10f, 2f, 5f, 1f)
    rows.take(if (translated) rows.size else 12).forEachIndexed { i, value -> float(at + i * 4, value) }
}

/** The pool slot [handle] names, holding [handle] and flying for [shooter] */
private fun FakeGame.bullet(handle: Int, shooter: Int) {
    val at = POOL + (handle and BulletIns.INDEX_MASK) * BulletIns.SIZE
    int(at + BulletIns.Handle, handle)
    int(at + BulletIns.Owner, shooter)
}

private fun FakeGame.walk(vararg owners: Int): List<HitRead> =
    AttackReader().walk(this, address(HITS), address(POOL), owners.toSet())

class AttackReaderTest : FunSpec({
    test("a capsule's ends are placed in the world through the hit's matrix") {
        val read = hits(Hit(KNIGHT, 0.4f, tip = Vec3(1f, 1f, 0f))).walk(KNIGHT).single()

        assertSoftly {
            read.shape shouldBe HitShape.Capsule(a = Vec3(10f, 2f, 5f), b = Vec3(10f, 3f, 4f), radius = 0.4f)
            read.owner shouldBe KNIGHT
            read.atkParamId shouldBe 241000
        }
    }

    test("a sphere sits at the matrix's translation") {
        val read = hits(Hit(KNIGHT, 0.5f, tip = null)).walk(KNIGHT).single()

        read.shape shouldBe HitShape.Sphere(Vec3(10f, 2f, 5f), 0.5f)
    }

    test("only the hits of tracked attackers are kept, and a hit not yet placed is left out") {
        val game = hits(
            Hit(ALLY, 0.4f, tip = Vec3(0f, 1f, 0f)),
            Hit(KNIGHT, 0.4f, tip = Vec3(0f, 1f, 0f), placed = false),
            Hit(KNIGHT, 0.2f, tip = Vec3(0f, 1f, 0f))
        )

        assertSoftly {
            game.walk(KNIGHT).map { it.key.id } shouldBe listOf(2)
            game.walk(KNIGHT, ALLY).map { it.key.id } shouldBe listOf(0, 2)
            game.walk() shouldBe emptyList()
        }
    }

    test("a bullet's hit is the shooter's, found through the bullet the hit names") {
        val game = hits(Hit(ARROW, 0.07f, tip = null)).apply { bullet(ARROW, shooter = KNIGHT) }

        assertSoftly {
            game.walk(KNIGHT).single().owner shouldBe KNIGHT
            game.walk(ALLY) shouldBe emptyList()
        }
    }

    test("a pool slot that has moved on to another bullet names no shooter") {
        val game = hits(Hit(ARROW, 0.07f, tip = null)).apply { bullet(ARROW + 0x10000, shooter = KNIGHT) }

        game.walk(KNIGHT) shouldBe emptyList()
    }

    test("a hit rotated but not yet moved into place is left out, as it would sit at the world origin") {
        val game = hits(Hit(KNIGHT, 0.4f, tip = Vec3(0f, 1f, 0f), placed = false)).apply {
            placeHit(HITS + AttackHit.Transform, translated = false)
        }

        game.walk(KNIGHT) shouldBe emptyList()
    }

    test("a hit whose shape is neither a sphere nor a capsule is skipped, and the walk goes on") {
        val game = hits(Hit(KNIGHT, 0.4f, tip = Vec3(0f, 1f, 0f)), Hit(KNIGHT, 0.5f, tip = null)).apply {
            int(SHAPES + HkpCapsuleShape.type, 0)
        }

        game.walk(KNIGHT).map { it.key.id } shouldBe listOf(1)
    }

    test("a list that has come apart into a loop still ends") {
        val game = hits(Hit(KNIGHT, 0.4f, tip = null)).apply { pointer(HITS + AttackHit.NextPointer, HITS) }

        game.walk(KNIGHT) shouldHaveSize 128
    }
})
