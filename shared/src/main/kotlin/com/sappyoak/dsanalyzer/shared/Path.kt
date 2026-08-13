package com.sappyoak.dsanalyzer.shared

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.serializer
import java.io.BufferedInputStream
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteIfExists
import kotlin.io.path.moveTo
import kotlin.io.path.outputStream
import kotlin.io.path.writeText
import java.io.BufferedOutputStream
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.inputStream


fun Path.writeFile(content: String): Result<Path> {
    parent?.createDirectories()
    val tempFile = resolveSibling("$fileName.tmp")

    return runCatching {
        tempFile.writeText(content)
        tempFile.moveTo(
            this,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        )
    }.also { tempFile.deleteIfExists() }
}

inline fun <reified T> Path.writeFile(
    json: Json,
    data: T,
    bufferSize: Int = 8192
): Result<Path> {
    parent?.createDirectories()

    val tempFile = resolveSibling("$fileName.tmp")

    return runCatching {
        BufferedOutputStream(tempFile.outputStream(), bufferSize).use { stream ->
            json.encodeToStream(serializer<T>(), data, stream)
        }

        tempFile.moveTo(
            this,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        )
    }.also { tempFile.deleteIfExists() }
}

inline fun <reified T> Path.readFile(
    json: Json,
    bufferSize: Int = 8192
): Result<T> {
    return runCatching {
        BufferedInputStream(inputStream(), bufferSize).use { stream ->
            json.decodeFromStream(serializer<T>(), stream)
        }
    }
}
