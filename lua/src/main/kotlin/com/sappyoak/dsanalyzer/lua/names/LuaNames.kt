package com.sappyoak.dsanalyzer.lua.names

import com.sappyoak.dsanalyzer.lua.chunk.LocalVariable

/**
 * Names for stripped functions, taken from a build that kept its debug info. [locals] has every local,
 * parameters first, of each function by [functionKey], for copies compiled exactly the same; [parameters]
 * has the parameters of each global function by name, for copies whose code differs
 */
public class LuaNames(
    public val locals: Map<String, List<LocalVariable>>,
    public val parameters: Map<String, List<String>>
) {
    public companion object {
        public val None: LuaNames = LuaNames(emptyMap(), emptyMap())
    }
}
