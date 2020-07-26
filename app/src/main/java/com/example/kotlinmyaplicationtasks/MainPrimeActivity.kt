package com.example.kotlinmyaplicationtasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.company.details.Engine
import com.company.professions.Driver
import com.company.vahicles.Car
import com.example.kotlinmyaplicationtasks.task1.MainActivity1
import com.example.kotlinmyaplicationtasks.task2.MainActivity2
import com.example.kotlinmyaplicationtasks.task3.MainActivity3
import kotlinx.android.synthetic.main.activity_main.*

class MainPrimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        task1.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity1::class.java)
            startActivity(intent)
        }
        task2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        task3.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}