package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.runtime.layout.Stat
import com.sappyoak.dsanalyzer.runtime.session.AttributeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot

/** A section title inside a panel's own padding */
@Composable
internal fun Section(title: String) {
    Text(title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = 8.dp, bottom = 1.dp))
}

/** Health, stamina and MP against their effective and base maxima */
@Composable
internal fun Vitals(player: PlayerSnapshot) {
    val sheet = player.attributes
    Field("Health", player.health.of(sheet?.healthMax, sheet?.healthBaseMax))
    Field("Stamina", player.stamina.of(sheet?.staminaMax, sheet?.staminaBaseMax))
    sheet?.let { Field("MP", it.mp.of(it.mpMax, null)) }
}

@Composable
internal fun CharacterAttributes(sheet: AttributeSnapshot) {
    Section("Character")
    Field("Soul level", sheet.soulLevel.toString())
    Field("Souls", "${sheet.souls} (soul memory ${sheet.soulMemory})")
    Stat.entries.forEach { stat -> Field(stat.name, sheet.stats[stat]?.toString() ?: "-") }
    Field("Humanity", sheet.humanity.toString())
    Field("Durability", sheet.durability.toString())
    Field("Covenant", sheet.covenant?.toString() ?: "not read for this edition")
    Field("Stance", sheet.stance?.describeStance() ?: "not read for this edition")

    Section("Identity")
    Field("Starting class", sheet.startingClass.toString())
    Field("Gift", sheet.gift.toString())
    Field("Physique", sheet.physique.toString())
    Field("Gender", sheet.gender.toString())

    Section("Resistances")
    with(sheet.resists) {
        Field("Poison", poison.toString())
        Field("Bleed", bleed.toString())
        Field("Toxic", toxic.toString())
        Field("Curse", curse.toString())
    }
}

private fun Int.of(max: Int?, base: Int?): String = buildString {
    append(this@of)
    max?.let { append(" / $it") }
    base?.takeIf { it != max }?.let { append(" (base $it)") }
}

private fun Int.describeStance(): String = when (this) {
    1 -> "one-handed"
    2 -> "two-handing left"
    3 -> "two-handing right"
    else -> toString()
}
