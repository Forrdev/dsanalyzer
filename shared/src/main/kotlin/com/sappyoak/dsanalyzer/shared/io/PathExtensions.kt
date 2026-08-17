package com.sappyoak.dsanalyzer.shared.io

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.*

const val DEFAULT_BUFFER_SIZE = 8192

inline fun <T> Path.readFile(
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    block: (BufferedInputStream) -> T
): Result<T> = runCatching {
    BufferedInputStream(inputStream(), bufferSize).use { stream ->
        block(stream)
    }
}

fun Path.readBytes(count: Int? = null, bufferSize: Int = DEFAULT_BUFFER_SIZE): Result<ByteArray> {
    if (count != null) {
        require(count > 0) { "Must specify greater than 0 bytes to read" }
    }

    return readFile { stream ->
        count?.let { stream.readNBytes(it) } ?: stream.readBytes()
    }
}

inline fun Path.writeFile(
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    block: (BufferedOutputStream) -> Unit
): Result<Unit> {
    parent?.createDirectories()
    return runCatching {
        BufferedOutputStream(outputStream(), bufferSize).use { stream ->
            block(stream)
        }
    }
}

fun Path.writeBytes(bytes: ByteArray, bufferSize: Int = DEFAULT_BUFFER_SIZE) = writeFile(bufferSize) {
    stream -> stream.write(bytes)
}

inline fun Path.writeFileAtomic(
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

fun Path.writeBytesAtomic(bytes: ByteArray, bufferSize: Int = DEFAULT_BUFFER_SIZE) = writeFileAtomic(bufferSize) {
    stream -> stream.write(bytes)
}

fun Path.deleteTree(): Result<Unit> = runCatching {
    if (!exists()) return@runCatching

    val (directories, files) = walk(PathWalkOption.INCLUDE_DIRECTORIES)
        .partition { it.isDirectory() }

    // sort and delete files first
    files.sortedBy { it.getLastModifiedTime().toMillis() }.forEach { file ->
        file.deleteIfExists()
    }

    // Sort and delete directories, sorting by path depth so children are deleted first
    directories.sortedByDescending { it.nameCount }.forEach { dir ->
        dir.deleteIfExists()
    }
}

fun Path.sizeOfDirectory(): Long {
    if (!exists()) return 0L
    return toFile().walkTopDown()
        .filter { it.isFile }
        .map { it.length() }
        .sum()
}

fun Path.freeSpaceOnDisk(): Long = runCatching {
    toFile().let { if (it.exists()) it.usableSpace else File(it.parent ?: "/").usableSpace }
}.getOrDefault(0L)

fun Path.isWithin(root: Path): Boolean =
    canonical.startsWith(root.canonical)

val Path.canonical: Path get() = toAbsolutePath().normalize()

fun String.toPath(): Path = Path.of(this)
fun String.toPathOrNull(): Path? = runCatching { toPath() }.getOrNull()

fun String?.toPath(): Path? = this?.takeIf { it.isNotBlank() }?.toPathOrNull()