package com.sappyoak.dsanalyzer.game.world.flags

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess

class EngineFlagsTest : FunSpec({
    val sites = EngineOrigin.read(EMPTY_CONTEXT).sites

    test("the bundled list reads, each entry once per flag it names") {
        sites.map { it.first }.toSet() shouldContainAll (8140..8146).toList() + listOf(4047, 5001, 11505100)
        sites.count { it.first == 8143 } shouldBe 3
    }

    test("a site keeps its access and its place in the executable") {
        sites.map { it.second } shouldContain FlagSite(
            FlagAccess.Writes,
            "set when returning to the title screen (ReturnTitle_wait)",
            FlagSource.Engine("FUN_00D76630", 0x00D76630)
        )
    }
})
