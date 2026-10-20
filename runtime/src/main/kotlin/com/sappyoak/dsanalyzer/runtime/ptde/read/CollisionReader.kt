package com.sappyoak.dsanalyzer.runtime.ptde.read

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
import com.sappyoak.dsanalyzer.runtime.session.collision.ShapeKind
import com.sappyoak.dsanalyzer.runtime.session.collision.transformedBy
import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Mat4

private const val NAME_LIMIT: Int = 64

internal class CollisionReader {

}