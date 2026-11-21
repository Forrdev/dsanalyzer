package com.sappyoak.dsanalyzer.app.connection

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId

private fun installation(root: String) = Installation(
    id = InstallationId(root),
    root = Path.of(root),
    executable = Path.of(root, "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

private val PTDE = installation("ptde")
private val OTHER = installation("other")

private val OFF = ConnectionState(target = PTDE)
private val SEARCHING = ConnectionState(ConnectionStatus.Searching, PTDE)

class ConnectionReducerTest : FunSpec({
    test("connecting starts a watch for the workspace's installation") {
        val transition = reduceConnection(OFF, ConnectionMessage.ConnectRequested)

        assertSoftly {
            transition.state.status shouldBe ConnectionStatus.Searching
            transition.effects.shouldContainExactly(ConnectionEffect.Watch(PTDE))
        }
    }

    test("connecting with no workspace open waits for one") {
        val transition = reduceConnection(ConnectionState(), ConnectionMessage.ConnectRequested)

        assertSoftly {
            transition.state.status shouldBe ConnectionStatus.NoWorkspace
            transition.effects.shouldBeEmpty()
        }
    }

    test("a workspace opened while waiting starts its watch") {
        val waiting = ConnectionState(ConnectionStatus.NoWorkspace)
        reduceConnection(waiting, ConnectionMessage.TargetChanged(PTDE)).effects
            .shouldContainExactly(ConnectionEffect.Watch(PTDE))
    }

    test("another installation while watching moves the watch to it") {
        val transition = reduceConnection(SEARCHING, ConnectionMessage.TargetChanged(OTHER))

        assertSoftly {
            transition.state shouldBe ConnectionState(ConnectionStatus.Searching, OTHER)
            transition.effects.shouldContainExactly(ConnectionEffect.Watch(OTHER))
        }
    }

    test("closing the workspace while watching stops the watch") {
        val transition = reduceConnection(SEARCHING, ConnectionMessage.TargetChanged(null))

        assertSoftly {
            transition.state.status shouldBe ConnectionStatus.NoWorkspace
            transition.effects.shouldContainExactly(ConnectionEffect.StopWatching)
        }
    }

    test("a workspace opened while disconnected is only remembered") {
        reduceConnection(ConnectionState(), ConnectionMessage.TargetChanged(PTDE)).effects.shouldBeEmpty()
    }

    test("connecting while already watching does not start a second watch") {
        reduceConnection(SEARCHING, ConnectionMessage.ConnectRequested).effects.shouldBeEmpty()
    }

    test("disconnecting stops the watch") {
        val transition = reduceConnection(SEARCHING, ConnectionMessage.DisconnectRequested)

        assertSoftly {
            transition.state.status shouldBe ConnectionStatus.Off
            transition.effects.shouldContainExactly(ConnectionEffect.StopWatching)
        }
    }

    test("a report queued behind a disconnect is ignored") {
        reduceConnection(OFF, ConnectionMessage.GameConnected(10, GameEdition.PrepareToDie)).state shouldBe OFF
    }

    test("a report while watching becomes the status") {
        reduceConnection(SEARCHING, ConnectionMessage.GameConnected(10, GameEdition.PrepareToDie))
            .state.status shouldBe ConnectionStatus.Connected(10, GameEdition.PrepareToDie)
    }

    test("enabling auto-connect remembers it and starts watching") {
        reduceConnection(OFF, ConnectionMessage.AutoConnectChanged(true)).effects.shouldContainExactly(
            ConnectionEffect.Watch(PTDE),
            ConnectionEffect.EditSettings(AutoConnect(true))
        )
    }

    test("disabling auto-connect remembers it and leaves the connection alone") {
        val transition = reduceConnection(SEARCHING, ConnectionMessage.AutoConnectChanged(false))

        assertSoftly {
            transition.state shouldBe SEARCHING
            transition.effects.shouldContainExactly(ConnectionEffect.EditSettings(AutoConnect(false)))
        }
    }

    test("A failed watch leaves connecting unavailable") {
        reduceConnection(SEARCHING, ConnectionMessage.WatchFailed("not supported"))
            .state.status shouldBe ConnectionStatus.Unavailable("not supported")
    }
})
