package com.sappyoak.dsanalyzer.lua.chunk

/** How an instruction's bits after the opcode are split */
public enum class OpFormat { ABC, ABx, AsBx }

/** Lua 5.0's opcodes, in bytecode order */
public enum class Opcode(public val format: OpFormat) {
    MOVE(OpFormat.ABC),
    LOADK(OpFormat.ABx),
    LOADBOOL(OpFormat.ABC),
    LOADNIL(OpFormat.ABC),
    GETUPVAL(OpFormat.ABC),
    GETGLOBAL(OpFormat.ABx),
    GETTABLE(OpFormat.ABC),
    SETGLOBAL(OpFormat.ABx),
    SETUPVAL(OpFormat.ABC),
    SETTABLE(OpFormat.ABC),
    NEWTABLE(OpFormat.ABC),
    SELF(OpFormat.ABC),
    ADD(OpFormat.ABC),
    SUB(OpFormat.ABC),
    MUL(OpFormat.ABC),
    DIV(OpFormat.ABC),
    POW(OpFormat.ABC),
    UNM(OpFormat.ABC),
    NOT(OpFormat.ABC),
    CONCAT(OpFormat.ABC),
    JMP(OpFormat.AsBx),
    EQ(OpFormat.ABC),
    LT(OpFormat.ABC),
    LE(OpFormat.ABC),
    TEST(OpFormat.ABC),
    CALL(OpFormat.ABC),
    TAILCALL(OpFormat.ABC),
    RETURN(OpFormat.ABC),
    FORLOOP(OpFormat.AsBx),
    TFORLOOP(OpFormat.ABC),
    TFORPREP(OpFormat.AsBx),
    SETLIST(OpFormat.ABx),
    SETLISTO(OpFormat.ABx),
    CLOSE(OpFormat.ABC),
    CLOSURE(OpFormat.ABx);

    public companion object {
        public fun of(code: Int): Opcode? = entries.getOrNull(code)
    }
}
