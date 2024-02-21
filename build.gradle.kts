import org.gradle.api.tasks.testing.logging.TestExceptionFormat

fun properties(key: String) = providers.gradleProperty(key)

plugins {
    id("org.jetbrains.intellij.platform") version "2.6.0"
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
        jetbrainsRuntime()
        nightly()
    }
}

val platformVersionProvider by extra(project.provider {
    properties("platformVersion").get() + properties("platformBranch").get() + properties("platformBuild").get()
})

version = properties("pluginVersion").get().ifEmpty { properties("platformVersion").get() } +
        properties("pluginBranch").get().ifEmpty { properties("platformBranch").get() } +
        properties("pluginBuild").get().ifEmpty { properties("platformBuild").get() }

apply(plugin = "java")
apply(plugin = "org.jetbrains.intellij.platform")

dependencies {
    intellijPlatform {
        create("IC", platformVersionProvider.get(), useInstaller = properties("useInstaller").get().toBoolean())
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = properties("javaVersion").get()
        targetCompatibility = properties("javaTargetVersion").get()
    }

    patchPluginXml {
        changeNotes.set(provider { file(properties("psiViewerChangesFile").get()).readText() })
        pluginDescription.set(properties("psiViewerDescription").get())
    }

    test {
        systemProperty("idea.plugins.path", project.rootDir.canonicalPath + "/.test-plugins")

        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            showStandardStreams = true
        }
    }

    publishPlugin {
        if (project.hasProperty("eap")) {
            channels.set(listOf("EAP"))
        }
        token.set(properties("jbToken").orElse(""))
    }
}
