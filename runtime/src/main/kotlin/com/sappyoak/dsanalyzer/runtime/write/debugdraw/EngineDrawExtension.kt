package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import java.util.concurrent.atomic.AtomicReference

import com.sappyoak.dsanalyzer.runtime.write.SessionContext
import com.sappyoak.dsanalyzer.runtime.write.SessionExtension
import com.sappyoak.dsanalyzer.runtime.snapshot.CollisionDrawSettings
import com.sappyoak.dsanalyzer.runtime.snapshot.DrawInstall
import com.sappyoak.dsanalyzer.runtime.snapshot.EngineDraw
import com.sappyoak.dsanalyzer.runtime.snapshot.EngineDrawRequest
import com.sappyoak.dsanalyzer.runtime.snapshot.EngineDrawStatus
import com.sappyoak.dsanalyzer.runtime.snapshot.WorldPlace

/**
 * A 30 Hz tick: tags about four times a second; retries an install, and re-reads what a load screen remakes,
 * every two seconds
 */
private const val TAG_TICKS = 8
private const val RETRY_TICKS = 60

/** No filter enabled, so the gate skips every part */
private val NO_COLLISION = CollisionDrawSettings(filters = emptySet())

public class EngineDrawRequests {
    private val wanted = AtomicReference(EngineDrawRequest())
    private val manual = AtomicReference<Boolean?>(null)

    internal val current: EngineDrawRequest get() = wanted.get()

    public fun set(request: EngineDrawRequest) {
        wanted.set(request)
    }

    public fun install(on: Boolean) {
        manual.set(on)
    }

    internal fun takeManual(): Boolean? = manual.getAndSet(null)
}

public class EngineDrawExtension(
    private val requests: EngineDrawRequests,
    private val report: (EngineDrawStatus) -> Unit
) : SessionExtension {
    private var draw: EngineDraw? = null
    private var place: () -> WorldPlace? = { null }
    private var held = false
    private var applied: EngineDrawRequest? = null
    private var last = EngineDrawRequest()
    private var lastPlace: WorldPlace? = null
    private var tick = 0
    private var retryAt = 0
    private var status: EngineDrawStatus? = null

    override fun attached(context: SessionContext) {
        draw = engineDrawFor(context)
        place = { context.place }
        publish(if (draw == null) EngineDrawStatus.Unsupported else EngineDrawStatus.Removed)
    }

    override fun afterSample() {
        val draw = draw ?: return
        val req = requests.current
        tick++

        when (requests.takeManual()) {
            true -> {
                held = false
                retryAt = tick
            }
            false -> remove(draw)
            null -> Unit
        }

        if (req.drawsAnything && !last.drawsAnything) held = false
        last = req

        if (!draw.installed && !held && (req.installOnAttach || req.drawsAnything) && tick >= retryAt) {
            install(draw)
        }
        if (draw.installed) sync(draw, req)
    }

    override fun release() {
        draw?.let(::remove)
    }

    private fun install(draw: EngineDraw) {
        when (val result = draw.install()) {
            DrawInstall.Installed -> applied = null
            is DrawInstall.Failed -> {
                retryAt = tick + RETRY_TICKS
                publish(EngineDrawStatus.Failed(result.reason))
            }
        }
    }

    private fun remove(draw: EngineDraw) {
        held = true
        applied = null
        if (draw.installed) draw.uninstall()
        publish(EngineDrawStatus.Removed)
    }

    private fun sync(draw: EngineDraw, req: EngineDrawRequest) {
        val now = place()
        val moved = now != lastPlace
        lastPlace = now
        val before = applied

        if (moved || tick % RETRY_TICKS == 0) draw.refresh()
        if (before == null || before.collision != req.collision) draw.apply(req.collision ?: NO_COLLISION)
        if (before == null || before.hitboxes != req.hitboxes || moved) draw.setHitboxes(req.hitboxes)
        applied = req

        val tagged = (status as? EngineDrawStatus.Installed)?.tagged ?: 0
        val retag = before == null || moved || (req.collision != null && tick % TAG_TICKS == 0)
        publish(EngineDrawStatus.Installed(if (retag) draw.tag() else tagged))
    }

    private fun publish(next: EngineDrawStatus) {
        if (next == status) return
        status = next
        report(next)
    }
}
