package com.sappyoak.dsanalyzer.app.runtime

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.milliseconds

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.ptde.FlagChange
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEBuild
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.SampleCost
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace

private val GAME = AttachedGame(
    executableName = "DARKSOULS.exe",
    pid = 10,
    edition = GameEdition.PrepareToDie,
    build = PTDEBuild.Steam,
    unresolved = emptyList()
)

private val FIRELINK = WorldPlace.InWorld(MapId.of(area = 10, block = 2))

private fun snapshot(
    frame: Long,
    vararg changes: FlagChange,
    place: WorldPlace = FIRELINK
) = RuntimeSnapshot(
    inGameTimeMillis = (frame * 1000 / 30).toInt(),
    frame = frame,
    place = place,
    loaded = place is WorldPlace.InWorld,
    reloaded = false,
    player = null,
    world = null,
    flagChanges = changes.toList(),
    cost = SampleCost(1.milliseconds, 10, 10)
)

private fun tick(state: RuntimeState, link: LinkState): RuntimeState =
    reduceRuntime(state, RuntimeMessage.LinkChanged(link)).state

class RuntimeReducerTest : FunSpec({
    test("opening the view watches the link rather than the game") {
        reduceRuntime(RuntimeState(), RuntimeMessage.Opened).effects shouldBe listOf(RuntimeEffect.Observe)
    }

    test("what the link reports becomes the state") {
        val state = tick(RuntimeState(), LinkState(attached = GAME, snapshot = snapshot(12)))

        assertSoftly {
            state.live shouldBe true
            state.attached shouldBe GAME
            state.snapshot?.frame shouldBe 12
        }
    }

    test("flag changes accumulate across ticks, newest first") {
        val first = tick(RuntimeState(), LinkState(attached = GAME, snapshot = snapshot(1, FlagChange(11_010_902, true))))
        val second = tick(first, LinkState(attached = GAME, snapshot = snapshot(2, FlagChange(11_020_000, false))))

        second.flagLog shouldBe listOf(
            FlagEntry(11_020_000, false, 2, FIRELINK),
            FlagEntry(11_010_902, true, 1, FIRELINK)
        )
    }

    test("a tick with nothing changing leaves the log alone") {
        val logged = tick(RuntimeState(), LinkState(attached = GAME, snapshot = snapshot(1, FlagChange(1, true))))
        val quiet = tick(logged, LinkState(attached = GAME, snapshot = snapshot(2)))

        quiet.flagLog shouldBe logged.flagLog
    }

    test("the log belongs to one attachment and starts over with the next") {
        val logged = tick(RuntimeState(), LinkState(attached = GAME, snapshot = snapshot(1, FlagChange(1, true))))
        val restarted = tick(logged, LinkState(attached = GAME.copy(pid = 11), snapshot = snapshot(1)))

        assertSoftly {
            logged.flagLog.size shouldBe 1
            restarted.flagLog.shouldBeEmpty()
        }
    }

    test("losing the game clears what was being read but says why") {
        val logged = tick(RuntimeState(), LinkState(attached = GAME, snapshot = snapshot(1, FlagChange(1, true))))
        val lost = tick(logged, LinkState(note = "Remastered is not read yet"))

        assertSoftly {
            lost.live shouldBe false
            lost.snapshot shouldBe null
            lost.problem shouldBe "Remastered is not read yet"
            lost.flagLog.shouldBeEmpty()
        }
    }

    test("the log is bounded, so a long session does not grow without end") {
        val changes = (1..250).map { FlagChange(11_000_000 + it, true) }
        val state = tick(RuntimeState(), LinkState(attached = GAME, snapshot = snapshot(1, *changes.toTypedArray())))

        assertSoftly {
            state.flagLog.size shouldBe 200
            state.flagLog.first().flagId shouldBe 11_000_001
        }
    }
})