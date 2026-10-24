package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.shared.math.Aabb
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.Vec4

/** One rigid body as a collision file stores it, before the game places it in the world */
public data class StaticBody(
    /** The body's own name: a map collision file's names its part, 'h0000B2' */
    public val name: String,
    /** Rows of three axes then the translation, as the live reader's transforms are */
    public val transform: Mat4,
    public val collisionFilterInfo: Int,
    public val shape: StaticShape
)

public sealed interface StaticShape {
    public val triangleCount: Int

    public data class Mesh(public val subparts: List<MeshSubpart>, public val bounds: Aabb) : StaticShape {
        override val triangleCount: Int get() = subparts.sumOf { it.triangleCount }
    }

    public class Convex(
        public val vertices: FloatArray,
        public val planes: List<Vec4>,
        public val radius: Float
    ) : StaticShape {
        override val triangleCount: Int get() = 0
        public val vertexCount: Int get() = vertices.size / 3
    }

    /** A shape with no reader yet, named so a survey can say which are worth one */
    public data class Unsupported(public val className: String) : StaticShape {
        override val triangleCount: Int get() = 0
    }
}

public class MeshSubpart(
    public val vertices: FloatArray,
    public val indices: IntArray,
    /**
     * FromSoft's per-subpart material id ('CustomMeshParameter' `+0x20`), which picks footstep sounds
     * and hit effects. Null for a plain Havok mesh. What each value means is not mapped yet
     */
    public val materialNameData: Int?
) {
    public val vertexCount: Int get() = vertices.size / 3
    public val triangleCount: Int get() = indices.size / 3

    public fun vertex(index: Int): Vec3 = Vec3(vertices[index * 3], vertices[index * 3 + 1], vertices[index * 3 + 2])
}
