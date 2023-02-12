package com.playtogether.kmp.web.util

import react.dom.events.FormEvent
import web.html.HTMLDivElement
import web.html.HTMLInputElement

val FormEvent<HTMLDivElement>.currentValue: String get() = (this.target as HTMLInputElement).value
