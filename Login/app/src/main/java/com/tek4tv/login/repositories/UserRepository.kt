package com.tek4tv.login.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tek4tv.login.db.AppDatabase
import com.tek4tv.login.model.User
import com.tek4tv.login.model.asDb
import com.tek4tv.login.model.asSiteMap
import com.tek4tv.login.network.AuthService
import com.tek4tv.login.network.UserBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val authService: AuthService,
    private val appDatabase: AppDatabase
) {
    var currentToken = ""
    var currentUser: User? = null

    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String> = _errorText


    suspend fun login(body: UserBody, token: String): Response<User>? {
        val mbody = mapOf(
            "UserName" to body.username,
            "PassWord" to body.password
        )
        return try {
            val response = authService.login(mbody, "Bearer ".plus(token))

            if (response.isSuccessful)
                currentUser = response.body()
            else
                _errorText.value = "Error: ${response.code()} - Error Message: ${response.message()}"

            response
        } catch (e: Exception) {
            Log.e("getToken()", e.message!!)
            _errorText.value = e.message ?: ""
            null
        }
    }

    suspend fun getToken(): String {
        val body = mapOf(
            "AppID" to "bc6da08b-3ad4-4452-8f29-d56bc69e31995",
            "ApiKey" to "5G2Zix5YcWLdatLFrr+81d7ldMV7Yt5CGftGF5VTqhM=8",
            "AccountId" to "64857311-d116-4c38-b0ab-1643050c441d"
        )

        return try {
            val response = authService.getToken(body)
            if (response.isSuccessful)
                currentToken = response.body()!!
            else
                _errorText.value = "Error: ${response.code()} - Error Message: ${response.message()}"
            currentToken
        } catch (e: Exception) {
            Log.e("getToken()", e.message!!)
            _errorText.value = e.message ?: ""
            ""
        }
    }

    suspend fun saveUser(user: User) {

        appDatabase.userDao.deleteUser(user.userId)

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