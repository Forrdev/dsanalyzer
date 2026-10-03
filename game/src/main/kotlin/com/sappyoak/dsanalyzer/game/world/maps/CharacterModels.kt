package com.sappyoak.dsanalyzer.game.world.maps

import kotlinx.serialization.json.Json

import com.sappyoak.dsanalyzer.game.world.scripts.bundled

private const val MODELS_RESOURCE = "/definitions/ds1-character-models.json"

/** Model names are the model's number with a c in front of it */
private val MODEL_NAME = Regex("""c(\d+)""", RegexOption.IGNORE_CASE)

public class CharacterModels(private val byId: Map<Int, String>) {
    public val size: Int get() = byId.size

    public operator fun get(id: Int): String? = byId[id]

    public fun describe(modelName: String): String? =
        MODEL_NAME.matchEntire(modelName.trim())?.groupValues?.get(1)?.toIntOrNull()?.let(byId::get)

    public companion object {
        public val Empty: CharacterModels = CharacterModels(emptyMap())
    }
}

public fun loadCharacterModels(): CharacterModels = CharacterModels(
    Json.decodeFromString<Map<String, String>>(bundled(MODELS_RESOURCE))
        .mapNotNull { (id, name) -> id.toIntOrNull()?.let { it to name } }
        .toMap()
)