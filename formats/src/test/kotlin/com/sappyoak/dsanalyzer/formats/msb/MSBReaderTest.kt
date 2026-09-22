package com.sappyoak.dsanalyzer.formats.msb

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.formats.msb.model.ModelType
import com.sappyoak.dsanalyzer.formats.msb.part.CollisionHitFilter
import com.sappyoak.dsanalyzer.formats.msb.part.Part
import com.sappyoak.dsanalyzer.formats.msb.part.hitFilter
import com.sappyoak.dsanalyzer.formats.msb.part.placeNameId
import com.sappyoak.dsanalyzer.formats.msb.part.playRegionId
import com.sappyoak.dsanalyzer.formats.msb.part.stableFootingFlag
import com.sappyoak.dsanalyzer.formats.msb.region.Shape
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader
import com.sappyoak.dsanalyzer.shared.math.Vec3

private fun collision(name: String, hitFilter: Int, placeName: Int, playRegion: Int) =
    part(name, typeCode = 5, modelIndex = 0, drawGroups = intArrayOf(0b101, 0, 0, 0)) {
        byte(hitFilter).byte(0).short(-1).float(0f)
        int(1).zeros(3)
        int(-1).int(-1).int(-1)
        short(placeName).byte(0).byte(0).int(-1)
        int(-1).int(-1).int(-1)
        int(playRegion).short(-1).short(-1).zeros(4)
    }

private val ENEMY = part("c1000_0000", typeCode = 2, modelIndex = 1) {
    zeros(2).int(100).int(200).int(-1).byte(0).byte(0).short(0).int(-1)
    int(0).zeros(2)
    short(1); repeat(7) { short(-1) }
    int(-1).int(-1)
}

private val CONNECT = part("h0001B0_0000", typeCode = 11, modelIndex = 0) {
    int(1).bytes(byteArrayOf(10, 2, 0, 0)).zeros(2)
}

private fun layout(
    params: List<String> = listOf("MODEL_PARAM_ST", "EVENT_PARAM_ST", "POINT_PARAM_ST", "PARTS_PARAM_ST"),
    parts: List<ByteArray> = listOf(
        collision("h0000B0_0000", hitFilter = 8, placeName = -1, playRegion = 12),
        collision("h0001B0_0000", hitFilter = 10, placeName = -5, playRegion = -1010),
        ENEMY,
        CONNECT
    )
): ByteArray = msbFile(
    params[0] to listOf(model("h0000B0", 5), model("c1000", 2)),
    params[1] to emptyList(),
    params[2] to listOf(region("start", 0, floatArrayOf(), 1000), region("zone", 5, floatArrayOf(4f, 5f, 6f), -1)),
    params[3] to parts
)

private fun read(bytes: ByteArray) = readMSB(BinaryReader.of(bytes))

class MSBReaderTest : FunSpec({
    test("models and regions read with their names, types and shapes") {
        val msb = read(layout())
        assertSoftly {
            msb.models.map { it.name to it.type } shouldBe listOf("h0000B0" to ModelType.Collision, "c1000" to ModelType.Enemy)
            msb.regions[0].shape shouldBe Shape.Point
            msb.regions[0].entityId shouldBe 1000
            msb.regions[1].shape shouldBe Shape.Box(4f, 5f, 6f)
            msb.regions[1].position shouldBe Vec3(1f, 2f, 3f)
            msb.regions[1].entityId shouldBe null
        }
    }

    test("collisions keep raw fields and decode the encoded ones") {
        val (plain, encoded) = read(layout()).collisions
        assertSoftly {
            plain.header.name shouldBe "h0000B0_0000"
            plain.hitFilter shouldBe CollisionHitFilter.Normal
            plain.placeNameId shouldBe null
            plain.playRegionId shouldBe 12
            plain.stableFootingFlag shouldBe null
            (0 in plain.header.drawGroups) shouldBe true
            (1 in plain.header.drawGroups) shouldBe false

            encoded.hitFilter shouldBe null
            encoded.placeNameId shouldBe 5
            encoded.playRegionId shouldBe null
            encoded.stableFootingFlag shouldBe 1000
        }
    }

    test("references resolve against the list they count through") {
        val msb = read(layout())
        val enemy = msb.parts[2].shouldBeInstanceOf<Part.Enemy>()
        val connect = msb.parts[3].shouldBeInstanceOf<Part.ConnectCollision>()
        assertSoftly {
            msb[enemy.header.model!!].name shouldBe "c1000"
            msb[enemy.data.drawParent!!].header.name shouldBe "h0000B0_0000"
            enemy.data.movePoints.first()?.let { msb[it].name } shouldBe "zone"
            enemy.data.movePoints.drop(1).all { it == null } shouldBe true
            msb[connect.collision!!].header.name shouldBe "h0001B0_0000"
            connect.connectedMap shouldBe listOf(10, 2, 0, 0)
        }
    }

    test("a param list out of order is rejected") {
        shouldThrow<BinaryFormatException> {
            read(layout(params = listOf("MODEL_PARAM_ST", "POINT_PARAM_ST", "EVENT_PARAM_ST", "PARTS_PARAM_ST")))
        }
    }

    test("a part type the format does not define is rejected") {
        shouldThrow<BinaryFormatException> {
            read(layout(parts = listOf(part("x", typeCode = 3, modelIndex = 0) { zeros(4) })))
        }
    }

    test("group masks overlap when any group is shared") {
        val a = GroupMask(listOf(0b0110u, 0u, 0u, 0u))
        assertSoftly {
            a.overlaps(GroupMask(listOf(0b0100u, 0u, 0u, 0u))) shouldBe true
            a.overlaps(GroupMask(listOf(0b1001u, 0u, 0u, 1u))) shouldBe false
            (127 in GroupMask(listOf(0u, 0u, 0u, 1u shl 31))) shouldBe true
        }
    }
})