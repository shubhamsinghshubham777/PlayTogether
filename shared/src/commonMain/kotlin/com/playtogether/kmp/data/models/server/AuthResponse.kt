package com.playtogether.kmp.data.models.server

import com.playtogether.kmp.data.models.User
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val accessToken: String,
    val user: User
)
