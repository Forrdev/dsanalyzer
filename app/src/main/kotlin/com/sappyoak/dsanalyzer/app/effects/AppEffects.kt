package com.sappyoak.dsanalyzer.app.effects

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.app.AppServices
import com.sappyoak.dsanalyzer.app.config.WorkspaceChange
import com.sappyoak.dsanalyzer.app.state.*
import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceAction
import com.sappyoak.dsanalyzer.app.state.workspace.WorkspaceState
import kotlin.time.Clock

class AppEffects(
    private val services: AppServices,
    private val setup: SetupEffects
) : AutoCloseable {
    private val environment get() = services.environment

    fun handle(
        envelope: Envelope,
        state: AppState,
        scope: CoroutineScope,
        dispatch: DispatchFn
    ) {
        when (envelope) {
            is Envelope.Global -> handleGlobal(envelope.action, state, scope, dispatch)
            is Envelope.Scoped -> {
                val workspace = state.workspaces[envelope.workspaceId] ?: return

                // journaled before the handler runs so a crash inside an effect still leaves
                // the change recorded
                journal(state, envelope)

                handleScoped(envelope.action, workspace, state, scope) { action ->
                    // replies stay in the workspace that asked. An effect dispatcher a bare
                    // action would otherwise have to know its own scope
                    dispatch(Envelope.Scoped(envelope.workspaceId, action))
                }
            }

            is Envelope.Lifecycle -> handleLifecycle(envelope.action, state, scope, dispatch)
        }
    }

    override fun close() {
        setup.close()
    }

    private fun handleGlobal(
        action: Action,
        state: AppState,
        scope: CoroutineScope,
        dispatch: DispatchFn
    ) {
        val reply: (Action) -> Unit = { dispatch(Envelope.Global(it)) }

        when (action) {
            else -> Unit
        }
    }

    /**
     * Actions belonging to one workspace
     *
     * The workspace is resolved before dispatching, so the effects take a concrete
     * installation rather than looking one up
     */
    private fun handleScoped(
        action: Action,
        workspace: WorkspaceState,
        state: AppState,
        scope: CoroutineScope,
        reply: (Action) -> Unit
    ) {
        when (action) {
            is Action.Setup -> setup.handle(action, workspace, scope, reply)
            else -> Unit
        }
    }

    private fun handleLifecycle(
        action: WorkspaceAction,
        state: AppState,
        scope: CoroutineScope,
        dispatch: DispatchFn
    ) {
        when (action) {
            is WorkspaceAction.Open -> {
                environment.recordWorkspaceChange(WorkspaceChange.Created(
                    workspaceId = action.installationKey,
                    installationKey = action.installationKey,
                    title = action.title,
                    at = Clock.System.now()
                ))
            }

            is WorkspaceAction.Close -> {
                val workspace = state.workspaces[action.workspaceId] ?: return

                environment.recordWorkspaceChange(WorkspaceChange.Closed(
                    action.workspaceId,
                    Clock.System.now()
                ))
            }

            else -> Unit
        }
    }

    private fun journal(state: AppState, envelope: Envelope) {
        val scoped = envelope as? Envelope.Scoped ?: return
        val worked = state.workspaces[scoped.workspaceId] ?: return
        val now = Clock.System.now()

        // no actions need this yet
    }

    /**
     * Mirrors state changes into settings, which will be updates *before* any of the
     * other effects run
     */
    private fun persist(action: Action) {
        when (action) {
            is Action.Setup.GamePathChosen -> {
                action.inspection.identity?.let { identity ->
                    environment.selectInstallation(identity, action.path)
                }
            }

            is Action.Setup.DataPathChosen -> environment.update {
                it.copy(dataPath = action.path)
            }

            else -> Unit
        }
    }

    /**
     * Switches installations without a restart
     */
    private fun switchInstallation(
        key: String,
        dispatch: (Action) -> Unit
    ) {
        environment.activeInstallation(key)
        dispatch(Action.Setup.CacheSizeRequested)
        dispatch(Action.Setup.InstallationsRequested)
    }
}