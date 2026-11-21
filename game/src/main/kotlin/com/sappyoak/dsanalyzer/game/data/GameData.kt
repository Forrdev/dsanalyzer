package com.sappyoak.dsanalyzer.game.data

import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.fmg.Fmg
import com.sappyoak.dsanalyzer.formats.msb.MSB
import com.sappyoak.dsanalyzer.formats.tae.Tae
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.exists
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.ai.AiIndex
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.ai.LuaSource
import com.sappyoak.dsanalyzer.game.world.ai.charactersByThink
import com.sappyoak.dsanalyzer.game.world.animations.AnimationSetId
import com.sappyoak.dsanalyzer.game.world.animations.availableAnimationSets
import com.sappyoak.dsanalyzer.game.world.animations.loadTae
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
import com.sappyoak.dsanalyzer.game.world.scripts.index.ScriptIndex
import com.sappyoak.dsanalyzer.game.world.scripts.index.indexScripts
import com.sappyoak.dsanalyzer.game.world.text.TextCategory
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage
import com.sappyoak.dsanalyzer.game.world.text.loadText
import java.nio.file.Path

private const val KEPT_LAYOUTS = 8
private const val KEPT_ESDS = 6
private const val KEPT_ANIMATION_SETS = 6

/**
 * One installation's parsed data, each piece loaded once and shared. [installationDirectory] is where its derived
 * data is kept between sessions, if anywhere. Lives until [InstallationData] retires or replaces it
 */
public class GameData internal constructor(private val data: DataScope, installationDirectory: Path?) {
    public val installation: Installation get() = data.installation
    private val edition get() = installation.build.edition

    private val catalogs = data.memo<Unit, GameCatalog>("catalog")
    private val talkIds = data.memo<Unit, Map<MapId, List<Int>>>("talk catalog")
    private val gameParams = data.memo<Unit, GameParams?>("params")
    private val names = data.memo<TextLanguage, ParamNames>("param names")
    private val texts = data.memo<Pair<TextLanguage, Set<TextCategory>>, Map<TextCategory, Fmg>>("text")
    private val scripts = data.memo<ScriptId, DecodedScript?>("script")
    private val eventNames = data.memo<ScriptId, EventNames>("script names")
    private val scriptIndexes = data.memo<Unit, ScriptIndex>("script index")
    private val layouts = data.memo<MapId, MSB?>("layout", KEPT_LAYOUTS)
    private val esds = data.memo<EsdSource, Esd?>("esd", KEPT_ESDS)
    private val animationSets = data.memo<Unit, List<AnimationSetId>>("animation sets")
    private val animations = data.memo<AnimationSetId, List<Tae>>("animations", KEPT_ANIMATION_SETS)
    private val thinkCharacters = data.memo<Unit, Map<Int, Set<AnimationSetId>>>("think characters")
    private val lua = LuaData(data, installationDirectory) { catalog().maps }

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
        names.get(language) {
            val params = params()
            parse { it.loadParamNames(language, params) }
        }

    /** [categories] of [language]'s text, read together so each container opens once */
    public suspend fun text(language: TextLanguage, categories: Set<TextCategory>): Map<TextCategory, Fmg> =
        texts.get(language to categories) { parse { it.loadText(language, categories) } }

    public suspend fun script(script: ScriptId): DecodedScript? = scripts.get(script) {
        parse { it.loadScript(script)?.decoded(script, loadInstructionDefinitions()) }
    }

    /** Every script indexed together; built once, since flag reading and the scripts view both need it */
    public suspend fun scriptIndex(): ScriptIndex = scriptIndexes.get(Unit) { indexScripts() }

    public suspend fun scriptNames(script: ScriptId): EventNames = eventNames.get(script) {
        parse { it.loadScriptNames(script)?.translatedBy(loadEventNameTranslations()) ?: EventNames.Empty }
    }
    public suspend fun msb(map: MapId): MSB? = layouts.get(map) { parse { it.loadMSB(map) } }

    public suspend fun esd(source: EsdSource): Esd? = esds.get(source) { parse { it.loadEsd(source) } }

    /** The characters and objects this installation has animations for */
    public suspend fun animationSets(): List<AnimationSetId> =
        animationSets.get(Unit) { parse { it.availableAnimationSets(loadFileManifest(edition)) } }

    public suspend fun animations(set: AnimationSetId): List<Tae> = animations.get(set) { parse { it.loadTae(set) } }

    /** The Lua binders and the scripts in each */
    public suspend fun luaScripts(): Map<LuaBinder, List<String>> = lua.scripts()

    public suspend fun lua(script: LuaScriptId): LuaSource? = lua.source(script)

    /** Goals and logic across every AI binder, and every Lua script's flags */
    public suspend fun aiIndex(): AiIndex = lua.aiIndex()

    /** The characters placed with each NpcThinkParam row, across every map */
    public suspend fun thinkCharacters(): Map<Int, Set<AnimationSetId>> = thinkCharacters.get(Unit) {
        val maps = catalog().maps
        charactersByThink(maps.mapNotNull { msb(it) })
    }

    public suspend fun holds(path: String): Boolean = data.files { it.exists(path) }

    internal fun close(): Unit = data.close()

    private suspend fun <T> parse(block: (GameFiles) -> T): T = data.parse(block)
}
