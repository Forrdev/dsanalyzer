package com.sappyoak.dsanalyzer.app.runtime.timing

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot

internal sealed interface TimingRequest {
    data class ForMap(val map: MapId, val known: Set<Int>) : TimingRequest
    data class ForModels(val models: Set<Int>) : TimingRequest
}

/**
 * The attack windows read so far, kept by model across maps, and what has been asked for
 */
public data class TimingLibrary(
    public val wanted: Boolean = false,
    public val models: Map<Int, ModelTimings> = emptyMap(),
    public val requestedModels: Set<Int> = emptySet(),
    public val requestedMaps: Set<MapId> = emptySet(),
    public val loadingMaps: Set<MapId> = emptySet()
) {
    public operator fun get(model: Int): ModelTimings? = models[model]

    internal fun loaded(found: Map<Int, ModelTimings>, map: MapId?): TimingLibrary =
        copy(models = models + found, loadingMaps = if (map == null) loadingMaps else loadingMaps - map)
}

internal fun TimingLibrary.requestsFor(snapshot: RuntimeSnapshot?): Pair<TimingLibrary, List<TimingRequest>> {
    if (!wanted || snapshot == null) return this to emptyList()

    val requests = ArrayList<TimingRequest>(2)
    var next = this

    val map = snapshot.map
    if (snapshot.inWorld && map != null && map !in requestedMaps) {
        requests.add(TimingRequest.ForMap(map, models.keys + requestedModels))
        next = next.copy(requestedMaps = requestedMaps + map, loadingMaps = loadingMaps + map)
    }

    if (next.loadingMaps.isNotEmpty()) return next to requests

    val missing = snapshot.characters
        .filter { it.ai != null && it.modelId != HUMAN_MODEL }
        .map { it.modelId }
        .filterNot { it in models || it in requestedModels }
        .toSet()
    if (missing.isNotEmpty()) {
        requests.add(TimingRequest.ForModels(missing))
        next = next.copy(requestedModels = next.requestedModels + missing)
    }

    return next to requests
}
