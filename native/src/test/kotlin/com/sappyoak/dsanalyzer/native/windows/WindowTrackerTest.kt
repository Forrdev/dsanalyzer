package com.sappyoak.dsanalyzer.native.windows

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

private val WINDOWED = ScreenBounds(100, 100, 1280, 720)
private val MOVED = ScreenBounds(200, 100, 1280, 720)
private val RESIZED = ScreenBounds(100, 100, 1920, 1080)

private fun overlayable(bounds: ScreenBounds) = Viewport(bounds, DisplayState.Overlayable)

/** Hands out a scripted sequence, so a whole session's worth of window events fits in one test */
private class ScriptedSource(private vararg val frames: Viewport?) : ViewportSource {
    private var index = 0
    override fun read(): Viewport? = frames[index.coerceAtMost(frames.lastIndex)].also { index++ }
}

class WindowTrackerTest : FunSpec({
    test("the first sighting of a window is a change") {
        val tracker = WindowTracker(ScriptedSource(overlayable(WINDOWED)))

        tracker.poll() shouldBe ViewportUpdate.Changed(overlayable(WINDOWED))
    }

    test("an unchanged window is reported once and then stays quiet") {
        val tracker = WindowTracker(ScriptedSource(overlayable(WINDOWED)))
        tracker.poll()

        assertSoftly {
            // The whole point: repositioning on these would flicker
            tracker.poll().shouldBeNull()
            tracker.poll().shouldBeNull()
            tracker.poll().shouldBeNull()
        }
    }

    test("moving and resizing are both changes") {
        val tracker = WindowTracker(
            ScriptedSource(overlayable(WINDOWED), overlayable(MOVED), overlayable(RESIZED))
        )

        assertSoftly {
            tracker.poll() shouldBe ViewportUpdate.Changed(overlayable(WINDOWED))
            tracker.poll() shouldBe ViewportUpdate.Changed(overlayable(MOVED))
            tracker.poll() shouldBe ViewportUpdate.Changed(overlayable(RESIZED))
        }
    }

    test("alt-tabbing is a change even though the window has not moved") {
        val away = Viewport(WINDOWED, DisplayState.NotForeground)
        val tracker = WindowTracker(ScriptedSource(overlayable(WINDOWED), away))
        tracker.poll()

        // A geometry-only tracker would miss this, and the overlay would hang over another app
        tracker.poll() shouldBe ViewportUpdate.Changed(away)
    }

    test("losing the window is reported once, not every tick after") {
        val tracker = WindowTracker(ScriptedSource(overlayable(WINDOWED), null))
        tracker.poll()

        assertSoftly {
            tracker.poll() shouldBe ViewportUpdate.Lost
            tracker.poll().shouldBeNull()
            tracker.poll().shouldBeNull()
        }
    }

    test("a game that was never running says nothing at all") {
        val tracker = WindowTracker(ScriptedSource(null))

        assertSoftly {
            // Not Lost — nothing was ever there to lose
            tracker.poll().shouldBeNull()
            tracker.poll().shouldBeNull()
        }
    }

    test("a window that comes back after a restart is a fresh change") {
        val tracker = WindowTracker(
            ScriptedSource(overlayable(WINDOWED), null, overlayable(WINDOWED))
        )

        assertSoftly {
            tracker.poll() shouldBe ViewportUpdate.Changed(overlayable(WINDOWED))
            tracker.poll() shouldBe ViewportUpdate.Lost
            // Same bounds as before, but the overlay has to be repositioned onto the new window
            tracker.poll() shouldBe ViewportUpdate.Changed(overlayable(WINDOWED))
        }
    }

    test("current follows the last change and clears when the window goes") {
        val tracker = WindowTracker(ScriptedSource(overlayable(WINDOWED), null))

        assertSoftly {
            tracker.current.shouldBeNull()
            tracker.poll()
            tracker.current shouldBe overlayable(WINDOWED)
            tracker.poll()
            tracker.current.shouldBeNull()
        }
    }

    test("bounds know their own aspect, which the frustum has to agree with") {
        assertSoftly {
            RESIZED.aspect shouldBe (1920f / 1080f)
            ScreenBounds(0, 0, 0, 0).isEmpty shouldBe true
            WINDOWED.sameSizeAs(MOVED) shouldBe true
            WINDOWED.sameSizeAs(RESIZED) shouldBe false
        }
    }
})