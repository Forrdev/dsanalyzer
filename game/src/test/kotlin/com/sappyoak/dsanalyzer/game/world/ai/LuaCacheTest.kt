package com.sappyoak.dsanalyzer.game.world.ai

import java.nio.file.Files
import kotlin.io.path.writeText
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.InstallationFingerprint
import com.sappyoak.dsanalyzer.game.world.maps.MapId

private val STAMP = LuaCacheStamp(InstallationFingerprint(3, "abc"), revision = 1, indexRevision = 1, names = "def")
private val MAP = checkNotNull(MapId.parse("m10_01_00_00"))
private val SCRIPT = LuaScriptId(LuaBinder.Map(MAP), "225000_battle.lua")
private val SOURCE = LuaSource("x = 1\n", listOf("a jump that is not a break or an if"))
private val CATALOG = mapOf(LuaBinder.Common to listOf("goal_list.lua"), LuaBinder.Map(MAP) to listOf(SCRIPT.name))

private val INDEX = AiIndex(
    goals = sortedMapOf(10 to AiGoal(10, "GOAL_X", "X", listOf("X_Activate"), listOf(SCRIPT.binder))),
    logics = sortedMapOf(20 to AiLogic(20, null, listOf("X_Logic"), listOf(SCRIPT.binder))),
    functions = mapOf("X_Activate" to listOf(SCRIPT))
)

class LuaCacheTest : FunSpec({
    fun directory() = Files.createTempDirectory("lua-cache")

    test("what is saved reads back in the next session") {
        val directory = directory()
        checkNotNull(LuaCache.open(directory, STAMP)).apply {
            saveCatalog(CATALOG)
            saveSource(SCRIPT, SOURCE)
            saveAiIndex(INDEX)
        }

        val reopened = checkNotNull(LuaCache.open(directory, STAMP))
        reopened.catalog() shouldBe CATALOG
        reopened.source(SCRIPT) shouldBe SOURCE
        val index = reopened.aiIndex().shouldNotBeNull()
        index.goals shouldBe INDEX.goals
        index.logics shouldBe INDEX.logics
        index.functions shouldBe INDEX.functions
    }

    test("a different stamp starts the cache over") {
        val directory = directory()
        checkNotNull(LuaCache.open(directory, STAMP)).saveSource(SCRIPT, SOURCE)

        val changed = checkNotNull(LuaCache.open(directory, STAMP.copy(revision = 2)))
        changed.source(SCRIPT).shouldBeNull()
        checkNotNull(LuaCache.open(directory, STAMP)).source(SCRIPT).shouldBeNull()
    }

    test("an unreadable entry is a miss") {
        val directory = directory()
        checkNotNull(LuaCache.open(directory, STAMP)).saveSource(SCRIPT, SOURCE)
        directory.resolve("scripts/${SCRIPT.binder.label}/${SCRIPT.name}.json").writeText("{ not json")

        checkNotNull(LuaCache.open(directory, STAMP)).source(SCRIPT).shouldBeNull()
    }
})
