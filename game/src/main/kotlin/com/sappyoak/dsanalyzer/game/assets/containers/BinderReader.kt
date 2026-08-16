package com.sappyoak.dsanalyzer.game.assets.containers


fun ByteArray.readBinder(): Map<String, ByteArray>? {
    val raw = DCXProcessor.process(this).bytesOrNull ?: this
    return when {
        raw.isBND3Archive() ->
            (BND3ArchiveReader.read(raw) as? BND3ArchiveReader.Outcome.Success)?.archive?.asMap()
        raw.isBND4Archive() ->
            (BND4ArchiveReader.read(raw) as? BND4ArchiveReader.Outcome.Success)?.archive?.asMap()
        else -> null
    }
}

fun ByteArray.binderGenerationOf(): String? {
    val raw = DCXProcessor.process(this).bytesOrNull ?: this
    return when {
        raw.isBND4Archive() -> BND4ArchiveReader.MAGIC_STR
        raw.isBND3Archive() -> BND3ArchiveReader.MAGIC_STR
        else -> null
    }
}
