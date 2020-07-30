package com.example.kotlinmyaplicationtasks.task4

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinmyaplicationtasks.R
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_facebook_account.*


class MyAccount : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_account)
        val accessOn: AccessToken? = AccessToken.getCurrentAccessToken()
        if (accessOn != null && !accessOn.isExpired ) {
            val grequest: GraphRequest = GraphRequest.newMeRequest(
                accessOn
            ) { `object`, response ->
                val firstName: String = `object`.getString("first_name")
                val lastName: String = `object`.getString("last_name")
                val id: String = `object`.getString("id")
                val image_url = "https://graph.facebook.com/$id/picture?return_ssl_resources=1"
                name_faceBook.text = "$firstName  $lastName"
                Picasso.get().load(image_url).into(foto_faceBook)

            }
            val bundle = Bundle()
            bundle.putString("fields", "first_name,last_name,id")
            grequest.parameters = bundle
            grequest.executeAsync()
        }
        val acct = GoogleSignIn.getLastSignedInAccount(this)       //Получил информацию профиля
        if (acct != null) {
            val personName = acct.displayName
            val personPhoto: Uri? = acct.photoUrl
            name_faceBook.text = personName
            Picasso.get().load(personPhoto).into(foto_faceBook)
        }
    }
}

