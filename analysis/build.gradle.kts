plugins {
    id("kotlin-jvm-conventions")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":shared"))

    api(project(":game"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
}
