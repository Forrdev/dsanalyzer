package com.sappyoak.dsanalyzer.runtime.write

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.runtime.layout.GameLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.snapshot.WorldPlace
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.DrawBackend

/**
 * A feature that runs inside a session's sampling loop without the session
 * knowing what it is. Every call comes on the sampling thread
 */
public interface SessionExtension {
    public fun attached(context: SessionContext)
    public fun afterSample()
    public fun release()
}

public class SessionContext internal constructor(
    public val edition: GameEdition,
    public val memory: GameMemory,
    public val writer: GameWriter,
    public val module: AddressRange,
    public val layout: GameLayout,
    internal val draw: DrawBackend,
    private val currentPlace: () -> WorldPlace?
) {
    public val place: WorldPlace? get() = currentPlace()
}
