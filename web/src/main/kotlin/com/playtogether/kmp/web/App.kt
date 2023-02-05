package com.playtogether.kmp.web

import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.di.dataSourceModuleJS
import com.playtogether.kmp.di.initKoin
import com.playtogether.kmp.presentation.viewmodels.AuthViewModel
import com.playtogether.kmp.presentation.viewmodels.MainViewModel
import com.playtogether.kmp.web.ui.PTTheme
import com.playtogether.kmp.web.ui.screens.auth.AuthScreen
import com.playtogether.kmp.web.ui.screens.common.ProtectedRoute
import com.playtogether.kmp.web.ui.screens.dashboard.DashboardScreen
import csstype.vh
import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.ToastifyModule
import io.kvision.module
import io.kvision.panel.root
import io.kvision.react.kv
import io.kvision.react.react
import io.kvision.startApplication
import io.kvision.state.bind
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import mui.material.Paper
import mui.material.Typography
import mui.material.styles.ThemeProvider
import mui.system.sx
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import react.create
import react.createContext
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter
import react.useState

val AuthContext = createContext<Boolean?>(defaultValue = null)

class App : Application(), CoroutineScope by CoroutineScope(Dispatchers.Main), KoinComponent {
    private val mainViewModel = get<MainViewModel>()
    private val authViewModel = get<AuthViewModel>()

    @Suppress("UnsafeCastFromDynamic")
    override fun start() {
        root("kvapp") {
            initAppStyle()
            react {
                var isUserLoggedIn: Boolean? by useState(null)
                kv {
                    bind(mainViewModel.isUserLoggedIn) { isUserLoggedIn = it }
                }

                AuthContext.Provider {
                    value = isUserLoggedIn
                    ThemeProvider {
                        theme = PTTheme
                        children = Paper.create {
                            square = true
                            elevation = 0
                            sx {
                                minHeight = 100.vh
                            }
                            BrowserRouter {
                                Routes {
                                    Route {
                                        path = Constants.NavRoutes.Root
                                        element = ProtectedRoute.create {
                                            DashboardScreen {}
                                        }
                                    }
                                    Route {
                                        path = Constants.NavRoutes.Auth
                                        element = AuthScreen.create {
                                            login = { email, password ->
                                                authViewModel.login(email = email, password = password)
                                            }
                                            register = { email, password ->
                                                authViewModel.register(email = email, password = password)
                                            }
                                            loginState = authViewModel.loginState
                                            registerState = authViewModel.registerState
                                        }
                                    }
                                    Route {
                                        path = "*"
                                        element = Typography.create { +"404 Page Not Found" }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initAppStyle() {
        with(document) {
            bgColor = PTTheme.palette.background.paper
            body?.style?.margin = "0"
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        ToastifyModule,
        CoreModule
    )
    initKoin {
        modules(dataSourceModuleJS)
    }
}
