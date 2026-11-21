package com.sappyoak.dsanalyzer.game.world.animations

import com.sappyoak.dsanalyzer.game.files.GamePath

internal const val CHARACTER_DIRECTORY = "/chr/"
internal const val OBJECT_DIRECTORY = "/obj/"

internal const val CHARACTER_BINDER_EXTENSION = ".anibnd.dcx"
internal const val OBJECT_BINDER_EXTENSION = ".objbnd.dcx"

/**
 * The binder holding a set's TAE files. The player's DLC timings are in his main binder too; his other
 * binders (`c0000_dlc`, `c0000_a0x` and the like) hold only the motions
 */
public val AnimationSetId.animationPath: GamePath
    get() = when (this) {
        is AnimationSetId.Character -> GamePath.of("$CHARACTER_DIRECTORY$label$CHARACTER_BINDER_EXTENSION")
        is AnimationSetId.Object -> GamePath.of("$OBJECT_DIRECTORY$label$OBJECT_BINDER_EXTENSION")
    }
