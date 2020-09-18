package com.example.kotlinmyaplicationtasks.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinmyaplicationtasks.model.User
import com.example.kotlinmyaplicationtasks.model.dao.UserDao
import com.example.kotlinmyaplicationtasks.model.dao.UsersDatabase
import java.util.concurrent.*
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

    fun addUser(view: View) {
        val currentUser = User(null, name, age.toInt())
        val service: ExecutorService = Executors.newSingleThreadExecutor()
        val call: Callable<Long> = Callable { userDao.insertUser(currentUser) }
        val future: Future<Long> = service.submit(call)
        service.shutdown()
        Toast.makeText(view.context, "service.isTerminated= ${service.isTerminated} id = ${future.get()}", Toast.LENGTH_LONG).show()

    }
}