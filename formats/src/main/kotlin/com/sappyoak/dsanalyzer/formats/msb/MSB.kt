package com.sappyoak.dsanalyzer.formats.msb

import com.sappyoak.dsanalyzer.formats.msb.model.Model

/**
 * A map's layout, the models it uses, its regions, and the parts placed in it.
 *
 * Entries refer to each other by index rather than by name because names repeat within a map.
 */
public class MSB(
    public val models: List<Model>
) {
    public operator fun get(index: ModelIndex): Model = models[index.value]
}

