package com.sappyoak.dsanalyzer.runtime.ptde.read.collision

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.MemoryView
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.pointers.TableView
import com.sappyoak.dsanalyzer.runtime.ptde.BODY_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.Havok
import com.sappyoak.dsanalyzer.runtime.ptde.HavokIsland
import com.sappyoak.dsanalyzer.runtime.ptde.HavokWorld
import com.sappyoak.dsanalyzer.runtime.ptde.ISLAND_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.MESH_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.MOPP_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.MeshShape
import com.sappyoak.dsanalyzer.runtime.ptde.MoppShape
import com.sappyoak.dsanalyzer.runtime.ptde.RigidBody
import com.sappyoak.dsanalyzer.runtime.ptde.SHAPE_SIZE
import com.sappyoak.dsanalyzer.runtime.ptde.Shape
import com.sappyoak.dsanalyzer.runtime.ptde.WORLD_SIZE
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionBody
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionGeometry
import com.sappyoak.dsanalyzer.runtime.session.collision.CollisionSnapshot
import com.sappyoak.dsanalyzer.runtime.session.collision.HitFilter
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.runtime.session.collision.transformedBy
import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Mat4

private const val NAME_LIMIT: Int = 64

internal class CollisionReader {
    private val manager = StructView(Havok.Manager)
    private val world = StructView(WORLD_SIZE)
    private val island = StructView(ISLAND_SIZE)
    private val entities = TableView(HavokIsland.Entities)
    private val body = StructView(BODY_SIZE)
    private val shape = StructView(SHAPE_SIZE)
    private val mopp = StructView(MOPP_SIZE)
    private val mesh = StructView(MESH_SIZE)
    private val name = MemoryView(NAME_LIMIT)
    private val meshes = MeshGeometryReader()
    private val convex = ConvexShapeReader()
    private val hitFilters = HitFilterReader()

    fun read(game: GameMemory): CollisionSnapshot {
        if (!manager.refresh(game)) return CollisionSnapshot.Empty

        val worldAt = manager.pointer(Havok.World)
        if (worldAt.isNull || !world.refresh(game.memory, worldAt)) return CollisionSnapshot.Empty

        val islandAt = world.pointer(HavokWorld.FixedIsland)
        if (islandAt.isNull || !island.refresh(game.memory, islandAt)) return CollisionSnapshot.Empty

        // Resolves the stated length and never the capacity. At the title screen the length is 1
        // while the capacity stays 256, and the slots between hold recycled heap memory
        if (!entities.refresh(game.memory, island.address)) return CollisionSnapshot.Empty

        val bodies = ArrayList<CollisionBody>(entities.elements.size)
        var failed = 0

        for (at in entities.elements) {
            // Only a read that never arrived is counted here. A body that was read and has no
            // reader for its shape is in the list carrying `Unread`, and `CollisionSnapshot`
            // derives that count from the list rather than keeping a second tally to disagree with
            val read = bodyAt(game.memory, at)
            if (read == null) failed++ else bodies += read
        }

        return CollisionSnapshot(
            bodies = bodies.withHitFilters(hitFilters.read(game)).resolvingDeathVolumes(),
            failed = failed
        )
    }

    private fun bodyAt(memory: ProcessMemory, at: Address): CollisionBody? {
        if (!body.refresh(memory, at)) return null

        val shapeAt = body.pointer(RigidBody.Shape)
        if (shapeAt.isNull || !shape.refresh(memory, shapeAt)) return null

        val kind = ShapeKind.of(shape.int(Shape.Type))

        // Everything that comes off `body` is taken here, before the shape walk, because the copy
        // is reused by the next element. All four are free: every one of these offsets is inside
        // the BODY_SIZE copy the refresh above already fetched
        val header = RigidBodyHeader(
            label = nameAt(memory, body.pointer(RigidBody.Name)),
            filterInfo = body.int(RigidBody.CollisionFilterInfo),
            allowedPenetration = body.float(RigidBody.AllowedPenetrationDepth),
            transform = body.mat4(RigidBody.Transform)
        )

        if (kind == ShapeKind.Mopp) return moppBody(memory, shapeAt, header)

        // Every other kind is a convex shape, which is parametric and exact — so the bounds come
        // from the parameters rather than from the engine's own `boundingVolumeData`. That field
        // looks free and is not: it is a quantised integer box needing the broadphase's scale and
        // offset to dequantise, and a subtly wrong AABB culls the wrong bodies and says nothing
        return header.describing(kind, convex.read(memory, shapeAt, kind, header.transform))
    }

    private fun moppBody(
        memory: ProcessMemory,
        shapeAt: Address,
        header: RigidBodyHeader
    ): CollisionBody? {
        if (!mopp.refresh(memory, shapeAt)) return null

        val childAt = mopp.pointer(MoppShape.ChildShape)
        if (childAt.isNull || !mesh.refresh(memory, childAt)) return null

        // Strictness on purpose: the only child kind ever observed is the mesh, and accepting
        // anything else would read a foreign layout through the mesh's offsets and return a mesh
        val childKind = ShapeKind.of(mesh.int(Shape.Type))
        if (childKind != ShapeKind.Mesh) {
            return header.describing(childKind, CollisionGeometry.Unread)
        }

        val bounds = Aabb(
            center = mesh.vec3(MeshShape.AabbCenter),
            halfExtents = mesh.vec3(MeshShape.AabbHalfExtents)
        )
        val triangles = meshes.read(memory, mesh.address, header.transform)

        return CollisionBody(
            name = header.label,
            kind = ShapeKind.Mopp,
            filterInfo = header.filterInfo,
            allowedPenetration = header.allowedPenetration,
            bounds = bounds.transformedBy(header.transform),
            geometry = triangles?.let { CollisionGeometry.Mesh(it) } ?: CollisionGeometry.Unread
        )
    }

    private fun nameAt(memory: ProcessMemory, at: Address): String {
        if (at.isNull || !name.refresh(memory, at, NAME_LIMIT)) return ""

        val text = StringBuilder(NAME_LIMIT)
        for (i in 0 until NAME_LIMIT) {
            val character = name.byte(i).toInt() and 0xFF
            if (character == 0) break
            text.append(if (character in 0x20..0x7E) character.toChar() else '?')
        }
        return text.toString()
    }
}