package com.tek4tv.login.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var dbId : Long,
    var userId : String
)
