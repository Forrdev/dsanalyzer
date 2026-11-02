package com.sappyoak.dsanalyzer.analysis.engine

/**
 * The task-ordering for each scope, as a set of levels rather than a scheduler.
 * Everything in a level depends only on earlier levels and levels run in sequence
 * and its analyzers run together. Most things should be level zero, so the barrier costs little.
 * Within a level analyzers are sorted by id, because cached results are keyed on
 * content and a run that orders differently would produce different bytes for the same key
 */
public class TaskGraph private constructor(
    public val levels: Map<Scope, List<List<Analyzer<*>>>>,
    public val unavailable: Map<AnalyzerId, Set<Requirement>>
) {
    public companion object {
        public fun build(
            analyzers: List<Analyzer<*>>,
            source: GameSource
        ): TaskGraph {
            val byId = analyzers.associateBy { it.descriptor.id }
            require(byId.size == analyzers.size) { "Two analyzers share an id" }
            analyzers.forEach { analyzer ->
                checkDependencies(analyzer, byId)
            }

            val unavailable = analyzers.associate { analyzer ->
                analyzer.descriptor.id to analyzer.input.requires.filterTo(mutableSetOf(), source::lacks)
            }.filterValues { it.isNotEmpty() }

            val runnable = analyzers.filter { it.descriptor.id !in unavailable }
            return TaskGraph(
                levels = runnable.groupBy { it.input.scope }.mapValues { (_, scoped) -> levelsOf(scoped) },
                unavailable = unavailable
            )
        }

        private fun checkDependencies(
            analyzer: Analyzer<*>,
            byId: Map<AnalyzerId, Analyzer<*>>
        ) {
            analyzer.descriptor.dependsOn.forEach { id ->
                val upstream = requireNotNull(byId[id]) { "${analyzer.descriptor.id} depends on unregistered $id" }
                require(upstream.input.scope == analyzer.input.scope) {
                    "${analyzer.descriptor.id} depends on $id, which runs at another scope"
                }
            }
        }

        private fun levelsOf(analyzers: List<Analyzer<*>>): List<List<Analyzer<*>>> {
            val present = analyzers.mapTo(mutableSetOf()) { it.descriptor.id }
            val remaining = analyzers.toMutableList()
            val done = mutableSetOf<AnalyzerId>()
            val levels = mutableListOf<List<Analyzer<*>>>()

            while (remaining.isNotEmpty()) {
                val ready = remaining.filter { analyzer ->
                    done.containsAll(analyzer.descriptor.dependsOn.filter { it in present })
                }
                check(ready.isNotEmpty()) {
                    "Analyzer dependency cycle among ${remaining.joinToString { it.descriptor.id.value }}"
                }

                levels.add(ready.sortedBy { it.descriptor.id.value })
                ready.forEach { done.add(it.descriptor.id) }
                remaining -= ready.toSet()
            }

            return levels
        }
    }
}
