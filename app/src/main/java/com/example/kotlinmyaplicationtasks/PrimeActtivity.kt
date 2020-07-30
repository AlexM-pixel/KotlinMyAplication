package com.example.kotlinmyaplicationtasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinmyaplicationtasks.task4.MainActivity4
import kotlinx.android.synthetic.main.activity_main.*

class PrimeActtivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_task4.setOnClickListener {
            intent= Intent(this,MainActivity4::class.java)
            startActivity(intent)
        }
    }
}