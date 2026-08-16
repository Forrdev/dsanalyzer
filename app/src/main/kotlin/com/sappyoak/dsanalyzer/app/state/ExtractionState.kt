package com.sappyoak.dsanalyzer.app.state

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class ExtractionState {
    @Serializable
    data object NotStarted : ExtractionState()

    @Serializable
    data class Running(val current: String, val done: Int, val total: Int) : ExtractionState() {
        val fraction: Float get() = if (total == 0) 0f else done.toFloat() / total
    }

    @Serializable
    data class Complete(val filesWritten: Int, val missed: Int) : ExtractionState()

    @Serializable
    data class Failed(val reason: String, @Transient val error: Throwable? = null) : ExtractionState()
}