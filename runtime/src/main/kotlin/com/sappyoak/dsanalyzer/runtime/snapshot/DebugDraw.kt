package com.sappyoak.dsanalyzer.runtime.snapshot

import com.sappyoak.dsanalyzer.formats.msb.part.CollisionHitFilter
import com.sappyoak.dsanalyzer.shared.math.Vec4

public enum class DrawMode {
    Lo, Hi, Both;
}

public data class FilterColors(public val wire: Vec4, public val fill: Vec4) {
    public companion object {
        public val Game: FilterColors = FilterColors(
            wire = Vec4(0.18f, 0f, 0.3f, 0.5f),
            fill = Vec4(0.18f, 0.15f, 0f, 0.5f)
        )
    }
}

public data class CollisionDrawSettings(
    public val mode: DrawMode = DrawMode.Lo,
    public val filters: Set<CollisionHitFilter> = CollisionHitFilter.entries.toSet(),
    public val colors: Map<CollisionHitFilter, FilterColors> = emptyMap(),
    public val fill: Boolean = true,
    public val radius: Float? = null
)

public sealed interface DrawInstall {
    public data object Installed : DrawInstall
    public data class Failed(public val reason: String) : DrawInstall
}

public interface EngineDraw {
    public val installed: Boolean

    public fun install(): DrawInstall
    public fun uninstall(): Boolean

    public fun refresh(): Boolean

    public fun apply(settings: CollisionDrawSettings): Boolean
    public fun tag(): Int
    public fun setHitboxes(enabled: Boolean): Boolean
}

public data class EngineDrawRequest(
    public val installOnAttach: Boolean = false,
    public val collision: CollisionDrawSettings? = null,
    public val hitboxes: Boolean = false
) {
    public val drawsAnything: Boolean get() = collision != null || hitboxes
}

public sealed interface EngineDrawStatus {
    public data object Unsupported : EngineDrawStatus
    public data object Removed : EngineDrawStatus
    public data class Installed(public val tagged: Int) : EngineDrawStatus
    public data class Failed(public val reason: String) : EngineDrawStatus
}
