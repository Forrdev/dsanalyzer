package com.sappyoak.dsanalyzer.formats.emevd.emedf

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val SET_FLAG = Opcode(2003, 2)
private val UNCOVERED = Opcode(2003, 99)

private fun arg(name: String, type: ArgType = ArgType.Int) =
    ArgDefinition(name = name, type = type, enumName = null, default = 0.0)

private val DEFINITIONS = Emedf(
    definitions = mapOf(
        SET_FLAG to InstructionDefinition(
            opcode = SET_FLAG,
            name = "SET Event Flag",
            args = listOf(arg("Target Event Flag ID"), arg("Flag State", ArgType.UByte))
        ),
        UNCOVERED to InstructionDefinition(
            opcode = UNCOVERED,
            name = "Something Nobody Named",
            args = listOf(arg("Area Entity ID"))
        )
    ),
    enums = emptyMap()
)

private val ALIASES = listOf(
    InstructionAlias(
        opcode = SET_FLAG,
        name = "SetFlagState",
        summary = "Enable, disable, or toggle a binary flag.",
        args = listOf(ArgAlias("flag", ArgReference.EventFlag), ArgAlias("state", null))
    )
)

class AliasesTest : FunSpec({
    test("an alias adds a readable name and an explanation without losing the original") {
        val definition = checkNotNull(DEFINITIONS.withAliases(ALIASES)[SET_FLAG])

        assertSoftly {
            definition.name shouldBe "SET Event Flag"
            definition.alias shouldBe "SetFlagState"
            definition.label shouldBe "SetFlagState"
            definition.summary shouldBe "Enable, disable, or toggle a binary flag."
        }
    }

    test("argument widths are the definitions' to state, not the aliases'") {
        val before = checkNotNull(DEFINITIONS[SET_FLAG])
        val after = checkNotNull(DEFINITIONS.withAliases(ALIASES)[SET_FLAG])

        assertSoftly {
            after.args.map { it.type } shouldBe before.args.map { it.type }
            after.packedSize shouldBe before.packedSize
        }
    }

    test("a declared kind replaces the one the argument's name implied") {
        val named = checkNotNull(DEFINITIONS[UNCOVERED]).args.single()
        val typed = checkNotNull(DEFINITIONS.withAliases(ALIASES)[SET_FLAG]).args.first()

        assertSoftly {
            named.reference shouldBe ArgReference.Entity
            typed.reference shouldBe ArgReference.EventFlag
        }
    }

    test("the definitions keep naming the arguments, since they name them for people") {
        checkNotNull(DEFINITIONS.withAliases(ALIASES)[SET_FLAG]).args.map { it.name } shouldBe
                listOf("Target Event Flag ID", "Flag State")
    }

    test("an instruction no alias covers is left exactly as it was") {
        val definition = checkNotNull(DEFINITIONS.withAliases(ALIASES)[UNCOVERED])

        assertSoftly {
            definition.alias shouldBe null
            definition.label shouldBe "Something Nobody Named"
            definition.summary shouldBe null
        }
    }

    test("the kinds that name something placed in a map are the ones worth following") {
        assertSoftly {
            ArgReference.Character.placed shouldBe true
            ArgReference.Region.placed shouldBe true
            ArgReference.Collision.placed shouldBe true
            ArgReference.Entity.placed shouldBe true
            ArgReference.EventFlag.placed shouldBe false
            ArgReference.Text.placed shouldBe false
            ArgReference.Item.placed shouldBe false
        }
    }
})