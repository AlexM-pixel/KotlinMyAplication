package com.example.kotlinmyaplicationtasks.view.ui.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmyaplicationtasks.view.ui.data.LoginDataSource
import com.example.kotlinmyaplicationtasks.view.ui.data.LoginRepository
import com.google.firebase.auth.FirebaseAuth

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(FirebaseAuth.getInstance())
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}