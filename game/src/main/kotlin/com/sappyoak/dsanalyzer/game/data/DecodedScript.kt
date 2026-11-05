package com.sappyoak.dsanalyzer.game.data

import com.sappyoak.dsanalyzer.formats.emevd.Emevd
import com.sappyoak.dsanalyzer.formats.emevd.emedf.DecodedInstruction
import com.sappyoak.dsanalyzer.formats.emevd.emedf.Emedf
import com.sappyoak.dsanalyzer.formats.emevd.emedf.decode
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public class DecodedScript(
    public val script: ScriptId,
    public val emevd: Emevd,
    public val definitions: Emedf,
    public val events: Map<Long, List<DecodedInstruction>>
)

internal fun Emevd.decoded(script: ScriptId, definitions: Emedf): DecodedScript =
    DecodedScript(script, this, definitions, events.associate { it.id to it.decode(definitions) })
