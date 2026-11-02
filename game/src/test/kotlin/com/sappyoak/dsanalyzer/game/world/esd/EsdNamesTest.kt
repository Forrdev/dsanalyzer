package com.sappyoak.dsanalyzer.game.world.esd


import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.esd.CommandCall
import com.sappyoak.dsanalyzer.formats.esd.EsdKind
import com.sappyoak.dsanalyzer.formats.esd.Verdict
import com.sappyoak.dsanalyzer.game.GameEdition

class EsdNamesTest : FunSpec({
    test("the bundled names cover both dispatchers, and Remastered renumbers talk functions") {
        val ptdeTalk = loadEsdDefinitions(GameEdition.PrepareToDie, EsdKind.Talk)
        val dsrTalk = loadEsdDefinitions(GameEdition.Remastered, EsdKind.Talk)

        assertSoftly {
            ptdeTalk.function(59)?.name shouldBe "CheckSpecificPersonMenuIsOpen"
            dsrTalk.function(59)?.name shouldBe "CheckCovenantMenuIsOpen"
        }
    }

    test("our Ghidra readings sit on the community names, and say where they disagree") {
        val chr = loadEsdDefinitions(GameEdition.PrepareToDie, EsdKind.Character)

        assertSoftly {
            chr.function(1000)?.name shouldBe "GetHP"
            chr.function(1000)?.reading?.verdict shouldBe Verdict.Agrees
            chr.command(CommandCall(1, 143, emptyList()))?.reading?.verdict shouldBe Verdict.Conflicts
            chr.command(CommandCall(1, 9000, emptyList()))?.args shouldBe listOf("PrintText")
        }
    }

    test("a withdrawn name is kept only as what it was") {
        val unnamed = loadEsdDefinitions(GameEdition.Remastered, EsdKind.Talk).function(0)

        assertSoftly {
            unnamed?.name shouldBe null
            unnamed?.label shouldBe "(GetWhetherEnemiesAreNearby?)"
        }
    }
})
