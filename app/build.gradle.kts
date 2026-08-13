import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("kotlin-serialization")
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose.compiler)
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":domain"))

    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)

    implementation(libs.kotlinx.coroutines.swing)
}

compose.desktop {
    application {
        mainClass = "com.sappyoak.dsanalyzer.app.MainKt"

        nativeDistributions {
            targetFormats(
                TargetFormat.Msi,
                TargetFormat.Deb,
                TargetFormat.Dmg
            )
            packageName = "dsanalyzer"
            packageVersion = "1.0.0"

            // Process access uses the Foreign Functions and Memory API, whose downcalls are
            // restricted methods. Without this the JVM warns on every launch, which reads like
            // something is wrong
            jvmArgs("--enable-native-access=ALL-UNNAMED")
        }
    }
}