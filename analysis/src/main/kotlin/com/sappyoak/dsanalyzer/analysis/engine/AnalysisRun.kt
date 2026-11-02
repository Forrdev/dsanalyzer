package com.sappyoak.dsanalyzer.analysis.engine

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.util.concurrent.ConcurrentHashMap

private const val DEFAULT_UNIT_CONCURRENCY = 4

/**
 * One run of [analyzers] over [source]
 *
 * Units run concurrently up to a bound. Within a unit, each input is loaded once and shared, levels
 * run in order and a level's analyzers run together. Everything an analyzer can end in is a result. A
 * failure is isolated to its own analyzer and unit, and cancellation is never caught
 */
public class AnalysisRun(
    private val analyzers: List<Analyzer<*>>,
    private val source: GameSource,
    private val values: Map<AnalyzerId, Map<String, Double>> = emptyMap(),
    private val unitConcurrency: Int = DEFAULT_UNIT_CONCURRENCY
) {
    public fun execute(): Flow<RunEvent> = channelFlow {
        val graph = TaskGraph.build(analyzers, source)
        val units = Scope.entries.associateWith { it.unitsOf(source) }
        val results = ConcurrentHashMap<AnalyzerId, MutableMap<RunUnit, AnalysisResult>>()

        suspend fun conclude(analyzer: AnalyzerId, unit: RunUnit, result: AnalysisResult) {
            results.computeIfAbsent(analyzer) { ConcurrentHashMap() }[unit] = result
            send(RunEvent.Concluded(analyzer, unit, result))
        }

        send(RunEvent.Started(analyzers.size, graph.levels.keys.sumOf { units.getValue(it).size }))
        analyzers.filter { it.descriptor.id in graph.unavailable }.forEach { analyzer ->
            val missing = AnalysisResult.Unavailable(graph.unavailable.getValue(analyzer.descriptor.id))
            units.getValue(analyzer.input.scope).forEach { unit ->
                conclude(analyzer.descriptor.id, unit, missing)
            }
        }

        val permits = Semaphore(unitConcurrency)
        coroutineScope {
            graph.levels.forEach { (scope, levels) ->
                units.getValue(scope).forEach { unit ->
                    launch {
                        permits.withPermit {
                            send(RunEvent.UnitStarted(unit))
                            runUnit(
                                unit = unit,
                                levels = levels,
                                resultOf = { id -> results[id]?.get(unit) },
                                conclude = { id, done, result -> conclude(id, done, result) }
                            )
                        }
                    }
                }
            }
        }
        send(RunEvent.Finished(RunReport(results.mapValues { (_, byUnit) -> byUnit.toMap() })))
    }.flowOn(Dispatchers.Default)

    private suspend fun runUnit(
        unit: RunUnit,
        levels: List<List<Analyzer<*>>>,
        resultOf: (AnalyzerId) -> AnalysisResult?,
        conclude: suspend (AnalyzerId, RunUnit, AnalysisResult) -> Unit
    ) = coroutineScope {
        val inputs = ConcurrentHashMap<ContextKey<*>, Deferred<Input>>()

        fun inputOf(key: ContextKey<*>): Deferred<Input> = inputs.computeIfAbsent(key) { async { load(it, unit) } }

        for (level in levels) {
            level.map { analyzer ->
                async {
                    val upstream = analyzer.descriptor.dependsOn.mapNotNull { id -> resultOf(id)?.let { id to it } }
                    analyzer.descriptor.id to analyzer.runOn(unit, inputOf(analyzer.input).await(), upstream.toMap())
                }
            }.awaitAll().forEach { (id, result) -> conclude(id, unit, result) }
        }
    }

    private suspend fun load(key: ContextKey<*>, unit: RunUnit): Input = try {
        Input.Read(key.load(source, unit))
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (err: Throwable) {
        Input.Unreadable(err.message ?: err::class.simpleName.orEmpty())
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T : Any> Analyzer<T>.runOn(
        unit: RunUnit,
        input: Input,
        upstream: Map<AnalyzerId, AnalysisResult>
    ): AnalysisResult {
        val name = this.input.name
        val data = when (input) {
            is Input.Unreadable -> return AnalysisResult.Failed("$name could not be read: ${input.message}")
            is Input.Read -> input.value as T? ?: return AnalysisResult.NotApplicable("No $name for ${unit.label}")
        }

        val run = RunInfo(source.build, values[descriptor.id].orEmpty(), upstream)
        return try {
            analyze(AnalysisContext(data, unit, run))
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (err: Throwable) {
            AnalysisResult.Failed(err.message ?: err::class.simpleName.orEmpty())
        }
    }

    private sealed interface Input {
        class Read(val value: Any?) : Input
        class Unreadable(val message: String) : Input
    }
}
