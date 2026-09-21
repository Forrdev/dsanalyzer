package com.sappyoak.dsanalyzer.app.settings

import java.nio.file.Path

public data class SettingsState(
    public val settings: Settings = Settings(),
    public val loaded: Boolean = false,
    public val problem: SettingsProblem? = null
)

/** A failure reading or writing settings */
public sealed interface SettingsProblem {
    public data class Recovered(public val backup: Path) : SettingsProblem
    public data class Unreadable(public val detail: String) : SettingsProblem
    public data class NotSaved(public val detail: String) : SettingsProblem
}