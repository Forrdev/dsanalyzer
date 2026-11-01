package com.sappyoak.dsanalyzer.app.data.maps.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.shownMap
import com.sappyoak.dsanalyzer.app.data.maps.MapsState
import com.sappyoak.dsanalyzer.app.data.maps.MapsStore
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.game.ref.Address

private val ENTRY_LIST_WIDTH = 320.dp

@Composable
public fun MapsDetail(
    state: MapsState,
    store: MapsStore,
    view: View.Data,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    val focused = view as? View.Data.MapEntry

    Row(modifier = Modifier.fillMaxSize()) {
        if (view.shownMap != null) {
            Column(
                modifier = Modifier
                    .width(ENTRY_LIST_WIDTH)
                    .fillMaxHeight()
                    .background(AnalyzerTheme.colors.panel)
            ) {
                EntryFilters(state, store)
                HorizontalDivider(color = AnalyzerTheme.colors.border)
                EntryList(
                    entries = state.visible,
                    focused = focused,
                    loading = state.loading,
                    onSelect = onFollow
                )
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(AnalyzerTheme.colors.border)
            )
        }

        InspectorPane(
            state = state,
            focused = focused,
            resolve = resolve,
            onFollow = onFollow
        )
    }
}
