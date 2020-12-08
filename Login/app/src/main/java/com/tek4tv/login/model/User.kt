package com.tek4tv.login.model

import com.squareup.moshi.Json

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
