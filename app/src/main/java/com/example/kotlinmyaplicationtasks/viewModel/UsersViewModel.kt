package com.example.kotlinmyaplicationtasks.viewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import com.example.kotlinmyaplicationtasks.model.User
import com.example.kotlinmyaplicationtasks.model.dao.UserDao
import com.example.kotlinmyaplicationtasks.model.dao.UsersDatabase
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.R
import java.util.concurrent.*


class UsersViewModel(application: Application) :
    AndroidViewModel(application) {
    private val userDao: UserDao = UsersDatabase.getDatabase(application).userDao()
    private val context = getApplication<Application>().applicationContext
    var name: String = ""
    var set_age: String = "0"
    var sort_age: String? = null
    var gender: String? = null
    var usersListLiveData: LiveData<List<User>>
    val usersGenderLiveData = MutableLiveData<String>()
    val ageLiveData: MutableLiveData<Int> = MutableLiveData()
lateinit var apl:Application

    init {
        apl=application
        val pairLiveData = MediatorLiveData<Pair<Int?, String?>>()
        pairLiveData.addSource(ageLiveData) { age ->
            pairLiveData.value = Pair(age, pairLiveData.value?.second)
        }
        pairLiveData.addSource(usersGenderLiveData) { gender ->
            pairLiveData.value = Pair(pairLiveData.value?.first, gender)
        }
        usersListLiveData = Transformations.switchMap(pairLiveData) { value ->
            userDao.getAllSortUsers(
                value.first,
                value.second
            )
        }
        Log.e("Database", "init liveData")
    }

    fun setMan() {  // эти три метода для моих трех radiobutton
        gender = "man"
    }

    fun setWoman() {
        gender = "woman"
    }

    fun setAll_OfGender() {
        gender = null
    }

    fun sort() {
        ageLiveData.value = sort_age?.toIntOrNull()
        usersGenderLiveData.value = gender
    }


    fun addUser() {
        val currentUser = User(null, name, set_age.toInt(), gender)
        val service: ExecutorService = Executors.newSingleThreadExecutor()
        val call: Callable<Long> = Callable { userDao.insertUser(currentUser) }
        service.submit(call)
        service.shutdown()
    }

}



