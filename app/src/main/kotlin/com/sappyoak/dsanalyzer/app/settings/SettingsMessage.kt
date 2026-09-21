package com.sappyoak.dsanalyzer.app.settings

import java.nio.file.Path

public sealed interface SettingsMessage {
    public data object Load : SettingsMessage

    public data class Loaded(
        public val settings: Settings,
        public val recoveredFrom: Path? = null
    ) : SettingsMessage

    public data class LoadFailed(public val detail: String) : SettingsMessage
    public data class Edited(public val edit: SettingsEdit) : SettingsMessage
    public data class SaveFailed(public val detail: String) : SettingsMessage

    public data object ProblemDismissed : SettingsMessage
}