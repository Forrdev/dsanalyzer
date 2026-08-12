import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.versions)
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
    rejectVersionIf {
        val stable = listOf("RELEASE", "FINAL", "GA").any { candidate.version.uppercase().contains(it) } ||
                "^[0-9,.v-]+(-r)?$".toRegex().matches(candidate.version)
        !stable
    }
}