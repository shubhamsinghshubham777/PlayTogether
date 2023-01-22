package com.playtogether.kmp.data.models.server

import com.playtogether.kmp.data.models.DUser
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val user: DUser
)
