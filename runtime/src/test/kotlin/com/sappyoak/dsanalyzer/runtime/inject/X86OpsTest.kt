package com.sappyoak.dsanalyzer.runtime.inject

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.memory.Address

private fun assembled(body: X86.() -> Unit): List<Int> =
    X86(Address(0)).apply(body).assemble().map { it.toInt() and 0xFF }

class X86OpsTest : FunSpec({
    test("an ESP base takes the SIB byte a bare ESP needs") {
        assembled { mov(Reg.EDX, Mem.Based(Reg.ESP, 0)) } shouldBe listOf(0x8B, 0x94, 0x24, 0, 0, 0, 0)
        assembled { lea(Reg.EAX, Mem.Based(Reg.ESP, 0x10)) } shouldBe listOf(0x8D, 0x84, 0x24, 0x10, 0, 0, 0)
    }

    test("byte tests and zero-extending loads name their memory operand") {
        assembled { testByte(Mem.Based(Reg.ESI, 0x80), 0x12) } shouldBe listOf(0xF6, 0x86, 0x80, 0, 0, 0, 0x12)
        assembled { movzxByte(Reg.EAX, Mem.Based(Reg.ESI, 0x81)) } shouldBe listOf(0x0F, 0xB6, 0x86, 0x81, 0, 0, 0)
    }

    test("register tests and compares use the register form") {
        assembled { test(Reg.ECX, Reg.ECX) } shouldBe listOf(0x85, 0xC9)
        assembled { test(Reg.EAX, 0x80) } shouldBe listOf(0xF7, 0xC0, 0x80, 0, 0, 0)
        assembled { cmp(Reg.EDX, 0x80000048.toInt()) } shouldBe listOf(0x81, 0xFA, 0x48, 0, 0, 0x80)
    }

    test("a bit test takes its bit number from a register") {
        assembled { bt(Mem.Absolute(Address(0x20000000)), Reg.EAX) } shouldBe listOf(0x0F, 0xA3, 0x05, 0, 0, 0, 0x20)
    }

    test("pushes and pops") {
        assembled { pushDword(0x0F5AA02) } shouldBe listOf(0x68, 0x02, 0xAA, 0xF5, 0x00)
        assembled { push(Mem.Absolute(Address(0x20000020))) } shouldBe listOf(0xFF, 0x35, 0x20, 0, 0, 0x20)
        assembled { pop(Reg.ECX) } shouldBe listOf(0x59)
    }
})
