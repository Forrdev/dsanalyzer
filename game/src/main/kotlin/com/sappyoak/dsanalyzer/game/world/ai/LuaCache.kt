package com.sappyoak.dsanalyzer.game.world.ai

import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.createDirectories
import kotlin.io.path.createTempFile
import kotlin.io.path.exists
import kotlin.io.path.moveTo
import kotlin.io.path.readText
import kotlin.io.path.writeText
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.json.Json

private val logger = KotlinLogging.logger { }

private const val STAMP = "stamp.json"
private const val CATALOG = "catalog.json"
private const val AI_INDEX = "ai-index.json"
private const val SCRIPTS = "scripts"

/**
 * One installation's decompiled Lua, kept on disk so later sessions read it back instead of parsing and
 * decompiling again. Everything in [directory] was made under one [LuaCacheStamp]; opening it under another
 * starts it over. An unreadable entry is a miss and a failed write is only logged: the cache only saves work
 */
internal class LuaCache private constructor(private val directory: Path) {
    /** Each binder's scripts, as [availableLua] gave them */
    fun catalog(): Map<LuaBinder, List<String>>? = read<Map<String, List<String>>>(CATALOG)?.let { written ->
        val binders = written.mapKeys { (label, _) -> LuaBinder.of(label) }
        if (null in binders.keys) null else binders.mapKeys { checkNotNull(it.key) }
    }

    fun saveCatalog(catalog: Map<LuaBinder, List<String>>) = write(CATALOG, catalog.mapKeys { it.key.label })

    fun source(script: LuaScriptId): LuaSource? =
        read<SourceRecord>(sourcePath(script))?.let { LuaSource(it.source, it.failures) }

    fun saveSource(script: LuaScriptId, source: LuaSource) =
        write(sourcePath(script), SourceRecord(source.source, source.failures))

    fun aiIndex(): AiIndex? = read<AiIndexRecord>(AI_INDEX)?.toIndex()

    fun saveAiIndex(index: AiIndex) = write(AI_INDEX, AiIndexRecord.of(index))

    private fun sourcePath(script: LuaScriptId): String = "$SCRIPTS/${script.binder.label}/${script.name}.json"

    private inline fun <reified T> read(relative: String): T? {
        val file = directory.resolve(relative)
        if (!file.exists()) return null
        return runCatching { Json.decodeFromString<T>(file.readText()) }
            .onFailure { logger.warn(it) { "Ignoring unreadable $file" } }
            .getOrNull()
    }

    private inline fun <reified T> write(relative: String, value: T) {
        runCatching { writeAtomically(directory.resolve(relative), Json.encodeToString(value)) }
            .onFailure { logger.warn(it) { "Could not cache $relative in $directory" } }
    }

    companion object {
        /** The cache in [directory], emptied first unless it was made under [stamp]; null if it cannot be used */
        fun open(directory: Path, stamp: LuaCacheStamp): LuaCache? = runCatching {
            val stampFile = directory.resolve(STAMP)
            val current = runCatching { Json.decodeFromString<LuaCacheStamp>(stampFile.readText()) }.getOrNull()
            if (current != stamp) {
                directory.toFile().deleteRecursively()
                writeAtomically(stampFile, Json.encodeToString(stamp))
            }
            LuaCache(directory)
        }.onFailure { logger.warn(it) { "Not caching Lua in $directory" } }.getOrNull()
    }
}

/** Writes beside [file] and moves into place, so a reader never sees half a file */
private fun writeAtomically(file: Path, text: String) {
    file.parent.createDirectories()
    val temp = createTempFile(file.parent, file.fileName.toString(), ".tmp")
    temp.writeText(text)
    temp.moveTo(file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
}
