package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.fmg.Fmg
import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.formats.param.refs.ParamRef
import com.sappyoak.dsanalyzer.game.world.maps.CharacterModels
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.params.GameParam
import com.sappyoak.dsanalyzer.game.world.params.GameParams
import com.sappyoak.dsanalyzer.game.world.params.ParamNames
import com.sappyoak.dsanalyzer.game.world.params.ParamSide
import com.sappyoak.dsanalyzer.game.world.params.paramFor
import com.sappyoak.dsanalyzer.game.world.text.TextCategory

/** The text categories naming reads, beside the item names [ParamNames] covers */
internal val NAMING_TEXT: List<TextCategory> =
    listOf(TextCategory.EventText, TextCategory.Conversations, TextCategory.NpcNames)

/** Everything the sources read, loaded once */
public class NamingContext(
    public val params: GameParams,
    private val items: ParamNames,
    private val text: Map<TextCategory, Fmg>,
    public val maps: Map<MapId, MSB>,
    public val models: CharacterModels
) {
    public val entities: PlacedEntities = PlacedEntities.of(maps, models, ::npcName)

    /** The item's name, or its kind and id where the text has none */
    public fun item(ref: ParamRef): String =
        items.nameOf(ref.kind.paramFor(ParamSide.Npc), ref.id) ?: "${ref.kind} ${ref.id}"

    /** One string, trimmed to its first line; null for an id the category does not hold */
    public fun text(category: TextCategory, id: Int): String? =
        text[category]?.get(id)?.lineSequence()?.firstOrNull { it.isNotBlank() }?.trim()

    /** A character's name from its NpcParam row, where the game's text gives it one */
    public fun npcName(npcParamId: Int): String? =
        params[GameParam.NpcParam][npcParamId]?.let { text(TextCategory.NpcNames, it.nameId) }
}
