package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.native.memory.AOBPattern
import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureScan
import com.sappyoak.dsanalyzer.native.memory.intAt
import com.sappyoak.dsanalyzer.native.memory.resolve
import com.sappyoak.dsanalyzer.native.process.ProcessMemory
import com.sappyoak.dsanalyzer.runtime.inject.HookSite

/**
 * Written just ahead of every cave, so a jump found at a site can be told to be the tool's own: another
 * tool's hook, a Cheat Engine script say, jumps out of the module just the same and is left alone
 */
internal val CAVE_MARK: ByteArray = "dsanalyzer caves".toByteArray(Charsets.US_ASCII)

private const val JMP: Byte = 0xE9.toByte()
private const val JMP_SIZE = 5

/**
 * Where [site] still holds a jump into one of the tool's cave blocks, which a session that ended without
 * reverting leaves behind; null when it holds anything else. A hooked site no longer matches its own
 * signature when the pattern covers the hooked bytes, so they are left open in the scan
 */
internal fun ProcessMemory.leftoverCave(site: HookSite, module: AddressRange): Address? {
    val match = (resolve(site.openSignature(), module) as? SignatureScan.Resolved)?.address ?: return null
    val at = match + site.offset.toLong()
    if (read(at, 1)?.single() != JMP) return null

    val target = at + JMP_SIZE.toLong() + (intAt(at + 1) ?: return null).toLong()
    val mark = target - CAVE_MARK.size.toLong()
    return at.takeIf { read(mark, CAVE_MARK.size)?.contentEquals(CAVE_MARK) == true }
}

private fun HookSite.openSignature(): Signature {
    val tokens = signature.pattern.toString().split(" ").toMutableList()
    (offset until offset + displaced).filter { it in tokens.indices }.forEach { tokens[it] = "??" }
    return Signature(signature.name, AOBPattern.parse(tokens.joinToString(" ")), signature.target)
}
