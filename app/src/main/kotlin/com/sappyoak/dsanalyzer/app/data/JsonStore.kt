package com.sappyoak.dsanalyzer.app.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlinx.serialization.json.*
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.copyTo

import com.sappyoak.dsanalyzer.shared.io.*


class JsonStore<T>(
    val location: Path,
    private val serializer: KSerializer<T>,
    private val jsonSerializer: Json,
    private val default: () -> T
) {
    fun load(): LoadResult<T> {
        if (!location.exists()) {
            return LoadResult(default(), false)
        }

        return location.readFile { jsonSerializer.decodeFromStream(serializer, it) }.fold(
            onSuccess = { LoadResult(it, true) },
            onFailure = { LoadResult(
                value = default(),
                existed = true,
                problem = "Could not read $location, falling back to defaults (${it.message})"
            )}
        )
    }

    fun save(value: T): Result<Path> = location.writeFileAtomic {
        jsonSerializer.encodeToStream(serializer, value, it)
    }

    fun backup(): Result<Unit> = runCatching {
        if (!location.exists()) return@runCatching

        location.copyTo(
            location.parent.resolve("${location.fileName}-${System.currentTimeMillis()}.bak", ),
            overwrite = true
        )
    }

    data class LoadResult<T>(
        val value: T,
        val existed: Boolean,
        val problem: String? = null,
        val err: Throwable? = null
    ) {
        val isFirstRun: Boolean get() = !existed
    }

    companion object {
        inline operator fun <reified T> invoke(
            location: Path,
            jsonSerializer: Json,
            noinline default: () -> T
        ): JsonStore<T> = JsonStore(location, serializer<T>(), jsonSerializer, default)
    }
}