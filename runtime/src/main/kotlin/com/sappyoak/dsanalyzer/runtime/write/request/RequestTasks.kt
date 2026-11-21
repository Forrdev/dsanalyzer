package com.sappyoak.dsanalyzer.runtime.write.request

import com.sappyoak.dsanalyzer.runtime.layout.PlayerGameDataLayout
import com.sappyoak.dsanalyzer.runtime.layout.WriteLayout
import com.sappyoak.dsanalyzer.runtime.write.flags.EventFlagTask
import com.sappyoak.dsanalyzer.runtime.write.items.SpawnItemTask
import com.sappyoak.dsanalyzer.runtime.write.position.RestorePositionTask
import com.sappyoak.dsanalyzer.runtime.write.stats.LevelUpTask
import com.sappyoak.dsanalyzer.runtime.write.stats.StatsEdit
import com.sappyoak.dsanalyzer.runtime.write.talk.TalkTask
import com.sappyoak.dsanalyzer.runtime.write.task.WriteTask
import com.sappyoak.dsanalyzer.runtime.write.warp.WarpTask

/** Builds the task behind each request that takes more than one sample, with the edition's layout */
internal class RequestTasks(private val layout: WriteLayout, private val player: PlayerGameDataLayout) {
    private val stats = StatsEdit(layout.stats, player)

    /** Null for a request that is applied at once rather than as a task */
    fun taskFor(request: WriteRequest): WriteTask? = when (request) {
        is WriteRequest.Warp -> WarpTask(request.destination, layout.warp)
        is WriteRequest.Talk -> TalkTask(request.command, layout.talk)
        is WriteRequest.SpawnItem -> SpawnItemTask(request.category, request.id, request.count, layout.items)
        is WriteRequest.SetFlag -> EventFlagTask(request.flag, request.on, layout.flags)
        is WriteRequest.ReadFlag -> EventFlagTask(request.flag, null, layout.flags)
        is WriteRequest.RestorePosition -> RestorePositionTask(request.saved, layout.position)
        is WriteRequest.SetSouls -> stats.souls(request.souls)
        is WriteRequest.SetHumanity -> stats.humanity(request.humanity)
        is WriteRequest.SetNewGame -> stats.newGame(request.cycle)
        is WriteRequest.SetCovenant -> stats.covenant(request.covenant)
        is WriteRequest.SetCovenantPoints -> stats.covenantPoints(request.covenant, request.points)
        is WriteRequest.SetAttributes -> LevelUpTask(request.attributes, layout.stats.levelUp, player)
        is WriteRequest.Run -> request.task
        is WriteRequest.SetCheat, is WriteRequest.Undo, WriteRequest.RevertAll -> null
    }
}
