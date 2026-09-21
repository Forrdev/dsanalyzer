package com.sappyoak.dsanalyzer.app.settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.sappyoak.dsanalyzer.app.store.EffectRunner

private const val SAVE_KEY = "save"

public class SettingsEffects(
    private val file: SettingsFile
) : EffectRunner<SettingsEffect, SettingsMessage> {
    override fun keyOf(effect: SettingsEffect): Any? =
        if (effect is SettingsEffect.Save) SAVE_KEY else null

    override fun execute(effect: SettingsEffect): Flow<SettingsMessage> = flow {
        when (effect) {
            SettingsEffect.Load -> file.read().let { emit(SettingsMessage.Loaded(it.settings, it.recoveredFrom)) }
            is SettingsEffect.Save -> file.write(effect.settings)
        }
    }

    override fun onFailure(effect: SettingsEffect, failure: Throwable): SettingsMessage {
        val detail = failure.message ?: failure.toString()
        return when (effect) {
            SettingsEffect.Load -> SettingsMessage.LoadFailed(detail)
            is SettingsEffect.Save -> SettingsMessage.SaveFailed(detail)
        }
    }

}
public sealed interface SettingsEffect {
    public data object Load : SettingsEffect
    public data class Save(public val settings: Settings) : SettingsEffect
}