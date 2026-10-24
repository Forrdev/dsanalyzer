package com.sappyoak.dsanalyzer.formats.havok

import com.sappyoak.dsanalyzer.formats.havok.packfile.readPackfile
import com.sappyoak.dsanalyzer.formats.havok.tagfile.readTagfile
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

private const val PACKFILE_MAGIC = 0x57E0E057
private const val TAGFILE_MAGIC = "TAG0"

/**
 * A Havok file read but not loaded, whichever container it came in: PTDE's hk_2010 packfiles or
 * Remastered's hk_2015 tagfiles. Both store objects as bytes at offsets in one data block, and
 * differ only in how a pointer names what it points at, which [data] hides
 */
public class HavokFile(
    /** 'hk_2010.2.0-r1' for a packfile, '20150100' for a tagfile */
    public val version: String,
    public val data: HavokData,
    /** Every object the file holds, in data order */
    public val objects: List<HavokObject>,
    /** The root object, normally an 'hkRootLevelContainer' */
    public val root: HavokObject?,
    /** The file's own type descriptions. A tagfile carries them; a packfile does not */
    public val types: HavokTypes?
) {
    private val byOffset = objects.associateBy { it.offset }

    /** The object starting at [offset], with its class */
    public fun objectAt(offset: Int): HavokObject? = byOffset[offset]
}

public data class HavokObject(public val offset: Int, public val className: String)

/** An 'hkArray': where its elements start in the data block, and how many there are */
public data class HkArray(public val offset: Int?, public val count: Int) {
    public val isEmpty: Boolean get() = offset == null || count <= 0

    public companion object {
        public val Empty: HkArray = HkArray(null, 0)
    }
}

/** Member offsets and type sizes by name, for a file that describes its own types */
public interface HavokTypes {
    /** Where [path], dotted through nested members ('collidable.shape'), sits in [type]; null when absent */
    public fun offsetOf(type: String, path: String): Int?

    /** The size of [type] in bytes; null when absent */
    public fun sizeOf(type: String): Int?
}

/** Reads either container, told apart by its magic */
public fun readHavokFile(reader: BinaryReader): HavokFile {
    val magic = reader.at(0) { readInt() }
    val tag = reader.at(4) { readFixedString(4) }
    return when {
        magic == PACKFILE_MAGIC -> readPackfile(reader)
        tag == TAGFILE_MAGIC -> readTagfile(reader)
        else -> throw BinaryFormatException("Neither a Havok packfile nor a tagfile", 0)
    }
}
