package com.sappyoak.dsanalyzer.formats.msb.model

import com.sappyoak.dsanalyzer.shared.binary.Coded

public enum class ModelType(override val code: Int) : Coded {
    MapPiece(0),
    Object(1),
    Enemy(2),
    Player(4),
    Collision(5),
    Navmesh(6);
}