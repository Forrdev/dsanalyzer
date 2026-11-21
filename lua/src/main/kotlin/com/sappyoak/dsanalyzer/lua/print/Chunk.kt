package com.sappyoak.dsanalyzer.lua.print

import com.sappyoak.dsanalyzer.lua.ir.FunctionBody

/** A main function's body as a source file */
public fun printChunk(main: FunctionBody): String = FunctionPrinter().body(main, "")
