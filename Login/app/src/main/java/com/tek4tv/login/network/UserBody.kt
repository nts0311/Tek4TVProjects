package com.tek4tv.login.network

import com.squareup.moshi.Json

class UserBody(
    @Json(name = "UserName")
    val username: String,
    @Json(name = "PassWord")
    val password: String) {
}