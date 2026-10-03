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
    val bases = mutableMapOf<Signature, Address>()
    val unresolved = mutableListOf<UnresolvedSignature>()

    pointers.groupBy { it.base }.forEach { (signature, sharing) ->
        when (val outcome = resolve(signature, module)) {
            is SignatureScan.Resolved -> bases[signature] = outcome.address
            else -> unresolved.add(UnresolvedSignature(signature.name, outcome, sharing.map { it.name }))
        }
    }

    unresolved.forEach { failure ->
        logger.atWarn {
            message = "Signature did not resolve"
            payload = mapOf(
                "signature" to failure.signature,
                "outcome" to failure.outcome.toString(),
                "leaves" to failure.pointers.joinToString()
            )
        }
    }

    logger.atInfo {
        message = "Signature scan finished"
        payload = mapOf(
            "resolved" to bases.size,
            "unresolved" to unresolved.size
        )
    }

    return ResolvedPointers(bases, unresolved)
}