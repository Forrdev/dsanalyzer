package com.sappyoak.dsanalyzer.runtime.ptde

public object TalkEvent {
    public const val Size: Int = 0x100
    public const val Command: Int = 0x04
    public const val FirstParam: Int = 0x08
    public const val ParamStride: Int = 0x08
    public const val ParamType: Int = 0x04
    public const val HighestIndex: Int = 0xFC

    /** `SetParam` asserts above this */
    public const val MaxParams: Int = 30
}

public object TalkHandler {
    public const val Owner: Int = 0x08
}

public object TalkOwner {
    public const val EntityId: Int = 0x10
}
