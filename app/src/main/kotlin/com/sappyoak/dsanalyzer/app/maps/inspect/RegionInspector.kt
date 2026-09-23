package com.sappyoak.dsanalyzer.app.maps.inspect

import com.sappyoak.dsanalyzer.formats.msb.model.Model
import com.sappyoak.dsanalyzer.formats.msb.region.Region
import com.sappyoak.dsanalyzer.formats.msb.region.Shape

internal fun regionSections(region: Region): List<InspectorSection> = listOf(
    InspectorSection("Region", rows {
        row("Name", region.name)
        row("Shape", region.shape.describe())
        entityRow("Entity id", region.entityId)
        row("Position", region.position)
        row("Rotation", region.rotation)
    })
)

internal fun modelSections(model: Model): List<InspectorSection> = listOf(
    InspectorSection("Model", rows {
        row("Name", model.name)
        row("Type", model.type)
        row("Placed by", "${model.instanceCount} parts")
        row("Sib path", model.sibPath.ifEmpty { "none" })
    })
)

private fun Shape.describe(): String = when (this) {
    is Shape.Point -> "Point"
    is Shape.Circle -> "Circle, radius $radius"
    is Shape.Sphere -> "Sphere, radius $radius"
    is Shape.Cylinder -> "Cylinder, radius $radius, height $height"
    is Shape.Rectangle -> "Rectangle, $width by $depth"
    is Shape.Box -> "Box, $width by $depth by $height"
}