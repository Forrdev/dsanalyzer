package com.sappyoak.dsanalyzer.runtime.inject

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.native.memory.Address

private fun assembled(body: X86.() -> Unit): List<Int> =
    X86(Address(0)).apply(body).assemble().map { it.toInt() and 0xFF }

class X86SseTest : FunSpec({
    test("scalar loads and stores take the F3 0F prefix and a 32-bit displacement") {
        assembled { movss(Xmm.XMM0, Mem.Based(Reg.EAX, 0x10)) } shouldBe
                listOf(0xF3, 0x0F, 0x10, 0x80, 0x10, 0x00, 0x00, 0x00)
        assembled { movss(Mem.Absolute(Address(0x01020304)), Xmm.XMM1) } shouldBe
                listOf(0xF3, 0x0F, 0x11, 0x0D, 0x04, 0x03, 0x02, 0x01)
    }

    test("register forms encode both registers in one ModRM byte") {
        assembled { mulss(Xmm.XMM0, Xmm.XMM0) } shouldBe listOf(0xF3, 0x0F, 0x59, 0xC0)
        assembled { addss(Xmm.XMM0, Xmm.XMM1) } shouldBe listOf(0xF3, 0x0F, 0x58, 0xC1)
    }

    test("a memory subtract names its register and base") {
        assembled { subss(Xmm.XMM2, Mem.Based(Reg.ESI, 4)) } shouldBe
                listOf(0xF3, 0x0F, 0x5C, 0x96, 0x04, 0x00, 0x00, 0x00)
    }

    test("comiss and movups carry no scalar prefix") {
        assembled { comiss(Xmm.XMM0, Mem.Absolute(Address(0x1000))) } shouldBe
                listOf(0x0F, 0x2F, 0x05, 0x00, 0x10, 0x00, 0x00)
        assembled { movups(Xmm.XMM1, Mem.Based(Reg.ECX, 0x20)) } shouldBe
                listOf(0x0F, 0x10, 0x89, 0x20, 0x00, 0x00, 0x00)
        assembled { movups(Mem.Based(Reg.EDI, 0x30), Xmm.XMM3) } shouldBe
                listOf(0x0F, 0x11, 0x9F, 0x30, 0x00, 0x00, 0x00)
    }

    test("float branches resolve their labels like every other jump") {
        assembled { val end = label(); ja(end); bind(end) } shouldBe listOf(0x0F, 0x87, 0x00, 0x00, 0x00, 0x00)
        assembled { val top = label(); bind(top); jbe(top) } shouldBe listOf(0x0F, 0x86, 0xFA, 0xFF, 0xFF, 0xFF)
    }
})
