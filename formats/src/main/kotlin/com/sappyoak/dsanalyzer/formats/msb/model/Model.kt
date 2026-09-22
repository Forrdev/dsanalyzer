package com.sappyoak.dsanalyzer.formats.msb.model

/** A model file the map's parts may place */
public data class Model(
    public val name: String,
    public val type: ModelType,
    /** Editor path to a placeholder file, unused by game */
    public val sibPath: String,
    /** How many parts place this model */
    public val instanceCount: Int
)