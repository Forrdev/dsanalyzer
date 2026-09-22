package com.sappyoak.dsanalyzer.formats.msb.part

import com.sappyoak.dsanalyzer.formats.msb.Coded

internal enum class PartType(override val code: Int) : Coded {
    MapPiece(0),
    Object(1),
    Enemy(2),
    Player(4),
    Collision(5),
    Navmesh(8),
    DummyObject(9),
    DummyEnemy(10),
    ConnectCollision(11);
}