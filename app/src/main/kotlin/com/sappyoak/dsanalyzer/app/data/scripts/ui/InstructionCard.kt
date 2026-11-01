package com.sappyoak.dsanalyzer.app.data.scripts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.FieldRow
import com.sappyoak.dsanalyzer.app.data.scripts.ArgumentLine
import com.sappyoak.dsanalyzer.app.data.scripts.ArgumentValue
import com.sappyoak.dsanalyzer.app.data.scripts.argumentLines
import com.sappyoak.dsanalyzer.app.data.scripts.displayName
import com.sappyoak.dsanalyzer.app.data.scripts.opcode
import com.sappyoak.dsanalyzer.app.data.scripts.summary
import com.sappyoak.dsanalyzer.app.data.ui.FieldTable
import com.sappyoak.dsanalyzer.app.nav.Resolution
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.game.ref.Address

private val CARD_RADIUS = 7.dp
private val INDEX_WIDTH = 28.dp

@Composable
internal fun InstructionCard(
    index: Int,
    instruction: DecodedInstruction,
    definitions: Emedf,
    resolve: (Address) -> Resolution,
    onFollow: (View) -> Unit
) {
    val colors = AnalyzerTheme.colors
    val undefined = instruction.definition == null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CARD_RADIUS))
            .border(1.dp, colors.border, RoundedCornerShape(CARD_RADIUS))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.raised)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = index.toString(),
                style = AnalyzerTheme.text.mono,
                color = colors.textLo,
                textAlign = TextAlign.End,
                modifier = Modifier.width(INDEX_WIDTH)
            )
            Text(
                text = instruction.displayName,
                style = MaterialTheme.typography.titleSmall,
                color = if (undefined) colors.textLo else colors.textHi
            )

            if (instruction.sizeMismatch) {
                Badge("extra bytes")
            }

            Text(
                text = instruction.opcode,
                style = AnalyzerTheme.text.monoSmall,
                color = colors.textLo,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }

        instruction.summary()?.let { summary ->
            Text(
                text = summary,
                style = AnalyzerTheme.text.label,
                color = colors.textLo,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.chrome)
                    .padding(horizontal = 14.dp, vertical = 7.dp)
            )
        }

        HorizontalDivider(color = colors.border)

        FieldTable(
            rows = instruction.argumentLines(definitions).map { it.asField() },
            resolve = resolve,
            onFollow = onFollow
        )
    }
}

private const val BADGE_TINT = 0.14f

@Composable
private fun Badge(text: String) {
    Text(
        text = text,
        style = AnalyzerTheme.text.label,
        color = AnalyzerTheme.colors.pending,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(AnalyzerTheme.colors.pending.copy(alpha = BADGE_TINT))
            .padding(horizontal = 6.dp, vertical = 1.dp)
    )
}


internal fun ArgumentLine.asField(): FieldRow = when (val argument = value) {
    is ArgumentValue.Literal ->
        FieldRow(label, argument.shown, argument.meaning, link)

    is ArgumentValue.Default ->
        FieldRow(label, argument.shown, "definition default", link, stated = false)

    is ArgumentValue.FromCaller ->
        FieldRow(label, "—", "from the caller, byte ${argument.byte}", null, stated = false)

    is ArgumentValue.Raw ->
        FieldRow(label, argument.bytes, "no definition to decode it", null, stated = false)
}
