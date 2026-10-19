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

tasks.register<JavaExec>("generateHavokLayouts") {
    group = "tools"
    description = "Generates Kotlin offset constants for every reflected Havok class"
    mainClass.set("com.sappyoak.dsanalyzer.tools.havok.GenerateHavokLayoutsKt")
    classpath = sourceSets.main.get().runtimeClasspath

    val table = providers.gradleProperty("table")
        .orElse(layout.projectDirectory.file("havok/reflection.tsv").asFile.path)

    val generated = rootProject.layout.projectDirectory
        .dir("runtime/src/main/kotlin/com/sappyoak/dsanalyzer/runtime/ptde/havok")
    val out = providers.gradleProperty("out").orElse(generated.asFile.path)

    inputs.file(table)
    outputs.dir(out)

    argumentProviders.add { listOf(table.get(), out.get())}
}