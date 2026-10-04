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
