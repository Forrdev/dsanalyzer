package com.sappyoak.dsanalyzer.formats.havok

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.havok.packfile.readPackfile
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

private val PACKFILE = readPackfile(collisionFile())

/** The mesh body's collision filter word, which holds a number and no fixup */
private const val MESH_FILTER_AT = 0xB0 + 0x10 + 0x14 + 0x08

class PackfileReaderTest : FunSpec({
    test("the header gives the contents version and the root object") {
        assertSoftly {
            PACKFILE.version shouldBe CONTENTS_VERSION
            PACKFILE.root?.offset shouldBe 0
            PACKFILE.root?.className shouldBe "hkRootLevelContainer"
        }
    }

    test("every object is named by its virtual fixup, in data order") {
        val objects = PACKFILE.objects
        assertSoftly {
            objects.map { it.offset } shouldBe objects.map { it.offset }.sorted()
            objects.count { it.className == "hkpRigidBody" } shouldBe 3
            PACKFILE.objectAt(0x750)?.className shouldBe "CustomParamStorageExtendedMeshShape"
            PACKFILE.objectAt(0x751) shouldBe null
        }
    }

    test("pointers come from the fixups, local and global alike") {
        assertSoftly {
            PACKFILE.data.array(0) shouldBe HkArray(0x10, 1)
            PACKFILE.data.string(0x10) shouldBe "Physics Data"
            PACKFILE.data.pointer(0x18) shouldBe 0x20
        }
    }

    test("a word no fixup names is not a pointer, whatever it holds") {
        assertSoftly {
            PACKFILE.data.pointer(4) shouldBe null
            PACKFILE.data.array(MESH_FILTER_AT) shouldBe HkArray.Empty
        }
    }

    test("a file that is not a 32-bit little endian packfile is turned away") {
        shouldThrow<BinaryFormatException> { readPackfile(collisionFile(magic = 0x12345678)) }
        shouldThrow<BinaryFormatException> { readPackfile(collisionFile(layout = PC_64_LAYOUT)) }
    }
})
