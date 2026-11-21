package com.sappyoak.dsanalyzer.runtime.sampling

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.resolvePointers
import com.sappyoak.dsanalyzer.runtime.ptde.ptdeLayout
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterReading
import com.sappyoak.dsanalyzer.runtime.read.character.CharacterSource
import com.sappyoak.dsanalyzer.runtime.read.character.PlayerReading
import com.sappyoak.dsanalyzer.runtime.snapshot.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.ai.AIScope
import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.runtime.read.LoadQueueReader

private val KNIGHT = CharacterSnapshot(
    handle = 0x10030035,
    modelId = 2410,
    npcParamId = 241000,
    position = Vec3.Zero,
    health = 100,
    healthMax = 100,
    stamina = 100,
    characterType = 5,
    teamType = 6,
    targetHandle = null,
    effects = null
)

/** Counts the slow-tier reads, and returns the knight from each */
private class CountingCharacters : CharacterSource {
    var reads = 0
    override val playerStructures: Int = 0
    override fun player(game: GameMemory): PlayerReading = PlayerReading.Empty
    override fun read(game: GameMemory, allEffects: Boolean): CharacterReading {
        reads++
        return CharacterReading(listOf(KNIGHT), emptyList(), null)
    }
}

private fun ticks(world: WorldReads, count: Int): List<WorldReading> {
    val game = FakeGame()
    val memory = GameMemory(game, game.resolvePointers(emptyList(), game.module))
    return List(count) {
        world.read(memory, loaded = true, player = null, SampleOptions(aiScope = AIScope.Off))
    }
}

class WorldReadsTest : FunSpec({
    test("the slow tier is read once every six ticks, and shown on the ticks between") {
        val characters = CountingCharacters()
        val layout = ptdeLayout()
        val readings = ticks(WorldReads(layout, characters, LoadQueueReader(layout.loadQueue)), count = 13)

        characters.reads shouldBe 3
        readings.all { it.characters == listOf(KNIGHT) } shouldBe true
    }
})
