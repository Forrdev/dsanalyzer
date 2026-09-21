package com.sappyoak.dsanalyzer.native.process

import com.sappyoak.dsanalyzer.native.memory.AddressRange

public data class ProcessInfo(
    public val pid: Int,
    public val executableName: String
)

public data class ModuleInfo(
    public val name: String,
    public val range: AddressRange
)