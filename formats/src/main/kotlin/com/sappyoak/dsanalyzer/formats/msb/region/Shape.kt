package com.sappyoak.dsanalyzer.formats.msb.region

import com.sappyoak.dsanalyzer.formats.msb.Coded
import com.sappyoak.dsanalyzer.shared.binary.BinaryReader

public sealed interface Shape {
    public data object Point : Shape
    public data class Circle(public val radius: Float) : Shape
    public data class Sphere(public val radius: Float) : Shape
    public data class Cylinder(public val radius: Float, public val height: Float) : Shape
    public data class Rectangle(public val width: Float, public val depth: Float) : Shape
    public data class Box(public val width: Float, public val depth: Float, public val height: Float) : Shape
}

/** DS1 has no composite type, but later is added as 6 */
internal enum class ShapeType(override val code: Int) : Coded {
    Point(0),
    Circle(1),
    Sphere(2),
    Cylinder(3),
    Rectangle(4),
    Box(5);

    val hasData: Boolean get() = this != Point
}

internal fun BinaryReader.readShape(type: ShapeType): Shape = when (type) {
    ShapeType.Point -> Shape.Point
    ShapeType.Circle -> Shape.Circle(readFloat())
    ShapeType.Sphere -> Shape.Sphere(readFloat())
    ShapeType.Cylinder -> Shape.Cylinder(readFloat(), readFloat())
    ShapeType.Rectangle -> Shape.Rectangle(readFloat(), readFloat())
    ShapeType.Box -> Shape.Box(readFloat(), readFloat(), readFloat())
}