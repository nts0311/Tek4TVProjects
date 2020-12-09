package com.tek4tv.login.model

import com.squareup.moshi.Json
import com.tek4tv.login.db.entities.Role
import com.tek4tv.login.db.entities.SiteMapId

data class User(
    @Json(name = "Roles")
    val roles: List<UserRole>,
    @Json(name = "SiteMapID")
    val siteMapId: List<Int>,
    @Json(name = "UserId")
    val userId: String
)

data class UserRole(
    @Json(name = "ID")
    val id: String,
    @Json(name = "Name")
    val name: String,
    @Json(name = "Description")
    val description: String
)

fun UserRole.asDb() = Role(id, name, description)
fun List<UserRole>.asDb() = map {it.asDb()}

fun List<Int>.asSiteMap() = map {SiteMapId(it)}

fun User.asDb() = com.tek4tv.login.db.entities.User(userId)

