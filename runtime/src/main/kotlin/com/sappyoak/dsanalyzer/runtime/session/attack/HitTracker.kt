package com.sappyoak.dsanalyzer.runtime.session.attack

import com.sappyoak.dsanalyzer.runtime.read.attack.HitKey
import com.sappyoak.dsanalyzer.runtime.read.attack.HitRead


internal class HitTracker(
    private val lingerTicks: Int,
    private val sweepLimit: Int
) {
    private val tracked = LinkedHashMap<HitKey, Tracked>()

    fun update(reads: List<HitRead>): List<HitSnapshot> {
        val seen = HashSet<HitKey>(reads.size)
        for (read in reads) {
            seen.add(read.key)
            val hit = tracked.getOrPut(read.key) { Tracked(read.owner, read.atkParamId) }
            hit.owner = read.owner
            hit.attackParamId = read.atkParamId
            hit.unseen = 0

            if (hit.sweep.lastOrNull() != read.shape) hit.sweep.addLast(read.shape)
            if (hit.sweep.size > sweepLimit) hit.sweep.removeFirst()
        }

        val each = tracked.entries.iterator()
        while (each.hasNext()) {
            val (key, hit) = each.next()
            if (key !in seen && hit.unseen++ > lingerTicks) each.remove()
        }

        return tracked.map { (key, hit) ->
            HitSnapshot(
                id = key.id,
                owner = hit.owner,
                atkParamId = hit.attackParamId,
                sweep = hit.sweep.toList(),
                live = hit.unseen == 0
            )
        }
    }

    fun clear() {
        tracked.clear()
    }

    private class Tracked(var owner: Int, var attackParamId: Int) {
        val sweep = ArrayDeque<HitShape>()
        var unseen = 0
    }
}
