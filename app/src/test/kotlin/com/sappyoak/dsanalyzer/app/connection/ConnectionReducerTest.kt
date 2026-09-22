package com.sappyoak.dsanalyzer.app.connection

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.GameEdition
import io.kotest.assertions.assertSoftly

private val OFF = ConnectionState()
private val SEARCHING = ConnectionState(ConnectionStatus.Searching)

class ConnectionReducerTest : FunSpec({
    test("connecting starts a watch") {
        val transition = reduceConnection(OFF, ConnectionMessage.ConnectRequested)

        assertSoftly {
            transition.state.status shouldBe ConnectionStatus.Searching
            transition.effects.shouldContainExactly(ConnectionEffect.Watch)
        }
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
            .state shouldBe ConnectionStatus.Connected(10, GameEdition.PrepareToDie)
    }

    test("enabling auto-connect remembers it and starts watching") {
        reduceConnection(OFF, ConnectionMessage.AutoConnectChanged(true)).effects.shouldContainExactly(
            ConnectionEffect.Watch,
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