package com.sappyoak.dsanalyzer.native.process

public class ProcessAccessException(
    message: String,
    public val nativeCode: Int? = null
) : RuntimeException(message)