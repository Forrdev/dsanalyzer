package com.sappyoak.dsanalyzer.app.nav

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.game.ref.parseAddress

private val WITH_WEAPONS =
    WorkspaceResolveContext(emptySet(), emptySet(), setOf("EquipParamWeapon"), { false }, { null })

private fun WorkspaceResolveContext.roundTrip(view: View.Data): Resolution =
    resolve(checkNotNull(parseAddress(checkNotNull(view.toDataAddress()).toAddress())))

class ParamAddressTest : FunSpec({
    test("a param and a row address resolve back to their views") {
        assertSoftly {
            View.Data.Param("EquipParamWeapon").toDataAddress()?.toAddress() shouldBe "ds:/param/EquipParamWeapon"
            WITH_WEAPONS.roundTrip(View.Data.ParamSearch) shouldBe Resolution.Resolved(View.Data.ParamSearch)
            WITH_WEAPONS.roundTrip(View.Data.Param("EquipParamWeapon")) shouldBe
                    Resolution.Resolved(View.Data.Param("EquipParamWeapon"))
            WITH_WEAPONS.roundTrip(View.Data.ParamRow("EquipParamWeapon", -1)) shouldBe
                    Resolution.Resolved(View.Data.ParamRow("EquipParamWeapon", -1))
        }
    }

    test("a param the installation lacks is absent, and a malformed row id has no view") {
        assertSoftly {
            WITH_WEAPONS.roundTrip(View.Data.Param("CoolTimeParam")).shouldBeInstanceOf<Resolution.Absent>()
            WITH_WEAPONS.resolve(checkNotNull(parseAddress("ds:/param/EquipParamWeapon/x")))
                .shouldBeInstanceOf<Resolution.NoView>()
        }
    }
})
