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
    }
}

rootProject.name = "EcoAR"
include(":app")
include(":core")
include(":feature_auth")
include(":feature_onboarding")
include(":main")
include(":main:feature_ar")
include(":main:feature_home")
include(":main:feature_map")
include(":main:feature_profile")
include(":main:feature_statistics")
