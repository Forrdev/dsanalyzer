package com.sappyoak.dsanalyzer.app.settings

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.connection.AutoConnect
import io.kotest.assertions.assertSoftly

private val LOADED = SettingsState(loaded = true)

class SettingsReducerTest : FunSpec({
    test("an edit is applied and saved") {
        val transition = reduceSettings(LOADED, SettingsMessage.Edited(AutoConnect(true)))

        assertSoftly {
            transition.state.settings.connection.autoConnect shouldBe true
            transition.effects.shouldContainExactly(SettingsEffect.Save(transition.state.settings))
        }
    }

    test("an edit that changes nothing is not saved") {
        reduceSettings(LOADED, SettingsMessage.Edited(AutoConnect(false))).effects.shouldBeEmpty()
    }

    test("edits made before loading are applied on top of what loads") {
        val waiting = reduceSettings(SettingsState(), SettingsMessage.Edited(AutoConnect(true))).state

        assertSoftly {
            waiting.settings.connection.autoConnect shouldBe false

            val transition = reduceSettings(waiting, SettingsMessage.Loaded(Settings()))

            transition.state.settings.connection.autoConnect shouldBe true
            transition.state.pending.shouldBeEmpty()
            transition.effects.shouldContainExactly(SettingsEffect.Save(transition.state.settings))
        }
    }

    test("a recovered file is reported") {
        val backup = Path.of("settings.bak")

        reduceSettings(SettingsState(), SettingsMessage.Loaded(Settings(), backup))
            .state.problem shouldBe SettingsProblem.Recovered(backup)
    }

    test("an unreadable file falls back to defaults without overwriting it") {
        val transition = reduceSettings(SettingsState(), SettingsMessage.LoadFailed("locked"))

        assertSoftly {
            transition.state.loaded shouldBe true
            transition.state.settings shouldBe Settings()
            transition.effects.shouldBeEmpty()
        }
    }
})