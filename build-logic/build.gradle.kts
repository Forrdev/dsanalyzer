plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    with(libs) {
        implementation(plugins.kotlin.jvm.resolve())
        implementation(plugins.kotlin.serialization.resolve())
    }
}

fun Provider<PluginDependency>.resolve() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}