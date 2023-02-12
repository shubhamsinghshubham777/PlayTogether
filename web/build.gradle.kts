import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    with(Plugins.Kotlin) {
        kotlin(serialization)
        kotlin(js)
    }
    id(Plugins.kvision) version Versions.kvision
}

version = Configs.Web.version
group = Configs.Web.group

val webDir = file("src/main/web")

kotlin {
    js(IR) {
        browser {
            runTask {
                outputFileName = "main.bundle.js"
                sourceMaps = false
                devServer = KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 3000,
                    proxy = mutableMapOf(
                        "/kv/*" to "http://localhost:8080",
                        "/kvws/*" to mapOf("target" to "ws://localhost:8080", "ws" to true)
                    ),
                    static = mutableListOf("$buildDir/processedResources/js/main")
                )
            }
            webpackTask {
                outputFileName = "main.bundle.js"
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }



    dependencies {
        implementation(project(Deps.Projects.shared))

        with(Deps.Web.KVision) {
            implementation(core)
            implementation(react)
            implementation(state)
            implementation(stateFlow)
            implementation(toastify)
            implementation(navigo)
            testImplementation(testUtils)
        }

        implementation(enforcedPlatform(Deps.Web.JS.wrappersBom))
        with(Deps.Web.JS) {
            implementation(emotion)
            implementation(react)
            implementation(reactDom)
            implementation(reactRouterDomLegacy)
            implementation(mui)
            implementation(muiIcons)
        }

        with(Deps.Web.NPM) {
            implementation(npm(sqlJS, Versions.sqlJSNpm))
            implementation(devNpm(copyWebpackPlugin, Versions.copyWebpackPluginNpm))
        }

        testImplementation(kotlin(Deps.Kotlin.testJs))
    }
    sourceSets["main"].resources.srcDir(webDir)
}
