package com.sappyoak.dsanalyzer.runtime.write.debugdraw.dsr

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate
import com.sappyoak.dsanalyzer.runtime.inject.HookSite
import com.sappyoak.dsanalyzer.runtime.inject.cave.CodeCave
import com.sappyoak.dsanalyzer.runtime.write.WriteKey

/** The hole every DSR cave leaves by: a `JMP rel32` back to the site's next instruction */
internal const val BACK = "back"

/**
 * An x64 cave from a hand-written [template]. [values] fills every hole but [BACK], which, when the
 * template has one, is the site's next instruction unless a value names it
 */
internal abstract class TemplateCave(
    override val key: WriteKey,
    override val site: HookSite,
    private val template: CaveTemplate
) : CodeCave {
    protected abstract fun values(at: Address): Map<String, Long>

    override fun assemble(origin: Address, at: Address): ByteArray {
        val values = values(at)
        val back = mapOf(BACK to (at + site.displaced.toLong()).value)
            .filterKeys { it in template.names && it !in values }
        return template.build(origin, values + back)
    }
}
