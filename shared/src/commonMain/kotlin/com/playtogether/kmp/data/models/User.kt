package com.playtogether.kmp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String?,
    val email: String,
    val avatarUrl: String?,
    val salt: String?,
    val hashedPassword: String?,
    val refreshToken: String?,
    val expiresAt: Long?
)
