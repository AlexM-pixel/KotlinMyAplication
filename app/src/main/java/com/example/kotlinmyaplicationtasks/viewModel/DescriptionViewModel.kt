package com.example.kotlinmyaplicationtasks.viewModel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinmyaplicationtasks.model.User
import kotlin.properties.Delegates

// тут будет viewModel для второго активити с  описанием выбранного юзера
class DescriptionViewModel : ViewModel() {
    lateinit var name:String
   lateinit var age:String



}