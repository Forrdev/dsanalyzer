package com.sappyoak.dsanalyzer.game.world.maps

import kotlinx.serialization.json.Json

import com.sappyoak.dsanalyzer.game.bundledText

private const val MODELS_RESOURCE = "/definitions/ds1-character-models.json"

/** Model names are the model's number with a c in front of it */
private const val MODEL_PREFIX = 'c'
private const val MAX_DIGITS = 9

public class CharacterModels(private val byId: Map<Int, String>) {
    public val size: Int get() = byId.size

    public operator fun get(id: Int): String? = byId[id]

    public fun describe(modelName: String): String? {
        val name = modelName.trim()
        if (name.length < 2 || name.length > 1 + MAX_DIGITS) return null
        if (!name[0].equals(MODEL_PREFIX, ignoreCase = true)) return null

        var id = 0
        for (index in 1 until name.length) {
            val digit = name[index].code - '0'.code
            if (digit < 0 || digit > 9) return null
            id = id * 10 + digit
        }
        return byId[id]
    }

    public companion object {
        public val Empty: CharacterModels = CharacterModels(emptyMap())
    }
}

public fun loadCharacterModels(): CharacterModels = CharacterModels(
    Json.decodeFromString<Map<String, String>>(bundledText(MODELS_RESOURCE))
        .mapNotNull { (id, name) -> id.toIntOrNull()?.let { it to name } }
        .toMap()
)