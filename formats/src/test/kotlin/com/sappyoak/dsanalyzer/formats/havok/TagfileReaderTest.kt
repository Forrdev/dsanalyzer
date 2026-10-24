package com.sappyoak.dsanalyzer.formats.havok

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.formats.havok.physics.StaticShape
import com.sappyoak.dsanalyzer.formats.havok.physics.rigidBodies
import com.sappyoak.dsanalyzer.formats.havok.tagfile.readTagfile
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.math.Vec4

private val TAGGED = readHavokFile(taggedCollision())

class TagfileReaderTest : FunSpec({
    test("the SDK version, the root, and every object item come out, in data order") {
        assertSoftly {
            TAGGED.version shouldBe "20150100"
            TAGGED.root shouldBe HavokObject(0, "hkRootLevelContainer")
            TAGGED.objects.map { it.className } shouldBe
                    listOf("hkRootLevelContainer", "hkpPhysicsData", "hkpPhysicsSystem", "hkpRigidBody", "hkpConvexVerticesShape")
        }
    }

    test("a slot points at its item, and a word no slot names is no pointer, whatever it holds") {
        assertSoftly {
            TAGGED.data.pointer(0x20) shouldBe 0x50
            TAGGED.data.string(NAMED_VARIANTS_AT) shouldBe "Physics Data"
            TAGGED.data.pointer(DECOY_AT) shouldBe null
        }
    }

    test("an array's count is its item's, not the data's size word") {
        TAGGED.data.array(0x0) shouldBe HkArray(NAMED_VARIANTS_AT, 1)
    }

    test("member offsets follow nested members and the parent chain, sizes the parent chain too") {
        val types = checkNotNull(TAGGED.types)
        assertSoftly {
            types.offsetOf("hkpRigidBody", "name") shouldBe 0x30
            types.offsetOf("hkpRigidBody", "collidable.broadPhaseHandle.collisionFilterInfo") shouldBe 0x20
            types.offsetOf("hkpRigidBody", "motion.motionState.transform") shouldBe 0x50
            types.sizeOf("hkMatrix3f") shouldBe 0x30
            types.offsetOf("hkpRigidBody", "nothing") shouldBe null
        }
    }

    test("bodies read through the file's own layout, not PTDE's") {
        val body = TAGGED.rigidBodies().single()
        val hull = body.shape.shouldBeInstanceOf<StaticShape.Convex>()
        assertSoftly {
            body.name shouldBe TAGGED_BODY
            body.collisionFilterInfo shouldBe TAGGED_FILTER
            body.transform.r3 shouldBe Vec4(5f, 6f, 7f, 1f)
            hull.vertices.toList() shouldBe listOf(0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 1f, 1f, 1f)
            hull.planes.size shouldBe 2
            hull.radius shouldBe TAGGED_RADIUS
        }
    }

    test("a file with only an empty root holds no bodies, and needs no PTCH") {
        readHavokFile(emptyTagfile()).rigidBodies() shouldBe emptyList()
    }

    test("another tagfile SDK is refused") {
        shouldThrow<BinaryFormatException> { readTagfile(taggedCollision(sdk = "20180100")) }
    }
})
