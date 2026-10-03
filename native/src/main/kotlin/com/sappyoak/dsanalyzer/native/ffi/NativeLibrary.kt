package com.sappyoak.dsanalyzer.native.ffi

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
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

    public fun downcall(
        symbol: String,
        descriptor: FunctionDescriptor,
        capture: CallState
    ): MethodHandle = handles.computeIfAbsent("$symbol:$descriptor") {
        linker.downcallHandle(
            lookup.find(symbol).orElseThrow {
                UnsatisfiedLinkError("$name!$symbol no found")
            },
            descriptor,
            Linker.Option.captureCallState(capture.captureName)
        )
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

internal fun NativeLibrary.downcallWindows(
    symbol: String,
    descriptor: FunctionDescriptor
): MethodHandle = downcall(symbol, descriptor, CallState.WindowsLastError)

internal fun NativeLibrary.downcallLinux(
    symbol: String,
    descriptor: FunctionDescriptor
): MethodHandle = downcall(symbol, descriptor, CallState.Errno)