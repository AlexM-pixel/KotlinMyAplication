package com.example.kotlinmyaplicationtasks.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.kotlinmyaplicationtasks.R
import com.example.kotlinmyaplicationtasks.model.User
import com.example.kotlinmyaplicationtasks.model.dao.UserDao
import com.example.kotlinmyaplicationtasks.model.dao.UsersDatabase
import java.util.concurrent.*

class UsersViewModel(application: Application) :
    AndroidViewModel(application) {
    private val userDao: UserDao = UsersDatabase.getDatabase(application).userDao()

    var name: String = ""
    var set_age: String = "0"
    var sort_age: String = "0"
    var gender = "man"
    var man = false
    var woman = false
    var usersListLiveData: LiveData<List<User>>
    val usersGenderLiveData: LiveData<MutableList<User>>
    val ageLiveData: MutableLiveData<String> = MutableLiveData()



    init {
        usersGenderLiveData =
            Transformations.switchMap(ageLiveData) { sort_age -> userDao.getByOld(sort_age.toInt()) }
        usersListLiveData = Transformations.switchMap(usersGenderLiveData) { onNewCheckGender() }
        //  usersListLiveData = userDao.getAllUsers()
        Log.e("Database", "init liveData")
    }

    fun buttonSetGender(view: View) {            // этот метод использую для установки пола юзера при сохранении
        if (view.id == R.id.radioButton_man) { // проверяю какая radiobutton нажата
            gender = "man"
        } else {
            gender = "woman"
        }
    }

    fun setAge() { // эта функция запускает всю сортировку
        ageLiveData.value = sort_age
    }

    fun onNewCheckGender(): MutableLiveData<List<User>> {
        var filter: MutableLiveData<List<User>>
        filter=MutableLiveData()
        if (man && woman) filter = MutableLiveData(usersGenderLiveData.value!!.filter { it.gender.equals("man") || it.gender.equals("woman") })
        if (man && !woman) filter = MutableLiveData(usersGenderLiveData.value!!.filter { it.gender.equals("man") })
        if (!man && woman) filter =  MutableLiveData(usersGenderLiveData.value!!.filter { it.gender.equals("woman") })
       if (!man && !woman) filter= MutableLiveData(usersGenderLiveData.value!!.filterNot { it.gender.equals("man") || it.gender.equals("woman") })
        return filter
    }


    fun addUser(view: View) {
        val currentUser = User(null, name, set_age.toInt(), gender)
        val service: ExecutorService = Executors.newSingleThreadExecutor()
        val call: Callable<Long> = Callable { userDao.insertUser(currentUser) }
        val future: Future<Long> = service.submit(call)
        service.shutdown()
        Toast.makeText(
            view.context,
            "service.isTerminated= ${service.isTerminated}, id = ${future.get()}",
            Toast.LENGTH_LONG
        ).show()
    }

}



