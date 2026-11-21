package com.sappyoak.dsanalyzer.app.data.scripts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.esd.title
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.view
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.world.flags.FlagSource
import com.sappyoak.dsanalyzer.game.world.flags.FlagSite
import com.sappyoak.dsanalyzer.game.world.maps.label

private val ACCESS_WIDTH = 64.dp

/** The kinds of place a flag is used outside the event scripts, in the order the pane lists them */
private enum class UseGroup(val title: String) {
    Params("Params"),
    Maps("Map objects"),
    Talk("Talk scripts"),
    Lua("Lua scripts"),
    Engine("Engine")
}

/** Every use of a flag outside the event scripts, under a heading per kind of place, each linked to it */
internal fun LazyListScope.flagSites(sites: List<FlagSite>, onFollow: (View) -> Unit) {
    sites.groupBy { it.source.group() }.toSortedMap().forEach { (group, members) ->
        item(key = "heading:$group") { Heading(group.title) }
        items(members) { use ->
            val view = use.source.view()
            AccessRow(use.access, "${use.label} · ${use.source.where()}", view?.let { { onFollow(it) } })
        }
    }
}

@Composable
internal fun Heading(title: String) {
    Text(
        text = title,
        style = AnalyzerTheme.text.label,
        color = AnalyzerTheme.colors.textLo,
        modifier = Modifier.padding(start = 12.dp, top = 8.dp)
    )
}

/** Whether a place sets or reads the flag, then what it is, as a link when there is a view to open */
@Composable
internal fun AccessRow(access: FlagAccess, text: String, onFollow: (() -> Unit)?) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (access == FlagAccess.Writes) "sets" else "reads",
            style = AnalyzerTheme.text.label,
            color = if (access == FlagAccess.Writes) AnalyzerTheme.colors.pending else AnalyzerTheme.colors.textLo,
            modifier = Modifier.width(ACCESS_WIDTH)
        )
        if (onFollow == null) {
            Text(text, style = MaterialTheme.typography.bodySmall)
        } else {
            TextButton(onClick = onFollow, contentPadding = PaddingValues(0.dp)) {
                Text(text, style = MaterialTheme.typography.bodySmall, color = AnalyzerTheme.colors.link)
            }
        }
    }
}

/** The view that shows a place that uses a flag; the executable's code has none */
internal fun FlagSource.view(): View.Data? = when (this) {
    is FlagSource.World -> ref.view()
    is FlagSource.Talk -> View.Data.EsdState(source, group, state)
    is FlagSource.Lua -> View.Data.LuaScript(script, function)
    is FlagSource.Engine -> null
}

private fun FlagSource.group(): UseGroup = when (this) {
    is FlagSource.World -> if (ref is WorldRef.ParamRow) UseGroup.Params else UseGroup.Maps
    is FlagSource.Talk -> UseGroup.Talk
    is FlagSource.Lua -> UseGroup.Lua
    is FlagSource.Engine -> UseGroup.Engine
}

private fun FlagSource.where(): String = when (this) {
    is FlagSource.World -> when (val ref = ref) {
        is WorldRef.ParamRow -> "${ref.file} ${ref.rowId}"
        is WorldRef.Entry -> "${ref.map.label} · ${ref.kind.name.lowercase()} ${ref.index}"
        else -> ref.toString()
    }
    is FlagSource.Talk -> "${source.title} · state $group/$state"
    is FlagSource.Lua -> listOfNotNull(script.toString(), function).joinToString(" · ")
    is FlagSource.Engine -> "$function at 0x%08X in Prepare to Die".format(address)
}
