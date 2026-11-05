package com.sappyoak.dsanalyzer.runtime.write

@RequiresOptIn("Places code in the game or changes it without a journal entry")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
public annotation class GameMutation
