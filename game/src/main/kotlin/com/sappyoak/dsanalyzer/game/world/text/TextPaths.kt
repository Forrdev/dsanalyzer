package com.sappyoak.dsanalyzer.game.world.text

import com.sappyoak.dsanalyzer.game.files.GamePath

public enum class TextLanguage(public val folder: String) {
    English("ENGLISH"),
    Japanese("JAPANESE"),
    French("FRENCH"),
    German("GERMAN"),
    Italian("ITALIAN"),
    Korean("KOREAN"),
    Polish("POLISH"),
    Portuguese("PORTUGUESE"),
    Russian("RUSSIAN"),
    Spanish("SPANISH"),
    SpanishNeutral("NSPANISH"),
    ChineseSimplified("SCHINESE"),
    ChineseTraditional("TCHINESE");
}

public enum class TextArchive(public val stem: String) {
    Item("item"),
    Menu("menu")
}

public fun TextLanguage.pathTo(archive: TextArchive): GamePath =
    GamePath.of("/msg/$folder/${archive.stem}.msgbnd.dcx")