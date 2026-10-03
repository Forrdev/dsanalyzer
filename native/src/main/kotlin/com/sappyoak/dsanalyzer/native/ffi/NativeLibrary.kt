package com.sappyoak.dsanalyzer.native.ffi

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.invoke.MethodHandle
import java.util.concurrent.ConcurrentHashMap

/**
 * Shared access to a native library
 */
public class NativeLibrary private constructor(
    private val lookup: SymbolLookup,
    val name: String
) {
    private val handles: ConcurrentHashMap<String, MethodHandle> = ConcurrentHashMap<String, MethodHandle>()

    public operator fun contains(symbol: String): Boolean = lookup.find(symbol).isPresent


    public fun downcall(symbol: String, descriptor: FunctionDescriptor): MethodHandle =
        handle(symbol, "$symbol:$descriptor") { address ->
            linker.downcallHandle(address, descriptor)
        }

    /**
     * A handle that captures the platform's error state as the call returns
     *
     * A handle takes a **leading** [MemorySegment] holding that state, ahead of everything
     * [descriptor] declares, and [CallState.read] reads the code out of it once the call returns
     */
    public fun downcallCapturing(
        symbol: String,
        descriptor: FunctionDescriptor,
        capture: CallState
    ): MethodHandle = handle(symbol, "$symbol:$descriptor:${capture.captureName}") { address ->
        linker.downcallHandle(address, descriptor, Linker.Option.captureCallState(capture.captureName))
    }

    private fun handle(symbol: String, key: String, bind: (MemorySegment) -> MethodHandle): MethodHandle =
        handles.computeIfAbsent(key) {
            bind(lookup.find(symbol).orElseThrow {
                UnsatisfiedLinkError("$name!$symbol not found")
            })
        }

    public companion object {
        private val linker: Linker = Linker.nativeLinker()
        private val arena: Arena = Arena.ofShared()
        private val libraries: ConcurrentHashMap<String, NativeLibrary> = ConcurrentHashMap<String, NativeLibrary>()

        public fun open(name: String): NativeLibrary = libraries.computeIfAbsent(name) {
            NativeLibrary(SymbolLookup.libraryLookup(it, arena), it)
        }

        /** The symbols this process already hash, which covers the C library it was loaded with */
        public fun builtin(): NativeLibrary = libraries.computeIfAbsent("builtin") {
            NativeLibrary(linker.defaultLookup(), it)
        }

        public fun openOrNull(name: String): NativeLibrary? = runCatching { open(name) }.getOrNull()
    }
}

internal fun NativeLibrary.capturingLastError(
    symbol: String,
    descriptor: FunctionDescriptor
): MethodHandle = downcallCapturing(symbol, descriptor, CallState.WindowsLastError)

internal fun NativeLibrary.capturingErrno(
    symbol: String,
    descriptor: FunctionDescriptor
): MethodHandle = downcallCapturing(symbol, descriptor, CallState.Errno)