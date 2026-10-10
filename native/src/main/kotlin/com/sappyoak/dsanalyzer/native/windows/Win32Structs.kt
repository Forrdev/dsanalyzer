package com.sappyoak.dsanalyzer.native.windows

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemoryLayout.PathElement.groupElement
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout.*

/**
 * Win32 structures as laid out for a 64-bit caller
 */
private const val MAX_PATH = 260
private const val MAX_MODULE_NAME = 256
private const val WCHAR = 2L


internal val PROCESS_ENTRY: StructLayout = MemoryLayout.structLayout(
    JAVA_INT.withName("dwSize"),
    JAVA_INT.withName("cntUsage"),
    JAVA_INT.withName("th32ProcessID"),
    MemoryLayout.paddingLayout(4),
    JAVA_LONG.withName("th32DefaultHeapID"),
    JAVA_INT.withName("th32ModuleID"),
    JAVA_INT.withName("cntThreads"),
    JAVA_INT.withName("th32ParentProcessID"),
    JAVA_INT.withName("pcPriClassBase"),
    JAVA_INT.withName("dwFlags"),
    MemoryLayout.sequenceLayout(MAX_PATH.toLong(), JAVA_SHORT).withName("szExeFile"),
    MemoryLayout.paddingLayout(4)
)

internal val MODULE_ENTRY: StructLayout = MemoryLayout.structLayout(
    JAVA_INT.withName("dwSize"),
    JAVA_INT.withName("th32ModuleID"),
    JAVA_INT.withName("th32ProcessID"),
    JAVA_INT.withName("GlblcntUsage"),
    JAVA_INT.withName("ProccntUsage"),
    MemoryLayout.paddingLayout(4),
    ADDRESS.withName("modBaseAddr"),
    JAVA_INT.withName("modBaseSize"),
    MemoryLayout.paddingLayout(4),
    ADDRESS.withName("hModule"),
    MemoryLayout.sequenceLayout(MAX_MODULE_NAME.toLong(), JAVA_SHORT).withName("szModule"),
    MemoryLayout.sequenceLayout(MAX_PATH.toLong(), JAVA_SHORT).withName("szExePath"),
)

internal val MEMORY_BASIC_INFORMATION: StructLayout = MemoryLayout.structLayout(
    ADDRESS.withName("BaseAddress"),
    ADDRESS.withName("AllocationBase"),
    JAVA_INT.withName("AllocationProtect"),
    JAVA_SHORT.withName("PartitionId"),
    MemoryLayout.paddingLayout(2),
    JAVA_LONG.withName("RegionSize"),
    JAVA_INT.withName("State"),
    JAVA_INT.withName("Protect"),
    JAVA_INT.withName("Type"),
    MemoryLayout.paddingLayout(4),
)

internal val RECT: StructLayout = MemoryLayout.structLayout(
    JAVA_INT.withName("left"),
    JAVA_INT.withName("top"),
    JAVA_INT.withName("right"),
    JAVA_INT.withName("bottom")
)

internal val POINT: StructLayout = MemoryLayout.structLayout(
    JAVA_INT.withName("x"),
    JAVA_INT.withName("y")
)

internal val MONITOR_INFO: StructLayout = MemoryLayout.structLayout(
    JAVA_INT.withName("cbSize"),
    RECT.withName("rcMonitor"),
    RECT.withName("rcWork"),
    JAVA_INT.withName("dwFlags")
)

internal fun StructLayout.offsetOf(field: String): Long = byteOffset(groupElement(field))

internal fun MemorySegment.intField(layout: StructLayout, field: String): Int =
    get(JAVA_INT, layout.offsetOf(field))

internal fun MemorySegment.longField(layout: StructLayout, field: String): Long =
    get(JAVA_LONG, layout.offsetOf(field))

internal fun MemorySegment.addressField(layout: StructLayout, field: String): Long =
    get(ADDRESS, layout.offsetOf(field)).address()

internal fun MemorySegment.wideString(layout: StructLayout, field: String): String {
    val start = layout.offsetOf(field)
    val capacity = layout.select(groupElement(field)).byteSize() / WCHAR
    val length = (0 until capacity).firstOrNull { get(JAVA_SHORT, start + it * WCHAR) == 0.toShort() } ?: capacity
    return String(asSlice(start, length * WCHAR).toArray(JAVA_BYTE), Charsets.UTF_16LE)
}