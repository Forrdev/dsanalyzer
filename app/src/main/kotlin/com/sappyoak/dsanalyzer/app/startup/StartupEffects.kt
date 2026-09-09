package com.sappyoak.dsanalyzer.app.startup

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsFile
import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceDirectory
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationCheck
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.inspectInstallation

private const val INSPECTION_KEY = "inspection"

public class StartupEffects(
    private val settingsFile: SettingsFile,
    private val workspaces: WorkspaceDirectory
) : EffectRunner<StartupEffect, StartupMessage> {
    /**
     * Folder inspection is keyed, so choosing a second folder while the first is
     * still being inspected cancels the first rather than racing it
     */
    override fun keyOf(effect: StartupEffect): Any? =
        if (effect is StartupEffect.InspectFolder) INSPECTION_KEY else null

    override fun execute(effect: StartupEffect): Flow<StartupMessage> =
        flow { performEffect(effect)?.let { emit(it) } }

    override fun onFailure(effect: StartupEffect, failure: Throwable): StartupMessage =
        StartupMessage.OperationFailed(
            operation = operationOf(effect),
            detail = failure.message?: failure.toString()
        )

    /** Returns null for effects that produce no message */
    private suspend fun performEffect(effect: StartupEffect): StartupMessage? = when (effect) {
        StartupEffect.LoadSettings -> settingsFile.read().let { result ->
            StartupMessage.SettingsLoaded(result.settings, result.recoveredFrom)
        }

        is StartupEffect.SaveSettings -> {
            settingsFile.write(effect.settings)
            null
        }

        is StartupEffect.InspectFolder -> StartupMessage.FolderInspected(
            folder = effect.folder,
            check = inspectFolder(effect.folder)
        )

        is StartupEffect.CheckInstallations -> StartupMessage.InstallationsChecked(
            stillUsable(effect.installations)
        )

        is StartupEffect.ListWorkspaces -> StartupMessage.WorkspacesListed(workspaces.list())
        is StartupEffect.CreateWorkspace -> StartupMessage.WorkspaceCreated(
            workspaces.create(effect.name, effect.installationId)
        )
    }

    private suspend fun inspectFolder(folder: Path): InstallationCheck = withContext(Dispatchers.IO) {
        inspectInstallation(folder)
    }

    private suspend fun stillUsable(installations: List<Installation>): List<Installation> = withContext(Dispatchers.IO) {
        installations.filter { inspectInstallation(it.root) is InstallationCheck.Valid }
    }

    private fun operationOf(effect: StartupEffect): FailedStartupOperation = when (effect) {
        StartupEffect.LoadSettings -> FailedStartupOperation.LoadSettings
        is StartupEffect.SaveSettings -> FailedStartupOperation.SaveSettings
        is StartupEffect.InspectFolder -> FailedStartupOperation.InspectFolder(effect.folder)
        is StartupEffect.CheckInstallations -> FailedStartupOperation.CheckInstallations
        StartupEffect.ListWorkspaces -> FailedStartupOperation.ListWorkspaces
        is StartupEffect.CreateWorkspace -> FailedStartupOperation.CreateWorkspace(effect.name)
    }
}

public sealed interface StartupEffect {
    public data object LoadSettings : StartupEffect
    public data class SaveSettings(public val settings: Settings) : StartupEffect

    public data class InspectFolder(public val folder: Path) : StartupEffect
    public data class CheckInstallations(
        public val installations: List<Installation>
    ) : StartupEffect

    public data object ListWorkspaces : StartupEffect
    public data class CreateWorkspace(
        public val name: String,
        public val installationId: InstallationId
    ) : StartupEffect
}