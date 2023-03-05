package com.playtogether.kmp.data.models

import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

/**
 * Room is a shared space in which several [User]s can join in, be able to audio/video/text chat,
 * and control the media player together.
 * @param id A unique identifier of this room, no two rooms should have the same id. It is generally
 * a randomly generated UUID.
 * @param name Name of this room.
 * @param members List of user emails that are a part of this room.
 * @param admin User email that is the admin of this room (initially, it is the user who creates
 * this room).
 * @param isPlayerControlUniversal Whether the shared media player should be controlled by all room
 * members or only the admin.
 * @param lobbyMembers List of user emails that are not a part of this room yet.
 */
@Serializable
data class Room(
    val id: UUID? = null,
    val name: String,
    val members: List<String> = emptyList(),
    val admin: String? = null,
    val isPlayerControlUniversal: Boolean = true,
    val lobbyMembers: List<String> = emptyList()
)
