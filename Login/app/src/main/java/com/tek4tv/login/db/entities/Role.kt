package com.tek4tv.login.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("dbId"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Role(
    @PrimaryKey(autoGenerate = true)
    var dbId : Long,
    val userId : Long,
    val id:String,
    val name:String,
    val description : String
)
