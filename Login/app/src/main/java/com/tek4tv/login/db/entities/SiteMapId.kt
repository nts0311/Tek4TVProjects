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
data class SiteMapId(
    var value: Int
)
{
    @PrimaryKey(autoGenerate = true)
    var dbId : Long = 0
    var userId : Long = 0
}

fun SiteMapId.asDomain() = value
fun List<SiteMapId>.asDomain() = map { it.asDomain() }
