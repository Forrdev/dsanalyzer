package com.sappyoak.dsanalyzer.app.data.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.sappyoak.dsanalyzer.app.data.DataKind
import com.sappyoak.dsanalyzer.app.data.ai.ui.aiChildren
import com.sappyoak.dsanalyzer.app.data.esd.title
import com.sappyoak.dsanalyzer.app.data.kind
import com.sappyoak.dsanalyzer.app.data.shownAnimationSet
import com.sappyoak.dsanalyzer.app.data.shownEsd
import com.sappyoak.dsanalyzer.app.data.shownFlagGroup
import com.sappyoak.dsanalyzer.app.data.shownMap
import com.sappyoak.dsanalyzer.app.data.shownParam
import com.sappyoak.dsanalyzer.app.data.shownScript
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.world.maps.label
import com.sappyoak.dsanalyzer.game.world.scripts.title

private val TRACKING = 0.8.sp

@Composable
public fun ColumnScope.DataNavigator(contents: NavigatorContents, view: View.Data, onFollow: (View) -> Unit) {
    var expanded by remember { mutableStateOf(view.kind) }

    LaunchedEffect(view) {
        view.kind?.let { expanded = it }
    }

    Text(
        text = "DATA",
        style = AnalyzerTheme.text.label.copy(letterSpacing = TRACKING),
        color = AnalyzerTheme.colors.textLo,
        modifier = Modifier.fillMaxWidth().padding(start = KIND_INDENT, top = 14.dp, bottom = 10.dp)
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        DataKind.entries.forEach { kind ->
            val open = kind == expanded

            item(key = kind.name) {
                KindRow(
                    kind = kind,
                    count = contents.countOf(kind),
                    loading = contents.loading(kind),
                    open = open,
                    onClick = { expanded = if (open) null else kind }
                )
            }

            if (open) children(kind, contents, view, onFollow)
        }
    }
}

private fun LazyListScope.children(
    kind: DataKind,
    contents: NavigatorContents,
    view: View.Data,
    onFollow: (View) -> Unit
) {
    val (params, esds, maps, scripts, flags, _, animations) = contents
    when (kind) {
        DataKind.Params -> {
            item(key = "param:search") {
                ChildRow(
                    label = "Search all params",
                    detail = null,
                    selected = view == View.Data.ParamSearch,
                    onClick = { onFollow(View.Data.ParamSearch) }
                )
            }
            items(params, key = { "param:${it.file}" }) { param ->
                ChildRow(
                    label = param.file,
                    detail = param.rows.toString(),
                    selected = view.shownParam == param.file,
                    onClick = { onFollow(View.Data.Param(param.file)) }
                )
            }
        }

        DataKind.Esd -> items(esds, key = { "esd:$it" }) { source ->
            ChildRow(
                label = source.title,
                detail = null,
                selected = view.shownEsd == source,
                onClick = { onFollow(View.Data.Esd(source)) }
            )
        }

        DataKind.Maps -> items(maps, key = { "map:$it" }) { map ->
            ChildRow(
                label = map.label,
                detail = map.name,
                selected = view.shownMap == map,
                onClick = { onFollow(View.Data.Map(map)) }
            )
        }

        DataKind.Scripts -> items(scripts, key = { "script:$it" }) { script ->
            ChildRow(
                label = script.title,
                detail = null,
                selected = view.shownScript == script,
                onClick = { onFollow(View.Data.Script(script)) }
            )
        }

        DataKind.EventFlags ->
            if (flags.size == 0) note(kind, "Listed once the scripts are indexed")
            else items(flags.groups, key = { "flags:$it" }) { group ->
                ChildRow(
                    label = group.label,
                    detail = flags[group].size.toString(),
                    selected = view.shownFlagGroup == group,
                    onClick = { onFollow(View.Data.FlagList(group)) }
                )
            }

        DataKind.Animations ->
            if (animations.sets.isEmpty()) note(kind, "Listed once the installation is read")
            else items(animations.sets, key = { "animations:$it" }) { set ->
                ChildRow(
                    label = animations.title(set),
                    detail = null,
                    selected = view.shownAnimationSet == set,
                    onClick = { onFollow(View.Data.AnimationSet(set)) }
                )
            }

        DataKind.AiScripts ->
            if (contents.ai.binders.isEmpty()) note(kind, "Listed once the installation is read")
            else aiChildren(contents.ai, view, onFollow)
    }
}

internal fun LazyListScope.note(kind: DataKind, text: String) {
    item(key = "${kind.name}:none") {
        Text(
            text = text,
            style = AnalyzerTheme.text.label,
            color = AnalyzerTheme.colors.textLo,
            modifier = Modifier.padding(start = CHILD_INDENT, top = 4.dp, bottom = 6.dp)
        )
    }
}
