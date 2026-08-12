import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain(22)

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_22)
        progressiveMode.set(true)

        freeCompilerArgs.addAll(
            "-Xrender-internal-diagnostic-names",
            "-Xreturn-value-checker=full",
            "-Xcontext-parameters",
            "-Xannotation-default-target=param-property"
        )

        optIn.addAll(
            "kotlin.contracts.ExperimentalContracts",
            "kotlin.time.ExperimentalTime",
            "kotlin.uuid.ExperimentalUuidApi",
            "kotlin.ExperimentalUnsignedTypes",
            "kotlinx.serialization.ExperimentalSerializationApi",

            )
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.bundles.testing)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}