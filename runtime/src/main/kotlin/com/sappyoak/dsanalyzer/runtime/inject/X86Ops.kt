package com.sappyoak.dsanalyzer.runtime.inject


/** Register-to-register ModRM: both operands are registers */
private const val REGISTER_FORM = 0b11_000_000

/** `TEST byte [mem], imm8` */
public fun X86.testByte(mem: Mem, imm: Int) {
    modRm(0xF6, 0, mem)
    bytes(imm and 0xFF)
}

/** `TEST reg, imm32` */
public fun X86.test(reg: Reg, imm: Int) {
    bytes(0xF7, REGISTER_FORM or reg.code)
    int(imm)
}

/** `TEST left, right` */
public fun X86.test(left: Reg, right: Reg): Unit = bytes(0x85, REGISTER_FORM or (right.code shl 3) or left.code)

/** `CMP reg, imm32` */
public fun X86.cmp(reg: Reg, imm: Int) {
    bytes(0x81, REGISTER_FORM or (7 shl 3) or reg.code)
    int(imm)
}

/** `MOVZX reg, byte [mem]` */
public fun X86.movzxByte(reg: Reg, mem: Mem) {
    bytes(0x0F)
    modRm(0xB6, reg.code, mem)
}

/** `BT dword [mem], reg`: the carry flag takes bit [reg] of the bit string at [mem] */
public fun X86.bt(mem: Mem, reg: Reg) {
    bytes(0x0F)
    modRm(0xA3, reg.code, mem)
}

public fun X86.lea(reg: Reg, mem: Mem): Unit = modRm(0x8D, reg.code, mem)

/** `PUSH imm32` */
public fun X86.pushDword(imm: Int) {
    bytes(0x68)
    int(imm)
}

/** `PUSH dword [mem]` */
public fun X86.push(mem: Mem): Unit = modRm(0xFF, 6, mem)

public fun X86.pop(reg: Reg): Unit = bytes(0x58 + reg.code)
