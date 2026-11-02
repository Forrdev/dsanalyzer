package com.sappyoak.dsanalyzer.analysis.engine

/**
 * The authoring mistake a lead instantiates. This is orthogonal to its kind, and is a better
 * guide for where to look next, since the same mistake recurs across analyzers that share no code
 */
public enum class BugPattern(public val summary: String) {
    SameFrameRace("order-dependent resolution that can be forced into a tie"),
    TypeConfusion("a value read through a table that does not own it");
}
