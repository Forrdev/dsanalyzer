package com.sappyoak.dsanalyzer.game.data

import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.files.exists
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.esd.availableTalk
import com.sappyoak.dsanalyzer.game.world.esd.loadEsd
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.game.world.maps.loadMSB
import com.sappyoak.dsanalyzer.game.world.params.GameParams
import com.sappyoak.dsanalyzer.game.world.params.ParamNames
import com.sappyoak.dsanalyzer.game.world.params.loadGameParams
import com.sappyoak.dsanalyzer.game.world.params.loadParamNames
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.availableScripts
import com.sappyoak.dsanalyzer.game.world.scripts.loadEventNameTranslations
import com.sappyoak.dsanalyzer.game.world.scripts.loadInstructionDefinitions
import com.sappyoak.dsanalyzer.game.world.scripts.loadScript
import com.sappyoak.dsanalyzer.game.world.scripts.loadScriptNames
import com.sappyoak.dsanalyzer.game.world.scripts.translatedBy
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val KEPT_LAYOUTS = 8
private const val KEPT_ESDS = 6

public class GameData internal constructor(
    public val installation: Installation,
    private val files: InstallationFiles,
    scope: CoroutineScope
) {
    private val edition get() = installation.build.edition

    private val catalogs = Memo<Unit, GameCatalog>("catalog", scope)
    private val talkIds = Memo<Unit, Map<MapId, List<Int>>>("talk catalog", scope)
    private val gameParams = Memo<Unit, GameParams?>("params", scope)
    private val names = Memo<TextLanguage, ParamNames>("param names", scope)
    private val scripts = Memo<ScriptId, DecodedScript?>("script", scope)
    private val eventNames = Memo<ScriptId, EventNames>("script names", scope)
    private val layouts = Memo<MapId, MSB?>("layout", scope, KEPT_LAYOUTS)
    private val esds = Memo<EsdSource, Esd?>("esd", scope, KEPT_ESDS)

    public suspend fun catalog(): GameCatalog = catalogs.get(Unit) {
        parse { game ->
            val maps = game.availableMaps(loadFileManifest(edition))
            GameCatalog(maps, game.availableScripts(maps))
        }
    }

    public suspend fun talk(): Map<MapId, List<Int>> = talkIds.get(Unit) {
        val maps = catalog().maps
        parse { it.availableTalk(maps) }
    }

    public suspend fun params(): GameParams? = gameParams.get(Unit) { parse { it.loadGameParams(edition) } }

    public suspend fun paramNames(language: TextLanguage): ParamNames =
        names.get(language) { parse { it.loadParamNames(language) } }

    public suspend fun script(script: ScriptId): DecodedScript? = scripts.get(script) {
        parse { it.loadScript(script)?.decoded(script, loadInstructionDefinitions()) }
    }

    public suspend fun scriptNames(script: ScriptId): EventNames = eventNames.get(script) {
        parse { it.loadScriptNames(script)?.translatedBy(loadEventNameTranslations()) ?: EventNames.Empty }
    }
    public suspend fun msb(map: MapId): MSB? = layouts.get(map) { parse { it.loadMSB(map) } }

    public suspend fun esd(source: EsdSource): Esd? = esds.get(source) { parse { it.loadEsd(source) } }

    public suspend fun holds(path: String): Boolean = files.use(installation) { it.exists(path) }

    public suspend fun <T> read(block: suspend (GameFiles) -> T): T = files.use(installation, block)

    internal fun clear() = listOf(catalogs, talkIds, gameParams, names, scripts, eventNames, layouts, esds)
        .forEach(Memo<*, *>::clear)

    private suspend fun <T> parse(block: (GameFiles) -> T): T =
        files.use(installation) { game -> withContext(Dispatchers.Default) { block(game)} }
}
