package com.sappyoak.dsanalyzer.runtime.pointers

import io.github.oshai.kotlinlogging.KotlinLogging

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureScan
import com.sappyoak.dsanalyzer.native.memory.resolve
import com.sappyoak.dsanalyzer.native.process.ProcessMemory

private val logger = KotlinLogging.logger {  }

public data class UnresolvedSignature(
    public val signature: String,
    public val outcome: SignatureScan,
    public val pointers: List<String>
)

public class ResolvedPointers internal constructor(
    private val bases: Map<Signature, Address>,
    public val unresolved: List<UnresolvedSignature>
) {
    public val resolved: Int get() = bases.size
    public val complete: Boolean get() = unresolved.isEmpty()

    public operator fun get(pointer: GamePointer): Address? = bases[pointer.base]

    internal operator fun plus(later: ResolvedPointers): ResolvedPointers =
        ResolvedPointers(bases + later.bases, later.unresolved)

    public fun describe(): String = when {
        complete -> "$resolved signatures captured"
        else -> "$resolved signatures resolved, ${unresolved.joinToString { "${it.signature} ${it.outcome}"} }"
    }
}

/** Scans [module] for each distinct signature among [pointers] */
public fun ProcessMemory.resolvePointers(
    pointers: Iterable<GamePointer>,
    module: AddressRange
): ResolvedPointers {
    val scanned = scan(pointers, module)

    scanned.unresolved.forEach { failure ->
        logger.atWarn {
            message = "Signature did not resolve"
            payload = mapOf(
                "signature" to failure.signature,
                "outcome" to failure.outcome.toString(),
                "leaves" to failure.pointers.joinToString()
            )
        }
    }

    logger.info {
        "Signature scan finished: resolved=${scanned.resolved}, unresolved=${scanned.unresolved.size}"
    }
    return scanned
}


/**
 * Scans again for the signatures [current] did not find, keeping everything it did.
 *
 * For a game attached to before its code was ready to scan, which misses signatures that are
 * there a moment later. Returns null when nothing new resolved, so only a change is reported, and
 * logs nothing else: a signature this build lacks fails every retry, and once was said already
 */
public fun ProcessMemory.resolveMissing(
    current: ResolvedPointers,
    pointers: Iterable<GamePointer>,
    module: AddressRange
): ResolvedPointers? {
    val missing = pointers.filter { current[it] == null }
    if (missing.isEmpty()) return null

    val later = scan(missing, module)
    if (later.resolved == 0) return null

    logger.info { "A later scan resolved ${later.resolved} more signatures, ${later.unresolved.size} still missing" }
    return current + later
}

private fun ProcessMemory.scan(pointers: Iterable<GamePointer>, module: AddressRange): ResolvedPointers {
    val bases = mutableMapOf<Signature, Address>()
    val unresolved = mutableListOf<UnresolvedSignature>()

    pointers.groupBy { it.base }.forEach { (signature, sharing) ->
        when (val outcome = resolve(signature, module)) {
            is SignatureScan.Resolved -> bases[signature] = outcome.address
            else -> unresolved.add(UnresolvedSignature(signature.name, outcome, sharing.map { it.name }))
        }
    }
    return ResolvedPointers(bases, unresolved)
}
