package com.sappyoak.dsanalyzer.runtime.dsr

/** `EzState::detail::EzStateExternalEventTemp` */
public object TalkEvent {
    public const val Size: Int = 0x200
    public const val Command: Int = 0x08
    public const val FirstParam: Int = 0x10
    public const val ParamStride: Int = 0x10
    public const val ParamType: Int = 0x08
    public const val HighestIndex: Int = 0x1F8

    public const val MaxParams: Int = 30
}

/** The handler the dispatcher runs on  */
public object TalkHandler {
    public const val Owner: Int = 0x10
}

public object TalkOwner {
    public const val EntityId: Int = 0x1C
    public const val Link: Int = 0x98
    public const val LinkBack: Int = 0x18
}
