package com.tek4tv.login

import com.tek4tv.login.db.AppDatabase
import com.tek4tv.login.model.User
import com.tek4tv.login.model.asDb
import com.tek4tv.login.model.asSiteMap
import com.tek4tv.login.network.Tek4TvService
import com.tek4tv.login.network.UserBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val tek4TvService: Tek4TvService,
    private val appDatabase: AppDatabase
) {
    var currentToken = ""
    var currentUser : User? = null

    suspend fun login(body: UserBody, token: String): Response<User> {
        val mbody = mapOf(
            "UserName" to body.username,
            "PassWord" to body.password
        )
        val response = tek4TvService.login(mbody, "Bearer ".plus(token))

        if(response.isSuccessful)
            currentUser = response.body()

        return response
    }

    suspend fun getToken(): String {
        val body = mapOf(
            "AppID" to "bc6da08b-3ad4-4452-8f29-d56bc69e31995",
            "ApiKey" to "5G2Zix5YcWLdatLFrr+81d7ldMV7Yt5CGftGF5VTqhM=8",
            "AccountId" to "64857311-d116-4c38-b0ab-1643050c441d"
        )

        val token = tek4TvService.getToken(body)
        currentToken = token
        return token
    }

    suspend fun saveUser(user: User)
    {
        val userDb = user.asDb()
        val roleDb = user.roles.asDb()
        val siteMapIdDb = user.siteMapId.asSiteMap()

        val userDbId = appDatabase.userDao.insertUser(userDb)
        roleDb.forEach { it.userId = userDbId }
        siteMapIdDb.forEach { it.userId = userDbId }
        appDatabase.roleDao.insertUserRoles(roleDb)
        appDatabase.siteMapIdDao.insertSiteMapId(siteMapIdDb)
    }
}