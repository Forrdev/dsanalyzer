package com.sappyoak.dsanalyzer.app.nav

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import com.sappyoak.dsanalyzer.game.ref.parseAddress
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.maps.MapId

private val BURG = LuaScriptId(LuaBinder.Map(checkNotNull(MapId.parse("m10_01_00_00"))), "006540_battle.lua")

private val WITH_BURG =
    WorkspaceResolveContext(emptySet(), emptySet(), emptySet(), { false }, { it == BURG }, emptySet(), { null })

private fun roundTrip(view: View.Data): Resolution =
    WITH_BURG.resolve(checkNotNull(parseAddress(checkNotNull(view.toDataAddress()).toAddress())))

class AiAddressTest : FunSpec({
    test("a script and a function in it are addressed under the lua space") {
        View.Data.LuaScript(BURG, "SunKnight6540Battle_Update").toDataAddress()?.toAddress() shouldBe
                "ds:/lua/m10_01_00_00/006540_battle.lua#SunKnight6540Battle_Update"
        roundTrip(View.Data.LuaScript(BURG, "SunKnight6540Battle_Update")) shouldBe
                Resolution.Resolved(View.Data.LuaScript(BURG, "SunKnight6540Battle_Update"))
        roundTrip(View.Data.LuaFiles(LuaBinder.Common)) shouldBe
                Resolution.Resolved(View.Data.LuaFiles(LuaBinder.Common))
    }

    test("a script the installation lacks is absent") {
        roundTrip(View.Data.LuaScript(LuaScriptId(LuaBinder.Event, "nope.lua"))).shouldBeInstanceOf<Resolution.Absent>()
    }

    test("goals and logic share the goal space") {
        View.Data.AiLogic(6540).toDataAddress()?.toAddress() shouldBe "ds:/goal/logic/6540"
        roundTrip(View.Data.AiGoal(6540)) shouldBe Resolution.Resolved(View.Data.AiGoal(6540))
        roundTrip(View.Data.AiLogic(0)) shouldBe Resolution.Resolved(View.Data.AiLogic(0))
    }
})
