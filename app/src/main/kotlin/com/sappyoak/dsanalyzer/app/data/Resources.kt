package com.sappyoak.dsanalyzer.app.data


import com.sappyoak.dsanalyzer.game.assets.GameAssetPaths

object Resources {
    private const val DICTIONARY_ROOT = "/dictionaries"

    // not accessed concurrently, so this is fine for now
    private val cachedDictionaries = LinkedHashMap<String, List<String>>()

    fun allDictionaryPaths(): List<String> =
        loadPathDictionaries().values.flatten().distinct()

    fun loadPathDictionaries(): Map<String, List<String>> {
        if (cachedDictionaries.isNotEmpty()) return cachedDictionaries

        val result = LinkedHashMap<String, List<String>>()
        for (name in GameAssetPaths.ARCHIVE_NAMES) {
            val stream = Resources::class.java.getResourceAsStream("$DICTIONARY_ROOT/$name.txt") ?: continue
            result[name] = stream.bufferedReader().useLines { lines ->
                lines.map { it.trim() }
                    .filter { it.isNotEmpty() && !it.startsWith("#") }
                    .toList()
            }
        }

        require(result.isNotEmpty()) {
            "No path directories on the classpath. Without them a packed installation " +
            "cannot be read at all, because BHD5 stores path hashes rather than names"
        }

        cachedDictionaries.putAll(result)
        return result
    }
}