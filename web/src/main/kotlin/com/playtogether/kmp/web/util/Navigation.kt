package com.playtogether.kmp.web.util

import history.LocationState
import react.router.NavigateFunction
import react.router.NavigateOptions

class NavigateOptions(
    override var replace: Boolean? = null,
    override var state: LocationState? = null
) : NavigateOptions

data class UrlParam(val name: String, val value: String)

fun String.addParams(vararg params: UrlParam): String {
    val builder = StringBuilder(content = this)
    params.forEachIndexed { index, param ->
        if (index == 0) builder.append("?")
        builder.append("${param.name}=${param.value}")
    }
    return builder.toString()
}

fun NavigateFunction.back() = this.invoke(-1)
