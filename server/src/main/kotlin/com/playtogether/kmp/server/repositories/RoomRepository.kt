package com.playtogether.kmp.server.repositories

import com.playtogether.kmp.data.models.Room
import com.playtogether.kmp.server.RoomNotFoundException
import com.playtogether.kmp.server.dbQuery
import com.playtogether.kmp.server.tables.RoomTable
import kotlinx.uuid.UUID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

interface RoomRepository {
    suspend fun createRoom(email: String, room: Room): UUID
    suspend fun getRoomById(id: UUID): Room
    suspend fun updateRoom(email: String, room: Room): Boolean
    suspend fun deleteRoom(id: UUID): Boolean
}

class RoomRepositoryImpl : RoomRepository {

    override suspend fun createRoom(email: String, room: Room): UUID = dbQuery {
        RoomTable.insertAndGetId {
            it[name] = room.name
            it[members] = room.members.joinToString(separator = ROOM_LIST_DELIMITER)
            it[admin] = room.admin ?: email
            it[isPlayerControlUniversal] = room.isPlayerControlUniversal
            it[lobbyMembers] = room.lobbyMembers.joinToString(separator = ROOM_LIST_DELIMITER)
        }.value
    }

    override suspend fun getRoomById(id: UUID): Room = dbQuery {
        RoomTable.select(where = { RoomTable.id eq id }).firstOrNull()?.toDRoom()
            ?: throw RoomNotFoundException
    }

    override suspend fun updateRoom(email: String, room: Room): Boolean = dbQuery {
        RoomTable.update(
            where = { RoomTable.id eq room.id }
        ) {
            it[name] = room.name
            it[members] = room.members.joinToString(separator = ROOM_LIST_DELIMITER)
            it[admin] = room.admin ?: email
            it[isPlayerControlUniversal] = room.isPlayerControlUniversal
            it[lobbyMembers] = room.lobbyMembers.joinToString(separator = ROOM_LIST_DELIMITER)
        } > 0
    }

    override suspend fun deleteRoom(id: UUID): Boolean = dbQuery {
        RoomTable.deleteWhere { RoomTable.id eq id } > 0
    }

}

fun ResultRow.toDRoom() = Room(
    name = this[RoomTable.name],
    id = this[RoomTable.id].value,
    members = this[RoomTable.members]?.split(ROOM_LIST_DELIMITER) ?: emptyList(),
    admin = this[RoomTable.admin],
    isPlayerControlUniversal = this[RoomTable.isPlayerControlUniversal],
    lobbyMembers = this[RoomTable.lobbyMembers]?.split(ROOM_LIST_DELIMITER) ?: emptyList()
)

private const val ROOM_LIST_DELIMITER = ","
