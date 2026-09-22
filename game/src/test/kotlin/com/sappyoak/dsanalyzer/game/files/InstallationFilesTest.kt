package com.sappyoak.dsanalyzer.game.files

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Path
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration.Companion.seconds

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId

private val IDLE = 30.seconds
private val INSTALLATION = Installation(
    id = InstallationId("ptde"),
    root = Path.of("game"),
    executable = Path.of("game", "DARKSOULS.exe"),
    build = GameBuild(GameEdition.PrepareToDie)
)

private class FakeGameFiles : GameFiles {
    var closed = false
        private set

    override fun listing(): FileListing = FileListing.Named(emptySet())
    override fun read(path: String): ByteArray? = null
    override fun exists(path: String): Boolean = false
    override fun close() {
        closed = true
    }
}

private fun TestScope.installationFiles(): Pair<InstallationFiles, List<FakeGameFiles>> {
    val opened = mutableListOf<FakeGameFiles>()
    val files = InstallationFiles(backgroundScope, IDLE, EmptyCoroutineContext) { FakeGameFiles().also(opened::add) }
    return files to opened
}

class InstallationFilesTest : FunSpec({
    test("uses share one open instance") {
        runTest {
            val (files, opened) = installationFiles()

            files.use(INSTALLATION) {}
            files.use(INSTALLATION) {}

            opened.size shouldBe 1
        }
    }

    test("closes once idle for the timeout, and not before") {
        runTest {
            val (files, opened) = installationFiles()
            files.use(INSTALLATION) {}

            assertSoftly {
                advanceTimeBy(IDLE - 1.seconds)
                opened.single().closed shouldBe false

                advanceTimeBy(2.seconds)
                opened.single().closed shouldBe true
            }
        }
    }

    test("a use before the timeout keeps the instance open") {
        runTest {
            val (files, opened) = installationFiles()
            files.use(INSTALLATION) {}
            advanceTimeBy(IDLE - 1.seconds)

            files.use(INSTALLATION) {}
            advanceTimeBy(IDLE - 1.seconds)

            assertSoftly {
                opened.size shouldBe 1
                opened.single().closed shouldBe false
            }
        }
    }

    test("retiring waits for the current reader, then the next use reopens") {
        runTest {
            val (files, opened) = installationFiles()
            val finish = CompletableDeferred<Unit>()
            launch { files.use(INSTALLATION) { finish.await() } }
            runCurrent()

            files.retire(INSTALLATION.id)

            assertSoftly {
                opened.single().closed shouldBe false

                finish.complete(Unit)
                runCurrent()
                opened.single().closed shouldBe true

                files.use(INSTALLATION) {}
                opened.size shouldBe 2
            }
        }
    }

    test("closing closes idle instances and leaves leased ones to their reader") {
        runTest {
            val (files, opened) = installationFiles()
            val finish = CompletableDeferred<Unit>()
            launch { files.use(INSTALLATION) { finish.await() } }
            runCurrent()

            files.close()

            assertSoftly {
                opened.single().closed shouldBe false

                finish.complete(Unit)
                runCurrent()
                opened.single().closed shouldBe true
            }
        }
    }
})