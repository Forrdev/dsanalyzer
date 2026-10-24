package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.formats.havok.HavokData
import com.sappyoak.dsanalyzer.formats.havok.HavokFile
import com.sappyoak.dsanalyzer.formats.havok.HkArray
import com.sappyoak.dsanalyzer.shared.math.Aabb

internal const val STORAGE_MESH = "hkpStorageExtendedMeshShape"

/** FromSoft's subclass of [STORAGE_MESH], which adds one parameter block per subpart */
internal const val CUSTOM_PARAM_MESH = "CustomParamStorageExtendedMeshShape"

internal val MESH_CLASSES: Set<String> = setOf(STORAGE_MESH, CUSTOM_PARAM_MESH)

/** 'hkpExtendedMeshShape::IndexStridingType' */
private const val INDICES_INT8 = 1
private const val INDICES_INT16 = 2
private const val INDICES_INT32 = 3

private const val VECTOR4_BYTES = 16

/** An 'hkQsTransform': translation, rotation quaternion, scale, each an 'hkVector4' */
private const val QS_ROTATION = 0x10
private const val QS_SCALE = 0x20

/**
 * A storage mesh's triangle subparts. Their 'vertexBase' and 'indexBase' are not saved — the load
 * points them into the storage — so each is read from the storage object at the same index
 */
internal fun HavokFile.meshAt(at: Int, layout: MeshLayout, fromSoft: Boolean): StaticShape {
    val subparts = data.array(at + layout.trianglesSubparts)
    val storage = pointersIn(at + layout.storage)
    val params = if (fromSoft) pointersIn(at + layout.customParams) else emptyList()

    val read = if (subparts.isEmpty) emptyList() else (0 until minOf(subparts.count, storage.size)).map { index ->
        val subpart = checkNotNull(subparts.offset) + index * layout.subpartSize
        val material = params.getOrNull(index)?.let { data.int(it + layout.materialNameData) }
        data.subpart(subpart, storage[index], material, layout)
    }

    val bounds = Aabb(center = data.vec3(at + layout.aabbCenter), halfExtents = data.vec3(at + layout.aabbHalfExtents))
    return StaticShape.Mesh(read, bounds)
}

private fun HavokData.subpart(at: Int, storage: Int, materialNameData: Int?, layout: MeshLayout): MeshSubpart {
    val triangles = int(at + layout.subpartTriangles)
    val stride = unsignedShort(at + layout.subpartIndexStriding)
    val flip = byte(at + layout.subpartFlip) != 0

    val (indices, width) = when (byte(at + layout.subpartStridingType)) {
        INDICES_INT8 -> array(storage + layout.indices8) to 1
        INDICES_INT16 -> array(storage + layout.indices16) to 2
        INDICES_INT32 -> array(storage + layout.indices32) to 4
        else -> HkArray.Empty to 1
    }

    return MeshSubpart(
        vertices = vertices(storage + layout.vertices, at + layout.subpartTransform),
        indices = triangleIndices(indices, width, stride, triangles, flip),
        materialNameData = materialNameData
    )
}

/** The storage's 'hkVector4' vertices, put through the subpart's transform */
private fun HavokData.vertices(arrayAt: Int, transform: Int): FloatArray {
    val array = array(arrayAt)
    if (array.isEmpty) return FloatArray(0)
    val first = checkNotNull(array.offset)
    val place = QsTransform(vec4(transform), vec4(transform + QS_ROTATION), vec4(transform + QS_SCALE))

    val out = FloatArray(array.count * 3)
    for (i in 0 until array.count) {
        place.apply(vec3(first + i * VECTOR4_BYTES), out, i * 3)
    }
    return out
}

/**
 * Three indices per triangle out of rows [stride] bytes apart, the fourth slot of each row being
 * padding. With [flip] every other triangle is wound the other way, which a strip needs
 */
private fun HavokData.triangleIndices(array: HkArray, width: Int, stride: Int, triangles: Int, flip: Boolean): IntArray {
    val first = array.offset ?: return IntArray(0)
    val rows = if (stride <= 0) 0 else minOf(triangles, array.count * width / stride)
    val out = IntArray(rows * 3)
    for (t in 0 until rows) {
        for (corner in 0 until 3) {
            val at = first + t * stride + corner * width
            val index = when (width) {
                1 -> byte(at)
                2 -> unsignedShort(at)
                else -> int(at)
            }
            val slot = if (flip && t % 2 == 1 && corner > 0) 3 - corner else corner
            out[t * 3 + slot] = index
        }
    }
    return out
}
