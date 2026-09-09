import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("kotlin-jvm-conventions")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.compose)
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)

    with(libs) {
        implementation(kotlinx.coroutines.core)
        implementation(kotlinx.coroutines.swing)
        implementation(kotlinx.serialization.json)

        implementation(kotlin.logging)
        implementation(slf4j.api)
        runtimeOnly(logback)
    }
}

compose.desktop {
    application {
        mainClass = "com.sappyoak.dsanalyzer.app.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Dmg)
            packageName = "dsanalyzer"
            packageVersion = "0.1.0"
        }
    }
}