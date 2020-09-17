package com.example.kotlinmyaplicationtasks.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmyaplicationtasks.R
import com.example.kotlinmyaplicationtasks.databinding.ActivityMainBinding
import com.example.kotlinmyaplicationtasks.model.User
import com.example.kotlinmyaplicationtasks.view.adapters.UserAdapter
import com.example.kotlinmyaplicationtasks.viewModel.UsersViewModel

class Main6Activity : AppCompatActivity() {
    lateinit var usersViewModel: UsersViewModel
    lateinit var userAdapter: UserAdapter
    val usersList: MutableList<User> = mutableListOf()
    val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //  usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(UsersViewModel::class.java)

        Log.e("Database", "viewModel ${usersViewModel.usersListLiveData.value?.size}")     //вот здесь почему-то null всегда
        userAdapter =
            UserAdapter(usersList)
        usersViewModel.usersListLiveData.observe(this,
            Observer { users: MutableList<User> ->
                userAdapter.setUserList(users)         // изменения в бд засетил в список адаптера
                Log.e("Database", "observe liveData ${users.size}")
            })
        binding.adapter = userAdapter
        binding.userViewModel = usersViewModel


    }

}