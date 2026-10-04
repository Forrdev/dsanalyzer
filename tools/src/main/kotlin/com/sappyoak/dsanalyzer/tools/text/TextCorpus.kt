package com.sappyoak.dsanalyzer.tools.text

import kotlinx.serialization.Serializable

/**
 * One term in two languages as the game's own localization pairs them.
 * The id is what makes this a pair as both languages' files index the same entry under the
 * same id
 */
@Serializable
internal data class TermPair(
    val category: String,
    val id: Int,
    val ja: String,
    val en: String
)

@Serializable
internal data class TermCorpus(
    val languages: List<String>,
    val pairs: List<TermPair>
)

@Serializable
internal data class EventNameCorpus(val names: List<EventName>)

@Serializable
internal data class EventName(
    val script: String,
    val eventId: Long,
    val name: String
)