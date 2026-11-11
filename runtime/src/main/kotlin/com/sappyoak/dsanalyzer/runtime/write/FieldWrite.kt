package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.layout.FieldLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory

/** Where [field] is now, or null while its structure cannot be reached */
internal fun GameMemory.addressOf(field: FieldLayout): Address? =
    addressOf(field.pointer).takeUnless { it.isNull }?.let { it + field.offset.toLong() }
