package com.sappyoak.dsanalyzer.native.linux

import java.lang.foreign.Arena
import java.lang.foreign.ValueLayout.JAVA_BYTE
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import com.sappyoak.dsanalyzer.native.memory.AOBPattern
import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.scan
import com.sappyoak.dsanalyzer.native.process.ProcessInfo
import com.sappyoak.dsanalyzer.native.process.Processes
import com.sappyoak.dsanalyzer.shared.platform.OS
import com.sappyoak.dsanalyzer.shared.platform.PointerSize

/** Reads this very process, which is the one target that is always here to be read */
private val ENABLED = OS.current.isLinux

private val MARKER = byteArrayOf(0x44, 0x53, 0x41, 0x4E, 0x2D, 0x54, 0x45, 0x53, 0x54)
private val PATTERN = AOBPattern.parse("44 53 41 4E ?? 54 45 53 54")

private fun self(): ProcessInfo {
    val pid = ProcessHandle.current().pid().toInt()
    return Processes.Current.list().first { it.pid == pid }
}

class LinuxProcessTest : FunSpec({
    test("the running processes include this one, named after its executable").config(enabled = ENABLED) {
        self().executableName shouldNotBe ""
    }

    test("attaching reports the pointer width of the target").config(enabled = ENABLED) {
        Processes.Current.attach(self()).use {
            it.pointerSize shouldBe PointerSize.LongPointer
        }
    }

    test("memory written here reads back through the kernel").config(enabled = ENABLED) {
        Arena.ofConfined().use { arena ->
            val planted = arena.allocateFrom(JAVA_BYTE, *MARKER)

            Processes.Current.attach(self()).use { process ->
                process.read(Address(planted.address()), MARKER.size) shouldBe MARKER
            }
        }
    }

    test("the mapped files are reported as modules").config(enabled = ENABLED) {
        Processes.Current.attach(self()).use { process ->
            val modules = process.modules()
            assertSoftly {
                modules.shouldNotBeEmpty()
                modules.all { it.range.size > 0 } shouldBe true
            }
        }
    }

    test("a scan finds a pattern planted in the target").config(enabled = ENABLED) {
        Arena.ofConfined().use { arena ->
            val planted = arena.allocateFrom(JAVA_BYTE, *MARKER)
            val at = Address(planted.address())

            Processes.Current.attach(self()).use { process ->
                process.scan(AddressRange(at, MARKER.size.toLong()), PATTERN) shouldBe listOf(at)
            }
        }
    }
})