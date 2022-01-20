package com.example.kotlinmyaplicationtasks.view.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmyaplicationtasks.R
import com.example.kotlinmyaplicationtasks.databinding.ActivityMainBinding
import com.example.kotlinmyaplicationtasks.model.User
import com.example.kotlinmyaplicationtasks.view.adapters.UserAdapter
import com.example.kotlinmyaplicationtasks.viewModel.UsersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import www.sanju.motiontoast.MotionToast

class Main6Activity : AppCompatActivity() {
    lateinit var usersViewModel: UsersViewModel
    lateinit var userAdapter: UserAdapter
    val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        //  usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(UsersViewModel::class.java)
        binding.root.startActivity_id.setOnClickListener {
            getPermission()
        }
        Log.e(
            "Database",
            "viewModel ${usersViewModel.usersListLiveData.value?.size}"
        )     //вот здесь почему-то null всегда
        userAdapter = UserAdapter()
        usersViewModel.usersListLiveData.observe(this, Observer { users: List<User> ->
            userAdapter.setUserList(users)         // изменения в бд засетил в список адаптера
            Log.e("Database", "observe liveData ${users.size}")
        })
        usersViewModel.usersGenderLiveData.observe(this, Observer {
            MotionToast.createColorToast(
                this,
                "Super!",
                "здесь описание сообщения",
                MotionToast.TOAST_SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(
                    this,
                    R.font.helvetica_regular
                )
            )
        })
        binding.adapter = userAdapter
        binding.userViewModel = usersViewModel


    }

    private fun getPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            startActivity()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                startActivity()
            }
        }
    }

    private fun startActivity() {
        val intent = Intent(this, ListSongsActivity::class.java)
        startActivity(intent)
    }

}