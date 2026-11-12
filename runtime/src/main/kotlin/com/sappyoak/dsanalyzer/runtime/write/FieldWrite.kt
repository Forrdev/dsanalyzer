package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.FieldLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/** Where [field] is now, or null while its structure cannot be reached */
internal fun GameMemory.addressOf(field: FieldLayout): Address? =
    addressOf(field.pointer).takeUnless { it.isNull }?.let { it + field.offset.toLong() }

/** Where [pointer] is now; throws while it cannot be reached, which a task reports as its failure */
internal fun GameMemory.found(pointer: GamePointer): Address =
    addressOf(pointer).takeUnless { it.isNull } ?: error("${pointer.name} could not be found")
