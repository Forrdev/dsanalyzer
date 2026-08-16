package com.sappyoak.dsanalyzer.game.data.map

import com.sappyoak.dsanalyzer.shared.geometry.Transform
import kotlinx.serialization.Serializable

@Serializable
data class MSBRegion(
    val name: String,
    val entityId: Int,
    val transform: Transform,
    val shape: Shape
) {
    @Serializable
    sealed class Shape {
        @Serializable
        data object Point : Shape()

        @Serializable
        data class Circle(val radius: Float) : Shape()

        @Serializable
        data class Sphere(val radius: Float) : Shape()

        @Serializable
        data class Cylinder(val radius: Float, val height: Float) : Shape()

        @Serializable
        data class Rect(val width: Float, val depth: Float) : Shape()

        @Serializable
        data class Box(val width: Float, val depth: Float, val height: Float) : Shape()

        @Serializable
        data class Composite(val childRegionNames: List<String>) : Shape()
    }
}