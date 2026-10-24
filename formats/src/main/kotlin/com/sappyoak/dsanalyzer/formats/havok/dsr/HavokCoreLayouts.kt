// GENERATED FILE - do not edit. Regenerate with:
//
//     ./gradlew :tools:generateDSRHavokLayouts
//
// Source: tools/havok/dsr-reflection.tsv, extracted from DarkSoulsRemastered.exe (hk_2015.1.0-r1, 64-bit)
//
// Core: 432 of 866 reflected classes. Offsets include inherited
// members, flattened along the parent chain; the binary itself stores declared members only.

@file:Suppress("ConstPropertyName", "ObjectPropertyName", "SpellCheckingInspection")

package com.sappyoak.dsanalyzer.formats.havok.dsr

/**
 * `CustomMeshParameter`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `CustomMeshParameter`
 */
public object CustomMeshParameter {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `uint32` */
    public const val Version: Int = 0x10

    /** `hkArray<uint8>` */
    public const val VertexDataBuffer: Int = 0x18

    /** `int32` */
    public const val VertexDataStride: Int = 0x28

    /** `hkArray<uint8>` */
    public const val PrimitiveDataBuffer: Int = 0x30

    /** `uint32` */
    public const val MaterialNameData: Int = 0x40
}

/**
 * `CustomParamMeshMaterial`, 0x4 bytes.
 */
public object CustomParamMeshMaterial {
    public const val ByteSize: Int = 0x4

    /** `struct` */
    public const val PParameter: Int = 0x0
}

/**
 * `CustomParamStorageExtendedMeshShape`, 0x170 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpExtendedMeshShape` -> `hkpStorageExtendedMeshShape` -> `CustomParamStorageExtendedMeshShape`
 */
public object CustomParamStorageExtendedMeshShape {
    public const val ByteSize: Int = 0x170

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `struct`, from `hkpExtendedMeshShape` */
    public const val EmbeddedTrianglesSubpart: Int = 0x30

    /** `hkVector4`, from `hkpExtendedMeshShape` */
    public const val AabbHalfExtents: Int = 0xc0

    /** `hkVector4`, from `hkpExtendedMeshShape` */
    public const val AabbCenter: Int = 0xd0

    /** `void*`, from `hkpExtendedMeshShape` */
    public const val MaterialClass: Int = 0xe0

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val NumBitsForSubpartIndex: Int = 0xe8

    /** `hkArray<struct>`, from `hkpExtendedMeshShape` */
    public const val TrianglesSubparts: Int = 0xf0

    /** `hkArray<struct>`, from `hkpExtendedMeshShape` */
    public const val ShapesSubparts: Int = 0x100

    /** `hkArray<uint16>`, from `hkpExtendedMeshShape` */
    public const val WeldingInfo: Int = 0x110

    /** `enum:uint8`, from `hkpExtendedMeshShape` */
    public const val WeldingType: Int = 0x120

    /** `uint32`, from `hkpExtendedMeshShape` */
    public const val DefaultCollisionFilterInfo: Int = 0x124

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val CachedNumChildShapes: Int = 0x128

    /** `float`, from `hkpExtendedMeshShape` */
    public const val TriangleRadius: Int = 0x12c

    /** `int32`, from `hkpExtendedMeshShape` */
    public const val Padding: Int = 0x130

    /** `hkArray<ptr>`, from `hkpStorageExtendedMeshShape` */
    public const val MeshStorage: Int = 0x140

    /** `hkArray<ptr>`, from `hkpStorageExtendedMeshShape` */
    public const val ShapeStorage: Int = 0x150

    /** `hkArray<ptr>` */
    public const val MaterialArray: Int = 0x160
}

/**
 * `CustomParamStorageMeshShape`, 0xa0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpShapeCollection` -> `hkpMeshShape` -> `hkpStorageMeshShape` -> `CustomParamStorageMeshShape`
 */
public object CustomParamStorageMeshShape {
    public const val ByteSize: Int = 0xa0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `bool`, from `hkpShapeCollection` */
    public const val DisableWelding: Int = 0x28

    /** `enum:uint8`, from `hkpShapeCollection` */
    public const val CollectionType: Int = 0x29

    /** `hkVector4`, from `hkpMeshShape` */
    public const val Scaling: Int = 0x30

    /** `int32`, from `hkpMeshShape` */
    public const val NumBitsForSubpartIndex: Int = 0x40

    /** `hkArray<struct>`, from `hkpMeshShape` */
    public const val Subparts: Int = 0x48

    /** `hkArray<uint16>`, from `hkpMeshShape` */
    public const val WeldingInfo: Int = 0x58

    /** `enum:uint8`, from `hkpMeshShape` */
    public const val WeldingType: Int = 0x68

    /** `float`, from `hkpMeshShape` */
    public const val Radius: Int = 0x6c

    /** `int32[3]`, from `hkpMeshShape` */
    public const val Pad: Int = 0x70

    /** `hkArray<ptr>`, from `hkpStorageMeshShape` */
    public const val Storage: Int = 0x80

    /** `hkArray<ptr>` */
    public const val MaterialArray: Int = 0x90
}

/**
 * `TestContainerThingEnd`, 0xc bytes.
 */
public object TestContainerThingEnd {
    public const val ByteSize: Int = 0xc

    /** `float` */
    public const val Data: Int = 0x0

    /** `float` */
    public const val Data3: Int = 0x4

    /** `int32` */
    public const val Index: Int = 0x8
}

/**
 * `TestContainerThingStart`, 0x8 bytes.
 */
public object TestContainerThingStart {
    public const val ByteSize: Int = 0x8

    /** `int32` */
    public const val Data: Int = 0x0

    /** `int32` */
    public const val Data2: Int = 0x4
}

/**
 * `TestTemplateTopLevelEnd`, 0x18 bytes.
 */
public object TestTemplateTopLevelEnd {
    public const val ByteSize: Int = 0x18

    /** `struct` */
    public const val IntContainer: Int = 0x0

    /** `struct` */
    public const val RealContainer: Int = 0xc
}

/**
 * `TestTemplateTopLevelStart`, 0x8 bytes.
 */
public object TestTemplateTopLevelStart {
    public const val ByteSize: Int = 0x8

    /** `struct` */
    public const val RemovedIntContainer: Int = 0x0
}

/**
 * `TypeRegistryTest::Ref`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `TypeRegistryTest::Ref`
 */
public object TypeRegistryTestRef {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `UnitTest::AfterReflect`, 0x4 bytes.
 */
public object UnitTestAfterReflect {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val I: Int = 0x0
}

/**
 * `UnitTest::AfterReflectWrapper1`, 0x4 bytes.
 */
public object UnitTestAfterReflectWrapper1 {
    public const val ByteSize: Int = 0x4

    /** `struct` */
    public const val Ar: Int = 0x0
}

/**
 * `UnitTest::AfterReflectWrapper2`, 0x40 bytes.
 */
public object UnitTestAfterReflectWrapper2 {
    public const val ByteSize: Int = 0x40

    /** `struct` */
    public const val Ar: Int = 0x0

    /** `struct` */
    public const val Arw: Int = 0x4

    /** `struct[10]` */
    public const val Repeat: Int = 0x8

    /** `hkArray<struct>` */
    public const val Array: Int = 0x30
}

/**
 * `UnitTest::AlignTest0`, 0x20 bytes.
 */
public object UnitTestAlignTest0 {
    public const val ByteSize: Int = 0x20

    /** `int32` */
    public const val First: Int = 0x0

    /** `int32` */
    public const val Aligned: Int = 0x10

    /** `int32[3]` */
    public const val Array: Int = 0x14
}

/**
 * `UnitTest::AlignTest1`, 0x20 bytes.
 */
public object UnitTestAlignTest1 {
    public const val ByteSize: Int = 0x20

    /** `int32` */
    public const val First: Int = 0x0

    /** `int32` */
    public const val Aligned: Int = 0x10

    /** `int32[3]` */
    public const val Array: Int = 0x14
}

/**
 * `UnitTest::AlignTest2`, 0x10 bytes.
 */
public object UnitTestAlignTest2 {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val First: Int = 0x0

    /** `int32` */
    public const val Aligned: Int = 0x4

    /** `int32[1]` */
    public const val Array: Int = 0x8
}

/**
 * `UnitTest::AlignTest3`, 0x20 bytes.
 */
public object UnitTestAlignTest3 {
    public const val ByteSize: Int = 0x20

    /** `int32` */
    public const val First: Int = 0x0

    /** `int32` */
    public const val Aligned: Int = 0x8

    /** `int32[5]` */
    public const val Array: Int = 0xc
}

/**
 * `UnitTest::ArrayOfArrayOfVirtuals`, 0x18 bytes.
 */
public object UnitTestArrayOfArrayOfVirtuals {
    public const val ByteSize: Int = 0x18

    /** `int32` */
    public const val MemA: Int = 0x0

    /** `hkArray<struct>` */
    public const val Arr: Int = 0x8
}

/**
 * `UnitTest::ArrayOfRefPtrs`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::ArrayOfRefPtrs`
 */
public object UnitTestArrayOfRefPtrs {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Child: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Ptrs: Int = 0x18
}

/**
 * `UnitTest::ArrayOfVirtuals`, 0x18 bytes.
 */
public object UnitTestArrayOfVirtuals {
    public const val ByteSize: Int = 0x18

    /** `int32` */
    public const val MemA: Int = 0x0

    /** `hkArray<struct>` */
    public const val Arr: Int = 0x8
}

/**
 * `UnitTest::ArrayTest`, 0x18 bytes.
 *
 * `UnitTest::ArrayTestParent` -> `UnitTest::ArrayTest`
 */
public object UnitTestArrayTest {
    public const val ByteSize: Int = 0x18

    /** `hkArray<struct>`, from `UnitTest::ArrayTestParent` */
    public const val Array: Int = 0x0

    /** `hkStringPtr` */
    public const val Name: Int = 0x10
}

/**
 * `UnitTest::ArrayTestElem`, 0x18 bytes.
 */
public object UnitTestArrayTestElem {
    public const val ByteSize: Int = 0x18

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `hkArray<hkStringPtr>` */
    public const val Strings: Int = 0x8
}

/**
 * `UnitTest::ArrayTestParent`, 0x10 bytes.
 */
public object UnitTestArrayTestParent {
    public const val ByteSize: Int = 0x10

    /** `hkArray<struct>` */
    public const val Array: Int = 0x0
}

/**
 * `UnitTest::BiggerStruct`, 0x18 bytes.
 */
public object UnitTestBiggerStruct {
    public const val ByteSize: Int = 0x18

    /** `hkUlong` */
    public const val Pad0: Int = 0x0

    /** `struct` */
    public const val Little: Int = 0x8

    /** `hkUlong` */
    public const val Pad1: Int = 0x10
}

/**
 * `UnitTest::CArrayOfhkArray`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::CArrayOfhkArray`
 */
public object UnitTestCArrayOfhkArray {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>[3]` */
    public const val Arrays: Int = 0x10
}

/**
 * `UnitTest::ClassTest0`, 0x8 bytes.
 */
public object UnitTestClassTest0 {
    public const val ByteSize: Int = 0x8

    /** `float` */
    public const val Field0: Int = 0x0
}

/**
 * `UnitTest::ClassTest1`, 0x8 bytes.
 */
public object UnitTestClassTest1 {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val Field0: Int = 0x0
}

/**
 * `UnitTest::ClassTest2`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::ClassTest2`
 */
public object UnitTestClassTest2 {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Field0: Int = 0x10
}

/**
 * `UnitTest::ClassTest3`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::ClassTest3`
 */
public object UnitTestClassTest3 {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Field0: Int = 0x10
}

/**
 * `UnitTest::ClassWithArrayOfClassWithClassWithHashMap`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::ClassWithArrayOfClassWithClassWithHashMap`
 */
public object UnitTestClassWithArrayOfClassWithClassWithHashMap {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Arr: Int = 0x10
}

/**
 * `UnitTest::ClassWithClassWithHashMap`, 0x70 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::ClassWithClassWithHashMap`
 */
public object UnitTestClassWithClassWithHashMap {
    public const val ByteSize: Int = 0x70

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val Rec: Int = 0x10
}

/**
 * `UnitTest::ClassWithHashMap`, 0x60 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::ClassWithHashMap`
 */
public object UnitTestClassWithHashMap {
    public const val ByteSize: Int = 0x60

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>` */
    public const val Keys: Int = 0x10

    /** `struct` */
    public const val HashMap: Int = 0x20

    /** `struct` */
    public const val HashSet: Int = 0x40
}

/**
 * `UnitTest::ColorHSV`, 0xc bytes.
 */
public object UnitTestColorHSV {
    public const val ByteSize: Int = 0xc

    /** `float` */
    public const val H: Int = 0x0

    /** `float` */
    public const val S: Int = 0x4

    /** `float` */
    public const val V: Int = 0x8
}

/**
 * `UnitTest::ColorHSV2`, 0x60 bytes.
 */
public object UnitTestColorHSV2 {
    public const val ByteSize: Int = 0x60

    /** `float` */
    public const val H: Int = 0x0

    /** `float` */
    public const val S: Int = 0x4

    /** `float` */
    public const val V: Int = 0x8

    /** `hkUlong[10]` */
    public const val Pad: Int = 0x10
}

/**
 * `UnitTest::ColorRGB`, 0x14 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestColorRGB {
    public const val ByteSize: Int = 0x14
}

/**
 * `UnitTest::CustomFormatter`, 0x9 bytes.
 */
public object UnitTestCustomFormatter {
    public const val ByteSize: Int = 0x9

    /** `hkStringPtr` */
    public const val Buf: Int = 0x0
}

/**
 * `UnitTest::EmbedWithVtable`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::EmbedWithVtable`
 */
public object UnitTestEmbedWithVtable {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float` */
    public const val Float: Int = 0x10
}

/**
 * `UnitTest::EmptyClass0`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::EmptyClass0`
 */
public object UnitTestEmptyClass0 {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `UnitTest::EmptyClass1`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::EmptyClass1`
 */
public object UnitTestEmptyClass1 {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `UnitTest::Interface`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::Interface`
 */
public object UnitTestInterface {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `UnitTest::Interface0`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestInterface0 {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTest::Interface1`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestInterface1 {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTest::InterfaceImpl0`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::Interface` -> `UnitTest::InterfaceImpl0`
 */
public object UnitTestInterfaceImpl0 {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `UnitTest::InterfaceImpl1`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::Interface` -> `UnitTest::InterfaceImpl1`
 */
public object UnitTestInterfaceImpl1 {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `UnitTest::InterfaceSynthetic`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::Interface` -> `UnitTest::InterfaceSynthetic`
 */
public object UnitTestInterfaceSynthetic {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val SyntheticUtil: Int = 0x10
}

/**
 * `UnitTest::InterfaceUnregistered0`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::Interface` -> `UnitTest::InterfaceUnregistered0`
 */
public object UnitTestInterfaceUnregistered0 {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `int32` */
    public const val Int: Int = 0x10

