package com.sappyoak.dsanalyzer.shared.platform

import java.util.Locale

enum class OS {
    Windows,
    Mac,
    Linux,
    Unknown;

    val isWindows: Boolean get() = this == Windows
    val isMac: Boolean get() = this == Mac
    val isLinux: Boolean get() = this == Linux
    val isUnknown: Boolean get() = this == Unknown

    public companion object {
        public val current: OS = from(System.getProperty("os.name"))

        internal fun from(name: String): OS {
            val normalized = name.lowercase(Locale.ROOT)
            return when {
                normalized.startsWith("win") -> Windows
                normalized.startsWith("mac") -> Mac
                normalized.startsWith("linux") -> Linux
                else -> Unknown
            }
        }
    }
}