package com.sappyoak.dsanalyzer.app.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.awt.datatransfer.StringSelection

import com.sappyoak.dsanalyzer.app.nav.Navigator
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.nav.toDataAddress
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme


private val STEP_SIZE = 24.dp
private val RADIUS = 5.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun DetailBar(navigator: Navigator) {
    val colors = AnalyzerTheme.colors
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    val address = (navigator.current as? View.Data)?.toDataAddress()?.toAddress()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.chrome)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Step("←", enabled = navigator.history.canGoBack, onClick = navigator::back)
        Step("→", enabled = navigator.history.canGoForward, onClick = navigator::forward)

        Text(
            text = address ?: navigator.current.describe(),
            style = AnalyzerTheme.text.monoSmall,
            color = if (address == null) colors.textLo else colors.textMid,
            maxLines = 1
        )

        Spacer(modifier = Modifier.weight(1f))

        if (address != null) {
            Pressable(onClick = {
                scope.launch {
                    val entry = ClipEntry(StringSelection(address))
                    clipboard.setClipEntry(entry)
                }
            }) {
                Text("Copy address", style = AnalyzerTheme.text.label, color = colors.textMid)
            }
        }
    }
    HorizontalDivider(color = colors.border)
}


@Composable
private fun Step(glyph: String, enabled: Boolean, onClick: () -> Unit) {
    val colors = AnalyzerTheme.colors

    Box(
        modifier = Modifier
            .size(STEP_SIZE)
            .clip(RoundedCornerShape(RADIUS))
            .background(if (enabled) colors.raised else colors.panel)
            .border(1.dp, colors.border, RoundedCornerShape(RADIUS))
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = glyph,
            style = AnalyzerTheme.text.monoSmall,
            color = if (enabled) colors.textHi else colors.border
        )
    }
}

@Composable
private fun Pressable(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(RADIUS))
            .background(AnalyzerTheme.colors.raised)
            .border(1.dp, AnalyzerTheme.colors.border, RoundedCornerShape(RADIUS))
            .clickable(onClick = onClick)
            .padding(horizontal = 11.dp, vertical = 5.dp)
    ) {
        content()
    }
}


private fun View.describe(): String = when (this) {
    is View.Data.Overview -> "no selection"
    is View.Data -> "no address"
    is View.Live -> "runtime · ${panel.label.lowercase()}"
    is View.Analysis.Leads -> "analysis run"
    is View.Analysis.Investigations -> "investigations"
    is View.Analysis.Investigation -> "investigation ${id.value}"
    is View.Analysis.Finding -> "finding ${id.value}"
}
