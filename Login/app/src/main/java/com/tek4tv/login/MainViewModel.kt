package com.tek4tv.login

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tek4tv.login.model.User
import com.tek4tv.login.network.UserBody
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject
constructor(
    private val repository: Repository,
) : ViewModel() {
    private var token : String = ""
    private var getTokenJob : Job? = null

    private val _user  = MutableLiveData<User>()
    val user : LiveData<User> = _user

    init {
        getTokenJob = viewModelScope.launch {
            token = repository.getToken()
            Log.d("Token", token)
        }
    }

    fun login(userBody: UserBody)
    {
        viewModelScope.launch {
            getTokenJob?.join()
            val response = repository.login(userBody, token)
            if(response.isSuccessful)
                _user.value = response.body()
        }
    }

}