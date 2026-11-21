package com.sappyoak.dsanalyzer.lua.names

import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.security.MessageDigest

import com.sappyoak.dsanalyzer.lua.chunk.LuaConstant
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction
import com.sappyoak.dsanalyzer.lua.chunk.encodeText

/** Bytes of the SHA-256 kept: 64 bits, far from colliding across DS1's few thousand functions */
private const val KEY_BYTES = 8

private const val NIL: Byte = 0
private const val NUMBER: Byte = 1
private const val TEXT: Byte = 2

/**
 * What identifies a function's compiled form wherever it sits and whether or not it kept its debug info: its
 * shape, code and constants, with strings as the bytes [charset] read them from. Nested functions are keyed
 * on their own, so only their count counts here
 */
public fun functionKey(function: LuaFunction, charset: Charset): String {
    val digest = MessageDigest.getInstance("SHA-256")
    digest.int(function.paramCount)
    digest.int(if (function.isVararg) 1 else 0)
    digest.int(function.upvalueCount)
    digest.int(function.maxStackSize)
    digest.int(function.functions.size)
    function.code.forEach { digest.int(it.raw) }
    function.constants.forEach { constant ->
        when (constant) {
            LuaConstant.Nil -> digest.update(NIL)
            is LuaConstant.Number -> digest.update(NUMBER).also { digest.long(constant.value.toRawBits()) }
            is LuaConstant.Text -> encodeText(constant.value, charset).let { bytes ->
                digest.update(TEXT)
                digest.int(bytes.size)
                digest.update(bytes)
            }
        }
    }
    return digest.digest().copyOf(KEY_BYTES).toHexString()
}

private fun MessageDigest.int(value: Int) = update(ByteBuffer.allocate(Int.SIZE_BYTES).putInt(value).array())

private fun MessageDigest.long(value: Long) = update(ByteBuffer.allocate(Long.SIZE_BYTES).putLong(value).array())
