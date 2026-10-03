package com.sappyoak.dsanalyzer.game

import java.io.BufferedReader

private object Anchor

internal fun <T> bundled(resource: String, read: (BufferedReader) -> T): T {
    val stream = checkNotNull(Anchor::class.java.getResourceAsStream(resource)) {
        "Bundled resource $resource is missing"
    }

    return stream.bufferedReader().use(read)
}

internal fun bundledText(resource: String): String =
    bundled(resource, BufferedReader::readText)