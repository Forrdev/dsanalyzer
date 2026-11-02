package com.sappyoak.dsanalyzer.analysis.script

import com.sappyoak.dsanalyzer.analysis.engine.ContextKey
import com.sappyoak.dsanalyzer.analysis.engine.GameSource
import com.sappyoak.dsanalyzer.analysis.engine.RunUnit
import com.sappyoak.dsanalyzer.analysis.engine.Scope
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.decode
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.loadInstructionDefinitions
import com.sappyoak.dsanalyzer.game.world.scripts.loadScript

public class DecodedScript(
    public val script: ScriptId,
    public val events: Map<Long, List<DecodedInstruction>>
)

public object ScriptInput : ContextKey<DecodedScript>("event script", Scope.PerScript) {
    private val definitions by lazy { loadInstructionDefinitions() }

    override suspend fun load(source: GameSource, unit: RunUnit): DecodedScript? {
        val script = (unit as RunUnit.Script).script
        val emevd = source.read { files -> files.loadScript(script) } ?: return null
        return DecodedScript(script, emevd.events.associate { it.id to it.decode(definitions) })
    }
}
