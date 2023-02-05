package com.playtogether.kmp.web.util

import dom.html.HTMLDivElement
import dom.html.HTMLInputElement
import react.dom.events.FormEvent

val FormEvent<HTMLDivElement>.currentValue: String get() = (this.target as HTMLInputElement).value