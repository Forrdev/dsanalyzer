package com.sappyoak.dsanalyzer.formats.msb.event

import com.sappyoak.dsanalyzer.shared.binary.Coded

internal enum class EventType(override val code: Int) : Coded {
    Light(0),
    Sound(1),
    Sfx(2),
    Wind(3),
    Treasure(4),
    Generator(5),
    Message(6),
    ObjAct(7),
    SpawnPoint(8),
    MapOffset(9),
    Navmesh(10),
    Environment(11),
    PseudoMultiplayer(12);
}