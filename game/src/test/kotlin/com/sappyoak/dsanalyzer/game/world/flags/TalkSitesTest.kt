package com.sappyoak.dsanalyzer.game.world.flags

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import java.nio.ByteBuffer
import java.nio.ByteOrder

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.formats.esd.Bytecode
import com.sappyoak.dsanalyzer.formats.esd.CommandCall
import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.esd.EsdCondition
import com.sappyoak.dsanalyzer.formats.esd.EsdState
import com.sappyoak.dsanalyzer.formats.esd.StateGroup
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.CharacterModels
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.params.GameParams
import com.sappyoak.dsanalyzer.game.world.params.ParamNames
import com.sappyoak.dsanalyzer.game.world.params.RowNameTranslations

private const val END = 0xA1
private const val BIAS = 64
private const val PUSH_INT = 0x82
private const val CALL_ONE_ARG = 0x85
private const val GET_EVENT_FLAG = 15
private const val MENU_FOLLOWS_FLAG = 49

private const val TALK = 1000
private const val SET = 11010700
private const val CLEARED = 11010701
private const val CHECKED = 11010702
private const val MENU = 11010703

private val MAP = MapId.of(10, 1)
private val CONTEXT = NamingContext(
    GameParams(emptyMap()), ParamNames.None, emptyMap(), emptyMap(), CharacterModels(emptyMap()),
    RowNameTranslations.None
)

private fun int(value: Int): Bytecode = Bytecode(
    ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN).put(PUSH_INT.toByte()).putInt(value).put(END.toByte()).array()
)

/** `GetEventFlag(CHECKED)` as a condition evaluates it */
private val READS_FLAG = Bytecode(
    ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN)
        .put((GET_EVENT_FLAG + BIAS).toByte()).put(PUSH_INT.toByte()).putInt(CHECKED)
        .put(CALL_ONE_ARG.toByte()).put(END.toByte()).array()
)

private fun talk(command: Int, vararg args: Bytecode) = CommandCall(TALK_BANK, command, args.toList())

private val STATE = EsdState(
    id = 3,
    conditions = listOf(EsdCondition(4, READS_FLAG, emptyList(), emptyList())),
    entryCommands = listOf(
        talk(SET_TALK_FLAG, int(SET), int(1)),
        talk(SET_TALK_FLAG, int(CLEARED), int(0)),
        talk(MENU_FOLLOWS_FLAG, int(5), int(MENU))
    ),
    whileCommands = emptyList(),
    exitCommands = emptyList()
)

private val SCRIPT = TalkScript(MAP, TALK, Esd(null, mapOf(1L to StateGroup(1, mapOf(3L to STATE)))))

class TalkSitesTest : FunSpec({
    test("a talk state's sets, clears, reads and menu gates are each a site, citing the state") {
        val source = FlagSource.Talk(EsdSource.Talk(MAP, TALK), 1, 3)

        CONTEXT.talkSites(listOf(SCRIPT)) shouldContainExactlyInAnyOrder listOf(
            SET to FlagSite(FlagAccess.Writes, "talk $TALK: turns it on", source),
            CLEARED to FlagSite(FlagAccess.Writes, "talk $TALK: turns it off", source),
            MENU to FlagSite(FlagAccess.Reads, "talk $TALK: shows a menu option by it", source),
            CHECKED to FlagSite(FlagAccess.Reads, "talk $TALK: checks it", source)
        )
    }
})
