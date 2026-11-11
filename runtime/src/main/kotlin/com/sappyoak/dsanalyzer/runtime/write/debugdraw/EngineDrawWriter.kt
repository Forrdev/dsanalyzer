package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.DebugDrawLayout
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.sampling.SessionContext
import com.sappyoak.dsanalyzer.runtime.session.CollisionDrawSettings
import com.sappyoak.dsanalyzer.runtime.session.DrawInstall
import com.sappyoak.dsanalyzer.runtime.session.EngineDraw
import com.sappyoak.dsanalyzer.runtime.write.CaveResult
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
import com.sappyoak.dsanalyzer.runtime.write.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.addressOf
import com.sappyoak.dsanalyzer.runtime.write.installCaves

private val HITBOXES = WriteKey("debugdraw:hitboxes")

/**
 * The game's own debug renderer, drawn into the scene. Everything here is the same for both
 * editions: the control block, the tags, and installing as one unit. [backend] supplies the rest
 */
@OptIn(GameMutation::class)
internal class EngineDrawWriter(
    private val context: SessionContext,
    private val layout: DebugDrawLayout,
    private val backend: DrawBackend
) : EngineDraw {
    private val memory get() = context.memory.memory
    private val writer get() = context.writer
    private val tags = CollisionTags(layout)

    private var settings = CollisionDrawSettings()

    /** Allocated once and kept: caves of an earlier install may still read it */
    private var control: Address? = null
    private var keys: List<WriteKey> = emptyList()

    override val installed: Boolean get() = keys.isNotEmpty()

    override fun install(): DrawInstall {
        if (installed) return DrawInstall.Installed
        val block = control ?: writer.allocate(DrawControl.SIZE, context.module.start)?.also { control = it }
        ?: return DrawInstall.Failed("no memory could be allocated in the game")
        if (!writeSettings(block)) return DrawInstall.Failed("the control block could not be written")
        val route = when (val found = backend.route(context, block)) {
            is DrawRoute.Ready -> found
            is DrawRoute.Unavailable -> return DrawInstall.Failed(found.reason)
        }

        if (!route.writable.all { writer.makeWritable(it.start, it.size) }) {
            return DrawInstall.Failed("game data the caves change could not be made writable")
        }

        val before = writer.active
        var reason = "a draw setup write failed"
        val done = writer.allOrNothing {
            route.edits.all { writeBytes(WriteKey("debugdraw:data@${it.address}"), it.address, Lifetime.Session, it.bytes) } &&
                    when (val caves = installCaves(memory, context.module, route.caves)) {
                        CaveResult.Installed -> true
                        is CaveResult.Failed -> false.also { reason = caves.reason }
                    }
        }
        if (!done) return DrawInstall.Failed(reason)

        keys = writer.active.filterNot { it in before }
        tag()
        return DrawInstall.Installed
    }

    override fun uninstall(): Boolean {
        if (!installed) return true
        manager()?.let { tags.clear(memory, writer, it) }
        val reverted = keys.asReversed().map(writer::revert).all { it }
        keys = emptyList()
        return reverted
    }

    override fun apply(settings: CollisionDrawSettings): Boolean {
        this.settings = settings
        return control?.let(::writeSettings) ?: true
    }

    override fun tag(): Int = manager()?.let { tags.tag(memory, writer, it) } ?: 0

    override fun setHitboxes(enabled: Boolean): Boolean {
        if (!enabled) return writer.revert(HITBOXES) || HITBOXES !in writer.active
        val field = context.layout.writes.hitboxDraw ?: return false
        val address = context.memory.addressOf(field) ?: return false
        return writer.writeBytes(HITBOXES, address, field.pointer.lifetime, byteArrayOf(1))
    }

    private fun writeSettings(block: Address): Boolean =
        writer.fill(block + DrawControl.Mask.toLong(), settings.header()) &&
                writer.fill(block + DrawControl.Colors.toLong(), settings.colorTable())

    private fun manager(): Address? = context.memory.addressOf(layout.hitIns.manager).takeIf { !it.isNull }
}
