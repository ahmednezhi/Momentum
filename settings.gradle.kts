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

rootProject.name = "Interview Project"
include(":app")
include(":feature:home:home_data")
include(":feature:home:home_domain")
include(":feature:home:home_presentation")
include(":core:ui")
include(":core:network")
include(":core:navigation")
include(":core:database")
include(":feature:goal:goal_presentation")
include(":feature:goal:goal_data")
include(":feature:goal:goal_domain")
include(":core:common")
include(":core:notifications")
