plugins {
    id("kotlin-jvm-conventions")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":game"))

    implementation(libs.kotlinx.serialization.json)
}

tasks.register<JavaExec>("extractText") {
    group = "tools"
    description = "Writes the game's Japanese/English term pairs and event names as JSON"
    mainClass.set("com.sappyoak.dsanalyzer.tools.text.ExtractTextKt")
    classpath = sourceSets.main.get().runtimeClasspath

    val root = providers.gradleProperty("ds1")
    val out = providers.gradleProperty("out").orElse(layout.buildDirectory.dir("text").map { it.asFile.path })

    argumentProviders.add {
        listOf(
            root.orNull ?: error("Pass the installation's DATA directory as -Pds1=<path>"),
            out.get()
        )
    }
}