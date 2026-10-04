import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain(22)

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_22)
        allWarningsAsErrors.set(true)

        freeCompilerArgs.addAll(
            "-Xrender-internal-diagnostic-names",
            "-Xreturn-value-checker=check",
            "-Xcontext-parameters",
            "-Xannotation-default-target=param-property"
        )

        optIn.addAll(
            "kotlin.contracts.ExperimentalContracts",
            "kotlin.time.ExperimentalTime",
            "kotlin.uuid.ExperimentalUuidApi",
            "kotlin.ExperimentalUnsignedTypes"
        )
    }
}

dependencies {
    implementation(libs.kotlin.logging)
    implementation(libs.slf4j.api)


    testRuntimeOnly(libs.logback)
    testImplementation(libs.bundles.jvm.testing)
}

/** One logback-test.xml for every module */
sourceSets.named("test") {
    resources.srcDir(rootProject.layout.projectDirectory.dir("config/logging-test"))
}

tasks.withType<Test>().configureEach {
    maxHeapSize = "4g"
    useJUnitPlatform()
}