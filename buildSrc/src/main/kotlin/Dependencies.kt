object Versions {
    const val gradleAndroid = "7.4.1"
    const val kotlin = "1.8.0"
    const val composeActivity = "1.6.1"
    const val coroutines = "1.6.4"
    const val koinCore = "3.3.0"
    const val koinAndroid = "3.3.1"
    const val koinAndroidCompose = "3.3.0"
    const val koinKtor = "3.3.1"
    const val ktor = "2.2.3"
    const val logback = "1.4.0"
    const val mokoKSwift = "0.6.1"
    const val nativeCoroutines = "0.13.3"
    const val viewModelKtx = "2.5.1"
    const val kvision = "6.2.0"
    const val kotlinJsWrappers = "1.0.0-pre.492"
    const val serialization = "1.4.1"
    const val apacheCommonsCodec = "1.15"
    const val postgresql = "42.2.2"
    const val exposed = "0.40.1"
    const val hikari = "5.0.1"
    const val jetbrainsCompose = "1.4.0-alpha01-dev938"
    const val sqlDelight = "2.0.0-SNAPSHOT"
    const val sqlJSNpm = "1.6.2"
    const val copyWebpackPluginNpm = "9.1.0"
    const val kermit = "1.2.2"
    const val multiplatformSettings = "0.9"
    const val precompose = "1.3.14"
    const val accompanist = "0.29.1-alpha"
    const val aws = "0.20.3-beta"
}

object Configs {
    object Android {
        const val namespace = "com.playtogether.kmp.android"
        const val namespaceShared = "com.playtogether.kmp"
        const val compileSdk = 33
        const val applicationId = namespace
        const val minSdk = 24
        const val targetSdk = 33
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object Desktop {
        const val group = "com.playtogether.kmp.desktop"
        const val version = "1.0.0"
        const val packageName = "PlayTogether"
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

    object iOS {
        const val frameworkBaseName = "shared"
    }
}

object Deps {
    object Projects {
        const val shared = ":shared"
    }

    object Kotlin {
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
        const val jUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        const val testJs = "test-js"
        const val testCommon = "test"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koinCore}"
        const val android = "io.insert-koin:koin-android:${Versions.koinCore}"
        const val compose = "io.insert-koin:koin-androidx-compose:3.4.2"
        const val ktor = "io.insert-koin:koin-ktor:${Versions.koinKtor}"
        const val slf4jLogger = "io.insert-koin:koin-logger-slf4j:${Versions.koinKtor}"
    }

    object KtorServer {
        const val core = "io.ktor:ktor-server-core:${Versions.ktor}"
        const val auth = "io.ktor:ktor-server-auth:${Versions.ktor}"
        const val authJwt = "io.ktor:ktor-server-auth-jwt:${Versions.ktor}"
        const val sessions = "io.ktor:ktor-server-sessions:${Versions.ktor}"
        const val resources = "io.ktor:ktor-server-resources:${Versions.ktor}"
        const val hostCommon = "io.ktor:ktor-server-host-common:${Versions.ktor}"
        const val statusPages = "io.ktor:ktor-server-status-pages:${Versions.ktor}"
        const val cors = "io.ktor:ktor-server-cors:${Versions.ktor}"
        const val compression = "io.ktor:ktor-server-compression:${Versions.ktor}"
        const val defaultHeaders = "io.ktor:ktor-server-default-headers:${Versions.ktor}"
        const val partialContent = "io.ktor:ktor-server-partial-content:${Versions.ktor}"
        const val callLogging = "io.ktor:ktor-server-call-logging:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-server-content-negotiation:${Versions.ktor}"
        const val kotlinxSerialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val websockets = "io.ktor:ktor-server-websockets:${Versions.ktor}"
        const val netty = "io.ktor:ktor-server-netty:${Versions.ktor}"
        const val tests = "io.ktor:ktor-server-tests:${Versions.ktor}"
    }

    object KtorClient {
        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val cioEngine = "io.ktor:ktor-client-cio:${Versions.ktor}"
        const val androidEngine = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val iOSEngine = "io.ktor:ktor-client-ios:${Versions.ktor}"
        const val jsEngine = "io.ktor:ktor-client-js:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val kotlinxJsonSerialization =
            "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
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
            val reactRouterDomLegacy = kotlinw("react-router-dom-legacy")
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
        const val android = "app.cash.sqldelight:android-driver:${Versions.sqlDelight}"
        const val native = "app.cash.sqldelight:native-driver:${Versions.sqlDelight}"
        const val jvm = "app.cash.sqldelight:sqlite-driver:${Versions.sqlDelight}"
        const val js = "app.cash.sqldelight:sqljs-driver:${Versions.sqlDelight}"
        const val runtime = "app.cash.sqldelight:runtime:${Versions.sqlDelight}"
        const val coroutinesExtensions =
            "app.cash.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        const val primitiveAdapters =
            "app.cash.sqldelight:primitive-adapters:${Versions.sqlDelight}"
    }

    const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    const val multiplatformSettingsNoArg =
        "com.russhwolf:multiplatform-settings-no-arg:${Versions.multiplatformSettings}"
    const val precompose = "moe.tlaster:precompose:${Versions.precompose}"

    object Accompanist {
        const val systemUiController =
            "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"
    }

    const val aws = "aws.sdk.kotlin:s3:${Versions.aws}"
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
        const val cocoapods = "native.cocoapods"
        const val parcelize = "kotlin-parcelize"
    }

    const val ktor = "io.ktor.plugin"
    const val mokoKSwift = "dev.icerock.moko.kswift"
    const val nativeCoroutines = "com.rickclephas.kmp.nativecoroutines"
    const val kvision = "io.kvision"
    const val jetBrainsCompose = "org.jetbrains.compose"
    const val sqlDelight = "app.cash.sqldelight"
    const val kermit = "co.touchlab.kermit"
}