    /** `hkStringPtr` */
    public const val Foo: Int = 0x18
}

/**
 * `UnitTest::InterfaceUnregistered1`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::Interface` -> `UnitTest::InterfaceUnregistered0` -> `UnitTest::InterfaceUnregistered1`
 */
public object UnitTestInterfaceUnregistered1 {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `int32`, from `UnitTest::InterfaceUnregistered0` */
    public const val Int: Int = 0x10

    /** `hkStringPtr`, from `UnitTest::InterfaceUnregistered0` */
    public const val Foo: Int = 0x18

    /** `hkVector4` */
    public const val Pos: Int = 0x20

    /** `hkArray<hkStringPtr>` */
    public const val Strings: Int = 0x30
}

/**
 * `UnitTest::LittleStruct`, 0x2 bytes.
 */
public object UnitTestLittleStruct {
    public const val ByteSize: Int = 0x2

    /** `char` */
    public const val A: Int = 0x0

    /** `char` */
    public const val B: Int = 0x1
}

/**
 * `UnitTest::MultipleInheritance`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::MultipleInheritance`
 */
public object UnitTestMultipleInheritance {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float` */
    public const val Double: Int = 0x20
}

/**
 * `UnitTest::NativeArrayTest`, 0x40 bytes.
 */
public object UnitTestNativeArrayTest {
    public const val ByteSize: Int = 0x40

    /** `hkArray<struct>` */
    public const val EmbedTypehkArray: Int = 0x0

    /** `hkArray<int32>` */
    public const val EmptyArray: Int = 0x10

    /** `hkArray<int32>` */
    public const val SimpleArray: Int = 0x20

    /** `hkArray<float>` */
    public const val RealArray: Int = 0x30
}

/**
 * `UnitTest::ObjKey`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::ObjKey`
 */
public object UnitTestObjKey {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `int32` */
    public const val I: Int = 0x10
}

/**
 * `UnitTest::OpaqueTest`, 0x20 bytes.
 */
public object UnitTestOpaqueTest {
    public const val ByteSize: Int = 0x20

    /** `float` */
    public const val F: Int = 0x0

    /** `void*` */
    public const val NrPtr: Int = 0x8

    /** `hkArray<void>` */
    public const val NrArray: Int = 0x10
}

/**
 * `UnitTest::PointerToArray`, 0x10 bytes.
 */
public object UnitTestPointerToArray {
    public const val ByteSize: Int = 0x10

    /** `hkVector4*` */
    public const val Vec: Int = 0x0

    /** `array*` */
    public const val Arr: Int = 0x8
}

/**
 * `UnitTest::PropertyContainer`, 0x20 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestPropertyContainer {
    public const val ByteSize: Int = 0x20
}

/**
 * `UnitTest::Ref0`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::Ref0`
 */
public object UnitTestRef0 {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `int32` */
    public const val I0: Int = 0x10

    /** `float` */
    public const val D: Int = 0x18
}

/**
 * `UnitTest::Ref1`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::Ref1`
 */
public object UnitTestRef1 {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val S: Int = 0x10
}

/**
 * `UnitTest::Reflected`, 0x10 bytes.
 *
 * `UnitTest::SimplePtr` -> `UnitTest::Reflected`
 */
public object UnitTestReflected {
    public const val ByteSize: Int = 0x10

    /** `void*`, from `UnitTest::SimplePtr` */
    public const val Ptr: Int = 0x0

    /** `void*` */
    public const val Ptr2: Int = 0x8
}

/**
 * `UnitTest::SemanticArrayTest`, 0xf0 bytes.
 */
public object UnitTestSemanticArrayTest {
    public const val ByteSize: Int = 0xf0

    /** `hkArray<struct>` */
    public const val EmbedTypeArray: Int = 0x0

    /** `hkArray<int32>` */
    public const val EmptyArray: Int = 0x30

    /** `hkArray<int32>` */
    public const val SimpleArray: Int = 0x60

    /** `hkArray<float>` */
    public const val RealArray: Int = 0x90

    /** `hkArray<uint8>` */
    public const val RealSize: Int = 0xc0
}

/**
 * `UnitTest::SimplePtr`, 0x8 bytes.
 */
public object UnitTestSimplePtr {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Ptr: Int = 0x0
}

/**
 * `UnitTest::StringTest`, 0x40 bytes.
 */
public object UnitTestStringTest {
    public const val ByteSize: Int = 0x40

    /** `hkStringPtr` */
    public const val EmptyString: Int = 0x0

    /** `hkStringPtr` */
    public const val String1: Int = 0x8

    /** `hkStringPtr` */
    public const val String2: Int = 0x10

    /** `hkStringPtr` */
    public const val EmptyStringAgain: Int = 0x18

    /** `hkStringPtr` */
    public const val String1again: Int = 0x20

    /** `hkStringPtr` */
    public const val NullString: Int = 0x28

    /** `hkArray<hkStringPtr>` */
    public const val StringArray: Int = 0x30
}

/**
 * `UnitTest::SyntheticUtil`, 0x8 bytes.
 */
public object UnitTestSyntheticUtil {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Type: Int = 0x0
}

/**
 * `UnitTest::TemplateTest_A`, 0x4 bytes.
 */
public object UnitTestTemplateTest_A {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val T: Int = 0x0
}

/**
 * `UnitTest::TemplateTest_B`, 0x10 bytes.
 *
 * `UnitTest::TemplateTest_A` -> `UnitTest::TemplateTest_B`
 */
public object UnitTestTemplateTest_B {
    public const val ByteSize: Int = 0x10

    /** `int32`, from `UnitTest::TemplateTest_A` */
    public const val T: Int = 0x0

    /** `struct` */
    public const val DA: Int = 0x8
}

/**
 * `UnitTest::TestCharArray`, 0x3c bytes.
 */
public object UnitTestTestCharArray {
    public const val ByteSize: Int = 0x3c

    /** `hkStringPtr` */
    public const val Chars: Int = 0x0
}

/**
 * `UnitTest::TestComplicated`, 0x38 bytes.
 */
public object UnitTestTestComplicated {
    public const val ByteSize: Int = 0x38

    /** `hkArray<hkStringPtr>` */
    public const val Names: Int = 0x0

    /** `hkArray<struct>` */
    public const val Nests: Int = 0x18

    /** `struct*` */
    public const val Root: Int = 0x30
}

/**
 * `UnitTest::TestComplicated::Nested`, 0x10 bytes.
 */
public object UnitTestTestComplicatedNested {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val Ref: Int = 0x0

    /** `hkStringPtr` */
    public const val ContainerName: Int = 0x8
}

/**
 * `UnitTest::TestComplicated::NestedRef`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::TestComplicated::NestedRef`
 */
public object UnitTestTestComplicatedNestedRef {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Name: Int = 0x10

    /** `hkArray<int32>` */
    public const val Data: Int = 0x30
}

/**
 * `UnitTest::TestDynamic`, 0x20 bytes.
 */
public object UnitTestTestDynamic {
    public const val ByteSize: Int = 0x20

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `hkArray<void>` */
    public const val Data: Int = 0x8
}

/**
 * `UnitTest::TestString`, 0x20 bytes.
 */
public object UnitTestTestString {
    public const val ByteSize: Int = 0x20

    /** `hkStringPtr` */
    public const val Str: Int = 0x0
}

/**
 * `UnitTest::TestVector`, 0x18 bytes.
 */
public object UnitTestTestVector {
    public const val ByteSize: Int = 0x18

    /** `hkArray<uint16>` */
    public const val Vec: Int = 0x0
}

/**
 * `UnitTest::TestVectorString`, 0x18 bytes.
 */
public object UnitTestTestVectorString {
    public const val ByteSize: Int = 0x18

    /** `hkArray<hkStringPtr>` */
    public const val VecStr: Int = 0x0
}

/**
 * `UnitTest::TrackerTestA`, 0x38 bytes.
 *
 * `UnitTest::SimplePtr` -> `UnitTest::Reflected` -> `UnitTest::TrackerTestA`
 */
public object UnitTestTrackerTestA {
    public const val ByteSize: Int = 0x38

    /** `void*`, from `UnitTest::SimplePtr` */
    public const val Ptr: Int = 0x0

    /** `void*`, from `UnitTest::Reflected` */
    public const val Ptr2: Int = 0x8

    /** `struct*` */
    public const val PtrA: Int = 0x10

    /** `type9` */
    public const val L: Int = 0x18

    /** `void*` */
    public const val PtrB: Int = 0x20

    /** `struct` */
    public const val Reflected: Int = 0x28
}

/**
 * `UnitTest::TupleTest`, 0x14 bytes.
 */
public object UnitTestTupleTest {
    public const val ByteSize: Int = 0x14

    /** `struct` */
    public const val Tuple1: Int = 0x0

    /** `struct` */
    public const val Tuple2: Int = 0x8

    /** `uint16` */
    public const val Integer: Int = 0x10
}

/**
 * `UnitTest::TupleTest::NestedFinishCtor`, 0x4 bytes.
 */
public object UnitTestTupleTestNestedFinishCtor {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val Field: Int = 0x0
}

/**
 * `UnitTest::TupleTest::NestedNonFinishCtor`, 0x4 bytes.
 */
public object UnitTestTupleTestNestedNonFinishCtor {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val Field: Int = 0x0
}

/**
 * `UnitTest::TypeCycleTest_A`, 0x18 bytes.
 *
 * `UnitTest::TypeCycleTest_Base` -> `UnitTest::TypeCycleTest_A`
 */
public object UnitTestTypeCycleTest_A {
    public const val ByteSize: Int = 0x18

    /** `struct*`, from `UnitTest::TypeCycleTest_Base` */
    public const val Y: Int = 0x0

    /** `int32`, from `UnitTest::TypeCycleTest_Base` */
    public const val Int: Int = 0x8

    /** `float` */
    public const val Double: Int = 0x10
}

/**
 * `UnitTest::TypeCycleTest_Base`, 0x10 bytes.
 */
public object UnitTestTypeCycleTest_Base {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val Y: Int = 0x0

    /** `int32` */
    public const val Int: Int = 0x8
}

/**
 * `UnitTest::TypeCycleTest_X`, 0x10 bytes.
 *
 * `UnitTest::TypeCycleTest_Base` -> `UnitTest::TypeCycleTest_X`
 */
public object UnitTestTypeCycleTest_X {
    public const val ByteSize: Int = 0x10

    /** `struct*`, from `UnitTest::TypeCycleTest_Base` */
    public const val Y: Int = 0x0

    /** `int32`, from `UnitTest::TypeCycleTest_Base` */
    public const val Int: Int = 0x8
}

/**
 * `UnitTest::TypeCycleTest_Y`, 0x18 bytes.
 *
 * `UnitTest::TypeCycleTest_Base` -> `UnitTest::TypeCycleTest_X` -> `UnitTest::TypeCycleTest_Y`
 */
public object UnitTestTypeCycleTest_Y {
    public const val ByteSize: Int = 0x18

    /** `struct*`, from `UnitTest::TypeCycleTest_Base` */
    public const val Y: Int = 0x0

    /** `int32`, from `UnitTest::TypeCycleTest_Base` */
    public const val Int: Int = 0x8

    /** `uint16` */
    public const val ShortInt: Int = 0x10
}

/**
 * `UnitTest::TypeVm::Bar`, 0x18 bytes.
 */
public object UnitTestTypeVmBar {
    public const val ByteSize: Int = 0x18

    /** `int32` */
    public const val Int: Int = 0x0

    /** `float[2]` */
    public const val Floats: Int = 0x4

    /** `struct` */
    public const val Embedded: Int = 0x10
}

/**
 * `UnitTest::TypeVm::Foo`, 0x8 bytes.
 */
public object UnitTestTypeVmFoo {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Ptr: Int = 0x0
}

/**
 * `UnitTest::TypeVm::Record`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::TypeVm::Record`
 */
public object UnitTestTypeVmRecord {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `int32` */
    public const val Int: Int = 0x10

    /** `float[2]` */
    public const val Floats: Int = 0x14

    /** `struct` */
    public const val Embedded: Int = 0x20

    /** `hkArray<char>` */
    public const val Array: Int = 0x38

    /** `hkStringPtr` */
    public const val String: Int = 0x48
}

/**
 * `UnitTest::UrlObject::Body`, 0x10 bytes.
 */
public object UnitTestUrlObjectBody {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val Shape: Int = 0x0

    /** `struct*` */
    public const val Child: Int = 0x8
}

/**
 * `UnitTest::UrlObject::ObjectWithTypedNull`, 0x18 bytes.
 */
public object UnitTestUrlObjectObjectWithTypedNull {
    public const val ByteSize: Int = 0x18

    /** `hkArray<ptr>` */
    public const val TypeRef: Int = 0x0

    /** `hkStringPtr` */
    public const val Name: Int = 0x10
}

/**
 * `UnitTest::UrlObject::Shape`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::UrlObject::Shape`
 */
public object UnitTestUrlObjectShape {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `float` */
    public const val Size: Int = 0x10
}

/**
 * `UnitTest::UrlObject::TestGraph`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::UrlObject::TestGraph`
 */
public object UnitTestUrlObjectTestGraph {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `int32` */
    public const val Id: Int = 0x10

    /** `struct*` */
    public const val ChildA: Int = 0x18

    /** `struct*` */
    public const val ChildB: Int = 0x20

    /** `struct*` */
    public const val ChildC: Int = 0x28
}

/**
 * `UnitTest::UrlObject::VariantTest`, 0x40 bytes.
 */
public object UnitTestUrlObjectVariantTest {
    public const val ByteSize: Int = 0x40

    /** `hkArray<void>` */
    public const val Array1: Int = 0x0

    /** `int32` */
    public const val A1: Int = 0x18

    /** `hkArray<void>` */
    public const val Array2: Int = 0x20

    /** `float` */
    public const val A4: Int = 0x38
}

/**
 * `UnitTest::VariantArrayTest`, 0x18 bytes.
 */
public object UnitTestVariantArrayTest {
    public const val ByteSize: Int = 0x18

    /** `hkArray<void>` */
    public const val Array: Int = 0x0
}

/**
 * `UnitTest::VariantTestWrapper`, 0x30 bytes.
 */
public object UnitTestVariantTestWrapper {
    public const val ByteSize: Int = 0x30

    /** `hkMatrix3` */
    public const val Matrix: Int = 0x0
}

/**
 * `UnitTest::WithEmpty`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::WithEmpty`
 */
public object UnitTestWithEmpty {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val `0`: Int = 0x10

    /** `struct*` */
    public const val `1`: Int = 0x18
}

/**
 * `UnitTest::WithProperty`, 0x4 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestWithProperty {
    public const val ByteSize: Int = 0x4
}

/**
 * `UnitTest::WithTypeRef`, 0x38 bytes.
 */
public object UnitTestWithTypeRef {
    public const val ByteSize: Int = 0x38

    /** `float` */
    public const val Float: Int = 0x0

    /** `struct*` */
    public const val String: Int = 0x8

