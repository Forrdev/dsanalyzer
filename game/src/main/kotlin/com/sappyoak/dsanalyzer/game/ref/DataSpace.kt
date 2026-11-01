package com.sappyoak.dsanalyzer.game.ref

public enum class DataSpace(public val token: String) {
    Map("map"),
    Entity("entity"),
    Flag("flag"),
    Script("script"),
    Param("param"),
    Animation("anim"),
    Esd("esd"),
    Lua("lua"),
    Goal("goal"),
    DummyPoly("dmypoly"),
    Collision("collision"),
    Request("request"),
    File("file");

    public companion object {
        private val BY_TOKEN: Map<String, DataSpace> = entries.associateBy { it.token }

        public fun of(token: String): DataSpace? = BY_TOKEN[token]
    }
}

public val DataAddress.knownSpace: DataSpace? get() = DataSpace.of(space)
