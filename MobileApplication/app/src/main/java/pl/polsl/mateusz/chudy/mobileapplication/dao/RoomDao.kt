package pl.polsl.mateusz.chudy.mobileapplication.dao

import android.arch.persistence.room.*
import pl.polsl.mateusz.chudy.mobileapplication.model.Room

/**
 *
 */
@Dao
interface RoomDao {

    @Query("select * from room")
    fun getRooms(): List<Room>

//    @Query("select * from room where room_id = :roomId")
//    fun getRoom(roomId: Long): Room
//
//    @Insert
//    fun createRoom(room: Room)
//
//    @Update
//    fun updateRoom(room: Room)

//    @Query("delete from room where room_id = :roomId")
//    fun deleteRoom(roomId: Long)

    @Delete
    fun deleteRoom(room: Room)
}