package com.sappyoak.dsanalyzer.formats.esd

/** Which dispatcher a script's commands go through */
public enum class EsdKind(public val key: String) {
    Character("chr"),
    Talk("talk");
}

public class EsdDefinitions(
    private val commands: Map<Pair<Int, Int>, EsdDefinition>,
    private val functions: Map<Int, EsdDefinition>
) {
    public fun command(call: CommandCall): EsdDefinition? = commands[call.bank to call.id]
    public fun function(id: Int): EsdDefinition? = functions[id]

    public companion object {
        public val None: EsdDefinitions = EsdDefinitions(emptyMap(), emptyMap())
    }
}

public class EsdDefinition(
    public val name: String?,
    public val formerly: List<String>,
    public val args: List<String>,
    public val used: Boolean,
    public val reading: Reading?
) {
    public val label: String? get() = name ?: formerly.firstOrNull()?.let { "($it?)" }
}

/** What the call was read to do in the binary, and how that sits against its community name */
public class Reading(public val text: String, public val verdict: Verdict)

public enum class Verdict { Agrees, Conflicts, Unjudged; }
