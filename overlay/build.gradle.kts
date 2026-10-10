plugins {
    id("kotlin-jvm-conventions")
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.compose)
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation(project(":shared"))
    implementation(project(":native"))
    implementation(project(":runtime"))

    implementation(libs.kotlinx.coroutines.core)
}