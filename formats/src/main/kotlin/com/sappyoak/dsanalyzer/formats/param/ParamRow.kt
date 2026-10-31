package com.sappyoak.dsanalyzer.formats.param

/**
 * A row read through its def. Each def has a generated subclass in 'defs' whose properties read
 * the row's bytes on access, so a subclass carries exactly the fields its def declares
 */
public abstract class ParamRow(row: ParamRowData) {
    public val id: Int = row.id
    public val name: String = row.name
    protected val bytes: RowBytes = row.bytes
}

/** What a generated row class is: the def it reads, and how to make one */
public open class ParamRowType<R : ParamRow>(
    public val paramType: String,
    public val rowSize: Int,
    public val dataVersion: Int,
    private val create: (ParamRowData) -> R
) {
    public fun wrap(row: ParamRowData): R = create(row)
}

/** A param's rows as one row class, in file order */
public class TypedParam<R : ParamRow>(public val type: ParamRowType<R>, public val rows: List<R>) {
    private val byId: Map<Int, List<R>> = rows.groupBy { it.id }

    /** The first row in file order with [id]. Whether the game finds the same one is not known yet */
    public operator fun get(id: Int): R? = byId[id]?.first()

    /** Every row with [id]; more than one when the file repeats it */
    public fun all(id: Int): List<R> = byId[id].orEmpty()
}

/**
 * Reads this param through [type]. Refuses a param of another def, or one written against another
 * version of it, rather than reading fields from the wrong places
 */
public fun <R : ParamRow> Param.typed(type: ParamRowType<R>): TypedParam<R> {
    require(paramType == type.paramType) { "A $paramType param cannot be read as ${type.paramType}" }
    require(dataVersion == type.dataVersion && rowSize == type.rowSize) {
        "$paramType is data version $dataVersion with 0x${rowSize.toString(16)}-byte rows; " +
                "the generated class expects ${type.dataVersion} and 0x${type.rowSize.toString(16)}"
    }
    return TypedParam(type, rows.map(type::wrap))
}
