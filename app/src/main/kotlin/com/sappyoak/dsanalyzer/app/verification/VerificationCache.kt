package com.sappyoak.dsanalyzer.app.verification

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.*

import com.sappyoak.dsanalyzer.game.InstallationId

public class VerificationCache(
    private val location: Path,
    private val json: Json
) {
    private val logger = KotlinLogging.logger {  }

    public suspend fun read(): Map<InstallationId, VerificationRecord> = withContext(Dispatchers.IO) {
        if (!location.exists()) {
            return@withContext emptyMap()
        }

        try {
            json.decodeFromString<Map<String, VerificationRecord>>(location.readText())
                .mapKeys { (id, _) -> InstallationId(id) }
        } catch (err: SerializationException) {
            logger.warn(err) { "Discarding unreadable $location"}
            location.deleteIfExists()
            emptyMap()
        }
    }

    public suspend fun write(records: Map<InstallationId, VerificationRecord>) = withContext(Dispatchers.IO) {
        val temp = createTempFile(location.parent, "verification", ".json")
        temp.writeText(json.encodeToString(records.mapKeys { (id, _) -> id.value }))
        temp.moveTo(
            location,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        )
    }
}