plugins {
    id("kotlin-jvm-conventions")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":shared"))
    api(project(":formats"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
}

kotlin {
    compilerOptions {
        optIn.add("kotlinx.coroutines.ExperimentalCoroutinesApi")
    }
}

/**
 * The suites that read a real installation.
 * It never caches as the question "does the tool still agree with the game" is not one
 * that an up-to-date result can answer. It says what is missing rather than quietly
 * skipping every gated test
 *
 *    ./gradlew :game:installatedTests -Pds1Path=<path to DATA>
 */
tasks.register<Test>("installedTests") {
    group = "verification"
    description = "Runs the tests the read a real game installation"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath

    val installation = providers.gradleProperty("ds1Path")
        .orElse(providers.environmentVariable("DS1_PTDE_PATH"))
    outputs.upToDateWhen { false }

    doFirst {
        require(installation.isPresent) {
            "No installation to read. Pass -Pds1Path=<path>, or set ds1Path in gradle properties"
        }
    }
}