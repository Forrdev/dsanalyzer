package com.sappyoak.dsanalyzer.runtime.read.ai

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame
import com.sappyoak.dsanalyzer.runtime.ptde.Goal
import com.sappyoak.dsanalyzer.runtime.ptde.GoalType
import com.sappyoak.dsanalyzer.runtime.session.ai.GoalSnapshot

private const val THINK = 0x1000
private const val GOALS = 0x2000
private const val TYPES = 0x6000
private const val NAMES = 0x9000
private const val MAPS = 0xA000
private const val STRIDE = 0x100

/** A goal as the test lays it out: [index] picks its slot, its type and its deque's memory */
private class Planned(
    val index: Int,
    val id: Int,
    val name: String,
    val params: List<Float> = emptyList(),
    val life: Float = -1f,
    val children: List<Planned> = emptyList()
)

private fun goalAt(index: Int) = GOALS + index * Goal.SIZE

/**
 * Lays [goal] and its children out, the deque in two blocks with its first element at [offset],
 * so the children run across the block boundary and back around the map
 */
private fun FakeGame.plan(goal: Planned, offset: Int = 3) {
    val at = goalAt(goal.index)
    pointer(at + Goal.ThinkPointer, THINK)
    pointer(at + Goal.SelfPointer, at)
    float(at + Goal.Life, goal.life)
    goal.params.take(Goal.PARAM_COUNT).forEachIndexed { i, p -> float(at + Goal.Params + i * 4, p) }
    val extra = goal.params.drop(Goal.PARAM_COUNT)
    if (extra.isNotEmpty()) {
        val begin = NAMES + 0x800 + goal.index * 0x40
        extra.forEachIndexed { i, p -> float(begin + i * 4, p) }
        pointer(at + Goal.ExtraParamsBegin, begin)
        pointer(at + Goal.ExtraParamsEnd, begin + extra.size * 4)
    }

    val type = TYPES + goal.index * STRIDE
    pointer(at + Goal.TypePointer, type)
    int(type + GoalType.Id, goal.id)
    int(type + GoalType.NameLength, goal.name.length)
    if (goal.name.length < GoalType.INLINE_CAPACITY) {
        int(type + GoalType.NameCapacity, GoalType.INLINE_CAPACITY - 1)
        write(type + GoalType.Name, *goal.name.map { it.code }.toIntArray())
    } else {
        val text = NAMES + goal.index * 0x80
        int(type + GoalType.NameCapacity, 0x3F)
        pointer(type + GoalType.Name, text)
        write(text, *goal.name.map { it.code }.toIntArray())
    }

    val map = MAPS + goal.index * STRIDE
    val blocks = listOf(map + 0x10, map + 0x40)
    pointer(at + Goal.SubgoalMap, map)
    int(at + Goal.SubgoalMapSize, blocks.size)
    int(at + Goal.SubgoalOffset, offset)
    int(at + Goal.SubgoalCount, goal.children.size)
    blocks.forEachIndexed { i, block -> pointer(map + i * 4, block) }
    goal.children.forEachIndexed { k, child ->
        val index = offset + k
        pointer(blocks[index / Goal.DEQUE_BLOCK % blocks.size] + index % Goal.DEQUE_BLOCK * 4, goalAt(child.index))
        plan(child)
    }
}

private val COMBAT = Planned(
    index = 0, id = 0, name = "TopGoal",
    children = listOf(
        Planned(
            index = 1, id = 241000, name = "WhiteKnight_Sword241000Battle",
            children = listOf(
                Planned(
                    index = 2, id = 2221, name = "ComboAttackTunableSpin", params = listOf(3000f, 0f, -2f, -1f, 25f),
                    children = listOf(Planned(3, 2200, "CommonAttack", listOf(3000f, 0f, -2f, 90f, 1.5f, 25f, 1f, 1f), life = 2.5f))
                ),
                Planned(index = 4, id = 2210, name = "ComboRepeat", params = listOf(3001f)),
                Planned(index = 5, id = 2211, name = "ComboFinal", params = listOf(3003f))
            )
        )
    )
)

private fun FakeGame.read(reader: GoalReader = GoalReader()): GoalSnapshot? =
    reader.read(this, address(goalAt(0)), address(THINK))

class GoalReaderTest : FunSpec({
    test("a battle plan reads from the top goal down, the goal being worked on first and the rest queued") {
        val top = checkNotNull(FakeGame().apply { plan(COMBAT) }.read())
        val battle = checkNotNull(top.active)

        assertSoftly {
            top.name shouldBe "TopGoal"
            top.life shouldBe null
            battle.id shouldBe 241000
            battle.name shouldBe "WhiteKnight_Sword241000Battle"
            battle.active?.name shouldBe "ComboAttackTunableSpin"
            battle.queued.map { it.name to it.params.first() } shouldBe listOf("ComboRepeat" to 3001f, "ComboFinal" to 3003f)
        }
    }

    test("a goal's parameters run past the five inline ones, and a timed goal has its life") {
        val attack = checkNotNull(FakeGame().apply { plan(COMBAT) }.read()).active?.active?.active

        assertSoftly {
            attack?.params shouldBe listOf(3000f, 0f, -2f, 90f, 1.5f, 25f, 1f, 1f)
            attack?.life shouldBe 2.5f
        }
    }

    test("a goal type is read once, and read again after a load") {
        val game = FakeGame().apply { plan(COMBAT) }
        val reader = GoalReader()
        game.read(reader)
        val reads = game.reads

        game.read(reader)
        val cached = game.reads - reads
        reader.forget()
        game.read(reader)

        (game.reads - reads - cached > cached) shouldBe true
    }

    test("a goal slot that has been reused is left out with everything under it") {
        val game = FakeGame().apply {
            plan(COMBAT)
            // What a freed slot looks like once the pool hands it to another goal
            pointer(goalAt(2) + Goal.SelfPointer, goalAt(6))
        }

        game.read()?.active?.subgoals?.map { it.name } shouldBe listOf("ComboRepeat", "ComboFinal")
    }

    test("a tree that is not this AI's is not read at all") {
        val game = FakeGame().apply {
            plan(COMBAT)
            pointer(goalAt(0) + Goal.ThinkPointer, 0x7000)
        }

        game.read() shouldBe null
    }

    test("a goal that has come to name itself as a subgoal still ends") {
        val game = FakeGame().apply {
            plan(Planned(0, 0, "TopGoal"))
            int(goalAt(0) + Goal.SubgoalCount, 1)
            pointer(MAPS + 0x10 + 3 * 4, goalAt(0))
        }

        var depth = 0
        var at = game.read()
        while (at != null) { depth++; at = at.active }
        depth shouldBe 8
    }
})
