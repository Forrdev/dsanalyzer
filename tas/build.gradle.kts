plugins {
    id("kotlin-jvm-conventions")
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":game"))
    implementation(project(":native"))
    implementation(project(":runtime"))
}

kotlin {
    compilerOptions {
        optIn.add("com.sappyoak.dsanalyzer.runtime.write.GameMutation")
    }
}
