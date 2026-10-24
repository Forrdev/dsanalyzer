package com.sappyoak.dsanalyzer.runtime.session.attack

import com.sappyoak.dsanalyzer.shared.math.Vec3

public sealed interface HitShape {
    public val radius: Float

    public data class Sphere(public val center: Vec3, override val radius: Float) : HitShape

    public data class Capsule(
        public val a: Vec3,
        public val b: Vec3,
        override val radius: Float
    ) : HitShape
}
