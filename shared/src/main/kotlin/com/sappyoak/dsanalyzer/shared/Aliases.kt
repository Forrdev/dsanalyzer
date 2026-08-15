package com.sappyoak.dsanalyzer.shared

import kotlinx.serialization.Serializable
import java.nio.file.Path
import kotlin.time.Instant

import com.sappyoak.dsanalyzer.shared.serialization.*

typealias SPath = @Serializable(with = PathSerializer::class) Path
typealias SNullablePath = @Serializable(with = NullablePathSerializer::class) Path
typealias SInstant = @Serializable(with = InstantComponentSerializer::class) Instant