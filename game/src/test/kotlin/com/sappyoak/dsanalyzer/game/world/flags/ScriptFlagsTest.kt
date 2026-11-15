package com.sappyoak.dsanalyzer.game.world.flags

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Opcode
import com.sappyoak.dsanalyzer.game.world.maps.CharacterModels
import com.sappyoak.dsanalyzer.game.world.params.GameParams
import com.sappyoak.dsanalyzer.game.world.params.ParamNames
import com.sappyoak.dsanalyzer.game.world.params.RowNameTranslations
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

private const val EVENT = 11010900L
private const val KNIGHT = 1010700
private const val FLAG = 11010700

private val CONTEXT = NamingContext(
    GameParams(emptyMap()), ParamNames.None, emptyMap(), emptyMap(), CharacterModels(emptyMap()), RowNameTranslations.None
)

private fun instruction(bank: Int, id: Int, vararg values: Long?) = BoundInstruction(Opcode(bank, id), values.toList())

private fun named(vararg instructions: BoundInstruction, name: String? = null): FlagNames {
    val event = BoundEvent(ScriptId.Common, EVENT, instructions.toList())
    val names = EventNames(listOfNotNull(name?.let { EVENT to it }).toMap())
    return FlagNames.of(CONTEXT.scriptFlags(listOf(event)) { names })
}

class ScriptFlagsTest : FunSpec({
    test("a flag turned on after a character dies is named for the death") {
        val names = named(instruction(4, 0, 0, KNIGHT.toLong(), 1), instruction(2003, 2, FLAG.toLong(), 1))

        assertSoftly {
            names[FLAG]!!.kind shouldBe FlagKind.CharacterDead
            names[FLAG]!!.label shouldBe "Killed entity $KNIGHT"
        }
    }

    test("a flag turned off, or a death the event waits to undo, names nothing") {
        val names = named(instruction(4, 0, 0, KNIGHT.toLong(), 0), instruction(2003, 2, FLAG.toLong(), 0))

        names.size shouldBe 0
    }

    test("the event's own name reads the flags it sets and its own id") {
        val names = named(instruction(2003, 2, FLAG.toLong(), 1), name = "Silver Knight dead")

        assertSoftly {
            names[FLAG]!!.label shouldBe "Silver Knight dead"
            names[EVENT.toInt()]!!.kind shouldBe FlagKind.EventState
        }
    }

    test("a bonfire registration names its flag directly, ahead of the event's name") {
        val names = named(instruction(2009, 3, FLAG.toLong(), KNIGHT.toLong()), name = "Bonfire")

        assertSoftly {
            names[FLAG]!!.kind shouldBe FlagKind.Bonfire
            names[FLAG]!!.best.direct shouldBe true
        }
    }

    test("an argument the caller could not supply leaves its flag unnamed") {
        named(instruction(2003, 2, null, 1)).size shouldBe 0
    }
})
