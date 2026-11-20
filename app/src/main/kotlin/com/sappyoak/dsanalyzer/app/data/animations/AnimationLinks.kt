package com.sappyoak.dsanalyzer.app.data.animations

import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.formats.tae.template.DecodedParam
import com.sappyoak.dsanalyzer.formats.tae.template.ParamValue

private const val SP_EFFECT_PARAM = "SpEffectParam"

/** Template parameter names whose value is a SpEffectParam row */
private val SP_EFFECT_IDS = setOf("SpEffectID", "RequiredSpEffectID")

/** Template parameter names whose value is another animation of the same set */
private val ANIMATION_IDS = setOf("AnimID", "AnimationID", "AimUpAnimID", "AimDownAnimID")

/** Where [param]'s value leads, when it names a row or an animation that exists */
public fun SetAnimations.linkOf(param: DecodedParam): View? {
    val raw = (param.value as? ParamValue.Number)?.raw?.takeIf { it > 0 } ?: return null
    return when (param.definition.name) {
        in SP_EFFECT_IDS -> View.Data.ParamRow(SP_EFFECT_PARAM, raw.toInt())
        in ANIMATION_IDS -> if (raw in this) View.Data.Animation(set, raw) else null
        else -> null
    }
}
