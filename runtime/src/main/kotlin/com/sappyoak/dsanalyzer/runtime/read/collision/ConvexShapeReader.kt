package com.sappyoak.dsanalyzer.runtime.read.collision

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.BOX_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.BoxShape
import com.sappyoak.dsanalyzer.runtime.ptde.CYLINDER_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.ConvexShape
import com.sappyoak.dsanalyzer.runtime.ptde.CylinderShape
import com.sappyoak.dsanalyzer.runtime.ptde.SHAPE_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.Shape
import com.sappyoak.dsanalyzer.runtime.ptde.TRANSLATE_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.TranslateShape
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.runtime.session.collision.place
import com.sappyoak.dsanalyzer.runtime.session.collision.turn
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3
import kotlin.math.max

internal class ConvexShapeReader {
    private val box = StructView(BOX_SIZE)
    private val translate = StructView(TRANSLATE_SIZE)
    private val cylinder = StructView(CYLINDER_SIZE)
    private val child = StructView(SHAPE_SIZE)
    private val hulls = HullGeometryReader()

    fun read(
        memory: ProcessMemory,
        shapeAt: Address,
        kind: ShapeKind?,
        transform: Mat4
    ): CollisionGeometry = when (kind) {
        ShapeKind.ConvexVertices -> hulls.read(memory, shapeAt, transform)
        ShapeKind.Cylinder -> cylinderAt(memory, shapeAt, transform)
        ShapeKind.ConvexTranslate -> translateAt(memory, shapeAt, transform)
        // A box is usually wrapped by a translate-shape, but nothing says it has to be: unwrapped,
        // its centre is simply the body's own origin
        ShapeKind.Box -> boxAt(memory, shapeAt, Vec3.Companion.Zero, transform, extra = 0f)
        else -> CollisionGeometry.Unread
    }

    private fun boxAt(
        memory: ProcessMemory,
        shapeAt: Address,
        at: Vec3,
        transform: Mat4,
        extra: Float
    ): CollisionGeometry {
        if (!box.refresh(memory, shapeAt)) return CollisionGeometry.Unread

        val half = box.vec3(BoxShape.HalfExtents)

        return CollisionGeometry.Box(
            center = transform.place(at.x, at.y, at.z),
            halfX = transform.turn(half.x, 0f, 0f),
            halfY = transform.turn(0f, half.y, 0f),
            halfZ = transform.turn(0f, 0f, half.z),
            radius = max(box.float(ConvexShape.Radius), extra)
        )
    }

    private fun translateAt(
        memory: ProcessMemory,
        shapeAt: Address,
        transform: Mat4
    ): CollisionGeometry {
        if (!translate.refresh(memory, shapeAt)) return CollisionGeometry.Unread

        val childAt = translate.pointer(TranslateShape.ChildShape)
        if (childAt.isNull || !child.refresh(memory, childAt)) return CollisionGeometry.Unread
        if (ShapeKind.Companion.of(child.int(Shape.Type)) != ShapeKind.Box) return CollisionGeometry.Unread

        return boxAt(
            memory = memory,
            shapeAt = childAt,
            at = translate.vec3(TranslateShape.Translation),
            transform = transform,
            extra = translate.float(ConvexShape.Radius)
        )
    }

    private fun cylinderAt(
        memory: ProcessMemory,
        shapeAt: Address,
        transform: Mat4
    ): CollisionGeometry {
        if (!cylinder.refresh(memory, shapeAt)) return CollisionGeometry.Unread

        val a = cylinder.vec3(CylinderShape.VertexA)
        val b = cylinder.vec3(CylinderShape.VertexB)

        return CollisionGeometry.Cylinder(
            a = transform.place(a.x, a.y, a.z),
            b = transform.place(b.x, b.y, b.z),
            cylRadius = cylinder.float(CylinderShape.CylRadius),
            radius = cylinder.float(ConvexShape.Radius)
        )
    }
}