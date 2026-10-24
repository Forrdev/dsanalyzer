package com.sappyoak.dsanalyzer.formats.havok.physics

import com.sappyoak.dsanalyzer.formats.havok.ptde.HkpShape

public object PTDERuntimeLayout : RuntimeHavokLayout {
    override val body: RuntimeBodyLayout = RuntimeBodyLayout(
        name = PTDEPhysicsLayout.body.name,
        shape = PTDEPhysicsLayout.body.shape,
        filter = PTDEPhysicsLayout.body.filter,
        transform = PTDEPhysicsLayout.body.transform
    )

    override val moppChild: Int = PTDEPhysicsLayout.moppChild

    /** hk2010 keeps the type byte on 'hkpShape' itself; hk2015 moved it down to 'hkcdShape' */
    override val shapeType: Int = HkpShape.Type

    override val mesh: MeshLayout = PTDEPhysicsLayout.mesh

    override val convex: ConvexLayout = PTDEPhysicsLayout.convex
}

