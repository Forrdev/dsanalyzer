package com.sappyoak.dsanalyzer.app.runtime

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.formats.msb.part.CollisionHitFilter
import com.sappyoak.dsanalyzer.runtime.session.CollisionDrawSettings
import com.sappyoak.dsanalyzer.runtime.session.DrawMode
import com.sappyoak.dsanalyzer.runtime.session.EngineDrawRequest
import com.sappyoak.dsanalyzer.runtime.session.FilterColors
import com.sappyoak.dsanalyzer.shared.math.Vec4

/** The game's colors bloom above about half brightness */
public const val MAX_CHANNEL: Float = 0.5f

private const val DEFAULT_DISTANCE = 60f

public val KILL_PLANES: Set<CollisionHitFilter> = setOf(CollisionHitFilter.KillPlane, CollisionHitFilter.LethalFall)

@Serializable
public data class DrawColor(
    public val r: Float,
    public val g: Float,
    public val b: Float,
    public val a: Float
) {
    internal fun toVec4(): Vec4 = Vec4(r, g, b, a)

    public companion object {
        internal fun of(vec: Vec4): DrawColor = DrawColor(vec.x, vec.y, vec.z, vec.w)
    }
}

@Serializable
public data class FilterColorPair(
    public val wire: DrawColor,
    public val fill: DrawColor
) {
    internal fun toFilterColors(): FilterColors = FilterColors(wire.toVec4(), fill.toVec4())

    public companion object {
        public val Game: FilterColorPair = FilterColorPair(
            wire = DrawColor.of(FilterColors.Game.wire),
            fill = DrawColor.of(FilterColors.Game.fill)
        )
    }
}

@Serializable
public data class GameCollisionSettings(
    public val enabled: Boolean = false,
    public val mode: DrawMode = DrawMode.Lo,
    public val filters: Set<CollisionHitFilter> = CollisionHitFilter.entries.toSet(),
    public val colors: Map<CollisionHitFilter, FilterColorPair> = emptyMap(),
    public val fill: Boolean = true,
    public val distance: Float? = DEFAULT_DISTANCE
) {
    public fun colorsOf(filter: CollisionHitFilter): FilterColorPair = colors[filter] ?: FilterColorPair.Game
}

@Serializable
public data class EngineDrawSettings(
    public val collision: GameCollisionSettings = GameCollisionSettings(),
    public val hitboxes: Boolean = false
)

internal val Settings.engineDrawRequest: EngineDrawRequest
    get() = EngineDrawRequest(
        installOnAttach = tool.cavesOnLaunch,
        collision = engineDraw.collision.takeIf { it.enabled }?.let {
            CollisionDrawSettings(
                mode = it.mode,
                filters = it.filters,
                colors = it.colors.mapValues { (_, pair) -> pair.toFilterColors() },
                fill = it.fill,
                radius = it.distance
            )
        },
        hitboxes = engineDraw.hitboxes
    )

public data class DrawGameHitboxes(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.copy(engineDraw = settings.engineDraw.copy(hitboxes = enabled))
}

public data class DrawGameCollision(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings = settings.collision { it.copy(enabled = enabled) }
}

public data class GameCollisionMode(public val mode: DrawMode) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings = settings.collision { it.copy(mode = mode) }
}

public data class GameCollisionFill(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings = settings.collision { it.copy(fill = enabled) }
}

public data class GameCollisionDistance(public val units: Float?) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.collision { it.copy(distance = units?.coerceAtLeast(0f)) }
}

public data class GameCollisionFilter(public val filter: CollisionHitFilter, public val drawn: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.collision { it.copy(filters = if (drawn) it.filters + filter else it.filters - filter) }
}

public data class GameCollisionFilters(public val filters: Set<CollisionHitFilter>) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings = settings.collision { it.copy(filters = filters) }
}

public data class GameCollisionColors(
    public val filter: CollisionHitFilter,
    public val colors: FilterColorPair
) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.collision { it.copy(colors = it.colors + (filter to colors.clamped())) }
}

public data object GameCollisionColorsReset : SettingsEdit {
    override fun applyTo(settings: Settings): Settings = settings.collision { it.copy(colors = emptyMap()) }
}

private fun Settings.collision(change: (GameCollisionSettings) -> GameCollisionSettings): Settings =
    copy(engineDraw = engineDraw.copy(collision = change(engineDraw.collision)))

private fun FilterColorPair.clamped(): FilterColorPair = FilterColorPair(wire.clamped(), fill.clamped())

private fun DrawColor.clamped(): DrawColor = DrawColor(
    r.coerceIn(0f, MAX_CHANNEL),
    g.coerceIn(0f, MAX_CHANNEL),
    b.coerceIn(0f, MAX_CHANNEL),
    a.coerceIn(0f, 1f)
)
