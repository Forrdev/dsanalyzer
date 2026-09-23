package com.sappyoak.dsanalyzer.formats.emevd

/**
 * A compiled event script that the game runs for one map, or the common script every map shared
 */
public class Emevd(
    public val events: List<ScriptEvent>,
    public val linkedFileOffsets: List<Int>,
    public val strings: StringTable
) {
    /** The linked file names, read out of the string table */
    public val linkedFiles: List<String> get() = linkedFileOffsets.map(strings::shiftJisAt)
}