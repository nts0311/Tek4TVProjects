package com.tek4tv.login.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tek4tv.login.model.UserRole

@JsonClass(generateAdapter = true)
data class PlaylistBody(
        @Json(name = "Roles")
        val roles: List<UserRole>,
        @Json(name = "ID")
        val Id: String
)