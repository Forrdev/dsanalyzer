package com.sappyoak.dsanalyzer.formats.param

public class Param(
    public val paramType: String,
    public val dataVersion: Int,
    public val rowSize: Int,
    public val rows: List<ParamRowData>
)

public class ParamRowData(
    public val id: Int,
    public val name: String,
    public val bytes: RowBytes
)
