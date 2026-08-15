package com.sappyoak.dsanalyzer.shared

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.serializer
import java.io.BufferedInputStream
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteIfExists
import kotlin.io.path.moveTo
import kotlin.io.path.outputStream
import java.io.BufferedOutputStream
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.exists
import kotlin.io.path.fileSize
import kotlin.io.path.inputStream

const val DEFAULT_BUFFER_SIZE = 8192

fun Path.writeFile(
    content: String,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
) = writeFile(content.toByteArray(), bufferSize)

fun Path.writeFile(
    content: ByteArray,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
) = writeFileAtomic(bufferSize) {
    stream -> stream.write(content)
}

fun <T> Path.writeFile(
    data: T,
    serializer: KSerializer<T>,
    jsonSerializer: Json,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
) = writeFileAtomic(bufferSize) {
    stream -> jsonSerializer.encodeToStream(serializer, data, stream)
}

inline fun <reified T> Path.writeFile(
    data: T,
    jsonSerializer: Json,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
) = writeFile(data, serializer<T>(), jsonSerializer, bufferSize)


fun Path.readBytes(count: Int? = null, bufferSize: Int = DEFAULT_BUFFER_SIZE): Result<ByteArray> {
    if (count != null) {
        require(count >= 0) { "Count must be greater or equal to zero. Received: $count" }
    }

    return readFile { stream ->
        count?.let { stream.readNBytes(it) } ?: stream.readBytes()
    }
}

fun <T> Path.readFile(
    serializer: KSerializer<T>,
    jsonSerializer: Json,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
) = readFile(bufferSize) {
    stream -> jsonSerializer.decodeFromStream(serializer, stream)
}

inline fun <reified T> Path.readFile(
    jsonSerializer: Json,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
) = readFile(serializer<T>(), jsonSerializer, bufferSize)


@PublishedApi
internal fun Path.writeFileAtomic(
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    block: (BufferedOutputStream) -> Unit
): Result<Path> {
    parent?.createDirectories()

    val tempFile = resolveSibling("$fileName.tmp")

    return runCatching {
        BufferedOutputStream(tempFile.outputStream(), bufferSize).use {
                stream -> block(stream)
        }

        tempFile.moveTo(
            this,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE
        )
    }.also { tempFile.deleteIfExists() }
}

@PublishedApi
internal fun <T> Path.readFile(
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    block: (BufferedInputStream) -> T
): Result<T> = runCatching {
    BufferedInputStream(inputStream(), bufferSize).use { stream ->
        block(stream)
    }
}


fun Path.freeSpaceAt(): Long = runCatching {
    if (exists()) fileSize() else (parent ?: Path.of("/")).fileSize()
}.getOrDefault(0L)