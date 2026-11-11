plugins {
    id("kotlin-jvm-conventions")
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":game"))
    implementation(project(":native"))
    implementation(project(":runtime"))

    implementation(libs.kotlinx.coroutines.core)
}

kotlin {
    compilerOptions {
        optIn.add("com.sappyoak.dsanalyzer.runtime.write.GameMutation")
    }
}
