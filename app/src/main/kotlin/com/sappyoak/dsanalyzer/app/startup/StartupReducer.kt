package com.sappyoak.dsanalyzer.app.startup

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with
import com.sappyoak.dsanalyzer.app.workspace.Workspace
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationCheck

public fun reduceStartup(
    state: StartupState,
    message: StartupMessage
): Transition<StartupState, StartupEffect> = when (message) {
    StartupMessage.Start -> state.with(StartupEffect.LoadSettings)

    is StartupMessage.SettingsLoaded -> state
        .copy(lastActiveWorkspaceId = message.settings.lastActiveWorkspaceId)
        .noting(message.recoveredFrom?.let(StartupNotice::SettingsRecovered))
        .with(StartupEffect.CheckInstallations(message.settings.installations))

    is StartupMessage.FolderChosen -> state
        .copy(phase = StartupPhase.Validating(message.folder))
        .with(StartupEffect.InspectFolder(message.folder))

    is StartupMessage.FolderInspected -> when (val check = message.check) {
        is InstallationCheck.Rejected -> state
            .copy(phase = StartupPhase.Rejected(message.folder, check.reason))
            .with()

        is InstallationCheck.Valid -> state.copy(
            phase = StartupPhase.NeedsWorkspace,
            installations = state.installations.replacing(check.installation)
        ).persisting()
    }

    is StartupMessage.InstallationsChecked -> state
        .copy(installations = message.installations)
        .with(StartupEffect.ListWorkspaces)

    is StartupMessage.WorkspacesListed -> resume(state.copy(workspaces = message.workspaces))

    StartupMessage.WorkspacePickerRequested -> state
        .copy(phase = idlePhase(state))
        .with()

    is StartupMessage.WorkspaceChosen -> state.workspaces.firstOrNull { it.id == message.id }
        ?.let(state::opening)
        ?: state.with()

    is StartupMessage.WorkspaceCreationRequested -> state.with(
        StartupEffect.CreateWorkspace(message. name, message.installationId)
    )

    is StartupMessage.WorkspaceCreated -> state
        .copy(workspaces = state.workspaces + message.workspace)
        .opening(message.workspace)

    StartupMessage.RejectionDismissed -> state
        .copy(phase = idlePhase(state))
        .with()

    StartupMessage.NoticesDismissed -> state
        .copy(notices = emptyList())
        .with()

    is StartupMessage.OperationFailed ->
        message.operation.recover(state.noting(noticeFor(message)))
}

/**
 * Reopens the last workspace when it still exists and its installation is still usable
 * otherwise asks the user for whichever piece is missing
 */
private fun resume(state: StartupState): Transition<StartupState, StartupEffect> {
    val resumable = state.workspaces
        .firstOrNull { it.id == state.lastActiveWorkspaceId }
        ?.takeIf { workspace -> state.installations.any { it.id == workspace.installationId } }

    return resumable
        ?.let(state::opening)
        ?: state.copy(phase = idlePhase(state)).with()
}

private fun idlePhase(state: StartupState): StartupPhase =
    if (state.installations.isEmpty()) StartupPhase.NeedsInstallation else StartupPhase.NeedsWorkspace

private fun StartupState.opening(workspace: Workspace): Transition<StartupState, StartupEffect> =
    copy(
        phase = StartupPhase.Ready(workspace),
        lastActiveWorkspaceId = workspace.id
    ).persisting()

private fun StartupState.persisting(): Transition<StartupState, StartupEffect> =
    with(StartupEffect.SaveSettings(Settings(installations, lastActiveWorkspaceId)))

private fun StartupState.noting(notice: StartupNotice?): StartupState =
    if (notice == null) this else copy(notices = notices + notice)

private fun noticeFor(message: StartupMessage.OperationFailed): StartupNotice =
    when (val operation = message.operation) {
        FailedStartupOperation.LoadSettings -> StartupNotice.SettingsUnreadable(message.detail)
        FailedStartupOperation.SaveSettings -> StartupNotice.SettingsNotSaved(message.detail)
        is FailedStartupOperation.InspectFolder -> StartupNotice.FolderNotInspected(operation.folder, message.detail)
        FailedStartupOperation.CheckInstallations -> StartupNotice.InstallationsUncheckable(message.detail)
        FailedStartupOperation.ListWorkspaces -> StartupNotice.WorkspacesUnreadable(message.detail)
        is FailedStartupOperation.CreateWorkspace -> StartupNotice.WorkspaceNotCreated(operation.name, message.detail)
    }

private fun FailedStartupOperation.recover(
    state: StartupState
): Transition<StartupState, StartupEffect> = when (this) {
    FailedStartupOperation.LoadSettings -> state.with(StartupEffect.CheckInstallations(emptyList()))
    FailedStartupOperation.SaveSettings -> state.with()
    is FailedStartupOperation.InspectFolder -> state.copy(phase = idlePhase(state)).with()
    FailedStartupOperation.CheckInstallations -> state
        .copy(installations = emptyList())
        .with(StartupEffect.ListWorkspaces)
    FailedStartupOperation.ListWorkspaces -> resume(state.copy(workspaces = emptyList()))
    is FailedStartupOperation.CreateWorkspace -> state.with()
}

private fun List<Installation>.replacing(installation: Installation): List<Installation> =
    filterNot { it.id == installation.id } + installation