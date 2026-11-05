package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.memory.AddressRange
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.write.GameWriter

/**
 * A feature that runs inside a session's sampling loop without the session
 * knowing what it is. Every call comes on the sampling thread
 */
public interface SessionExtension {
    public fun attached(context: SessionContext)
    public fun afterSample()
    public fun release()
}

public class SessionContext(
    public val edition: GameEdition,
    public val memory: GameMemory,
    public val writer: GameWriter,
    public val module: AddressRange,
    private val currentPlace: () -> WorldPlace?
) {
    public val place: WorldPlace? get() = currentPlace()
}
