package com.sappyoak.dsanalyzer.app.data.esd

import com.sappyoak.dsanalyzer.app.data.FieldRow
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.toDataAddress
import com.sappyoak.dsanalyzer.formats.esd.CommandCall
import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.esd.EsdCondition
import com.sappyoak.dsanalyzer.formats.esd.EsdDefinition
import com.sappyoak.dsanalyzer.formats.esd.EsdDefinitions
import com.sappyoak.dsanalyzer.formats.esd.Verdict
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.label

public val EsdSource.title: String
    get() = when (this) {
        EsdSource.Player -> "c0000 (player)"
        is EsdSource.Talk -> "t$talkId · ${map.label}"
    }

/** One state as the state list shows it, under the machine it belongs to */
public data class StateEntry(public val group: Long, public val state: Long, public val exits: Int)

public fun Esd.stateEntries(): List<StateEntry> = groups.values.flatMap { group ->
    group.states.values.sortedBy { it.id }.map { StateEntry(group.id, it.id, it.conditions.size) }
}

/**
 * Commands by name and 'bank[id]', each argument decoded under its name
 * The note says when a name is borrowed from later games or the binary disagrees with it
 */
public fun List<CommandCall>.toRows(definitions: EsdDefinitions): List<FieldRow> = map { command ->
    val definition = definitions.command(command)
    FieldRow(
        name = definitions.labelOf(command),
        value = "${command.args.size} args",
        note = listOfNotNull(command.argumentsUnder(definition, definitions), definition?.provenance())
            .joinToString(" . ")
            .ifEmpty { null }
    )
}

/**
 * Conditions in the order the game tests them, subconditions indented under the condition that
 * holds them. A condition with a target links to that state, which is what makes the machine walkable
 */
public fun List<EsdCondition>.toRows(
    source: EsdSource,
    group: Long,
    definitions: EsdDefinitions,
    depth: Int = 0
): List<FieldRow> =
    flatMap { condition ->
        val target = condition.target
        val row = FieldRow(
            name = "  ".repeat(depth) + (target?.let { "→ state $it" } ?: "no target"),
            value = "${condition.evaluator.size} bytes",
            note = listOfNotNull(
                condition.evaluator.rendered(definitions),
                condition.passCommands.takeIf { it.isNotEmpty() }
                    ?.let { commands -> "then ${commands.joinToString { definitions.labelOf(it) }}" }
            ).joinToString(" · "),
            link = target?.let { View.Data.EsdState(source, group, it).toDataAddress() }
        )
        listOf(row) + condition.subconditions.toRows(source, group, definitions, depth + 1)
    }

private fun EsdDefinitions.labelOf(command: CommandCall): String =
    command(command)?.label?.let { "$it $command" } ?: command.toString()

private fun CommandCall.argumentsUnder(definition: EsdDefinition?, definitions: EsdDefinitions): String? =
    args.mapIndexed { index, arg ->
        val name = definition?.args?.getOrNull(index)?.takeIf { it.isNotBlank() }
        (name?.let { "$it = " } ?: "") + arg.rendered(definitions)
    }.joinToString(" | ").ifEmpty { null }

private fun EsdDefinition.provenance(): String? = listOfNotNull(
    "not called by this edition's own scripts".takeIf { !used },
    reading?.let { read ->
        when (read.verdict) {
            Verdict.Conflicts -> "binary disagrees: ${read.text}"
            Verdict.Agrees -> "binary agrees: ${read.text}"
            Verdict.Unjudged -> "binary: ${read.text}"
        }
    }
).joinToString(" . ").ifEmpty { null }