    /** `void*` */
    public const val Type0: Int = 0x10

    /** `void*` */
    public const val Type1: Int = 0x18

    /** `void*` */
    public const val Type2: Int = 0x20

    /** `void*` */
    public const val Type3: Int = 0x28

    /** `void*` */
    public const val Type4: Int = 0x30
}

/**
 * `UnitTest::hkTFEmbedType`, 0x4 bytes.
 */
public object UnitTestHkTFEmbedType {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val Emb_b: Int = 0x0
}

/**
 * `UnitTest::hkTFPatchParent`, 0x4 bytes.
 */
public object UnitTestHkTFPatchParent {
    public const val ByteSize: Int = 0x4

    /** `float` */
    public const val Mem: Int = 0x0
}

/**
 * `UnitTest::hkTFSecondEmbedType`, 0x10 bytes.
 */
public object UnitTestHkTFSecondEmbedType {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val Emb_c: Int = 0x0

    /** `struct*` */
    public const val Tnp: Int = 0x8
}

/**
 * `UnitTest::hkTFTestNewPatching`, 0x48 bytes.
 *
 * `UnitTest::hkTFPatchParent` -> `UnitTest::hkTFTestNewPatching`
 */
public object UnitTestHkTFTestNewPatching {
    public const val ByteSize: Int = 0x48

    /** `float`, from `UnitTest::hkTFPatchParent` */
    public const val Mem: Int = 0x0

    /** `int32` */
    public const val A: Int = 0x4

    /** `struct` */
    public const val Embed: Int = 0x8

    /** `int32` */
    public const val E: Int = 0xc

    /** `int32` */
    public const val Add_rem_add: Int = 0x10

    /** `struct` */
    public const val Embed2: Int = 0x18

    /** `struct*` */
    public const val EmbedPtr: Int = 0x28

    /** `struct*` */
    public const val Ptr: Int = 0x30

    /** `int32[4]` */
    public const val Array: Int = 0x38
}

/**
 * `UnitTest::hkTestGeometryContainer`, 0x18 bytes.
 */
public object UnitTestHkTestGeometryContainer {
    public const val ByteSize: Int = 0x18

    /** `struct*` */
    public const val Obj: Int = 0x0

    /** `struct*` */
    public const val Link: Int = 0x8

    /** `int32` */
    public const val A_member: Int = 0x10
}

/**
 * `UnitTest::hkTestRecursive`, 0x60 bytes.
 */
public object UnitTestHkTestRecursive {
    public const val ByteSize: Int = 0x60

    /** `int32` */
    public const val Int0: Int = 0x0

    /** `int64` */
    public const val U64: Int = 0x8

    /** `struct` */
    public const val Nested: Int = 0x10

    /** `hkUlong` */
    public const val Ulong: Int = 0x38

    /** `float` */
    public const val Real: Int = 0x40

    /** `hkVector4` */
    public const val Vector: Int = 0x50
}

/**
 * `UnitTest::hkTestRecursive::Nested`, 0x28 bytes.
 */
public object UnitTestHkTestRecursiveNested {
    public const val ByteSize: Int = 0x28

    /** `float` */
    public const val Double0: Int = 0x0

    /** `struct*` */
    public const val Ptr: Int = 0x8

    /** `type9[2]` */
    public const val Array: Int = 0x10

