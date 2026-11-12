package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable

/** 'EzState::detail::EzStateExternalEventTemp' */
public data class TalkEventLayout(
    public val size: Int,
    public val command: Int,
    public val firstParam: Int,
    public val paramStride: Int,
    public val paramType: Int,
    public val highestIndex: Int,
    public val maxParams: Int
)

public data class EntityTableLayout(
    public val manager: GamePointer,
    public val blocks: GameTable,
    public val count: Int,
    public val entries: Int
)

public data class OwnerLinkLayout(
    public val link: Int,
    public val back: Int
)

/** The talk-script dispatcher and what it reads. The handler holds the owner at
 * [handlerOwner], and the owner holds an entity id at [ownerEntity] that [ownerLookup] resolves to a character
 */
public data class TalkLayout(
    public val dispatcher: GamePointer,
    public val ownerLookup: GamePointer,
    public val eventVTable: GamePointer,
    public val handlerVTable: GamePointer?,
    public val event: TalkEventLayout,
    public val handlerOwner: Int,
    public val ownerEntity: Int,
    public val ownerLink: OwnerLinkLayout?,
    public val entities: EntityTableLayout
)
