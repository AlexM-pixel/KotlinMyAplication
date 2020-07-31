package com.example.kotlinmyaplicationtasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinmyaplicationtasks.task5.MainActivity5
import kotlinx.android.synthetic.main.activity_main.*

class MainPrimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        task5_button.setOnClickListener {
            intent= Intent(this,MainActivity5::class.java)
            startActivity(intent)
        }
    }
}