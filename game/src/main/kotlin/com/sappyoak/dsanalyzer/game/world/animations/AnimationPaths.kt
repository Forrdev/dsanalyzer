package com.sappyoak.dsanalyzer.game.world.animations

import com.sappyoak.dsanalyzer.game.files.GamePath

internal const val CHARACTER_DIRECTORY = "/chr/"
internal const val OBJECT_DIRECTORY = "/obj/"

internal const val CHARACTER_BINDER_EXTENSION = ".anibnd.dcx"
internal const val OBJECT_BINDER_EXTENSION = ".objbnd.dcx"

/** The DLC weapons gave the player animations that did not fit in the binder he already had */
private const val DLC_SUFFIX = "_dlc01"

public val AnimationSetId.animationPaths: List<GamePath>
    get() = when (this) {
        is AnimationSetId.Character -> buildList {
            add(GamePath.of("$CHARACTER_DIRECTORY$label$CHARACTER_BINDER_EXTENSION"))
            if (isPlayer) {
                add(GamePath.of("$CHARACTER_DIRECTORY$label$DLC_SUFFIX$CHARACTER_BINDER_EXTENSION"))
            }
        }

        is AnimationSetId.Object -> listOf(GamePath.of("$OBJECT_DIRECTORY$label$OBJECT_BINDER_EXTENSION"))
    }