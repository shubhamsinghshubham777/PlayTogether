import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("js")
    val kvisionVersion: String by System.getProperties()
    id("io.kvision") version kvisionVersion
}

version = "1.0.0-SNAPSHOT"
group = "com.playtogether"

repositories {
    mavenCentral()
    mavenLocal()
}

// Versions
val kotlinVersion: String by System.getProperties()
val kvisionVersion: String by System.getProperties()

val webDir = file("src/main/web")

kotlin {
    js {
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

    fun kotlinw(target: String): String =
        "org.jetbrains.kotlin-wrappers:kotlin-$target"

    val kotlinWrappersVersion = "1.0.0-pre.466"

    dependencies {
        implementation("io.kvision:kvision:$kvisionVersion")
        implementation("io.kvision:kvision-react:$kvisionVersion")
        implementation("io.kvision:kvision-state:$kvisionVersion")
        implementation("io.kvision:kvision-state-flow:$kvisionVersion")
        implementation("io.kvision:kvision-toastify:$kvisionVersion")
        implementation("io.kvision:kvision-routing-navigo-ng:$kvisionVersion")
        implementation(project(":shared"))

        implementation(enforcedPlatform(kotlinw("wrappers-bom:$kotlinWrappersVersion")))
        implementation(kotlinw("emotion"))
        implementation(kotlinw("react"))
        implementation(kotlinw("react-dom"))
        implementation(kotlinw("react-router-dom"))
        implementation(kotlinw("mui"))
        implementation(kotlinw("mui-icons"))

        testImplementation(kotlin("test-js"))
        testImplementation("io.kvision:kvision-testutils:$kvisionVersion")
    }
    sourceSets["main"].resources.srcDir(webDir)
}
