plugins {
    id("kotlin-jvm-conventions")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":game"))

    implementation(libs.kotlinx.serialization.json)

    runtimeOnly(libs.logback)
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

fun registerHavokLayouts(name: String, edition: String, tableFile: String, source: String) = tasks.register<JavaExec>(name) {
    group = "tool"
    description = "Generates Kotlin offset constants for every reflected Havok class ($edition)"
    mainClass.set("com.sappyoak.dsanalyzer.tools.havok.GenerateHavokLayoutsKt")
    classpath = sourceSets.main.get().runtimeClasspath

    val table = providers.gradleProperty("table")
        .orElse(layout.projectDirectory.file("havok/$tableFile").asFile.path)

    val generated = rootProject.layout.projectDirectory
        .dir("formats/src/main/kotlin/com/sappyoak/dsanalyzer/formats/havok/$edition")
    val out = providers.gradleProperty("out").orElse(generated.asFile.path)

    inputs.file(table)
    outputs.dir(out)

    argumentProviders.add {
        listOf(
            "--table=${table.get()}",
            "--out=${out.get()}",
            "--package=com.sappyoak.dsanalyzer.formats.havok.$edition",
            "--source=$source",
            "--task=$name"
        )
    }
}

val generatePTDEHavokLayouts = registerHavokLayouts(
    name = "generatePTDEHavokLayouts",
    edition = "ptde",
    tableFile = "reflection.tsv",
    source = "tools/havok/reflection.tsv, extracted from DARKSOULS.exe (hk_2010.2.0-r1, 32-bit)"
)

val generateDSRHavokLayouts = registerHavokLayouts(
    name = "generateDSRHavokLayouts",
    edition = "dsr",
    tableFile = "dsr-reflection.tsv",
    source = "tools/havok/dsr-reflection.tsv, extracted from DarkSoulsRemastered.exe (hk_2015.1.0-r1, 64-bit)"
)

tasks.register("generateHavokLayouts") {
    group = "tools"
    description = "Generates the Havok offset constants for both editions"
    dependsOn(generatePTDEHavokLayouts, generateDSRHavokLayouts)
}

tasks.register<JavaExec>("generateParamDefs") {
    group = "tools"
    description = "Generates a typed row class for every GameParam def, and the GameParam file list"
    mainClass.set("com.sappyoak.dsanalyzer.tools.params.GenerateParamDefsKt")
    classpath = sourceSets.main.get().runtimeClasspath

    val root = providers.gradleProperty("ds1")
    val defs = rootProject.layout.projectDirectory
        .dir("formats/src/main/kotlin/com/sappyoak/dsanalyzer/formats/param/defs")
    val game = rootProject.layout.projectDirectory
        .dir("game/src/main/kotlin/com/sappyoak/dsanalyzer/game/world/params")

    outputs.dir(defs)
    outputs.file(game.file("GameParam.Kt"))

    argumentProviders.add {
        listOf(
            root.orNull ?: error("Pass the installations DATA directory as -Pds1=<path>"),
            defs.asFile.path,
            game.asFile.path
        )
    }
}
