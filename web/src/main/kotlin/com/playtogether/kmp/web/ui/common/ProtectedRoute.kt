package com.playtogether.kmp.web.ui.common

import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.web.AuthContext
import com.playtogether.kmp.web.util.NavigateOptions
import com.playtogether.kmp.web.util.UrlParam
import com.playtogether.kmp.web.util.addParams
import react.FC
import react.Props
import react.ReactNode
import react.router.useLocation
import react.router.useNavigate
import react.useContext
import react.useEffect

external interface ProtectedRouteProps : Props {
    var children: ReactNode
}

val ProtectedRoute = FC<ProtectedRouteProps> { props ->
    val isUserLoggedIn = useContext(context = AuthContext)
    val navigate = useNavigate()
    val location = useLocation()

    useEffect(isUserLoggedIn) {
        if (isUserLoggedIn == false) navigate(
            to = Constants.NavRoutes.Auth.addParams(
                UrlParam(name = Constants.NavRoutes.Params.continueRoute, value = location.pathname)
            ),
            options = NavigateOptions(replace = true)
        )
    }

    if (isUserLoggedIn == true) +props.children
}
