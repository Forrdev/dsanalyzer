package com.sappyoak.dsanalyzer.formats.esd

/**
 * Every program in the file, state by state. Each condition's evaluator and its pass commands'
 * arguments, nested conditions included, then the entry, while, and exit commands' arguments
 */
public fun Esd.programs(): Sequence<Bytecode> = sequence {
    for (group in groups.values) {
        for (state in group.states.values) {
            state.conditions.forEach { yieldAll(it.programs()) }
            (state.entryCommands + state.whileCommands + state.entryCommands).forEach { yieldAll(it.args) }
        }
    }
}

private fun EsdCondition.programs(): Sequence<Bytecode> = sequence {
    yield(evaluator)
    passCommands.forEach { yieldAll(it.args) }
    subconditions.forEach { yieldAll(it.programs()) }
}
