package pl.polsl.mateusz.chudy.mobileapplication.dao

import android.arch.persistence.room.*
import pl.polsl.mateusz.chudy.mobileapplication.model.User

/**
 *
 */
@Dao
interface UserDao {

    @Query("select * from user")
    fun getUsers(): List<User>

//    @Query("select * from user where user_id = :userId")
//    fun getUser(userId: Long): User
//
//    @Insert
//    fun createUser(user: User)
//
//    @Update
//    fun updateUser(user: User)

//    @Query("delete from user where user_id = :userId")
//    fun deleteUser(userId: Long)

    @Delete
    fun deleteUser(user: User)
}