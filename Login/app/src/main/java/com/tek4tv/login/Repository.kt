package com.tek4tv.login

import com.tek4tv.login.model.User
import com.tek4tv.login.network.Tek4TvService
import com.tek4tv.login.network.UserBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val tek4TvService : Tek4TvService) {
    suspend fun login(body: UserBody, token : String) : Response<User> {
        val mbody  = mutableMapOf<String, String>()
        mbody["UserName"] = body.username
        mbody["PassWord"] = body.password
        return tek4TvService.login(mbody, "Bearer ".plus(token))
    }

    suspend fun getToken() : String {
        val body = mutableMapOf<String, String>()
        body.put("AppID", "bc6da08b-3ad4-4452-8f29-d56bc69e31995")
        body.put("ApiKey", "5G2Zix5YcWLdatLFrr+81d7ldMV7Yt5CGftGF5VTqhM=8")
        body.put("AccountId", "64857311-d116-4c38-b0ab-1643050c441d")
        return tek4TvService.getToken(body)
    }
}