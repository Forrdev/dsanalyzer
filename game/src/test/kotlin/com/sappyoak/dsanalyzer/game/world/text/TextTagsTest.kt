package com.sappyoak.dsanalyzer.game.world.text

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.fmg.Fmg

private val TAGS = TextTags(goods = { if (it == 2100) "Master Key" else null }, system = Fmg(mapOf(3 to "YES")))

class TextTagsTest : FunSpec({
    test("a goods tag reads the goods name and a system tag the system message") {
        TAGS.resolve("Used <?gdsparam@2100?>: <?sysmsg@3?>") shouldBe "Used Master Key: YES"
    }

    test("a tag the game fills at run time, or one naming nothing, becomes a bracketed stand-in") {
        TAGS.resolve("<?hostName?> paid <?evntAcquittalPrice?> for <?gdsparam@9?>") shouldBe
                "[host name] paid [evnt acquittal price] for [gdsparam]"
    }
})
