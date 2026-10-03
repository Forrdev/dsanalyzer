package com.sappyoak.dsanalyzer.app.logging

import ch.qos.logback.core.PropertyDefinerBase
import java.nio.file.Path

import com.sappyoak.dsanalyzer.app.paths.defaultToolRoot


private const val LOGS_DIRECTORY = "logs"

internal fun defaultLogDirectory(): Path = defaultToolRoot().resolve(LOGS_DIRECTORY)

public class LogDirectory : PropertyDefinerBase() {
    override fun getPropertyValue(): String = defaultLogDirectory().toString()
}