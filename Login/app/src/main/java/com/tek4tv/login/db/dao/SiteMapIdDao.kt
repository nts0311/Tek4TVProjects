package com.tek4tv.login.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tek4tv.login.db.entities.SiteMapId

@Dao
interface SiteMapIdDao {
    @Query("SELECT * FROM SiteMapId WHERE userId = :userDbId")
    suspend fun getSiteMapId(userDbId: Long): List<SiteMapId>

    @Insert
    suspend fun insertSiteMapId(roles: List<SiteMapId>): List<Long>
}