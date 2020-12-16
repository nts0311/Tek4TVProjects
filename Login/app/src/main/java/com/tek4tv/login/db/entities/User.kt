package com.tek4tv.login.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tek4tv.login.model.UserRole

@Entity
data class User(

    var userId: String
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0
}

fun User.asDomain(
    roles: List<UserRole>,
    siteMapId: List<Int>,
) = com.tek4tv.login.model.User(roles, siteMapId, userId)
