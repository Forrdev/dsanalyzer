package com.sappyoak.dsanalyzer.app.data.params

import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.toDataAddress
import com.sappyoak.dsanalyzer.formats.param.ParamRow
import com.sappyoak.dsanalyzer.formats.param.TypedParam
import com.sappyoak.dsanalyzer.formats.param.refs.references
import com.sappyoak.dsanalyzer.game.ref.Address
import com.sappyoak.dsanalyzer.game.world.params.GameParam
import com.sappyoak.dsanalyzer.game.world.params.ParamNames
import com.sappyoak.dsanalyzer.game.world.params.paramFor
import com.sappyoak.dsanalyzer.game.world.params.side

public data class ParamSummary(
    public val file: String,
    public val rows: Int
)

/** One row as the row list shows it. [index] is its place in file order */
public data class RowSummary(
    public val index: Int,
    public val id: Int,
    public val name: String
) {
    public fun matches(query: String): Boolean {
        val wanted = query.trim()
        return wanted.isEmpty() || id.toString().contains(wanted) || name.contains(wanted, ignoreCase = true)
    }
}

/** The game's own text for the row where it has one, else the name the param file stores */
public fun ParamEntry.nameOf(row: ParamRow, names: ParamNames): String = names.nameOf(param, row.id) ?: row.name

public fun ParamEntry.summaries(names: ParamNames): List<RowSummary> =
    table.rows.mapIndexed { index, row -> RowSummary(index, row.id, nameOf(row, names)) }

/** The row a view of [id] opens: the first in file order, since which one the game finds is unconfirmed */
public fun TypedParam<*>.indexOf(id: Int): Int? = rows.indexOfFirst { it.id == id }.takeIf { it >= 0 }

/** Each field of [row] that names another row, by property name, as the address of the row it names */
public fun ParamEntry.linksOf(row: ParamRow): Map<String, Address> = row.references.mapValues { (_, ref) ->
    checkNotNull(View.Data.ParamRow(ref.kind.paramFor(param.side).fileName, ref.id).toDataAddress())
}

public fun paramAddress(param: GameParam<*>, id: Int): Address =
    checkNotNull(View.Data.ParamRow(param.fileName, id).toDataAddress())
