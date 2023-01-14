package com.playtogether.kmp.web.util

import react.router.NavigateFunction

fun NavigateFunction.back() = this.invoke(-1)
