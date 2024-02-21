import org.gradle.api.tasks.testing.logging.TestExceptionFormat

fun properties(key: String) = providers.gradleProperty(key)

plugins {
    id("org.jetbrains.intellij") version "1.17.2"
}

repositories {
    mavenCentral()
}

version = properties("pluginVersion").get().ifEmpty { properties("platformVersion").get() } +
        properties("pluginBranch").get().ifEmpty { properties("platformBranch").get() } +
        properties("pluginBuild").get().ifEmpty { properties("platformBuild").get() }

apply(plugin = "java")
apply(plugin = "org.jetbrains.intellij")
intellij {
    pluginName.set(properties("name").get())
    version.set(project.provider {
        properties("platformVersion").get() + properties("platformBranch").get() + properties("platformBuild").get()
    })
    updateSinceUntilBuild.set(true)
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