    /** `float` */
    public const val Real: Int = 0x20
}

/**
 * `UnitTest::hkTestRelArray`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTest::hkTestRelArray`
 */
public object UnitTestHkTestRelArray {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int32>` */
    public const val Array: Int = 0x10
}

/**
 * `UnitTestClang::Abstract1`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangAbstract1 {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTestClang::Abstract2`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangAbstract2 {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTestClang::Abstract3`, 0x8 bytes.
 *
 * `UnitTestClang::Abstract1` -> `UnitTestClang::Abstract3`
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangAbstract3 {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTestClang::Abstract4`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTestClang::Abstract4`
 */
public object UnitTestClangAbstract4 {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `UnitTestClang::AfterReflect::BaseHas`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangAfterReflectBaseHas {
    public const val ByteSize: Int = 0x1
}

/**
 * `UnitTestClang::AfterReflect::BaseVtable`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangAfterReflectBaseVtable {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTestClang::AfterReflect::Derived2`, 0x28 bytes.
 *
 * `UnitTestClang::AfterReflect::BaseVtable` -> `UnitTestClang::AfterReflect::Derived2`
 */
public object UnitTestClangAfterReflectDerived2 {
    public const val ByteSize: Int = 0x28

    /** `float` */
    public const val Foo: Int = 0x18

    /** `struct` */
    public const val Base_inDerived2: Int = 0x20
}

/**
 * `UnitTestClang::AfterReflect::DerivedHas`, 0x30 bytes.
 *
 * `UnitTestClang::AfterReflect::Indirect2` -> `UnitTestClang::AfterReflect::DerivedHas`
 */
public object UnitTestClangAfterReflectDerivedHas {
    public const val ByteSize: Int = 0x30

    /** `struct`, from `UnitTestClang::AfterReflect::Indirect2` */
    public const val BaseHas_in2: Int = 0x0

    /** `struct`, from `UnitTestClang::AfterReflect::Indirect2` */
    public const val Indirect1_in2: Int = 0x1

    /** `struct`, from `UnitTestClang::AfterReflect::Indirect2` */
    public const val Base_in2: Int = 0x8

    /** `struct` */
    public const val Indirect1_inDerived: Int = 0x10

    /** `struct` */
    public const val Base_inDerived: Int = 0x18

    /** `struct` */
    public const val Indirect3_inDerived: Int = 0x20
}

/**
 * `UnitTestClang::AfterReflect::Indirect1`, 0x1 bytes.
 */
public object UnitTestClangAfterReflectIndirect1 {
    public const val ByteSize: Int = 0x1

    /** `struct` */
    public const val HasFunc_in1: Int = 0x0
}

/**
 * `UnitTestClang::AfterReflect::Indirect2`, 0x10 bytes.
 */
public object UnitTestClangAfterReflectIndirect2 {
    public const val ByteSize: Int = 0x10

    /** `struct` */
    public const val BaseHas_in2: Int = 0x0

    /** `struct` */
    public const val Indirect1_in2: Int = 0x1

    /** `struct` */
    public const val Base_in2: Int = 0x8
}

/**
 * `UnitTestClang::AfterReflect::Indirect3`, 0x10 bytes.
 */
public object UnitTestClangAfterReflectIndirect3 {
    public const val ByteSize: Int = 0x10

    /** `hkUlong` */
    public const val Padding: Int = 0x0

    /** `struct` */
    public const val Base_in3: Int = 0x8
}

/**
 * `UnitTestClang::AfterReflect::Indirect4`, 0x28 bytes.
 */
public object UnitTestClangAfterReflectIndirect4 {
    public const val ByteSize: Int = 0x28

    /** `struct` */
    public const val Derived2_in4: Int = 0x0
}

/**
 * `UnitTestClang::AfterReflect::Interface1`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangAfterReflectInterface1 {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTestClang::AfterReflect::Interface2`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangAfterReflectInterface2 {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTestClang::Aligned_Class`, 0x10 bytes.
 *
 * `UnitTestClang::Aligned_Class_Base` -> `UnitTestClang::Aligned_Class`
 */
public object UnitTestClangAligned_Class {
    public const val ByteSize: Int = 0x10

    /** `uint8`, from `UnitTestClang::Aligned_Class_Base` */
    public const val Tag: Int = 0x0

    /** `int32` */
    public const val Val: Int = 0x4
}

/**
 * `UnitTestClang::Aligned_Class_Base`, 0x10 bytes.
 */
public object UnitTestClangAligned_Class_Base {
    public const val ByteSize: Int = 0x10

    /** `uint8` */
    public const val Tag: Int = 0x0
}

/**
 * `UnitTestClang::Aligned_Field`, 0x20 bytes.
 *
 * `UnitTestClang::Aligned_Field_Base` -> `UnitTestClang::Aligned_Field`
 */
public object UnitTestClangAligned_Field {
    public const val ByteSize: Int = 0x20

    /** `uint8`, from `UnitTestClang::Aligned_Field_Base` */
    public const val Tag: Int = 0x0

    /** `int32` */
    public const val Val: Int = 0x10
}

/**
 * `UnitTestClang::Aligned_Field_Base`, 0x10 bytes.
 */
public object UnitTestClangAligned_Field_Base {
    public const val ByteSize: Int = 0x10

    /** `uint8` */
    public const val Tag: Int = 0x0
}

/**
 * `UnitTestClang::ArrayAttr`, 0x10 bytes.
 *
 * `hk::ValueAttribute` -> `UnitTestClang::ArrayAttr`
 */
public object UnitTestClangArrayAttr {
    public const val ByteSize: Int = 0x10

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `UnitTestClang::ArrayAttrDef`, 0x10 bytes.
 */
public object UnitTestClangArrayAttrDef {
    public const val ByteSize: Int = 0x10

    /** `hkArray<int32>` */
    public const val Value: Int = 0x0
}

/**
 * `UnitTestClang::ArrayAttrEmptyDef`, 0x10 bytes.
 */
public object UnitTestClangArrayAttrEmptyDef {
    public const val ByteSize: Int = 0x10

    /** `hkArray<int32>` */
    public const val Value: Int = 0x0
}

/**
 * `UnitTestClang::CastAttr`, 0x8 bytes.
 */
public object UnitTestClangCastAttr {
    public const val ByteSize: Int = 0x8

    /** `hkUlong` */
    public const val Long: Int = 0x0
}

/**
 * `UnitTestClang::Mixed_Class`, 0x10 bytes.
 *
 * `UnitTestClang::Mixed_Class_Base` -> `UnitTestClang::Mixed_Class`
 */
public object UnitTestClangMixed_Class {
    public const val ByteSize: Int = 0x10

    /** `uint8`, from `UnitTestClang::Mixed_Class_Base` */
    public const val Tag: Int = 0x0

    /** `int16` */
    public const val Val: Int = 0x2
}

/**
 * `UnitTestClang::Mixed_Class2`, 0x10 bytes.
 *
 * `UnitTestClang::Mixed_Class_Base` -> `UnitTestClang::Mixed_Class` -> `UnitTestClang::Mixed_Class2`
 */
public object UnitTestClangMixed_Class2 {
    public const val ByteSize: Int = 0x10

    /** `uint8`, from `UnitTestClang::Mixed_Class_Base` */
    public const val Tag: Int = 0x0

    /** `int16`, from `UnitTestClang::Mixed_Class` */
    public const val Val: Int = 0x2

    /** `int32` */
    public const val Val2: Int = 0x4
}

/**
 * `UnitTestClang::Mixed_Class_Base`, 0x1 bytes.
 */
public object UnitTestClangMixed_Class_Base {
    public const val ByteSize: Int = 0x1

    /** `uint8` */
    public const val Tag: Int = 0x0
}

/**
 * `UnitTestClang::NonReflected::Nested`, 0x4 bytes.
 */
public object UnitTestClangNonReflectedNested {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val I: Int = 0x0
}

/**
 * `UnitTestClang::NotAbstract`, 0x8 bytes.
 *
 * `UnitTestClang::Abstract1` -> `UnitTestClang::Abstract3` -> `UnitTestClang::NotAbstract`
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangNotAbstract {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTestClang::OldMacro`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangOldMacro {
    public const val ByteSize: Int = 0x1
}

/**
 * `UnitTestClang::PrivateCtor`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangPrivateCtor {
    public const val ByteSize: Int = 0x1
}

/**
 * `UnitTestClang::SimpleAttr`, 0x4 bytes.
 *
 * `hk::ValueAttribute` -> `UnitTestClang::SimpleAttr`
 */
public object UnitTestClangSimpleAttr {
    public const val ByteSize: Int = 0x4

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `UnitTestClang::Specializations`, 0x18 bytes.
 */
public object UnitTestClangSpecializations {
    public const val ByteSize: Int = 0x18

    /** `struct` */
    public const val `0`: Int = 0x0

    /** `struct` */
    public const val `1`: Int = 0x8

    /** `struct` */
    public const val `2`: Int = 0x10
}

/**
 * `UnitTestClang::TemplateFunctions`, 0x4 bytes.
 */
public object UnitTestClangTemplateFunctions {
    public const val ByteSize: Int = 0x4

    /** `float` */
    public const val Value: Int = 0x0
}

/**
 * `UnitTestClang::TestArrayAttr`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangTestArrayAttr {
    public const val ByteSize: Int = 0x1
}

/**
 * `UnitTestClang::TestArrayAttrDefaults`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangTestArrayAttrDefaults {
    public const val ByteSize: Int = 0x1
}

/**
 * `UnitTestClang::TestCastAttr`, 0xc bytes.
 */
public object UnitTestClangTestCastAttr {
    public const val ByteSize: Int = 0xc

    /** `int32` */
    public const val `1`: Int = 0x0

    /** `int32` */
    public const val `2`: Int = 0x4

    /** `int32` */
    public const val `3`: Int = 0x8
}

/**
 * `UnitTestClang::TestDefaultTypes`, 0xe0 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangTestDefaultTypes {
    public const val ByteSize: Int = 0xe0
}

/**
 * `UnitTestClang::TestDependentType`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangTestDependentType {
    public const val ByteSize: Int = 0x1
}

/**
 * `UnitTestClang::TestEnumTypedefs`, 0x8 bytes.
 */
public object UnitTestClangTestEnumTypedefs {
    public const val ByteSize: Int = 0x8

    /** `int32` */
    public const val Count: Int = 0x0

    /** `float` */
    public const val Real: Int = 0x4
}

/**
 * `UnitTestClang::TestMethods`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangTestMethods {
    public const val ByteSize: Int = 0x1
}

/**
 * `UnitTestClang::TestRecordPresets`, 0x4 bytes.
 */
public object UnitTestClangTestRecordPresets {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val Value: Int = 0x0
}

/**
 * `UnitTestClang::TestTemplateAttribute`, 0x4 bytes.
 */
public object UnitTestClangTestTemplateAttribute {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val T: Int = 0x0
}

/**
 * `UnitTestClang::TestTemplateMethods`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangTestTemplateMethods {
    public const val ByteSize: Int = 0x1
}

/**
 * `UnitTestClang::TestTemplateProperty`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangTestTemplateProperty {
    public const val ByteSize: Int = 0x8
}

/**
 * `UnitTestClang::TestTypedef`, 0xe0 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object UnitTestClangTestTypedef {
    public const val ByteSize: Int = 0xe0
}

/**
 * `UnitTestClang::TypedefArg`, 0x18 bytes.
 */
public object UnitTestClangTypedefArg {
    public const val ByteSize: Int = 0x18

    /** `hkArray<int32>` */
    public const val T: Int = 0x0

    /** `float` */
    public const val Dep: Int = 0x10

    /** `uint8` */
    public const val WithAttrs: Int = 0x14
}

/**
 * `UnitTestClang::WithPtr`, 0x8 bytes.
 */
public object UnitTestClangWithPtr {
    public const val ByteSize: Int = 0x8

    /** `int32*` */
    public const val RawPtr: Int = 0x0
}

/**
 * `UnitTestClang::WithTemplateInterface`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `UnitTestClang::WithTemplateInterface`
 */
public object UnitTestClangWithTemplateInterface {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `VarTest::Bar`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `VarTest::Bar`
 */
public object VarTestBar {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `VarTest::CustomMethods`, 0x4 bytes.
 */
public object VarTestCustomMethods {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val Int: Int = 0x0
}

/**
 * `VarTest::Empty`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object VarTestEmpty {
    public const val ByteSize: Int = 0x1
}

/**
 * `VarTest::Foo`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `VarTest::Foo`
 */
public object VarTestFoo {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `int32` */
    public const val Things: Int = 0x10

    /** `enum:uint8` */
    public const val Things2: Int = 0x14

    /** `int32` */
    public const val Things3: Int = 0x18

    /** `int32` */
    public const val Things4: Int = 0x1c

    /** `int32` */
    public const val Hidden: Int = 0x20

    /** `hkArray<int32>` */
    public const val IntArray: Int = 0x28

    /** `struct*` */
    public const val RawPtr: Int = 0x38
}

/**
 * `VarTest::ImplicitMethods`, 0x48 bytes.
 *
 * `VarTest::CustomMethods` -> `VarTest::ImplicitMethods`
 */
public object VarTestImplicitMethods {
    public const val ByteSize: Int = 0x48

    /** `int32`, from `VarTest::CustomMethods` */
    public const val Int: Int = 0x0

    /** `int32` */
    public const val I: Int = 0x4

    /** `hkArray<int32>` */
    public const val A: Int = 0x8

    /** `struct` */
    public const val X: Int = 0x18

    /** `struct` */
    public const val B: Int = 0x20

    /** `hkStringPtr` */
    public const val S: Int = 0x30

    /** `hkArray<hkStringPtr>` */
    public const val As: Int = 0x38
}

/**
 * `VarTest::InPadding`, 0x1 bytes.
 */
public object VarTestInPadding {
    public const val ByteSize: Int = 0x1

    /** `int8` */
    public const val Data: Int = 0x0
}

/**
 * `VarTest::NoCopy`, 0x8 bytes.
 */
public object VarTestNoCopy {
    public const val ByteSize: Int = 0x8

    /** `struct*` */
    public const val Ptr: Int = 0x0
}

/**
 * `VarTest::NonCopiable`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object VarTestNonCopiable {
    public const val ByteSize: Int = 0x1
}

/**
 * `VarTest::NonReflectedField`, 0x10 bytes.
 */
public object VarTestNonReflectedField {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val I: Int = 0x0
}

/**
 * `VarTest::NonTrivialTemplate`, 0x4 bytes.
 */
public object VarTestNonTrivialTemplate {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val T: Int = 0x0
}

/**
 * `VarTest::PodLike`, 0x1c bytes.
 */
public object VarTestPodLike {
    public const val ByteSize: Int = 0x1c

    /** `struct` */
    public const val Handle: Int = 0x0

    /** `float` */
    public const val Float: Int = 0x4

    /** `uint16[10]` */
    public const val Array: Int = 0x8
}

/**
 * `VarTest::RefPtrField`, 0x8 bytes.
 */
public object VarTestRefPtrField {
    public const val ByteSize: Int = 0x8

    /** `struct*` */
    public const val Ptr: Int = 0x0
}

/**
 * `VarTest::ReuseEmpty`, 0x1 bytes.
 *
 * `VarTest::Empty` -> `VarTest::ReuseEmpty`
 */
public object VarTestReuseEmpty {
    public const val ByteSize: Int = 0x1

    /** `struct` */
    public const val P: Int = 0x0
}

/**
 * `VarTest::ReusePadding`, 0x18 bytes.
 *
 * `VarTest::WithPadding` -> `VarTest::ReusePadding`
 */
public object VarTestReusePadding {
    public const val ByteSize: Int = 0x18

    /** `type9`, from `VarTest::WithPadding` */
    public const val I64: Int = 0x0

    /** `int8`, from `VarTest::WithPadding` */
    public const val I8: Int = 0x8

    /** `struct` */
    public const val P: Int = 0x10
}

/**
 * `VarTest::TrivialTemplate`, 0x4 bytes.
 */
public object VarTestTrivialTemplate {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val Int: Int = 0x0
}

/**
 * `VarTest::WithPadding`, 0x10 bytes.
 */
public object VarTestWithPadding {
    public const val ByteSize: Int = 0x10

    /** `type9` */
    public const val I64: Int = 0x0

    /** `int8` */
    public const val I8: Int = 0x8
}

/**
 * `castTest::Interface0`, 0x10 bytes.
 *
 * `castTest::InterfaceBase` -> `castTest::Interface0`
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object CastTestInterface0 {
    public const val ByteSize: Int = 0x10
}

/**
 * `castTest::Interface1`, 0x8 bytes.
 *
 * `castTest::InterfaceBase` -> `castTest::Interface1`
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object CastTestInterface1 {
    public const val ByteSize: Int = 0x8
}

/**
 * `castTest::Interface2`, 0x8 bytes.
 *
 * `castTest::InterfaceBase` -> `castTest::Interface2`
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object CastTestInterface2 {
    public const val ByteSize: Int = 0x8
}

/**
 * `castTest::InterfaceBase`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object CastTestInterfaceBase {
    public const val ByteSize: Int = 0x8
}

/**
 * `castTest::InterfaceOtherBase`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object CastTestInterfaceOtherBase {
    public const val ByteSize: Int = 0x8
}

/**
 * `castTest::Leaf`, 0x8 bytes.
 *
 * `castTest::Root` -> `castTest::Leaf`
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object CastTestLeaf {
    public const val ByteSize: Int = 0x8
}

/**
 * `castTest::NonLeaf`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object CastTestNonLeaf {
    public const val ByteSize: Int = 0x1
}

/**
 * `castTest::OtherLeaf`, 0x8 bytes.
 *
 * `castTest::Root` -> `castTest::OtherLeaf`
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object CastTestOtherLeaf {
    public const val ByteSize: Int = 0x8
}

/**
 * `castTest::Root`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object CastTestRoot {
    public const val ByteSize: Int = 0x8
}

/**
 * `castTest::WithInterfaces`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `castTest::WithInterfaces`
 */
public object CastTestWithInterfaces {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hclToolNamedObjectReference`, 0x18 bytes.
 */
public object HclToolNamedObjectReference {
    public const val ByteSize: Int = 0x18

    /** `hkStringPtr` */
    public const val PluginName: Int = 0x0

    /** `hkStringPtr` */
    public const val ObjectName: Int = 0x8

    /** `uint32` */
    public const val Hash: Int = 0x10
}

/**
 * `hctAttributeDescription`, 0x28 bytes.
 */
public object HctAttributeDescription {
    public const val ByteSize: Int = 0x28

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `hkStringPtr` */
    public const val EnabledBy: Int = 0x8

    /** `enum:int8` */
    public const val ForcedType: Int = 0x10

    /** `struct*` */
    public const val Enum: Int = 0x18

    /** `enum:int8` */
    public const val Hint: Int = 0x20

    /** `bool` */
    public const val ClearHints: Int = 0x21

    /** `float` */
    public const val FloatScale: Int = 0x24
}

/**
 * `hctAttributeDescription::Enum`, 0x18 bytes.
 */
public object HctAttributeDescriptionEnum {
    public const val ByteSize: Int = 0x18

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `hkArray<struct>` */
    public const val Items: Int = 0x8
}

/**
 * `hctAttributeDescription::Enum::Item`, 0x10 bytes.
 */
public object HctAttributeDescriptionEnumItem {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val Value: Int = 0x0

    /** `hkStringPtr` */
    public const val Name: Int = 0x8
}

/**
 * `hctAttributeDescriptionDatabase`, 0x10 bytes.
 */
public object HctAttributeDescriptionDatabase {
    public const val ByteSize: Int = 0x10

    /** `hkArray<struct>` */
    public const val GroupDescriptions: Int = 0x0
}

/**
 * `hctAttributeGroupDescription`, 0x18 bytes.
 */
public object HctAttributeGroupDescription {
    public const val ByteSize: Int = 0x18

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `hkArray<struct>` */
    public const val AttributeDescriptions: Int = 0x8
}

/**
 * `hctClothSetup20141Options`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hctClothSetup20141Options`
 */
public object HctClothSetup20141Options {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val ClothData: Int = 0x10
}

/**
 * `hctClothSetupClothData20141Options`, 0xa0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hctClothSetupClothData20141Options`
 */
public object HctClothSetupClothData20141Options {
    public const val ByteSize: Int = 0xa0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Name: Int = 0x10

    /** `int32` */
    public const val CurrentState: Int = 0x18

    /** `int32` */
    public const val DefaultState: Int = 0x1c

    /** `hkArray<ptr>` */
    public const val SetupMeshes: Int = 0x20

    /** `hkArray<ptr>` */
    public const val SimSetupMeshes: Int = 0x30

    /** `hkArray<ptr>` */
    public const val Buffers: Int = 0x40

    /** `hkArray<ptr>` */
    public const val SimCloth: Int = 0x50

    /** `hkArray<ptr>` */
    public const val Operators: Int = 0x60

    /** `hkArray<ptr>` */
    public const val TransformSets: Int = 0x70

    /** `hkArray<ptr>` */
    public const val SimClothConstraints: Int = 0x80

    /** `hkArray<ptr>` */
    public const val ClothStates: Int = 0x90
}

/**
 * `hctClothSetupObjectData`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hctClothSetupObjectData`
 */
public object HctClothSetupObjectData {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val Ref: Int = 0x10

    /** `hkArray<void>` */
    public const val Options: Int = 0x30
}

/**
 * `hctFilterClothSetupStubOptions`, 0x50 bytes.
 */
public object HctFilterClothSetupStubOptions {
    public const val ByteSize: Int = 0x50

    /** `hkArray<void>` */
    public const val ClothToolOptions: Int = 0x0

    /** `bool` */
    public const val EnablePreviewDataExport: Int = 0x20

    /** `bool` */
    public const val ExportSetupOnly: Int = 0x21

    /** `bool` */
    public const val OverrideSetupData: Int = 0x22

    /** `bool` */
    public const val Batch: Int = 0x23

    /** `bool` */
    public const val RunScript: Int = 0x24

    /** `hkStringPtr` */
    public const val Filename: Int = 0x28

    /** `hkStringPtr` */
    public const val Config: Int = 0x30

    /** `hkStringPtr` */
    public const val ScriptFile: Int = 0x38

    /** `hkStringPtr` */
    public const val ScriptName: Int = 0x40

    /** `hkStringPtr` */
    public const val ScriptCommandLineArgs: Int = 0x48
}

/**
 * `hctFilterConfigurationSet`, 0x18 bytes.
 */
public object HctFilterConfigurationSet {
    public const val ByteSize: Int = 0x18

    /** `hkArray<struct>` */
    public const val Configurations: Int = 0x0

    /** `int32` */
    public const val CurrentIndex: Int = 0x10
}

/**
 * `hctFilterConfigurationSet::Configuration`, 0x18 bytes.
 */
public object HctFilterConfigurationSetConfiguration {
    public const val ByteSize: Int = 0x18

    /** `hkStringPtr` */
    public const val ConfigName: Int = 0x0

    /** `hkArray<struct>` */
    public const val FilterStages: Int = 0x8
}

/**
 * `hctFilterConfigurationSet::FilterStage`, 0x30 bytes.
 */
public object HctFilterConfigurationSetFilterStage {
    public const val ByteSize: Int = 0x30

    /** `uint32` */
    public const val FilterId: Int = 0x0

    /** `uint32` */
    public const val OptionDataVersion: Int = 0x4

    /** `hkArray<void>` */
    public const val Options: Int = 0x10
}

/**
 * `hk::AbsMax`, 0x10 bytes.
 *
 * `hk::ValueAttribute` -> `hk::AutoType` -> `hk::AbsMax`
 */
public object HkAbsMax {
    public const val ByteSize: Int = 0x10

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::AbsMin`, 0x10 bytes.
 *
 * `hk::ValueAttribute` -> `hk::AutoType` -> `hk::AbsMin`
 */
public object HkAbsMin {
    public const val ByteSize: Int = 0x10

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::AlignReal`, 0x1 bytes.
 */
public object HkAlignReal {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Allocatable`, 0x1 bytes.
 */
public object HkAllocatable {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::ApplyDiff`, 0x8 bytes.
 */
public object HkApplyDiff {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Fn: Int = 0x0
}

/**
 * `hk::AutoType`, 0x10 bytes.
 *
 * `hk::ValueAttribute` -> `hk::AutoType`
 */
public object HkAutoType {
    public const val ByteSize: Int = 0x10

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Backup`, 0x8 bytes.
 */
public object HkBackup {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Fn: Int = 0x0
}

/**
 * `hk::CanCreateInRuleEditor`, 0x1 bytes.
 */
public object HkCanCreateInRuleEditor {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::CanCreateInToolAsAsset`, 0x1 bytes.
 */
public object HkCanCreateInToolAsAsset {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Cast`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Cast`
 */
public object HkCast {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::ContainsRelArrays`, 0x1 bytes.
 */
public object HkContainsRelArrays {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Default`, 0x10 bytes.
 *
 * `hk::ValueAttribute` -> `hk::AutoType` -> `hk::Default`
 */
public object HkDefault {
    public const val ByteSize: Int = 0x10

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::DeleteTypeInfo`, 0x18 bytes.
 */
public object HkDeleteTypeInfo {
    public const val ByteSize: Int = 0x18

    /** `void*` */
    public const val Func: Int = 0x0

    /** `void*` */
    public const val Allocator: Int = 0x8

    /** `hkUlong` */
    public const val Data: Int = 0x10
}

/**
 * `hk::DependentUUID`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::DependentUUID`
 */
public object HkDependentUUID {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::DependsOn`, 0x10 bytes.
 *
 * `hk::ValueAttribute` -> `hk::DependsOn`
 */
public object HkDependsOn {
    public const val ByteSize: Int = 0x10

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Diff`, 0x8 bytes.
 */
public object HkDiff {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Fn: Int = 0x0
}

/**
 * `hk::DynamicFieldsOffset`, 0x2 bytes.
 *
 * `hk::ValueAttribute` -> `hk::DynamicFieldsOffset`
 */
public object HkDynamicFieldsOffset {
    public const val ByteSize: Int = 0x2

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::EditableIf`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::EditableIf`
 */
public object HkEditableIf {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::ExactTypeFunction`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::ExactTypeFunction`
 */
public object HkExactTypeFunction {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::ExcludeFromVersionCheck`, 0x1 bytes.
 */
public object HkExcludeFromVersionCheck {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::ExternalReflection`, 0x1 bytes.
 */
public object HkExternalReflection {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Feature`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Feature`
 */
public object HkFeature {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::FixupReflectedMethods`, 0x8 bytes.
 */
public object HkFixupReflectedMethods {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Func: Int = 0x0
}

/**
 * `hk::Format`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Format`
 */
public object HkFormat {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::GenerateBypassCtor`, 0x1 bytes.
 */
public object HkGenerateBypassCtor {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::GenerateExactTypeMethod`, 0x1 bytes.
 */
public object HkGenerateExactTypeMethod {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::HandledMessage`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::HandledMessage`
 */
public object HkHandledMessage {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::IgnoreTemplateParams`, 0x10 bytes.
 *
 * `hk::ValueAttribute` -> `hk::IgnoreTemplateParams`
 */
public object HkIgnoreTemplateParams {
    public const val ByteSize: Int = 0x10

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Implements`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Implements`
 */
public object HkImplements {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::IncludeInMgd`, 0x1 bytes.
 */
public object HkIncludeInMgd {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::IsValueArray`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkIsValueArray {
    public const val ByteSize: Int = 0x1
}

/**
 * `hk::MemoryTracker`, 0x10 bytes.
 */
public object HkMemoryTracker {
    public const val ByteSize: Int = 0x10

    /** `bool` */
    public const val Opaque: Int = 0x0

    /** `void*` */
    public const val Handler: Int = 0x8
}

/**
 * `hk::MessageHandler`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::MessageHandler`
 */
public object HkMessageHandler {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::MessageType`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::MessageType`
 */
public object HkMessageType {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::OpaqueType`, 0x1 bytes.
 */
public object HkOpaqueType {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Optional`, 0x2 bytes.
 */
public object HkOptional {
    public const val ByteSize: Int = 0x2

    /** `bool` */
    public const val Optional: Int = 0x0

    /** `bool` */
    public const val Implicit: Int = 0x1
}

/**
 * `hk::ParsingDecl`, 0x10 bytes.
 */
public object HkParsingDecl {
    public const val ByteSize: Int = 0x10

    /** `void*` */
    public const val Decl: Int = 0x0

    /** `void*` */
    public const val ConvertFunc: Int = 0x8
}

/**
 * `hk::Pod`, 0x1 bytes.
 */
public object HkPod {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::PointerType`, 0x4 bytes.
 */
public object HkPointerType {
    public const val ByteSize: Int = 0x4

    /** `enum:uint32` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Presets`, 0x28 bytes.
 */
public object HkPresets {
    public const val ByteSize: Int = 0x28

    /** `bool` */
    public const val Strict: Int = 0x0

    /** `void*` */
    public const val ValueType: Int = 0x8

    /** `int32` */
    public const val NumPresets: Int = 0x10
}

/**
 * `hk::Presets::ParsingDecl`, 0x20 bytes.
 */
public object HkPresetsParsingDecl {
    public const val ByteSize: Int = 0x20

    /** `bool` */
    public const val Strict: Int = 0x0

    /** `void*` */
    public const val Type: Int = 0x8

    /** `hkArray<char*>` */
    public const val Values: Int = 0x10
}

/**
 * `hk::Reflect`, 0x1 bytes.
 */
public object HkReflect {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::ReflectChildren`, 0x1 bytes.
 */
public object HkReflectChildren {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::ReflectDetails`, 0x5 bytes.
 */
public object HkReflectDetails {
    public const val ByteSize: Int = 0x5

    /** `bool` */
    public const val Parents: Int = 0x0

    /** `bool` */
    public const val Inheritance: Int = 0x1

    /** `bool` */
    public const val Specials: Int = 0x2

    /** `bool` */
    public const val Fields: Int = 0x3

    /** `bool` */
    public const val Methods: Int = 0x4
}

/**
 * `hk::ReflectionImpl`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::ReflectionImpl`
 */
public object HkReflectionImpl {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::RegisterType`, 0x1 bytes.
 */
public object HkRegisterType {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Rename`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Rename`
 */
public object HkRename {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Retargetable`, 0x1 bytes.
 */
public object HkRetargetable {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Serialize`, 0x1 bytes.
 */
public object HkSerialize {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::StepPerPixel`, 0x4 bytes.
 *
 * `hk::ValueAttribute` -> `hk::StepPerPixel`
 */
public object HkStepPerPixel {
    public const val ByteSize: Int = 0x4

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::StringOut`, 0x1 bytes.
 */
public object HkStringOut {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::TagSuggestionCallback`, 0x8 bytes.
 */
public object HkTagSuggestionCallback {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Cb: Int = 0x0
}

/**
 * `hk::ToString`, 0x8 bytes.
 */
public object HkToString {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Func: Int = 0x0
}

/**
 * `hk::Type`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Type`
 */
public object HkType {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::TypeLayout`, 0x8 bytes.
 */
public object HkTypeLayout {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Func: Int = 0x0
}

/**
 * `hk::TypeOwner`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::TypeOwner`
 */
public object HkTypeOwner {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_Category`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Ui_Category`
 */
public object HkUi_Category {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_DefineFieldOrder`, 0x10 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Ui_DefineFieldOrder`
 */
public object HkUi_DefineFieldOrder {
    public const val ByteSize: Int = 0x10

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_FieldName`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Ui_FieldName`
 */
public object HkUi_FieldName {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_FixedSize`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkUi_FixedSize {
    public const val ByteSize: Int = 0x1
}

/**
 * `hk::Ui_Group`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Ui_Group`
 */
public object HkUi_Group {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_Hidden`, 0x1 bytes.
 */
public object HkUi_Hidden {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_Icon`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Ui_Icon`
 */
public object HkUi_Icon {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_Navigable`, 0x1 bytes.
 */
public object HkUi_Navigable {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_NumSliderSteps`, 0x4 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Ui_NumSliderSteps`
 */
public object HkUi_NumSliderSteps {
    public const val ByteSize: Int = 0x4

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_Show`, 0x1 bytes.
 */
public object HkUi_Show {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_ShowManipulator`, 0x1 bytes.
 */
public object HkUi_ShowManipulator {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_Type`, 0x4 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Ui_Type`
 */
public object HkUi_Type {
    public const val ByteSize: Int = 0x4

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Ui_TypeName`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Ui_TypeName`
 */
public object HkUi_TypeName {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Untracked`, 0x1 bytes.
 */
public object HkUntracked {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hk::ValueAttribute`, 0x4 bytes.
 */
public object HkValueAttribute {
    public const val ByteSize: Int = 0x4

    /** `float` */
    public const val Value: Int = 0x0
}

/**
 * `hk::Version`, 0x4 bytes.
 *
 * `hk::ValueAttribute` -> `hk::Version`
 */
public object HkVersion {
    public const val ByteSize: Int = 0x4

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hkAabb`, 0x20 bytes.
 */
public object HkAabb {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val Min: Int = 0x0

    /** `hkVector4` */
    public const val Max: Int = 0x10
}

/**
 * `hkAabb16`, 0x10 bytes.
 */
public object HkAabb16 {
    public const val ByteSize: Int = 0x10

    /** `uint16[3]` */
    public const val Min: Int = 0x0

    /** `uint16` */
    public const val Key: Int = 0x6

    /** `uint16[3]` */
    public const val Max: Int = 0x8

    /** `uint16` */
    public const val Key1: Int = 0xe
}

/**
 * `hkAabb24_16_24`, 0x10 bytes.
 */
public object HkAabb24_16_24 {
    public const val ByteSize: Int = 0x10

    /** `int64` */
    public const val Min: Int = 0x0

    /** `int64` */
    public const val Max: Int = 0x8
}

/**
 * `hkAabbHalf`, 0x10 bytes.
 */
public object HkAabbHalf {
    public const val ByteSize: Int = 0x10

    /** `uint16[8]` */
    public const val Data: Int = 0x0
}

/**
 * `hkAabbUint32`, 0x20 bytes.
 */
public object HkAabbUint32 {
    public const val ByteSize: Int = 0x20

    /** `uint32[3]` */
    public const val Min: Int = 0x0

    /** `uint8[3]` */
    public const val ExpansionMin: Int = 0xc

    /** `uint8` */
    public const val ExpansionShift: Int = 0xf

    /** `uint32[3]` */
    public const val Max: Int = 0x10

    /** `uint8[3]` */
    public const val ExpansionMax: Int = 0x1c

    /** `uint8` */
    public const val ShapeKeyByte: Int = 0x1f
}

/**
 * `hkAlignSceneToNodeOptions`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkAlignSceneToNodeOptions`
 */
public object HkAlignSceneToNodeOptions {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `bool` */
    public const val Invert: Int = 0x10

    /** `bool` */
    public const val TransformPositionX: Int = 0x11

    /** `bool` */
    public const val TransformPositionY: Int = 0x12

    /** `bool` */
    public const val TransformPositionZ: Int = 0x13

    /** `bool` */
    public const val TransformRotation: Int = 0x14

    /** `bool` */
    public const val TransformScale: Int = 0x15

    /** `bool` */
    public const val TransformSkew: Int = 0x16

    /** `int32` */
    public const val Keyframe: Int = 0x18

    /** `hkStringPtr` */
    public const val NodeName: Int = 0x20
}

/**
 * `hkAttributeHideCriteria`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkAttributeHideCriteria {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkAxialRotation`, 0x1 bytes.
 */
public object HkAxialRotation {
    public const val ByteSize: Int = 0x1

    /** `uint8` */
    public const val Tag: Int = 0x0
}

/**
 * `hkAxialTransform`, 0x20 bytes.
 */
public object HkAxialTransform {
    public const val ByteSize: Int = 0x20

    /** `struct` */
    public const val Rotation: Int = 0x0

    /** `hkVector4` */
    public const val Translation: Int = 0x10
}

/**
 * `hkBaseObject`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkBaseObject {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkBitField`, 0x18 bytes.
 *
 * `hkBitFieldBase` -> `hkBitField`
 */
public object HkBitField {
    public const val ByteSize: Int = 0x18

    /** `struct`, from `hkBitFieldBase` */
    public const val Storage: Int = 0x0
}

/**
 * `hkBitFieldBase`, 0x18 bytes.
 */
public object HkBitFieldBase {
    public const val ByteSize: Int = 0x18

    /** `struct` */
    public const val Storage: Int = 0x0
}

/**
 * `hkBitFieldStorage`, 0x18 bytes.
 */
public object HkBitFieldStorage {
    public const val ByteSize: Int = 0x18

    /** `hkArray<uint32>` */
    public const val Words: Int = 0x0

    /** `int32` */
    public const val NumBits: Int = 0x10
}

/**
 * `hkCamera3d`, 0xd0 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkCamera3d {
    public const val ByteSize: Int = 0xd0
}

/**
 * `hkCameraData`, 0x40 bytes.
 */
public object HkCameraData {
    public const val ByteSize: Int = 0x40

    /** `hkVector4` */
    public const val From: Int = 0x0

    /** `hkVector4` */
    public const val To: Int = 0x10

    /** `hkVector4` */
    public const val Up: Int = 0x20

    /** `float` */
    public const val FovyDegrees: Int = 0x30

    /** `float` */
    public const val Near: Int = 0x34

    /** `float` */
    public const val Far: Int = 0x38

    /** `bool` */
    public const val IsOrthographic: Int = 0x3c

    /** `enum:uint8` */
    public const val Handedness: Int = 0x3d
}

/**
 * `hkColor`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkColor {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkCompressedMassProperties`, 0x20 bytes.
 */
public object HkCompressedMassProperties {
    public const val ByteSize: Int = 0x20

    /** `struct` */
    public const val CenterOfMass: Int = 0x0

    /** `struct` */
    public const val Inertia: Int = 0x8

    /** `int16[4]` */
    public const val MajorAxisSpace: Int = 0x10

    /** `float` */
    public const val Mass: Int = 0x18

    /** `float` */
    public const val Volume: Int = 0x1c
}

/**
 * `hkContactPoint`, 0x20 bytes.
 */
public object HkContactPoint {
    public const val ByteSize: Int = 0x20

    /** `hkVector4` */
    public const val Position: Int = 0x0

    /** `hkVector4` */
    public const val SeparatingNormal: Int = 0x10
}

/**
 * `hkContactPointMaterial`, 0x10 bytes.
 */
public object HkContactPointMaterial {
    public const val ByteSize: Int = 0x10

    /** `hkUlong` */
    public const val UserData: Int = 0x0

    /** `struct` */
    public const val Friction: Int = 0x8

    /** `uint8` */
    public const val Restitution: Int = 0x9

    /** `struct` */
    public const val MaxImpulse: Int = 0xa

    /** `uint8` */
    public const val Flags: Int = 0xb
}

/**
 * `hkContainerDebugAllocator`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkContainerDebugAllocator {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkContainerHeapAllocator`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkContainerHeapAllocator {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkContainerTempAllocator`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkContainerTempAllocator {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkDefaultCompoundMeshBody`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshBody` -> `hkDefaultCompoundMeshBody`
 */
public object HkDefaultCompoundMeshBody {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val Bodies: Int = 0x10

    /** `hkMatrix4` */
    public const val Transform: Int = 0x20

    /** `struct*` */
    public const val Shape: Int = 0x60

    /** `struct*` */
    public const val TransformSet: Int = 0x68

    /** `bool` */
    public const val TransformIsDirty: Int = 0x70

    /** `bool` */
    public const val TransformSetUpdated: Int = 0x71
}

/**
 * `hkDefaultCompoundMeshShape`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape` -> `hkDefaultCompoundMeshShape`
 */
public object HkDefaultCompoundMeshShape {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<ptr>` */
    public const val Shapes: Int = 0x10

    /** `hkArray<hkMatrix4>` */
    public const val DefaultChildTransforms: Int = 0x20

    /** `hkArray<struct>` */
    public const val Sections: Int = 0x30
}

/**
 * `hkDefaultCompoundMeshShape::MeshSection`, 0x8 bytes.
 */
public object HkDefaultCompoundMeshShapeMeshSection {
    public const val ByteSize: Int = 0x8

    /** `int32` */
    public const val ShapeIndex: Int = 0x0

    /** `int32` */
    public const val SectionIndex: Int = 0x4
}

/**
 * `hkDescriptionAttribute`, 0x8 bytes.
 */
public object HkDescriptionAttribute {
    public const val ByteSize: Int = 0x8

    /** `char*` */
    public const val String: Int = 0x0
}

/**
 * `hkDiagonalizedMassProperties`, 0x40 bytes.
 */
public object HkDiagonalizedMassProperties {
    public const val ByteSize: Int = 0x40

    /** `float` */
    public const val Volume: Int = 0x0

    /** `float` */
    public const val Mass: Int = 0x4

    /** `float[2]` */
    public const val Padding: Int = 0x8

    /** `hkVector4` */
    public const val CenterOfMass: Int = 0x10

    /** `hkVector4` */
    public const val InertiaTensor: Int = 0x20

    /** `hkQuaternion` */
    public const val MajorAxisSpace: Int = 0x30
}

/**
 * `hkDocumentationAttribute`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkDocumentationAttribute {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkFloat16`, 0x2 bytes.
 */
public object HkFloat16 {
    public const val ByteSize: Int = 0x2

    /** `uint16` */
    public const val Value: Int = 0x0
}

/**
 * `hkFloat16Transform`, 0x18 bytes.
 */
public object HkFloat16Transform {
    public const val ByteSize: Int = 0x18

    /** `struct[12]` */
    public const val Elements: Int = 0x0
}

/**
 * `hkFourTransposedPointsf`, 0x30 bytes.
 */
public object HkFourTransposedPointsf {
    public const val ByteSize: Int = 0x30

    /** `hkVector4[3]` */
    public const val Vertices: Int = 0x0
}

/**
 * `hkGeometry`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkGeometry`
 */
public object HkGeometry {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<hkVector4>` */
    public const val Vertices: Int = 0x10

    /** `hkArray<struct>` */
    public const val Triangles: Int = 0x20
}

/**
 * `hkGeometry::Triangle`, 0x10 bytes.
 */
public object HkGeometryTriangle {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val A: Int = 0x0

    /** `int32` */
    public const val B: Int = 0x4

    /** `int32` */
    public const val C: Int = 0x8

    /** `int32` */
    public const val Material: Int = 0xc
}

/**
 * `hkGizmoAttribute`, 0x18 bytes.
 */
public object HkGizmoAttribute {
    public const val ByteSize: Int = 0x18

    /** `bool` */
    public const val Visible: Int = 0x0

    /** `char*` */
    public const val Label: Int = 0x8

    /** `enum:int8` */
    public const val Type: Int = 0x10
}

/**
 * `hkGpuTraceResult`, 0x28 bytes.
 */
public object HkGpuTraceResult {
    public const val ByteSize: Int = 0x28

    /** `int64` */
    public const val Id: Int = 0x0

    /** `float` */
    public const val GpuTimeBegin: Int = 0x8

    /** `float` */
    public const val GpuTimeEnd: Int = 0x10

    /** `uint32` */
    public const val NumPixelsTouched: Int = 0x18

    /** `enum:uint16` */
    public const val Type: Int = 0x1c

    /** `uint16` */
    public const val ThreadId: Int = 0x1e

    /** `struct*` */
    public const val Meta: Int = 0x20
}

/**
 * `hkHandle`, 0x4 bytes.
 */
public object HkHandle {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val Value: Int = 0x0
}

/**
 * `hkHashBase`, 0x20 bytes.
 */
public object HkHashBase {
    public const val ByteSize: Int = 0x20

    /** `hkArray<struct>` */
    public const val Items: Int = 0x0

    /** `struct` */
    public const val Index: Int = 0x10
}

/**
 * `hkHashMap`, 0x20 bytes.
 *
 * `hkHashBase` -> `hkHashMap`
 */
public object HkHashMap {
    public const val ByteSize: Int = 0x20

    /** `hkArray<struct>`, from `hkHashBase` */
    public const val Items: Int = 0x0

    /** `struct`, from `hkHashBase` */
    public const val Index: Int = 0x10
}

/**
 * `hkHashMapDetail::Index`, 0x10 bytes.
 */
public object HkHashMapDetailIndex {
    public const val ByteSize: Int = 0x10

    /** `void*` */
    public const val Entries: Int = 0x0

    /** `int32` */
    public const val HashMod: Int = 0x8
}

/**
 * `hkHashMapDetail::MapTuple`, 0x8 bytes.
 *
 * `hkTuple` -> `hkHashMapDetail::MapTuple`
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkHashMapDetailMapTuple {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkHashSet`, 0x20 bytes.
 *
 * `hkHashBase` -> `hkHashSet`
 */
public object HkHashSet {
    public const val ByteSize: Int = 0x20

    /** `hkArray<struct>`, from `hkHashBase` */
    public const val Items: Int = 0x0

    /** `struct`, from `hkHashBase` */
    public const val Index: Int = 0x10
}

/**
 * `hkIndexedTransformSet`, 0x68 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkIndexedTransformSet`
 */
public object HkIndexedTransformSet {
    public const val ByteSize: Int = 0x68

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<hkMatrix4>` */
    public const val Matrices: Int = 0x10

    /** `hkArray<hkMatrix4>` */
    public const val InverseMatrices: Int = 0x20

    /** `hkArray<int16>` */
    public const val MatricesOrder: Int = 0x30

    /** `hkArray<hkStringPtr>` */
    public const val MatricesNames: Int = 0x40

    /** `hkArray<struct>` */
    public const val IndexMappings: Int = 0x50

    /** `bool` */
    public const val AllMatricesAreAffine: Int = 0x60
}

/**
 * `hkIntRealPair`, 0x8 bytes.
 */
public object HkIntRealPair {
    public const val ByteSize: Int = 0x8

    /** `int32` */
    public const val Key: Int = 0x0

    /** `float` */
    public const val Value: Int = 0x4
}

/**
 * `hkLinkAttribute`, 0x1 bytes.
 */
public object HkLinkAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val Type: Int = 0x0
}

/**
 * `hkLocalFrame`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkLocalFrame`
 */
public object HkLocalFrame {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkLocalFrameGroup`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkLocalFrameGroup`
 */
public object HkLocalFrameGroup {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Name: Int = 0x10
}

/**
 * `hkMapOperations`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkMapOperations {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkMassProperties`, 0x50 bytes.
 */
public object HkMassProperties {
    public const val ByteSize: Int = 0x50

    /** `float` */
    public const val Volume: Int = 0x0

    /** `float` */
    public const val Mass: Int = 0x4

    /** `hkVector4` */
    public const val CenterOfMass: Int = 0x10

    /** `hkMatrix3` */
    public const val InertiaTensor: Int = 0x20
}

/**
 * `hkMemoryMeshBody`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshBody` -> `hkMemoryMeshBody`
 */
public object HkMemoryMeshBody {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkMatrix4` */
    public const val Transform: Int = 0x10

    /** `struct*` */
    public const val TransformSet: Int = 0x50

    /** `struct*` */
    public const val Shape: Int = 0x58

    /** `hkArray<ptr>` */
    public const val VertexBuffers: Int = 0x60

    /** `hkStringPtr` */
    public const val Name: Int = 0x70
}

/**
 * `hkMemoryMeshMaterial`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshMaterial` -> `hkMemoryMeshMaterial`
 */
public object HkMemoryMeshMaterial {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val MaterialName: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Textures: Int = 0x18

    /** `hkVector4` */
    public const val DiffuseColor: Int = 0x30

    /** `hkVector4` */
    public const val AmbientColor: Int = 0x40

    /** `hkVector4` */
    public const val SpecularColor: Int = 0x50

    /** `hkVector4` */
    public const val EmissiveColor: Int = 0x60

    /** `hkUlong` */
    public const val UserData: Int = 0x70

    /** `float` */
    public const val TesselationFactor: Int = 0x78

    /** `float` */
    public const val DisplacementAmount: Int = 0x7c
}

/**
 * `hkMemoryMeshShape`, 0x48 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape` -> `hkMemoryMeshShape`
 */
public object HkMemoryMeshShape {
    public const val ByteSize: Int = 0x48

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Sections: Int = 0x10

    /** `hkArray<uint16>` */
    public const val Indices16: Int = 0x20

    /** `hkArray<uint32>` */
    public const val Indices32: Int = 0x30

    /** `hkStringPtr` */
    public const val Name: Int = 0x40
}

/**
 * `hkMemoryMeshShape::Section`, 0x38 bytes.
 */
public object HkMemoryMeshShapeSection {
    public const val ByteSize: Int = 0x38

    /** `struct*` */
    public const val VertexBuffer: Int = 0x0

    /** `struct*` */
    public const val Material: Int = 0x8

    /** `struct` */
    public const val BoneMatrixMap: Int = 0x10

    /** `enum:uint8` */
    public const val PrimitiveType: Int = 0x20

    /** `int32` */
    public const val NumPrimitives: Int = 0x24

    /** `enum:uint8` */
    public const val IndexType: Int = 0x28

    /** `int32` */
    public const val VertexStartIndex: Int = 0x2c

    /** `int32` */
    public const val TransformIndex: Int = 0x30

    /** `int32` */
    public const val IndexBufferOffset: Int = 0x34
}

/**
 * `hkMemoryMeshSystem`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshSystem` -> `hkMemoryMeshSystem`
 */
public object HkMemoryMeshSystem {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkMemoryMeshTexture`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshTexture` -> `hkMemoryMeshTexture`
 */
public object HkMemoryMeshTexture {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Filename: Int = 0x10

    /** `hkArray<uint8>` */
    public const val Data: Int = 0x18

    /** `enum:int8` */
    public const val Format: Int = 0x28

    /** `bool` */
    public const val HasMipMaps: Int = 0x29

    /** `enum:int8` */
    public const val FilterMode: Int = 0x2a

    /** `enum:int8` */
    public const val UsageHint: Int = 0x2b

    /** `int32` */
    public const val TextureCoordChannel: Int = 0x2c
}

/**
 * `hkMemoryMeshVertexBuffer`, 0x1b8 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshVertexBuffer` -> `hkMemoryMeshVertexBuffer`
 */
public object HkMemoryMeshVertexBuffer {
    public const val ByteSize: Int = 0x1b8

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val Format: Int = 0x10

    /** `int32[32]` */
    public const val ElementOffsets: Int = 0x114

    /** `hkArray<uint8>` */
    public const val Memory: Int = 0x198

    /** `int32` */
    public const val VertexStride: Int = 0x1a8

    /** `bool` */
    public const val Locked: Int = 0x1ac

    /** `int32` */
    public const val NumVertices: Int = 0x1b0

    /** `bool` */
    public const val IsBigEndian: Int = 0x1b4

    /** `bool` */
    public const val IsSharable: Int = 0x1b5
}

/**
 * `hkMemoryResourceContainer`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceContainer` -> `hkMemoryResourceContainer`
 */
public object HkMemoryResourceContainer {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Name: Int = 0x10

    /** `struct*` */
    public const val Parent: Int = 0x18

    /** `hkArray<ptr>` */
    public const val ResourceHandles: Int = 0x20

    /** `hkArray<ptr>` */
    public const val Children: Int = 0x30
}

/**
 * `hkMemoryResourceHandle`, 0x30 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceHandle` -> `hkMemoryResourceHandle`
 */
public object HkMemoryResourceHandle {
    public const val ByteSize: Int = 0x30

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Variant: Int = 0x10

    /** `hkStringPtr` */
    public const val Name: Int = 0x18

    /** `hkArray<struct>` */
    public const val References: Int = 0x20
}

/**
 * `hkMemoryResourceHandle::ExternalLink`, 0x10 bytes.
 */
public object HkMemoryResourceHandleExternalLink {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val MemberName: Int = 0x0

    /** `hkStringPtr` */
    public const val ExternalId: Int = 0x8
}

/**
 * `hkMeshBody`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshBody`
 */
public object HkMeshBody {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkMeshBoneIndexMapping`, 0x10 bytes.
 */
public object HkMeshBoneIndexMapping {
    public const val ByteSize: Int = 0x10

    /** `hkArray<int16>` */
    public const val Mapping: Int = 0x0
}

/**
 * `hkMeshMaterial`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshMaterial`
 */
public object HkMeshMaterial {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkMeshSection`, 0x40 bytes.
 */
public object HkMeshSection {
    public const val ByteSize: Int = 0x40

    /** `enum:uint8` */
    public const val PrimitiveType: Int = 0x0

    /** `int32` */
    public const val NumPrimitives: Int = 0x4

    /** `int32` */
    public const val NumIndices: Int = 0x8

    /** `int32` */
    public const val VertexStartIndex: Int = 0xc

    /** `int32` */
    public const val TransformIndex: Int = 0x10

    /** `enum:uint8` */
    public const val IndexType: Int = 0x14

    /** `void*` */
    public const val Indices: Int = 0x18

    /** `struct*` */
    public const val VertexBuffer: Int = 0x20

    /** `struct*` */
    public const val Material: Int = 0x28

    /** `struct*` */
    public const val BoneMatrixMap: Int = 0x30

    /** `int32` */
    public const val SectionIndex: Int = 0x38
}

/**
 * `hkMeshSectionCinfo`, 0x40 bytes.
 */
public object HkMeshSectionCinfo {
    public const val ByteSize: Int = 0x40

    /** `struct*` */
    public const val VertexBuffer: Int = 0x0

    /** `struct*` */
    public const val Material: Int = 0x8

    /** `struct` */
    public const val BoneMatrixMap: Int = 0x10

    /** `enum:uint8` */
    public const val PrimitiveType: Int = 0x20

    /** `int32` */
    public const val NumPrimitives: Int = 0x24

    /** `enum:uint8` */
    public const val IndexType: Int = 0x28

    /** `void*` */
    public const val Indices: Int = 0x30

    /** `int32` */
    public const val VertexStartIndex: Int = 0x38

    /** `int32` */
    public const val TransformIndex: Int = 0x3c
}

/**
 * `hkMeshShape`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape`
 */
public object HkMeshShape {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkMeshSystem`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshSystem`
 */
public object HkMeshSystem {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkMeshTexture`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshTexture`
 */
public object HkMeshTexture {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkMeshTexture::RawBufferDescriptor`, 0x10 bytes.
 */
public object HkMeshTextureRawBufferDescriptor {
    public const val ByteSize: Int = 0x10

    /** `type9` */
    public const val Offset: Int = 0x0

    /** `uint32` */
    public const val Stride: Int = 0x8

    /** `uint32` */
    public const val NumElements: Int = 0xc
}

/**
 * `hkMeshVertexBuffer`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshVertexBuffer`
 */
public object HkMeshVertexBuffer {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkModelerNodeTypeAttribute`, 0x1 bytes.
 */
public object HkModelerNodeTypeAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val Type: Int = 0x0
}

/**
 * `hkMonitorStreamColorTable`, 0x28 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMonitorStreamColorTable`
 */
public object HkMonitorStreamColorTable {
    public const val ByteSize: Int = 0x28

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val ColorPairs: Int = 0x10

    /** `uint32` */
    public const val DefaultColor: Int = 0x20
}

/**
 * `hkMonitorStreamColorTable::ColorPair`, 0x10 bytes.
 */
public object HkMonitorStreamColorTableColorPair {
    public const val ByteSize: Int = 0x10

    /** `hkStringPtr` */
    public const val ColorName: Int = 0x0

    /** `uint32` */
    public const val Color: Int = 0x8
}

/**
 * `hkMonitorStreamContainer`, 0xf0 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMonitorStreamContainer`
 */
public object HkMonitorStreamContainer {
    public const val ByteSize: Int = 0xf0

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val Info: Int = 0x10

    /** `bool` */
    public const val PointersAre64Bit: Int = 0x18

    /** `bool` */
    public const val LittleEndian: Int = 0x19

    /** `bool` */
    public const val TimersAre64Bit: Int = 0x1a

    /** `float` */
    public const val TimerFactor: Int = 0x1c

    /** `struct` */
    public const val StringMap: Int = 0x20

    /** `struct` */
    public const val TypeMap: Int = 0x60

    /** `hkArray<uint8>` */
    public const val Trace: Int = 0xa0

    /** `hkArray<uint32>` */
    public const val ThreadStartOffsets: Int = 0xb0

    /** `hkArray<uint32>` */
    public const val FrameStartOffsets: Int = 0xc0

    /** `hkArray<struct>` */
    public const val GpuTrace: Int = 0xd0

    /** `hkArray<uint32>` */
    public const val GpuFrameStartOffsets: Int = 0xe0
}

/**
 * `hkMonitorStreamFrameInfo`, 0x28 bytes.
 */
public object HkMonitorStreamFrameInfo {
    public const val ByteSize: Int = 0x28

    /** `hkStringPtr` */
    public const val Heading: Int = 0x0

    /** `int32` */
    public const val IndexOfTimer0: Int = 0x8

    /** `int32` */
    public const val IndexOfTimer1: Int = 0xc

    /** `enum:uint32` */
    public const val AbsoluteTimeCounter: Int = 0x10

    /** `float` */
    public const val TimerFactor0: Int = 0x14

    /** `float` */
    public const val TimerFactor1: Int = 0x18

    /** `int32` */
    public const val ThreadId: Int = 0x1c

    /** `int32` */
    public const val FrameStreamStart: Int = 0x20

    /** `int32` */
    public const val FrameStreamEnd: Int = 0x24
}

/**
 * `hkMonitorStreamStringMap`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMonitorStreamStringMap`
 */
public object HkMonitorStreamStringMap {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Map: Int = 0x10
}

/**
 * `hkMonitorStreamStringMap::StringMap`, 0x10 bytes.
 */
public object HkMonitorStreamStringMapStringMap {
    public const val ByteSize: Int = 0x10

    /** `int64` */
    public const val Id: Int = 0x0

    /** `hkStringPtr` */
    public const val String: Int = 0x8
}

/**
 * `hkMonitorStreamTypeMap`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMonitorStreamTypeMap`
 */
public object HkMonitorStreamTypeMap {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Map: Int = 0x10
}

/**
 * `hkMonitorStreamTypeMap::TypeMap`, 0x10 bytes.
 */
public object HkMonitorStreamTypeMapTypeMap {
    public const val ByteSize: Int = 0x10

    /** `int64` */
    public const val Id: Int = 0x0

    /** `void*` */
    public const val Type: Int = 0x8
}

/**
 * `hkMoppBvTreeShapeBase`, 0x50 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkcdShape` -> `hkpShapeBase` -> `hkpShape` -> `hkpBvTreeShape` -> `hkMoppBvTreeShapeBase`
 */
public object HkMoppBvTreeShapeBase {
    public const val ByteSize: Int = 0x50

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `enum:uint8`, from `hkcdShape` */
    public const val Type: Int = 0x10

    /** `enum:uint8`, from `hkcdShape` */
    public const val DispatchType: Int = 0x11

    /** `uint8`, from `hkcdShape` */
    public const val BitsPerKey: Int = 0x12

    /** `enum:uint8`, from `hkcdShape` */
    public const val ShapeInfoCodecType: Int = 0x13

    /** `hkUlong`, from `hkpShape` */
    public const val UserData: Int = 0x18

    /** `enum:uint8`, from `hkpBvTreeShape` */
    public const val BvTreeType: Int = 0x20

    /** `struct*` */
    public const val Code: Int = 0x28

    /** `void*` */
    public const val MoppData: Int = 0x30

    /** `uint32` */
    public const val MoppDataSize: Int = 0x38

    /** `hkVector4` */
    public const val CodeInfoCopy: Int = 0x40
}

/**
 * `hkMotionState`, 0xb0 bytes.
 */
public object HkMotionState {
    public const val ByteSize: Int = 0xb0

    /** `hkTransform` */
    public const val Transform: Int = 0x0

    /** `hkVector4[5]` */
    public const val SweptTransform: Int = 0x40

    /** `hkVector4` */
    public const val DeltaAngle: Int = 0x90

    /** `float` */
    public const val ObjectRadius: Int = 0xa0

    /** `hkHalf` */
    public const val LinearDamping: Int = 0xa4

    /** `hkHalf` */
    public const val AngularDamping: Int = 0xa6

    /** `hkHalf` */
    public const val TimeFactor: Int = 0xa8

    /** `struct` */
    public const val MaxLinearVelocity: Int = 0xaa

    /** `struct` */
    public const val MaxAngularVelocity: Int = 0xab

    /** `uint8` */
    public const val DeactivationClass: Int = 0xac
}

/**
 * `hkMultiThreadCheck`, 0xc bytes.
 */
public object HkMultiThreadCheck {
    public const val ByteSize: Int = 0xc

    /** `uint32` */
    public const val ThreadId: Int = 0x0

    /** `int32` */
    public const val StackTraceId: Int = 0x4

    /** `uint16` */
    public const val MarkCount: Int = 0x8

    /** `uint16` */
    public const val MarkBitStack: Int = 0xa
}

/**
 * `hkMultipleVertexBuffer`, 0x160 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshVertexBuffer` -> `hkMultipleVertexBuffer`
 */
public object HkMultipleVertexBuffer {
    public const val ByteSize: Int = 0x160

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val VertexFormat: Int = 0x10

    /** `hkArray<struct>` */
    public const val LockedElements: Int = 0x118

    /** `struct*` */
    public const val LockedBuffer: Int = 0x128

    /** `hkArray<struct>` */
    public const val ElementInfos: Int = 0x130

    /** `hkArray<struct>` */
    public const val VertexBufferInfos: Int = 0x140

    /** `int32` */
    public const val NumVertices: Int = 0x150

    /** `bool` */
    public const val IsLocked: Int = 0x154

    /** `uint32` */
    public const val UpdateCount: Int = 0x158

    /** `bool` */
    public const val WriteLock: Int = 0x15c

    /** `bool` */
    public const val IsSharable: Int = 0x15d

    /** `bool` */
    public const val ConstructionComplete: Int = 0x15e
}

/**
 * `hkMultipleVertexBuffer::ElementInfo`, 0x2 bytes.
 */
public object HkMultipleVertexBufferElementInfo {
    public const val ByteSize: Int = 0x2

    /** `uint8` */
    public const val VertexBufferIndex: Int = 0x0

    /** `uint8` */
    public const val ElementIndex: Int = 0x1
}

/**
 * `hkMultipleVertexBuffer::LockedElement`, 0x7 bytes.
 */
public object HkMultipleVertexBufferLockedElement {
    public const val ByteSize: Int = 0x7

    /** `uint8` */
    public const val VertexBufferIndex: Int = 0x0

    /** `uint8` */
    public const val ElementIndex: Int = 0x1

    /** `uint8` */
    public const val LockedBufferIndex: Int = 0x2

    /** `uint8` */
    public const val VertexFormatIndex: Int = 0x3

    /** `uint8` */
    public const val LockFlags: Int = 0x4

    /** `uint8` */
    public const val OutputBufferIndex: Int = 0x5

    /** `int8` */
    public const val EmulatedIndex: Int = 0x6
}

/**
 * `hkMultipleVertexBuffer::VertexBufferInfo`, 0x18 bytes.
 */
public object HkMultipleVertexBufferVertexBufferInfo {
    public const val ByteSize: Int = 0x18

    /** `struct*` */
    public const val VertexBuffer: Int = 0x0

    /** `void*` */
    public const val LockedVertices: Int = 0x8

    /** `bool` */
    public const val IsLocked: Int = 0x10
}

/**
 * `hkOffsetBitField`, 0x18 bytes.
 *
 * `hkBitFieldBase` -> `hkOffsetBitField`
 */
public object HkOffsetBitField {
    public const val ByteSize: Int = 0x18

    /** `struct`, from `hkBitFieldBase` */
    public const val Storage: Int = 0x0
}

/**
 * `hkOffsetBitFieldStorage`, 0x18 bytes.
 */
public object HkOffsetBitFieldStorage {
    public const val ByteSize: Int = 0x18

    /** `hkArray<uint32>` */
    public const val Words: Int = 0x0

    /** `int32` */
    public const val Offset: Int = 0x10
}

/**
 * `hkPackedVector3`, 0x8 bytes.
 */
public object HkPackedVector3 {
    public const val ByteSize: Int = 0x8

    /** `int16[4]` */
    public const val Values: Int = 0x0
}

/**
 * `hkPackedVector4_6`, 0x4 bytes.
 */
public object HkPackedVector4_6 {
    public const val ByteSize: Int = 0x4

    /** `uint8[4]` */
    public const val Values: Int = 0x0
}

/**
 * `hkPackedVector8_3`, 0x4 bytes.
 */
public object HkPackedVector8_3 {
    public const val ByteSize: Int = 0x4

    /** `int8[4]` */
    public const val Values: Int = 0x0
}

/**
 * `hkQTransform`, 0x20 bytes.
 *
 * `hkQTransformf` -> `hkQTransform`
 */
public object HkQTransform {
    public const val ByteSize: Int = 0x20

    /** `hkQuaternion`, from `hkQTransformf` */
    public const val Rotation: Int = 0x0

    /** `hkVector4`, from `hkQTransformf` */
    public const val Translation: Int = 0x10
}

/**
 * `hkQTransformd`, 0x40 bytes.
 */
public object HkQTransformd {
    public const val ByteSize: Int = 0x40

    /** `float[4]` */
    public const val Rotation: Int = 0x0

    /** `float[4]` */
    public const val Translation: Int = 0x20
}

/**
 * `hkQTransformf`, 0x20 bytes.
 */
public object HkQTransformf {
    public const val ByteSize: Int = 0x20

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x0

    /** `hkVector4` */
    public const val Translation: Int = 0x10
}

/**
 * `hkQsTransformd`, 0x60 bytes.
 */
public object HkQsTransformd {
    public const val ByteSize: Int = 0x60

    /** `float[4]` */
    public const val Translation: Int = 0x0

    /** `float[4]` */
    public const val Rotation: Int = 0x20

    /** `float[4]` */
    public const val Scale: Int = 0x40
}

/**
 * `hkQsTransformf`, 0x30 bytes.
 */
public object HkQsTransformf {
    public const val ByteSize: Int = 0x30

    /** `hkVector4` */
    public const val Translation: Int = 0x0

    /** `hkQuaternion` */
    public const val Rotation: Int = 0x10

    /** `hkVector4` */
    public const val Scale: Int = 0x20
}

/**
 * `hkRangeInt32Attribute`, 0x10 bytes.
 */
public object HkRangeInt32Attribute {
    public const val ByteSize: Int = 0x10

    /** `int32` */
    public const val Absmin: Int = 0x0

    /** `int32` */
    public const val Absmax: Int = 0x4

    /** `int32` */
    public const val Softmin: Int = 0x8

    /** `int32` */
    public const val Softmax: Int = 0xc
}

/**
 * `hkRangeRealAttribute`, 0x10 bytes.
 */
public object HkRangeRealAttribute {
    public const val ByteSize: Int = 0x10

    /** `float` */
    public const val Absmin: Int = 0x0

    /** `float` */
    public const val Absmax: Int = 0x4

    /** `float` */
    public const val Softmin: Int = 0x8

    /** `float` */
    public const val Softmax: Int = 0xc
}

/**
 * `hkRefCountedProperties`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkRefCountedProperties`
 */
public object HkRefCountedProperties {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<struct>` */
    public const val Entries: Int = 0x10
}

/**
 * `hkRefCountedProperties::Entry`, 0x10 bytes.
 */
public object HkRefCountedPropertiesEntry {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val Object: Int = 0x0

    /** `uint16` */
    public const val Key: Int = 0x8

    /** `uint16` */
    public const val Flags: Int = 0xa
}

/**
 * `hkReferencedObject`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject`
 */
public object HkReferencedObject {
    public const val ByteSize: Int = 0x10

    /** `uint16` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkReferencedTask`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkReferencedTask`
 */
public object HkReferencedTask {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkReflect::DataFieldDecl`, 0x8 bytes.
 *
 * `hkReflect::Decl` -> `hkReflect::FieldDecl` -> `hkReflect::DataFieldDecl`
 */
public object HkReflectDataFieldDecl {
    public const val ByteSize: Int = 0x8

    /** `void*`, from `hkReflect::Decl` */
    public const val Type: Int = 0x0
}

/**
 * `hkReflect::Decl`, 0x8 bytes.
 */
public object HkReflectDecl {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Type: Int = 0x0
}

/**
 * `hkReflect::Detail::SizeAlign`, 0x8 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkReflectDetailSizeAlign {
    public const val ByteSize: Int = 0x8
}

/**
 * `hkReflect::FieldDecl`, 0x8 bytes.
 *
 * `hkReflect::Decl` -> `hkReflect::FieldDecl`
 */
public object HkReflectFieldDecl {
    public const val ByteSize: Int = 0x8

    /** `void*`, from `hkReflect::Decl` */
    public const val Type: Int = 0x0
}

/**
 * `hkReflect::PropertyFieldDecl`, 0x8 bytes.
 *
 * `hkReflect::Decl` -> `hkReflect::FieldDecl` -> `hkReflect::PropertyFieldDecl`
 */
public object HkReflectPropertyFieldDecl {
    public const val ByteSize: Int = 0x8

    /** `void*`, from `hkReflect::Decl` */
    public const val Type: Int = 0x0
}

/**
 * `hkResourceBase`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase`
 */
public object HkResourceBase {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkResourceContainer`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceContainer`
 */
public object HkResourceContainer {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkResourceHandle`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkResourceBase` -> `hkResourceHandle`
 */
public object HkResourceHandle {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkResult`, 0x4 bytes.
 */
public object HkResult {
    public const val ByteSize: Int = 0x4

    /** `int32` */
    public const val Enum: Int = 0x0
}

/**
 * `hkRootLevelContainer`, 0x10 bytes.
 */
public object HkRootLevelContainer {
    public const val ByteSize: Int = 0x10

    /** `hkArray<struct>` */
    public const val NamedVariants: Int = 0x0
}

/**
 * `hkRootLevelContainer::NamedVariant`, 0x18 bytes.
 */
public object HkRootLevelContainerNamedVariant {
    public const val ByteSize: Int = 0x18

    /** `hkStringPtr` */
    public const val Name: Int = 0x0

    /** `hkStringPtr` */
    public const val ClassName: Int = 0x8

    /** `struct*` */
    public const val Variant: Int = 0x10
}

/**
 * `hkScopedPtrTest::Foo`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkScopedPtrTestFoo {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkScriptableAttribute`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkScriptableAttribute {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkSemanticsAttribute`, 0x1 bytes.
 */
public object HkSemanticsAttribute {
    public const val ByteSize: Int = 0x1

    /** `enum:int8` */
    public const val Type: Int = 0x0
}

/**
 * `hkSerialize::Detail::IdFromPointer`, 0x4 bytes.
 */
public object HkSerializeDetailIdFromPointer {
    public const val ByteSize: Int = 0x4

    /** `uint32` */
    public const val Id: Int = 0x0
}

/**
 * `hkSerialize::Note::Export`, 0x8 bytes.
 */
public object HkSerializeNoteExport {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val Name: Int = 0x0
}

/**
 * `hkSerialize::Note::Import`, 0x8 bytes.
 */
public object HkSerializeNoteImport {
    public const val ByteSize: Int = 0x8

    /** `hkStringPtr` */
    public const val Name: Int = 0x0
}

/**
 * `hkSet`, 0x18 bytes.
 */
public object HkSet {
    public const val ByteSize: Int = 0x18

    /** `hkArray<int64>` */
    public const val Elem: Int = 0x0

    /** `int32` */
    public const val NumElems: Int = 0x10
}

/**
 * `hkSetIntFloatPair`, 0x18 bytes.
 *
 * `hkSet` -> `hkSetIntFloatPair`
 */
public object HkSetIntFloatPair {
    public const val ByteSize: Int = 0x18

    /** `hkArray<int64>`, from `hkSet` */
    public const val Elem: Int = 0x0

    /** `int32`, from `hkSet` */
    public const val NumElems: Int = 0x10
}

/**
 * `hkSetUint32`, 0x18 bytes.
 *
 * `hkSet` -> `hkSetUint32`
 */
public object HkSetUint32 {
    public const val ByteSize: Int = 0x18

    /** `hkArray<int64>`, from `hkSet` */
    public const val Elem: Int = 0x0

    /** `int32`, from `hkSet` */
    public const val NumElems: Int = 0x10
}

/**
 * `hkSetUint64`, 0x18 bytes.
 *
 * `hkSet` -> `hkSetUint64`
 */
public object HkSetUint64 {
    public const val ByteSize: Int = 0x18

    /** `hkArray<int64>`, from `hkSet` */
    public const val Elem: Int = 0x0

    /** `int32`, from `hkSet` */
    public const val NumElems: Int = 0x10
}

/**
 * `hkSimpleLocalFrame`, 0x80 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkLocalFrame` -> `hkSimpleLocalFrame`
 */
public object HkSimpleLocalFrame {
    public const val ByteSize: Int = 0x80

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkTransform` */
    public const val Transform: Int = 0x10

    /** `hkArray<ptr>` */
    public const val Children: Int = 0x50

    /** `struct*` */
    public const val ParentFrame: Int = 0x60

    /** `struct*` */
    public const val Group: Int = 0x68

    /** `hkStringPtr` */
    public const val Name: Int = 0x70
}

/**
 * `hkSimpleProperty`, 0x10 bytes.
 */
public object HkSimpleProperty {
    public const val ByteSize: Int = 0x10

    /** `uint32` */
    public const val Key: Int = 0x0

    /** `uint32` */
    public const val AlignmentPadding: Int = 0x4

    /** `struct` */
    public const val Value: Int = 0x8
}

/**
 * `hkSimplePropertyValue`, 0x8 bytes.
 */
public object HkSimplePropertyValue {
    public const val ByteSize: Int = 0x8

    /** `int64` */
    public const val Data: Int = 0x0
}

/**
 * `hkSkinBinding`, 0x38 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape` -> `hkSkinBinding`
 */
public object HkSkinBinding {
    public const val ByteSize: Int = 0x38

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val Skin: Int = 0x10

    /** `hkArray<hkMatrix4>` */
    public const val WorldFromBoneTransforms: Int = 0x18

    /** `hkArray<hkStringPtr>` */
    public const val BoneNames: Int = 0x28
}

/**
 * `hkSkinnedMeshShape`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkSkinnedMeshShape`
 */
public object HkSkinnedMeshShape {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hkSkinnedMeshShape::BoneSection`, 0x10 bytes.
 */
public object HkSkinnedMeshShapeBoneSection {
    public const val ByteSize: Int = 0x10

    /** `struct*` */
    public const val MeshBuffer: Int = 0x0

    /** `uint16` */
    public const val StartBoneSetId: Int = 0x8

    /** `int16` */
    public const val NumBoneSets: Int = 0xa
}

/**
 * `hkSkinnedMeshShape::BoneSet`, 0x4 bytes.
 */
public object HkSkinnedMeshShapeBoneSet {
    public const val ByteSize: Int = 0x4

    /** `uint16` */
    public const val BoneBufferOffset: Int = 0x0

    /** `uint16` */
    public const val NumBones: Int = 0x2
}

/**
 * `hkSkinnedMeshShape::Part`, 0x30 bytes.
 */
public object HkSkinnedMeshShapePart {
    public const val ByteSize: Int = 0x30

    /** `int32` */
    public const val StartVertex: Int = 0x0

    /** `int32` */
    public const val NumVertices: Int = 0x4

    /** `int32` */
    public const val StartIndex: Int = 0x8

    /** `int32` */
    public const val NumIndices: Int = 0xc

    /** `uint16` */
    public const val BoneSetId: Int = 0x10

    /** `uint16` */
    public const val MeshSectionIndex: Int = 0x12

    /** `hkVector4` */
    public const val BoundingSphere: Int = 0x20
}

/**
 * `hkSkinnedRefMeshShape`, 0x40 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkMeshShape` -> `hkSkinnedRefMeshShape`
 */
public object HkSkinnedRefMeshShape {
    public const val ByteSize: Int = 0x40

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct*` */
    public const val SkinnedMeshShape: Int = 0x10

    /** `hkArray<int16>` */
    public const val Bones: Int = 0x18

    /** `hkArray<hkVector4>` */
    public const val LocalFromRootTransforms: Int = 0x28

    /** `hkStringPtr` */
    public const val Name: Int = 0x38
}

/**
 * `hkSphere`, 0x10 bytes.
 */
public object HkSphere {
    public const val ByteSize: Int = 0x10

    /** `hkVector4` */
    public const val Pos: Int = 0x0
}

/**
 * `hkStorageSkinnedMeshShape`, 0x58 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkSkinnedMeshShape` -> `hkStorageSkinnedMeshShape`
 */
public object HkStorageSkinnedMeshShape {
    public const val ByteSize: Int = 0x58

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkArray<int16>` */
    public const val BonesBuffer: Int = 0x10

    /** `hkArray<struct>` */
    public const val BoneSets: Int = 0x20

    /** `hkArray<struct>` */
    public const val BoneSections: Int = 0x30

    /** `hkArray<struct>` */
    public const val Parts: Int = 0x40

    /** `hkStringPtr` */
    public const val Name: Int = 0x50
}

/**
 * `hkStringObject`, 0x18 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkStringObject`
 */
public object HkStringObject {
    public const val ByteSize: Int = 0x18

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `hkStringPtr` */
    public const val String: Int = 0x10
}

/**
 * `hkSweptTransform`, 0x50 bytes.
 */
public object HkSweptTransform {
    public const val ByteSize: Int = 0x50

    /** `hkVector4` */
    public const val CenterOfMass0: Int = 0x0

    /** `hkVector4` */
    public const val CenterOfMass1: Int = 0x10

    /** `hkQuaternion` */
    public const val Rotation0: Int = 0x20

    /** `hkQuaternion` */
    public const val Rotation1: Int = 0x30

    /** `hkVector4` */
    public const val CenterOfMassLocal: Int = 0x40
}

/**
 * `hkTask`, 0x10 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkTask {
    public const val ByteSize: Int = 0x10
}

/**
 * `hkTraceStream::Title`, 0x20 bytes.
 */
public object HkTraceStreamTitle {
    public const val ByteSize: Int = 0x20

    /** `hkStringPtr` */
    public const val Value: Int = 0x0
}

/**
 * `hkTuple`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkTuple {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkUFloat8`, 0x1 bytes.
 */
public object HkUFloat8 {
    public const val ByteSize: Int = 0x1

    /** `uint8` */
    public const val Value: Int = 0x0
}

/**
 * `hkUiAttribute`, 0x28 bytes.
 */
public object HkUiAttribute {
    public const val ByteSize: Int = 0x28

    /** `bool` */
    public const val Visible: Int = 0x0

    /** `bool` */
    public const val Editable: Int = 0x1

    /** `enum:int8` */
    public const val HideCriteria: Int = 0x2

    /** `char*` */
    public const val Label: Int = 0x8

    /** `char*` */
    public const val Group: Int = 0x10

    /** `char*` */
    public const val HideBaseClassMembers: Int = 0x18

    /** `bool` */
    public const val EndGroup: Int = 0x20

    /** `bool` */
    public const val EndGroup2: Int = 0x21

    /** `bool` */
    public const val Advanced: Int = 0x22
}

/**
 * `hkUuid`, 0x10 bytes.
 */
public object HkUuid {
    public const val ByteSize: Int = 0x10

    /** `uint32[4]` */
    public const val Data: Int = 0x0
}

/**
 * `hkUuidObject`, 0x20 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkUuidObject`
 */
public object HkUuidObject {
    public const val ByteSize: Int = 0x20

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa

    /** `struct` */
    public const val Uuid: Int = 0x10
}

/**
 * `hkVector2d`, 0x10 bytes.
 */
public object HkVector2d {
    public const val ByteSize: Int = 0x10

    /** `float` */
    public const val X: Int = 0x0

    /** `float` */
    public const val Y: Int = 0x8
}

/**
 * `hkVector2f`, 0x8 bytes.
 */
public object HkVector2f {
    public const val ByteSize: Int = 0x8

    /** `float` */
    public const val X: Int = 0x0

    /** `float` */
    public const val Y: Int = 0x4
}

/**
 * `hkVertexFormat`, 0x104 bytes.
 */
public object HkVertexFormat {
    public const val ByteSize: Int = 0x104

    /** `struct[32]` */
    public const val Elements: Int = 0x0

    /** `int32` */
    public const val NumElements: Int = 0x100
}

/**
 * `hkVertexFormat::Element`, 0x8 bytes.
 */
public object HkVertexFormatElement {
    public const val ByteSize: Int = 0x8

    /** `enum:uint8` */
    public const val DataType: Int = 0x0

    /** `uint8` */
    public const val NumValues: Int = 0x1

    /** `enum:uint8` */
    public const val Usage: Int = 0x2

    /** `uint8` */
    public const val SubUsage: Int = 0x3

    /** `flags:uint8` */
    public const val Flags: Int = 0x4

    /** `uint8[3]` */
    public const val Pad: Int = 0x5
}

/**
 * `hkWorldMemoryAvailableWatchDog`, 0x10 bytes.
 *
 * `hkBaseObject` -> `hkReferencedObject` -> `hkWorldMemoryAvailableWatchDog`
 */
public object HkWorldMemoryAvailableWatchDog {
    public const val ByteSize: Int = 0x10

    /** `uint16`, from `hkReferencedObject` */
    public const val MemSizeAndFlags: Int = 0x8

    /** `uint16`, from `hkReferencedObject` */
    public const val RefCount: Int = 0xa
}

/**
 * `hke::AddPropertyIfMissing`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeAddPropertyIfMissing {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::CachedData`, 0x1 bytes.
 */
public object HkeCachedData {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hke::ConstantData`, 0x4 bytes.
 *
 * `hk::ValueAttribute` -> `hke::ConstantData`
 */
public object HkeConstantData {
    public const val ByteSize: Int = 0x4

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hke::CustomAssignCallback`, 0x8 bytes.
 */
public object HkeCustomAssignCallback {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Fn: Int = 0x0
}

/**
 * `hke::CustomTypeMatchCallback`, 0x8 bytes.
 */
public object HkeCustomTypeMatchCallback {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Fn: Int = 0x0
}

/**
 * `hke::Derived`, 0x1 bytes.
 */
public object HkeDerived {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hke::EngineNode`, 0x1 bytes.
 */
public object HkeEngineNode {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hke::IndexedArrayInitializer`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeIndexedArrayInitializer {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::IndexedArrayInitializerSpace`, 0x4 bytes.
 *
 * `hk::ValueAttribute` -> `hke::IndexedArrayInitializerSpace`
 */
public object HkeIndexedArrayInitializerSpace {
    public const val ByteSize: Int = 0x4

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hke::Input`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeInput {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::LocalService`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeLocalService {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::MakeCloneDistinct`, 0x8 bytes.
 */
public object HkeMakeCloneDistinct {
    public const val ByteSize: Int = 0x8

    /** `void` */
    public const val Func: Int = 0x0
}

/**
 * `hke::Output`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeOutput {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::PrefabArrayType`, 0x4 bytes.
 */
public object HkePrefabArrayType {
    public const val ByteSize: Int = 0x4

    /** `enum:uint32` */
    public const val Value: Int = 0x0
}

/**
 * `hke::PrefabFixup`, 0x8 bytes.
 */
public object HkePrefabFixup {
    public const val ByteSize: Int = 0x8

    /** `void` */
    public const val Func: Int = 0x0
}

/**
 * `hke::PrefabGroup`, 0x8 bytes.
 */
public object HkePrefabGroup {
    public const val ByteSize: Int = 0x8

    /** `char*` */
    public const val Value: Int = 0x0
}

/**
 * `hke::PrefabProxyField`, 0x8 bytes.
 *
 * `hk::ValueAttribute` -> `hke::PrefabProxyField`
 */
public object HkePrefabProxyField {
    public const val ByteSize: Int = 0x8

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hke::PrefabVisible`, 0x1 bytes.
 */
public object HkePrefabVisible {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hke::PublicFieldPinBehavior`, 0x4 bytes.
 *
 * `hk::ValueAttribute` -> `hke::PublicFieldPinBehavior`
 */
public object HkePublicFieldPinBehavior {
    public const val ByteSize: Int = 0x4

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}

/**
 * `hke::ReferencedWiredArray`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeReferencedWiredArray {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::RequiredProperty`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeRequiredProperty {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::RuleInput`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeRuleInput {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::RuleOutput`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeRuleOutput {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::RuntimeDataSection`, 0x1 bytes.
 */
public object HkeRuntimeDataSection {
    public const val ByteSize: Int = 0x1

    /** `bool` */
    public const val Value: Int = 0x0
}

/**
 * `hke::SerializeCallback`, 0x8 bytes.
 */
public object HkeSerializeCallback {
    public const val ByteSize: Int = 0x8

    /** `void*` */
    public const val Cb: Int = 0x0
}

/**
 * `hke::TempBuffer`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeTempBuffer {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::TriggerOutputData`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeTriggerOutputData {
    public const val ByteSize: Int = 0x1
}

/**
 * `hke::WireableStruct`, 0x1 bytes.
 *
 * Declares and inherits no reflected members: only its size is registered.
 */
public object HkeWireableStruct {
    public const val ByteSize: Int = 0x1
}

/**
 * `hkms::Accumulate`, 0x4 bytes.
 *
 * `hk::ValueAttribute` -> `hkms::Accumulate`
 */
public object HkmsAccumulate {
    public const val ByteSize: Int = 0x4

    /** `float`, from `hk::ValueAttribute` */
    public const val Value: Int = 0x0
}
