package com.sappyoak.dsanalyzer.game.assets

/**
 * Asset names, not file system paths.
 *
 * These identify files inside the game and are the same whether it is packed ,extracted or
 * unpacked in place. A packed lookup hashes the name with no filesystem involved at all.
 * Turing one into a real location is [GameAssetResolver]'s job
 */
object GameAssetPaths {
    const val DCX_EXTENSION = ".dcx"
    const val BHD5_EXTENSION = ".bhd5"
    const val BDT_EXTENSION = ".bdt"

    val ARCHIVE_NAMES = listOf("dvdbnd0", "dvdbnd1", "dvdbnd2", "dvdbnd3")

    val LOOSE_FILE_MARKERS = listOf(
        "event/common.emevd",
        "map/MapStudio/m10_02_00_00.msb",
        "chr/c0000.anibnd"
    )

    val REMASTERED_ONLY_FILE_MARKERS = listOf(
        "map/m18_01_00_00",
        "sfx/frpg_sfxbnd_m18.ffxbnd.dcx"
    )

    val CommonEMEVD = GameAsset("/event/common.emevd$DCX_EXTENSION")
    val GameParam = GameAsset("/param/gameparam/gameparam.parambnd$DCX_EXTENSION")
    val EventCommon = GameAsset("/script/eventcommon.luabnd$DCX_EXTENSION")
    val AICommon = GameAsset("/script/aicommon.luabnd$DCX_EXTENSION")

    fun archiveFilePair(name: String): Pair<String, String> =
        archiveHeader(name) to archiveData(name)

    fun archiveHeader(name: String) = "$name.$BHD5_EXTENSION"
    fun archiveData(name: String) = "$name.$BDT_EXTENSION"

    fun msb(mapId: String) = GameAsset("/map/mapstudio/$mapId.msb")
    fun navmesh(mapId: String) = GameAsset("/map/$mapId/$mapId.nvmbnd$DCX_EXTENSION")

    fun lowCollisionHeader(mapId: String) = collisionHeader(mapId, true)
    fun lowCollisionData(mapId: String) = collisionData(mapId, true)
    fun lowCollisionPair(mapId: String): Pair<GameAsset, GameAsset> =
        lowCollisionHeader(mapId) to lowCollisionData(mapId)

    fun highCollisionHeader(mapId: String) = collisionHeader(mapId, false)
    fun highCollisionData(mapId: String) = collisionData(mapId, false)
    fun highCollisionPair(mapId: String): Pair<GameAsset, GameAsset> =
        highCollisionHeader(mapId) to highCollisionData(mapId)

    fun emevd(mapId: String) = GameAsset("/event/$mapId.emevd$DCX_EXTENSION")
    fun emeld(mapId: String) = GameAsset("/event/$mapId.emeld$DCX_EXTENSION")
    fun aiScripts(mapId: String) = GameAsset("/script/$mapId.luabnd$DCX_EXTENSION")

    fun characterAnibnd(chrId: String) = GameAsset("/chr/$chrId.anibnd$DCX_EXTENSION")
    fun characterChrbnd(chrId: String) = GameAsset("/chr/$chrId.chrbnd$DCX_EXTENSION")
    fun playerCategoryAnibnd(decade: Int) = GameAsset("/chr/c0000_at${decade}x.anibnd$DCX_EXTENSION")
    fun playerPairedAnibnd(enemyChrId: String) = GameAsset("/chr/c0000_$enemyChrId.anibnd$DCX_EXTENSION")

    fun getArchivePairs(): List<Pair<String, String>> = ARCHIVE_NAMES.map(::archiveFilePair)

    private fun collisionHeader(mapId: String, low: Boolean): GameAsset {
        val prefix = if (low) "l" else "h"
        return GameAsset("/map/$mapId/$prefix${mapId.removePrefix("m")}.hkxbhd$DCX_EXTENSION")
    }

    private fun collisionData(mapId: String, low: Boolean): GameAsset {
        val prefix = if (low) "l" else "h"
        return GameAsset("/map/$mapId/$prefix${mapId.removePrefix("m")}.hkxbdt")
    }
}