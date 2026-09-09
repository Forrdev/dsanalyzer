plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.plugins.kotlin.jvm.resolve())
}

fun Provider<PluginDependency>.resolve() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}