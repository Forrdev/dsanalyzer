package com.sappyoak.dsanalyzer.formats.param.paramdef

public enum class ParamFieldType(public val code: String, public val size: Int) {
    S8("s8", 1),
    U8("u8", 1),
    S16("s16", 2),
    U16("u16", 2),
    S32("s32", 4),
    U32("u32", 4),
    F32("f32", 4),
    /** Padding: never a value, only bytes or bits to step over */
    Dummy8("dummy8", 1);

    public companion object {
        private val byCode = entries.associateBy { it.code }

        public fun of(code: String): ParamFieldType? = byCode[code]
    }
}

/**
 * One '.paramdef': the row layout every param of [paramType] shares. Dark Souls ships these in
 * 'paramdef.paramdefbnd', in FromSoft's own words, so the layout is the game's rather than a guess
 */
public class ParamDef(
    /** 'NPC_PARAM_ST'; the name a '.param' file states to say which def lays it out */
    public val paramType: String,
    /** Bumped when the row layout changes; a '.param' states the version it was written against */
    public val dataVersion: Int,
    public val fields: List<ParamDefField>
) {
    /** Every field placed in the row, bitfields packed as the game packs them */
    public val layout: ParamLayout by lazy { layOut(fields) }
}

public class ParamDefField(
    /** The internal name with its ':bits' or '[length]' taken off, 'behaviorVariationId' */
    public val name: String,
    public val type: ParamFieldType,
    /** The field's type as the editor names it: an enum such as 'NPC_BOOL', or [type]'s own code */
    public val internalType: String,
    /** Set for a bitfield: how many bits of its storage unit it takes */
    public val bitCount: Int?,
    /** Set for an array; only padding is ever one in Dark Souls */
    public val arrayLength: Int?,
    /** Japanese, as shipped */
    public val displayName: String,
    /** Japanese, as shipped; null when the field has none */
    public val description: String?,
    public val default: Float,
    public val minimum: Float,
    public val maximum: Float,
    public val sortId: Int
) {
    public val isPadding: Boolean get() = type == ParamFieldType.Dummy8

    /** The enum [internalType] names, or null when the field is a plain number */
    public val enumName: String? get() = internalType.takeIf { it != type.code }
}
