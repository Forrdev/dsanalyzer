package com.sappyoak.dsanalyzer.formats.havok.physics

public interface RuntimeHavokLayout {
    public val body: RuntimeBodyLayout

    public val moppChild: Int
    public val shapeType: Int
    public val mesh: MeshLayout
    public val convex: ConvexLayout
}

public class RuntimeBodyLayout(
    public val name: Int,
    public val shape: Int,
    public val filter: Int,
    public val transform: Int
)
