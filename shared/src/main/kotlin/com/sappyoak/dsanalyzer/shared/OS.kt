package com.sappyoak.dsanalyzer.shared

enum class OS {
    Windows,
    Mac,
    Linux;

    companion object {
        val Current: OS = fromEnv()

        fun fromEnv(): OS {
            val os = System.getProperty("os.name").lowercase()
            return when {
                os.contains("win") -> Windows
                os.contains("mac") -> Mac
                else -> Linux
            }
        }
    }
}