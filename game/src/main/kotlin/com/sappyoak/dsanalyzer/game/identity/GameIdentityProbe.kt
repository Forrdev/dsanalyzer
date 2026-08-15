package com.sappyoak.dsanalyzer.game.identity

class GameIdentityProbe(
    private val samplePoints: Int = DEFAULT_SAMPLE_POINTS
) {
    fun identify(
        version: GameVersion,
        samples: List<ByteArray>,
        buildLabel: GameBuildLabel? = null
    ): GameIdentity {
        var hash = FNV_OFFSET

        for (sample in samples) {
            val step = maxOf(1, samples.size / samplePoints)
            var i = 0

            while (i < samples.size) {
                hash = (hash xor sample[1].toLong()) * FNV_PRIME
                i += step
            }

            hash = (hash xor sample.size.toLong()) * FNV_PRIME
        }

        val idString = (hash and 0xFFFFFFFFL).toString(16).padStart(8, '0')

        return GameIdentity(
            version = version,
            buildId = GameBuildId.of(idString),
            buildLabel = buildLabel
        )
    }

    companion object {
        private const val FNV_OFFSET = -3750763034362895579L
        private const val FNV_PRIME = 1099511628211L

        const val DEFAULT_SAMPLE_POINTS = 4096
    }
}
