package com.playtogether.kmp.data.models.server

import com.playtogether.kmp.data.models.User
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val user: User
)
