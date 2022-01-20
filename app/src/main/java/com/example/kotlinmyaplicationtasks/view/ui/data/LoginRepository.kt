package com.example.kotlinmyaplicationtasks.view.ui.data

import android.util.Log
import com.example.kotlinmyaplicationtasks.view.ui.data.model.LoggedInUser
import com.google.firebase.auth.AuthResult

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

     fun login(username: String, password: String): Result<AuthResult>? {
        // handle login
        val result = dataSource.login(username, password)
Log.e("retrofit_tag", username)
        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(data: AuthResult) {
        this.user = LoggedInUser(data.user?.uid ?: "empty", data.user?.displayName ?: "empty")
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}