package com.sappyoak.dsanalyzer.runtime.sampling

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.FakeGame

private const val WILDCARD = "??"

/** One slot per site, far enough apart for the longest pattern */
private const val SITE_STRIDE = 0x40
private const val STATIC_STRIDE = 0x10

/**
 * Plants each of [signatures] by rendering its pattern back out, from [sitesAt] on, and points its
 * operand at a static of its own from [staticsAt] on. Returns each signature's static.
 *
 * A pattern that begins another one matches the same site in the game, so the two share a slot
 * rather than matching twice
 */
internal fun FakeGame.plantSignatures(signatures: List<Signature>, sitesAt: Int, staticsAt: Int): Map<Signature, Int> {
    val statics = signatures.withIndex().associate { (slot, signature) -> signature to staticsAt + slot * STATIC_STRIDE }
    val sites = signatures.groupBy { signature -> signatures.filter { it.covers(signature) }.maxBy { it.pattern.length } }

    sites.entries.forEachIndexed { slot, (longest, sharing) ->
        val at = sitesAt + slot * SITE_STRIDE
        longest.tokens().forEachIndexed { index, token ->
            if (token != WILDCARD) write(at + index, token.toInt(16))
        }
        sharing.forEach { aim(at, it.target, statics.getValue(it)) }
    }
    return statics
}

private fun FakeGame.aim(site: Int, target: SignatureTarget, static: Int) = when (target) {
    SignatureTarget.Match -> Unit
    is SignatureTarget.Embedded -> pointer(site + target.at, static)
    is SignatureTarget.Relative -> int(site + target.at, static - (site + target.instructionEnd))
}

private fun Signature.tokens(): List<String> = pattern.toString().split(' ')

/** Whether [other]'s fixed bytes are where this pattern begins */
private fun Signature.covers(other: Signature): Boolean {
    val mine = tokens()
    val theirs = other.tokens()
    return theirs.size <= mine.size && theirs.indices.all { theirs[it] == WILDCARD || theirs[it] == mine[it] }
}
