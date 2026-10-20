package com.sappyoak.dsanalyzer.runtime.session.collision

import kotlinx.serialization.Serializable

@Serializable
public enum class ShapeKind(val code: Int) {
    Cylinder(2),
    Box(4),
    ConvexVertices(6),
    Mopp(10),
    ConvexTranslate(11),
    Mesh(14);

    public companion object {
        private val byCode = entries.associateBy { it.code }

        public fun of(code: Int): ShapeKind? = byCode[code]
    }
}