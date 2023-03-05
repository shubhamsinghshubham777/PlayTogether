package com.playtogether.kmp.server.tables

import com.playtogether.kmp.data.models.Room
import kotlinx.uuid.exposed.KotlinxUUIDTable
import org.jetbrains.exposed.sql.Table

/**
 * This [Table] represents an instance of [Room] in an SQL-based DB.
 */
object RoomTable : KotlinxUUIDTable() {
    val name = varchar(name = "name", length = 128)
    val members = largeText(name = "members").nullable()
    val admin = text(name = "admin")
    val isPlayerControlUniversal = bool(name = "isPlayerControlUniversal")
    val lobbyMembers = largeText(name = "lobbyMembers").nullable()
}
