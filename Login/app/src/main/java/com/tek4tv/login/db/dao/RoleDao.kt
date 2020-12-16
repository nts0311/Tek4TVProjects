package com.tek4tv.login.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tek4tv.login.db.entities.Role

@Dao
interface RoleDao {
    @Query("SELECT * FROM Role WHERE userId = :userDbId")
    suspend fun getUserRoles(userDbId: Long): List<Role>

    @Insert
    suspend fun insertUserRoles(roles: List<Role>): List<Long>
}