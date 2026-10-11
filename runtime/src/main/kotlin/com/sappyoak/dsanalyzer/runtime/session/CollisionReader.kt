package com.sappyoak.dsanalyzer.runtime.session

import io.github.oshai.kotlinlogging.KotlinLogging

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
import com.sappyoak.dsanalyzer.runtime.ptde.ShapeKind
import com.sappyoak.dsanalyzer.runtime.ptde.WORLD_SIZE
import com.sappyoak.dsanalyzer.shared.math.Aabb

private const val NAME_LIMIT: Int = 64

private val logger = KotlinLogging.logger {  }

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
    private val geometry = MeshGeometryReader()

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
        var skipped = 0

        for (at in entities.elements) {
            val read = bodyAt(game.memory, at)
            if (read == null || read.mesh == null) skipped++
            if (read != null) bodies += read
        }

        return CollisionSnapshot(bodies = bodies, skipped = skipped)
    }

    private fun bodyAt(memory: ProcessMemory, at: Address): CollisionBody? {
        if (!body.refresh(memory, at)) return null

        val shapeAt = body.pointer(RigidBody.Shape)
        if (shapeAt.isNull || !shape.refresh(memory, shapeAt)) return null

        val kind = ShapeKind.of(shape.int(Shape.Type))
        val label = nameAt(memory, body.pointer(RigidBody.Name))

        if (kind != ShapeKind.Mopp) {
            return CollisionBody(name = label, kind = kind, bounds = Aabb.Empty, mesh = null)
        }
        return moppBody(memory, shapeAt, label)
    }

    private fun moppBody(memory: ProcessMemory, shapeAt: Address, label: String): CollisionBody? {
        if (!mopp.refresh(memory, shapeAt)) return null

        val childAt = mopp.pointer(MoppShape.ChildShape)
        if (childAt.isNull || !mesh.refresh(memory, childAt)) return null

        // Strictness on purpose: the only child kind ever observed is the mesh, and accepting
        // anything else would read a foreign layout through the mesh's offsets and return a mesh
        val childKind = ShapeKind.of(mesh.int(Shape.Type))
        if (childKind != ShapeKind.Mesh) {
            return CollisionBody(name = label, kind = childKind, bounds = Aabb.Empty, mesh = null)
        }

        val bounds = Aabb(
            center = mesh.vec3(MeshShape.AabbCenter),
            halfExtents = mesh.vec3(MeshShape.AabbHalfExtents)
        )

        return CollisionBody(
            name = label,
            kind = ShapeKind.Mopp,
            bounds = bounds,
            mesh = geometry.read(memory, mesh.address)
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