package com.tek4tv.login.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tek4tv.login.db.dao.RoleDao
import com.tek4tv.login.db.dao.SiteMapIdDao
import com.tek4tv.login.db.dao.UserDao
import com.tek4tv.login.db.entities.Role
import com.tek4tv.login.db.entities.SiteMapId
import com.tek4tv.login.db.entities.User

@Database(entities = [Role::class, SiteMapId::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val roleDao: RoleDao
    abstract val siteMapIdDao: SiteMapIdDao
    abstract val userDao: UserDao
}