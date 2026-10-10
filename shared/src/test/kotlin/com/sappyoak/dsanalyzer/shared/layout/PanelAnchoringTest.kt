package com.sappyoak.dsanalyzer.shared.layout

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import kotlin.math.sqrt

private const val SMALL = 1000
private const val LARGE = 4000

/** The pixel offset from whichever edge the panel is nearer */
private fun gap(fraction: Float, size: Int): Float =
    if (fraction < 0.5f) fraction * size else (1f - fraction) * size

class PanelAnchoringTest : FunSpec({
    test("nothing moves when the window does not change") {
        assertSoftly {
            reanchor(0.25f, SMALL, SMALL) shouldBe 0.25f
            reanchor(0.5f, SMALL, SMALL) shouldBe 0.5f
        }
    }

    test("a size that cannot be a window leaves the anchor alone") {
        assertSoftly {
            reanchor(0.25f, 0, LARGE) shouldBe 0.25f
            reanchor(0.25f, SMALL, 0) shouldBe 0.25f
            reanchor(0.25f, -1, LARGE) shouldBe 0.25f
        }
    }

    test("the centre is the fixed point, in both directions") {
        assertSoftly {
            reanchor(0.5f, SMALL, LARGE) shouldBe (0.5f plusOrMinus 1e-6f)
            reanchor(0.5f, LARGE, SMALL) shouldBe (0.5f plusOrMinus 1e-6f)
        }
    }

    test("an edge panel's gap grows by the square root of the ratio, not by the ratio") {
        val fraction = 0.02f
        val before = gap(fraction, SMALL)
        val after = gap(reanchor(fraction, SMALL, LARGE), LARGE)

        assertSoftly {
            // The two answers this rejects: holding the gap, and scaling it with the window
            before shouldBe (20f plusOrMinus 0.01f)
            after shouldBe (before * sqrt(LARGE.toFloat() / SMALL) plusOrMinus 2f)
            (after > before) shouldBe true
            (after < before * (LARGE.toFloat() / SMALL)) shouldBe true
        }
    }

    test("a panel near the far edge keeps its far-edge gap, not its near-edge one") {
        val near = reanchor(0.02f, SMALL, LARGE)
        val far = reanchor(0.98f, SMALL, LARGE)

        assertSoftly {
            // Mirror images, which is what measuring from the nearer edge buys
            near shouldBe (1f - far plusOrMinus 1e-6f)
            (far > 0.9f) shouldBe true
        }
    }

    test("shrinking the window pushes an edge panel proportionally further in") {
        val fraction = 0.02f
        val after = reanchor(fraction, LARGE, SMALL)

        assertSoftly {
            (after > fraction) shouldBe true
            // but still nearer the edge than the middle by a long way
            (after < 0.2f) shouldBe true
        }
    }

    test("a panel pinned flat against an edge stays grabbable") {
        assertSoftly {
            (reanchor(0f, SMALL, LARGE) >= 0.01f) shouldBe true
            (reanchor(1f, SMALL, LARGE) <= 0.99f) shouldBe true
            (reanchor(0f, LARGE, SMALL) >= 0.01f) shouldBe true
        }
    }

    test("the two axes are reanchored independently, so an aspect change is handled") {
        // A 4:3 window going 16:9 wide: the x axis grows, the y axis does not
        val moved = PanelAnchor(0.05f, 0.05f).reanchored(1024, 768, 1920, 768)

        assertSoftly {
            (moved.x < 0.05f) shouldBe true
            moved.y shouldBe (0.05f plusOrMinus 1e-6f)
        }
    }

    test("a corner panel stays in its corner across a fullscreen switch") {
        val corner = PanelAnchor(0.97f, 0.97f).reanchored(1280, 720, 3840, 2160)

        assertSoftly {
            (corner.x > 0.95f) shouldBe true
            (corner.y > 0.95f) shouldBe true
            (corner.x <= 0.99f) shouldBe true
        }
    }
})