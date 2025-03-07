pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.myket.ir")
        maven("https://jitpack.io")
        maven("https://europe-west3-maven.pkg.dev/talsec-artifact-repository/freerasp")
        maven("https://repo.eclipse.org/content/repositories/paho-releases/")

    }
}

rootProject.name = "amozeshgam"
include(":app")
include(":benchmark")
include(":baselineprofile")
