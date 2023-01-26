object Versions {
    const val gradleAndroid = "7.3.0"
    const val kotlin = "1.7.21"
    const val ksp = "1.7.21-1.0.8"
    const val compose = "1.4.0-alpha02"
    const val composeActivity = "1.6.1"
    const val coroutines = "1.6.4"
    const val koinCore = "3.3.0"
    const val koinAndroid = "3.3.1"
    const val koinAndroidCompose = "3.3.0"
    const val koinKtor = "3.3.0"
    const val composeDestinations = "1.7.30-beta"
    const val ktor = "2.2.2"
    const val logback = "1.2.11"
    const val mokoKSwift = "0.6.1"
    const val nativeCoroutines = "0.13.2"
    const val viewModelKtx = "2.5.1"
    const val kvision = "5.18.1"
    const val kotlinJsWrappers = "1.0.0-pre.466"
    const val serialization = "1.4.1"
    const val apacheCommonsCodec = "1.15"
    const val postgresql = "42.2.2"
    const val exposed = "0.40.1"
    const val hikari = "5.0.1"
    const val jetbrainsCompose = "1.3.0-rc05"
    const val sqlDelight = "1.5.5"
    const val sqlJSNpm = "1.6.2"
    const val copyWebpackPluginNpm = "9.1.0"
}

object Configs {
    object Android {
        const val namespace = "com.playtogether.kmp.android"
        const val namespaceShared = "com.playtogether.kmp"
        const val compileSdk = 33
        const val applicationId = namespace
        const val minSdk = 21
        const val targetSdk = 33
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object Desktop {
        const val group = "com.playtogether.kmp.desktop"
        const val version = "0.0.1"
    }

    object Server {
        const val group = "com.playtogether.kmp.server"
        const val version = "0.0.1"
    }

    object Cocoapods {
        const val summary = "Some description for the Shared Module"
        const val homepage = "Link to the Shared Module homepage"
        const val version = "1.0"
        const val iosDeploymentTarget = "14.1"
    }

    object Web {
        const val version = "1.0.0-SNAPSHOT"
        const val group = "com.playtogether"
    }

    object SQLDelight {
        const val dbName = "PTDatabase"
        const val packageName = "com.playtogether.kmp"
        const val schemaOutputDirectory = "src/commonMain/sqldelight/databases"
    }
}

object Deps {
    object Projects {
        const val shared = ":shared"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
        const val compiler = "androidx.compose.compiler:compiler:${Versions.compose}"
    }

    object Kotlin {
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
        const val jUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        const val testJs = "test-js"
        const val testCommon = "test"
    }

    object KSP {
        const val composeDestinations = "io.github.raamcosta.compose-destinations:ksp:${Versions.composeDestinations}"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koinCore}"
        const val android = "io.insert-koin:koin-android:${Versions.koinAndroid}"
        const val androidCompose = "io.insert-koin:koin-androidx-compose:${Versions.koinAndroidCompose}"
        const val ktor = "io.insert-koin:koin-ktor:${Versions.koinKtor}"
        const val slf4jLogger = "io.insert-koin:koin-logger-slf4j:${Versions.koinKtor}"
    }

    const val composeDestinations =
        "io.github.raamcosta.compose-destinations:animations-core:${Versions.composeDestinations}"

    object KtorServer {
        const val core = "io.ktor:ktor-server-core-jvm:${Versions.ktor}"
        const val auth = "io.ktor:ktor-server-auth-jvm:${Versions.ktor}"
        const val authJwt = "io.ktor:ktor-server-auth-jwt-jvm:${Versions.ktor}"
        const val sessions = "io.ktor:ktor-server-sessions-jvm:${Versions.ktor}"
        const val resources = "io.ktor:ktor-server-resources:${Versions.ktor}"
        const val hostCommon = "io.ktor:ktor-server-host-common-jvm:${Versions.ktor}"
        const val statusPages = "io.ktor:ktor-server-status-pages-jvm:${Versions.ktor}"
        const val cors = "io.ktor:ktor-server-cors-jvm:${Versions.ktor}"
        const val compression = "io.ktor:ktor-server-compression-jvm:${Versions.ktor}"
        const val defaultHeaders = "io.ktor:ktor-server-default-headers-jvm:${Versions.ktor}"
        const val partialContent = "io.ktor:ktor-server-partial-content-jvm:${Versions.ktor}"
        const val callLogging = "io.ktor:ktor-server-call-logging-jvm:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-server-content-negotiation-jvm:${Versions.ktor}"
        const val kotlinxSerialization = "io.ktor:ktor-serialization-kotlinx-json-jvm:${Versions.ktor}"
        const val websockets = "io.ktor:ktor-server-websockets-jvm:${Versions.ktor}"
        const val netty = "io.ktor:ktor-server-netty-jvm:${Versions.ktor}"
        const val tests = "io.ktor:ktor-server-tests-jvm:${Versions.ktor}"
    }

