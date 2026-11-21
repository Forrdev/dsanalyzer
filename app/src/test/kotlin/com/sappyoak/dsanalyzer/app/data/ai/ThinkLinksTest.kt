package com.sappyoak.dsanalyzer.app.data.ai

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.toDataAddress
import com.sappyoak.dsanalyzer.formats.param.ParamRowData
import com.sappyoak.dsanalyzer.formats.param.RowBytes
import com.sappyoak.dsanalyzer.formats.param.defs.NpcThinkParamRow
import com.sappyoak.dsanalyzer.formats.param.defs.TalkParamRow

private fun think(logic: Int, battle: Int, caution: Int, find: Int): NpcThinkParamRow {
    val bytes = ByteBuffer.allocate(NpcThinkParamRow.rowSize).order(ByteOrder.LITTLE_ENDIAN)
    bytes.putInt(0x0, logic).putInt(0x4, battle).putInt(0x1C, caution).putInt(0x24, find)
    return NpcThinkParamRow(ParamRowData(654000, "SunKnight", RowBytes(bytes.array())))
}

private fun address(view: View.Data) = checkNotNull(view.toDataAddress())

class ThinkLinksTest : FunSpec({
    test("a think row links its logic and every goal it names") {
        aiLinksOf(think(logic = 6540, battle = 6540, caution = 6541, find = 6542)) shouldContainExactly mapOf(
            "logicId" to address(View.Data.AiLogic(6540)),
            "battleGoalID" to address(View.Data.AiGoal(6540)),
            "goalID_ToCaution" to address(View.Data.AiGoal(6541)),
            "goalID_ToFind" to address(View.Data.AiGoal(6542))
        )
    }

    test("logic 0 is the default logic, but a goal of -1 or 0 is none") {
        aiLinksOf(think(logic = 0, battle = -1, caution = 0, find = -1)) shouldContainExactly
                mapOf("logicId" to address(View.Data.AiLogic(0)))
    }

    test("other params have no AI links") {
        aiLinksOf(TalkParamRow(ParamRowData(1, "", RowBytes(ByteArray(TalkParamRow.rowSize))))) shouldBe emptyMap()
    }
})
