package com.example.kotlinmyaplicationtasks.view.ui.data

import android.util.Log
import com.example.kotlinmyaplicationtasks.view.ui.data.model.LoggedInUser
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private val mAuth: FirebaseAuth) {

    fun login(username: String, password: String): Result<AuthResult>? {
        var result: Result<AuthResult>? = null
        mAuth.signInWithEmailAndPassword(username, password).addOnSuccessListener {
            result = Result.Success(it)
            Log.e("retrofit_tag", "ssss")

        }
            .addOnFailureListener {
                Log.e("retrofit_tag", it.message.toString())

                result = Result.Error(it)
            }
        return result
    }

    fun logout() {
        mAuth.signOut()
    }
}