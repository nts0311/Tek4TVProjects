package com.tek4tv.login.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.tek4tv.login.model.UserRole

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("dbId"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Role(
    val id: String,
    val name: String,
    val description: String
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0
    var userId: Long = 0
}

fun Role.asDomain() = UserRole(id, name, description)

fun List<Role>.asDomain() = map { it.asDomain() }
