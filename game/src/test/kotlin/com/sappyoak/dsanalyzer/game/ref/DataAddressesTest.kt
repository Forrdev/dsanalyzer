package com.sappyoak.dsanalyzer.game.ref

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.world.maps.EntryKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

private val UNDEAD_BURG = MapId.of(10, 1)

class DataAddressesTest : FunSpec({
    test("each kind of thing has one spelling, and it parses back to the same address") {
        val addresses = mapOf(
            DataAddress.map(UNDEAD_BURG) to "ds:/map/m10_01_00_00",
            DataAddress.mapEntry(UNDEAD_BURG, EntryKind.Part, 12) to "ds:/map/m10_01_00_00/part/12",
            DataAddress.entity(1010700) to "ds:/entity/1010700",
            DataAddress.flag(11010100, UNDEAD_BURG) to "ds:/flag/11010100@m10_01_00_00",
            DataAddress.scriptEvent(ScriptId.Common, 50) to "ds:/script/common/event/50",
            DataAddress.paramRow("EquipParamWeapon", 100000) to "ds:/param/EquipParamWeapon/100000"
        )

        assertSoftly {
            addresses.forEach { (address, text) ->
                address.toAddress() shouldBe text
                parseAddress(text) shouldBe address
            }
        }
    }

    test("tokens read back to what wrote them") {
        assertSoftly {
            EntryKind.entries.forEach { entryKindOf(it.token) shouldBe it }
            listOf(ScriptId.Common, ScriptId.Of(UNDEAD_BURG)).forEach { scriptIdOf(it.token) shouldBe it }
        }
    }
})
