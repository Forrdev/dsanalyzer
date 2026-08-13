package com.sappyoak.dsanalyzer.shared

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.serializer
import java.io.BufferedOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.outputStream

fun Path.writeFile(content: String): Result<Path> {
    parent?.let { Files.createDirectories(it) }
    val tempFile = resolveSibling("$fileName.tmp")

    return runCatching {
        Files.writeString(tempFile, content)
        Files.move(
            tempFile,
            this,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        )
    }.also { Files.deleteIfExists(tempFile) }
}

inline fun <reified T> Path.writeFile(
    json: Json,
    data: T,
    bufferSize: Int = 8192
): Result<Path> {
    parent?.let { Files.createDirectories(it) }
    val tempFile = resolveSibling("$fileName.tmp")

    return runCatching {
        BufferedOutputStream(tempFile.outputStream(), bufferSize).use { stream ->
            json.encodeToStream(serializer<T>(), data, stream)
        }

        Files.move(
            tempFile,
            this,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        )
    }.also { Files.deleteIfExists(tempFile) }
}
