package com.sappyoak.dsanalyzer.runtime.ptde

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual

import java.io.File
import java.lang.reflect.Modifier

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/**
 * The widest single read a `StructView` offers, which is a `vec3` of three floats. Every structure
 * has to have room for one past its highest offset, so that a field added at the top offset is not
 * an overrun only that field's first reader finds out about
 */
private const val WIDEST_READ = 12

private const val PACKAGE = "com/sappyoak/dsanalyzer/runtime/ptde"

private const val EXPECTED_STRUCTURES = 12

private const val CLASS_SUFFIX = ".class"

class StructSizeTest : FunSpec({
    test("every structure has room for its highest offset and the widest read past it") {
        val structures = structures()

        structures.size shouldBeGreaterThanOrEqual EXPECTED_STRUCTURES
        assertSoftly {
            structures.forEach { (pointer, offsets) ->
                val highest = offsets.maxBy { it.value }
                withClue("${pointer.name}'s highest offset is ${highest.describe()}") {
                    pointer.size shouldBeGreaterThanOrEqual highest.value + WIDEST_READ
                }
            }
        }
    }

    test("no structure declares a negative constant, which would be a mask rather than an offset") {
        structures()
            .flatMap { (pointer, offsets) -> offsets.filter { it.value < 0 }.map { "${pointer.name}.${it.name}" } }
            .shouldBeEmpty()
    }
})

private data class Offset(val name: String, val value: Int) {
    fun describe(): String = "$name = 0x%X".format(value)
}

private data class Structure(val pointer: GamePointer, val offsets: List<Offset>)

/**
 * Every object in the package that declares a [GamePointer], paired with its own constants
 */
private fun structures(): List<Structure> = classFiles().flatMap { type ->
    val instance = type.declaredFields.firstOrNull { it.name == "INSTANCE" } ?: return@flatMap emptyList()
    val accessors = type.methods.filter { it.returnType == GamePointer::class.java && it.parameterCount == 0 }

    val offsets = type.declaredFields
        .filter { Modifier.isStatic(it.modifiers) && Modifier.isPublic(it.modifiers) }
        .filter { it.type == Int::class.javaPrimitiveType }
        .map { Offset(it.name, it.getInt(null)) }

    if (offsets.isEmpty()) {
        emptyList()
    } else {
        accessors.map { Structure(it.invoke(instance.get(null)) as GamePointer, offsets) }
    }
}

/**
 * The package's classes, read off every classpath directory that holds it
 */
private fun classFiles(): List<Class<*>> {
    val resources = Thread.currentThread().contextClassLoader.getResources(PACKAGE).toList()
    check(resources.isNotEmpty()) { "$PACKAGE is not on the test classpath, so no structure could be checked" }

    return resources.flatMap { resource ->
        val directory = File(resource.toURI())
        check(directory.isDirectory) { "$PACKAGE resolved to ${resource.protocol}, which this scan cannot walk" }

        directory.listFiles().orEmpty()
            .filter { it.name.endsWith(CLASS_SUFFIX) && !it.name.contains('$') }
            .map { Class.forName("${PACKAGE.replace('/', '.')}.${it.name.removeSuffix(CLASS_SUFFIX)}") }
    }
}