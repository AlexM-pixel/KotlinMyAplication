package com.example.kotlinmyaplicationtasks.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinmyaplicationtasks.model.User
import com.example.kotlinmyaplicationtasks.model.dao.UserDao
import com.example.kotlinmyaplicationtasks.model.dao.UsersDatabase
import kotlin.coroutines.suspendCoroutine

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao = UsersDatabase.getDatabase(application).userDao()

    var name: String = ""
    var age: String = "0"
    val usersListLiveData: LiveData<MutableList<User>>


    init {
        usersListLiveData = userDao.getAllUsers()
        Log.e("Database", "init liveData")
    }

    fun addUser() {
        val currentUser = User(null, name, age.toInt())
        userDao.insertUser(currentUser)
    }
}