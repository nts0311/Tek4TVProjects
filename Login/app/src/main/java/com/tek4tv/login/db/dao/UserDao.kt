package com.tek4tv.login.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tek4tv.login.db.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE userId = :userId")
    suspend fun getUser(userId: String): User?

    @Query("SELECT COUNT(*) FROM User WHERE userId = :userId")
    suspend fun countUser(userId: String): Int

    @Insert
    suspend fun insertUser(user: User): Long

    @Query("DELETE FROM User WHERE userId = :userId")
    suspend fun deleteUser(userId: String)
}