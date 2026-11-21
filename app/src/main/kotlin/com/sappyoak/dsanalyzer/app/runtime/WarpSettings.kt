package com.sappyoak.dsanalyzer.app.runtime

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.snapshot.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.SavedPosition
import com.sappyoak.dsanalyzer.runtime.write.warp.WarpDestination
import com.sappyoak.dsanalyzer.shared.math.Vec3

/** A point the user saved and named in game. [map] is packed, and [angle] is the yaw in radians */
@Serializable
public data class CustomWarp(
    public val name: String,
    public val map: Int,
    public val x: Float,
    public val y: Float,
    public val z: Float,
    public val angle: Float
) {
    public val destination: WarpDestination
        get() = WarpDestination.Point(SavedPosition(MapId(map), Vec3(x, y, z), angle))
}

/** The user's own warps, each known by its name */
@Serializable
public data class WarpSettings(
    public val custom: List<CustomWarp> = emptyList()
)

/** Saves [saved] as [name], in place of a warp that already has that name */
public data class SaveWarp(public val name: String, public val saved: SavedPosition) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings {
        val warp = CustomWarp(name, saved.map.packed, saved.position.x, saved.position.y, saved.position.z, saved.angle)
        val custom = settings.warps.custom
        val index = custom.indexOfFirst { it.name == name }
        return settings.custom(if (index < 0) custom + warp else custom.toMutableList().apply { set(index, warp) })
    }
}

/** Gives warp [from] the name [to], unless another warp has it */
public data class RenameWarp(public val from: String, public val to: String) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings {
        val custom = settings.warps.custom
        if (custom.any { it.name == to }) return settings
        return settings.custom(custom.map { if (it.name == from) it.copy(name = to) else it })
    }
}

public data class DeleteWarp(public val name: String) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings =
        settings.custom(settings.warps.custom.filterNot { it.name == name })
}

/** Saves where the player stands as [name], or null outside the world */
public fun RuntimeSnapshot.saveAsWarp(name: String): SaveWarp? = SavedPosition.of(this)?.let { SaveWarp(name, it) }

private fun Settings.custom(custom: List<CustomWarp>): Settings = copy(warps = warps.copy(custom = custom))
