package com.sappyoak.dsanalyzer.runtime.session

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.ptde.AnimData
import com.sappyoak.dsanalyzer.runtime.ptde.CharData
import com.sappyoak.dsanalyzer.runtime.ptde.CharFlags2
import com.sappyoak.dsanalyzer.runtime.ptde.CharMapData
import com.sappyoak.dsanalyzer.runtime.ptde.CharPosData
import com.sappyoak.dsanalyzer.runtime.ptde.FlagChange
import com.sappyoak.dsanalyzer.runtime.ptde.GameDataMan
import com.sappyoak.dsanalyzer.runtime.ptde.PlayerStats
import com.sappyoak.dsanalyzer.runtime.ptde.WorldState
import com.sappyoak.dsanalyzer.shared.math.Vec3

private const val SAMPLE_FLAG = 11_010_902

class GameSessionTest : FunSpec({
    test("one tick reads every structure the tables name") {
        val fixture = PTDEFixture().apply { populate() }

        val snapshot = fixture.session().sample()

        assertSoftly {
            snapshot.cost.complete shouldBe true
            snapshot.map shouldBe MapId.parse("m10_02_00_00")
            snapshot.inGameTimeMillis shouldBe 66_000
            snapshot.frame shouldBe 1980
            snapshot.reloaded shouldBe false
            snapshot.inWorld shouldBe true
        }
    }

    test("the player arrives from the live structures and the persistent one") {
        val fixture = PTDEFixture().apply { populate() }

        val player = checkNotNull(fixture.session().sample().player)

        assertSoftly {
            player.position shouldBe Vec3(12.5f, -30f, 44f)
            player.angle shouldBe 1.25f
            player.health shouldBe 837
            player.stamina shouldBe 92
            player.playRegion shouldBe 1_002_600
            player.animationSpeed shouldBe 2f
            player.cheats.shouldBeEmpty()
            checkNotNull(player.attributes).healthMax shouldBe 1000
            checkNotNull(player.attributes).soulLevel shouldBe 42
            checkNotNull(player.attributes).covenant shouldBe 3
        }
    }

    test("a debug flag the game is running with is reported") {
        val fixture = PTDEFixture().apply {
            populate()
            game.int(CHARACTER_AT + CharData.Flags2, CharFlags2.NoDead)
            game.int(PLACEMENT_AT + CharMapData.Flags, CharMapData.DisableMapHit)
        }

        checkNotNull(fixture.session().sample().player).cheats shouldBe
                setOf(CheatFlag.NoDead, CheatFlag.MapCollisionDisabled)
    }

    test("standing away from the last grounded position is measured") {
        val fixture = PTDEFixture().apply {
            populate()
            game.float(POSITION_AT + CharPosData.Position + 4, 20f)
        }

        val snapshot = fixture.session().sample()

        assertSoftly {
            snapshot.divergence shouldBe 50f
            snapshot.offStableGround shouldBe true
        }
    }

    test("a map change re-resolves the walks that a load invalidates") {
        val fixture = PTDEFixture().apply { populate() }
        val session = fixture.session()
        session.sample().player?.health shouldBe 837

        fixture.reallocateCharacter()
        fixture.game.int(RELOADED_CHARACTER_AT + CharData.Health, 400)
        fixture.map(world = 13, area = 0)

        val next = session.sample()

        assertSoftly {
            next.reloaded shouldBe true
            next.map shouldBe MapId.parse("m13_00_00_00")
            next.player?.health shouldBe 400
        }
    }

    test("a character that is not allocated leaves the player absent rather than zeroed") {
        val fixture = PTDEFixture().apply { populate() }
        fixture.deallocateCharacter()

        val snapshot = fixture.session().sample()

        assertSoftly {
            snapshot.player shouldBe null
            snapshot.map shouldNotBe null
            snapshot.world shouldNotBe null
            snapshot.inWorld shouldBe false
            snapshot.cost.complete shouldBe false
        }
    }

    test("a flag turning on between ticks is reported once") {
        val fixture = PTDEFixture().apply { populate() }
        val session = fixture.session()

        session.sample().flagChanges.shouldBeEmpty()
        fixture.setFlag(SAMPLE_FLAG, true)

        assertSoftly {
            session.sample().flagChanges.shouldContainExactly(FlagChange(SAMPLE_FLAG, true))
            session.sample().flagChanges.shouldBeEmpty()
            session.isFlagSet(SAMPLE_FLAG) shouldBe true
        }
    }

    test("a flag that moves before a character exists is read but not reported") {
        val fixture = PTDEFixture().apply { populate() }
        fixture.deallocateCharacter()
        val session = fixture.session()

        session.sample()
        fixture.setFlag(SAMPLE_FLAG, true)
        val snapshot = session.sample()

        assertSoftly {
            snapshot.player shouldBe null
            snapshot.flagChanges.shouldBeEmpty()
            session.isFlagSet(SAMPLE_FLAG) shouldBe true
        }
    }

    test("a reload does not report the repopulated flag block as a burst of changes") {
        val fixture = PTDEFixture().apply { populate() }
        val session = fixture.session()
        session.sample()

        fixture.setFlag(SAMPLE_FLAG, true)
        fixture.map(world = 13, area = 0)

        assertSoftly {
            session.sample().flagChanges.shouldBeEmpty()
            session.sample().flagChanges.shouldBeEmpty()
        }
    }
})