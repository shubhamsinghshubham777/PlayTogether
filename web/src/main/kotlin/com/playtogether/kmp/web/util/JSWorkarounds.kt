package com.playtogether.kmp.web.util

import mui.material.GridProps

object GridPropsExtensions {
    var GridProps.xs: Int?
        get() = asDynamic().xs as? Int
        set(value) {
            asDynamic().xs = value
        }

    var GridProps.sm: Int?
        get() = asDynamic().sm as? Int
        set(value) {
            asDynamic().sm = value
        }

    var GridProps.md: Int?
        get() = asDynamic().md as? Int
        set(value) {
            asDynamic().md = value
        }

    var GridProps.lg: Int?
        get() = asDynamic().lg as? Int
        set(value) {
            asDynamic().lg = value
        }
}