    object KtorClient {
        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val cioEngine = "io.ktor:ktor-client-cio:${Versions.ktor}"
        const val androidEngine = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val iOSEngine = "io.ktor:ktor-client-ios:${Versions.ktor}"
        const val jsEngine = "io.ktor:ktor-client-js:${Versions.ktor}"
    }

    const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"

    object Pods {
        const val nativeCoroutines = "KMPNativeCoroutinesRxSwift"
    }

    const val mokoKSwiftRuntime = "dev.icerock.moko:kswift-runtime:${Versions.mokoKSwift}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"

    object Web {
        object KVision {
            const val core = "io.kvision:kvision:${Versions.kvision}"
            const val react = "io.kvision:kvision-react:${Versions.kvision}"
            const val state = "io.kvision:kvision-state:${Versions.kvision}"
            const val stateFlow = "io.kvision:kvision-state-flow:${Versions.kvision}"
            const val toastify = "io.kvision:kvision-toastify:${Versions.kvision}"
            const val navigo = "io.kvision:kvision-routing-navigo-ng:${Versions.kvision}"
            const val testUtils = "io.kvision:kvision-testutils:${Versions.kvision}"
        }

        object JS {
            private fun kotlinw(target: String): String =
                "org.jetbrains.kotlin-wrappers:kotlin-$target"

            val wrappersBom = kotlinw("wrappers-bom:${Versions.kotlinJsWrappers}")
            val emotion = kotlinw("emotion")
            val react = kotlinw("react")
            val reactDom = kotlinw("react-dom")
            val reactRouterDom = kotlinw("react-router-dom")
            val mui = kotlinw("mui")
            val muiIcons = kotlinw("mui-icons")
        }

        object NPM {
            const val sqlJS = "sql.js"
            const val copyWebpackPlugin = "copy-webpack-plugin"
        }
    }

    const val apacheCommonsCodec = "commons-codec:commons-codec:${Versions.apacheCommonsCodec}"
    const val postgresql = "org.postgresql:postgresql:${Versions.postgresql}"

    object Exposed {
        const val core = "org.jetbrains.exposed:exposed-core:${Versions.exposed}"
        const val dao = "org.jetbrains.exposed:exposed-dao:${Versions.exposed}"
        const val jdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}"
    }

    const val hikari = "com.zaxxer:HikariCP:${Versions.hikari}"

    object SQLDelight {
        const val android = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        const val native = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        const val jvm = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
        const val js = "com.squareup.sqldelight:sqljs-driver:${Versions.sqlDelight}"
        const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        const val coroutinesExtensions = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
    }
}

object Plugins {
    const val gradleAndroidApplication = "com.android.application"
    const val gradleAndroidLibrary = "com.android.library"

    object Kotlin {
        const val android = "android"
        const val js = "js"
        const val jvm = "jvm"
        const val multiplatform = "multiplatform"
        const val serialization = "plugin.serialization"
        const val ksp = "com.google.devtools.ksp"
        const val cocoapods = "native.cocoapods"
        const val parcelize = "kotlin-parcelize"
    }

    const val ktor = "io.ktor.plugin"
    const val mokoKSwift = "dev.icerock.moko.kswift"
    const val nativeCoroutines = "com.rickclephas.kmp.nativecoroutines"
    const val kvision = "io.kvision"
    const val jetBrainsCompose = "org.jetbrains.compose"
    const val sqlDelight = "com.squareup.sqldelight"
    const val sqlDelightGradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
}
