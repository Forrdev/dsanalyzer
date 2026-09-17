package com.sappyoak.dsanalyzer.shared.binary

public open class BinaryFormatException(
    message: String,
    public val position: Long,
    cause: Throwable? = null
) : RuntimeException("$message (at 0x${position.toString(16)}", cause) {
    constructor(message: String, position: Int, cause: Throwable? = null) : this(message, position.toLong(), cause)
}