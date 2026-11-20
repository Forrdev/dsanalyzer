package com.sappyoak.dsanalyzer.app.data.animations.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.animations.AnimationsState
import com.sappyoak.dsanalyzer.app.data.animations.SetAnimations
import com.sappyoak.dsanalyzer.app.data.shownAnimationSet
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private val LIST_WIDTH = 240.dp

/** One set's animation ids beside the events of the animation picked from them */
@Composable
public fun AnimationsPane(state: AnimationsState, view: View.Data, onFollow: (View) -> Unit) {
    val set = view.shownAnimationSet ?: return
    val animations = state.animations?.takeIf { it.set == set }

    when {
        state.problem != null -> Message(state.problem)
        animations == null -> Message("Reading ${set.label}…")
        else -> Row(modifier = Modifier.fillMaxSize()) {
            AnimationIdList(
                title = state.title(set),
                animations = animations,
                selected = (view as? View.Data.Animation)?.animationId,
                onFollow = onFollow,
                modifier = Modifier.width(LIST_WIDTH).fillMaxHeight()
            )
            VerticalDivider(color = AnalyzerTheme.colors.border)
            Detail(animations, view, onFollow)
        }
    }
}

@Composable
private fun Detail(animations: SetAnimations, view: View.Data, onFollow: (View) -> Unit) {
    val animationId = (view as? View.Data.Animation)?.animationId ?: return Message("Pick an animation")
    val events = animations.events(animationId)
        ?: return Message("${animations.set.label} has no animation $animationId")
    AnimationEvents(animations, animationId, events, onFollow)
}

@Composable
private fun Message(text: String) {
    Box(modifier = Modifier.padding(16.dp)) {
        Text(text, style = MaterialTheme.typography.bodyMedium, color = AnalyzerTheme.colors.textLo)
    }
}
