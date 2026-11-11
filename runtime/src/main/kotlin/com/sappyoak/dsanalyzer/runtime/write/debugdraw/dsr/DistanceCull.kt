package com.sappyoak.dsanalyzer.runtime.write.debugdraw.dsr

import com.sappyoak.dsanalyzer.native.memory.Address
import com.sappyoak.dsanalyzer.runtime.dsr.DSRHookSites
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate
import com.sappyoak.dsanalyzer.runtime.inject.CaveTemplate.Hole
import com.sappyoak.dsanalyzer.runtime.write.WriteKey

private const val CONTROL = "control"

/**
 * Collapses a triangle whose first corner is farther from the player than the radius: v1 and v2
 * are pointed at v0, so the visitor still fills its slot
 */
internal class DistanceCull(private val control: Address) : TemplateCave(
    WriteKey("debugdraw:distance"),
    DSRHookSites.AddTriangle,
    TEMPLATE
) {
    override fun values(at: Address): Map<String, Long> = mapOf(CONTROL to control.value)

    private companion object {
        val TEMPLATE = CaveTemplate(
            CaveTemplate.hex(
                """
                48 B8 00 00 00 00 00 00 00 00 F3 41 0F 10 00 F3
                0F 5C 40 10 F3 0F 59 C0 F3 41 0F 10 48 04 F3 0F
                5C 48 14 F3 0F 59 C9 F3 0F 58 C1 F3 41 0F 10 48
                08 F3 0F 5C 48 18 F3 0F 59 C9 F3 0F 58 C1 0F 2F
                40 08 76 08 4D 89 C1 4C 89 44 24 28 53 48 8B 59
                18 E9 00 00 00 00
                """
            ),
            mapOf(CONTROL to Hole.Abs64(2), BACK to Hole.Rel32(82))
        )
    }
}
