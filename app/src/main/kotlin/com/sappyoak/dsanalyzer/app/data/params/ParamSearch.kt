package com.sappyoak.dsanalyzer.app.data.params

import com.sappyoak.dsanalyzer.game.world.params.ParamNames

/** A search is answered with at most this many matches, since a common value like 0 is in most fields */
public const val SEARCH_LIMIT: Int = 500

/** What to look for: [field] narrows by name and may be blank; [value] is matched as the field table shows it */
public data class ParamQuery(public val field: String = "", public val value: String = "") {
    public val runnable: Boolean get() = value.isNotBlank()

    internal fun wantsField(name: String): Boolean = field.isBlank() || name.contains(field.trim(), ignoreCase = true)
    internal fun wantsValue(read: Any): Boolean = formatValue(read) == value.trim()
}

/** One field of one row holding the value searched for */
public data class ParamMatch(
    public val file: String,
    public val rowId: Int,
    public val rowName: String,
    public val field: String,
    public val value: String
)

public data class SearchOutcome(
    public val query: ParamQuery,
    public val matches: List<ParamMatch>,
    /** True when the limit cut the search short, so there are more than [matches] shows */
    public val truncated: Boolean
)

/** Every param in registry order, each searched until the limit is reached across all of them */
public fun searchParams(entries: Collection<ParamEntry>, names: ParamNames, query: ParamQuery): SearchOutcome {
    val matches = ArrayList<ParamMatch>()
    for (entry in entries) {
        val left = SEARCH_LIMIT + 1 - matches.size
        entry.table.search(fields = { query.wantsField(it.name) }, value = query::wantsValue, limit = left)
            .mapTo(matches) { match ->
                val row = entry.table.rows[match.index]
                ParamMatch(
                    file = entry.param.fileName,
                    rowId = row.id,
                    rowName = entry.nameOf(row, names),
                    field = match.field.name,
                    value = formatValue(match.value)
                )
            }
        if (matches.size > SEARCH_LIMIT) break
    }
    return SearchOutcome(query, matches.take(SEARCH_LIMIT), truncated = matches.size > SEARCH_LIMIT)
}
