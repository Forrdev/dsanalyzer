package com.sappyoak.dsanalyzer.lua.names

import java.nio.charset.Charset

import com.sappyoak.dsanalyzer.lua.chunk.LocalVariable
import com.sappyoak.dsanalyzer.lua.chunk.LuaFunction

/** [names] as one chunk looks them up, keying its functions with the [charset] its strings were read in */
internal class Borrowing(private val names: LuaNames, private val charset: Charset) {
    /** The locals of a function compiled the same as one that kept its debug info */
    fun localsOf(function: LuaFunction): List<LocalVariable>? =
        if (names.locals.isEmpty()) null else names.locals[functionKey(function, charset)]

    /** The parameters of global function [global], when its named copy takes as many */
    fun parametersOf(global: String?, count: Int): List<String>? =
        global?.let { names.parameters[it] }?.takeIf { it.size == count }

    companion object {
        val None: Borrowing = Borrowing(LuaNames.None, Charsets.ISO_8859_1)
    }
}
