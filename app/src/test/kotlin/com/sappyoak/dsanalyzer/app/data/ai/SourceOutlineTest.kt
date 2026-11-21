package com.sappyoak.dsanalyzer.app.data.ai

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

private val OUTLINE = SourceOutline.of(
    """
    |REGISTER_GOAL(GOAL_X, "X")
    |
    |function X_Activate(ai, goal)
    |    local function inner()
    |    end
    |end
    |
    |local function helper(n)
    |end
    |
    |function Goal:Update(ai)
    |end
    |""".trimMargin()
)

class SourceOutlineTest : FunSpec({
    test("top-level functions are listed with their lines; nested ones are not") {
        OUTLINE.functions.map { it.name } shouldContainExactly listOf("X_Activate", "helper", "Goal:Update")
        OUTLINE.lineOf("X_Activate") shouldBe 2
    }

    test("a method is found by its dotted name too") {
        OUTLINE.lineOf("Goal.Update") shouldBe 10
        OUTLINE.lineOf("Missing").shouldBeNull()
    }
})
