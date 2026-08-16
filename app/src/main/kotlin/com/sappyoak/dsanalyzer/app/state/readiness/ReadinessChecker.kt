package com.sappyoak.dsanalyzer.app.state.readiness

import com.sappyoak.dsanalyzer.app.state.Action
import com.sappyoak.dsanalyzer.app.state.AppState
import com.sappyoak.dsanalyzer.game.identity.GameVersion

/**
 * Works out what is blocking the application from state.
 *
 * This is derived rather than tracked so it cannot go stale. A stored "setup complete"
 * flag can be true while the path it recorded has since been unmounted, and the failure
 * would appear as every read returning nothing rather than as the drive being absent
 */
object ReadinessChecker {
    fun evaluate(state: AppState): Readiness {
        val setup = state.setup
        val problems = buildList {
            // No installation at all. Everything else is moot until this is answered
            if (setup.gamePath == null) {
                add(Blocker(
                    Blocker.Kind.NoInstallation,
                    summary = "No game directory selected",
                    consequences = "Nothing can be ready, so every screen would be empty " +
                            "which looks like a game with no bugs in it rather than a tool that " +
                            "has not been pointed anywhere",
                    resolutions = buildList {
                        add(Blocker.Resolution("Browse for game", Action.Setup.GamePathRequested))
                        if (setup.suggestedPaths.isNotEmpty()) {
                            add(Blocker.Resolution("Use a detected installation", Action.Setup.InstallationsRequested))
                        }
                    }
                ))
                return@buildList
            }

            // The recorded game path is gone. Distinguished from having no path because the
            // resolutions differ in that this one cannot be re-pointed, and the installation's
            // data is worth keeping is the path comes back
            if (!setup.gamePathResolves) {
                add(Blocker(
                    kind = Blocker.Kind.InstallationMissing,
                    summary = "Game files are not longer where they were",
                    consequences = "The recorded path does not resolve. A drive may not be " +
                            "mounted, or the game may have moved",
                    resolutions = listOf(
                        Blocker.Resolution("Point at the game again", Action.Setup.GamePathRequested),
                        Blocker.Resolution("Choose a different installation", Action.Setup.InstallationsRequested),
                        Blocker.Resolution(
                            "Forget this installation",
                            setup.activeInstallation?.let { Action.Setup.InstallationRemoved(it) } ?: Action.Setup.InstallationsRequested,
                            false
                        )
                    ),
                    installationKey = setup.activeInstallation
                ))
                return@buildList
            }

            // A version that could not be determined is not a degraded state because almost
            // every other piece of the tool depends on this and it isnt something we can guess
            if (setup.version == null) {
                add(Blocker(
                    kind = Blocker.Kind.UnknownVersion,
                    summary = "Could not tell which game this is",
                    consequences = "Which readers apply, which offsets are valid and what a " +
                            "frame means, and how memory is accessed all follow from the version. " +
                            "This isn't something we can guess",
                    resolutions = listOf(
                        Blocker.Resolution("It is Prepare to Die Edition", Action.Setup.GameVersionOverridden(GameVersion.PTDE)),
                        Blocker.Resolution("It is Remastered", Action.Setup.GameVersionOverridden(GameVersion.Remastered)),
                        Blocker.Resolution("Choose a different directory", Action.Setup.GamePathRequested, false)
                    ),
                ))
            }

            // Nothing identifying could be read. This is distinct from unknown version. The version
            // may be certain from an executable name while the data files are unreadable, and that
            // combination fails at the first parse rather than at detection
            if (setup.version != null && setup.identityUnknown) {
                add(Blocker(
                    kind = Blocker.Kind.UnreadableFiles,
                    summary = "Game files could not be read",
                    consequences = "No archive header or loose game files could be opened. The " +
                            "directory looks like an installation but nothing inside it is readable, " +
                            "possibly a permissions problem or a partial copy",
                    resolutions = listOf(Blocker.Resolution("Choose a different directory", Action.Setup.GamePathRequested))
                ))
            }

            if (!setup.dataPathWritable) {
                add(Blocker(
                    kind = Blocker.Kind.DataDirectoryUnwritable,
                    summary = "Cannot write to the data directory",
                    consequences = "The tool needs a place to save all of its data. Things like " +
                            "Reports, captured sessions, testing verdicts. The current data path selected " +
                            "is not writable",
                    resolutions = listOf(Blocker.Resolution("Choose another location", Action.Setup.DataPathRequested))
                ))
            }
        }

        return if (problems.isEmpty()) Readiness.Ready else Readiness.Blocked(problems)
    }
}