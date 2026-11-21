package com.sappyoak.dsanalyzer.runtime.write.debugdraw

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.DebugDrawLayout
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.write.SessionContext
import com.sappyoak.dsanalyzer.runtime.snapshot.CollisionDrawSettings
import com.sappyoak.dsanalyzer.runtime.snapshot.DrawInstall
import com.sappyoak.dsanalyzer.runtime.snapshot.EngineDraw
import com.sappyoak.dsanalyzer.runtime.snapshot.WriteKey
import com.sappyoak.dsanalyzer.runtime.write.CaveResult
import com.sappyoak.dsanalyzer.runtime.write.GameMutation
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

    /** What the edits and words last written say, so [refresh] writes only when the game has moved on */
    private var routed: String? = null

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

        if (!writeWords(block, route.words)) return DrawInstall.Failed("the control block could not be written")
        if (!route.writable.all { writer.makeWritable(it.start, it.size) }) {
            return DrawInstall.Failed("game data the caves change could not be made writable")
        }

        val before = writer.active
        var reason = "a draw setup write failed"
        val done = writer.allOrNothing {
            route.edits.all { writeEdit(it) } &&
                    when (val caves = installCaves(memory, context.module, route.caves)) {
                        CaveResult.Installed -> true
                        is CaveResult.Failed -> false.also { reason = caves.reason }
                    }
        }
        if (!done) return DrawInstall.Failed(reason)

        keys = writer.active.filterNot { it in before }
        routed = route.stamp()
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

    /** Rewrites edits under the keys they went in with, so the game's own values still go back on uninstall */
    override fun refresh(): Boolean {
        val block = control
        if (!installed || block == null || !backend.reroutes) return true
        val route = backend.route(context, block) as? DrawRoute.Ready ?: return false
        val stamp = route.stamp()
        if (stamp == routed) return true

        val written = writeWords(block, route.words) && route.edits.all { writeEdit(it) }
        if (written) routed = stamp
        return written
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

    private fun writeEdit(edit: DrawEdit): Boolean =
        writer.writeBytes(WriteKey("debugdraw:data@${edit.address}"), edit.address, Lifetime.Session, edit.bytes)

    private fun writeWords(block: Address, words: List<ControlWord>): Boolean =
        words.all { writer.fillDword(block + it.offset.toLong(), it.value) }

    private fun DrawRoute.Ready.stamp(): String {
        val words = words.map { "${it.offset}=${it.value}" }
        val edits = edits.map { "${it.address}=${it.bytes.contentToString()}" }
        return (words + edits).joinToString()
    }

    private fun manager(): Address? = context.memory.addressOf(layout.hitIns.manager).takeIf { !it.isNull }
}
