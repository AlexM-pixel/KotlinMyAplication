package com.example.kotlinmyaplicationtasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.company.details.Engine
import com.company.professions.Driver
import com.company.vahicles.Car
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.list_sortid)
        var list = listOf("2", "12", "53532", "65656", "131131", "3", "351")
        list = list.filter { it.length % 2 == 0 }
        list = list.sortedBy { it.length }
        textView.text = list.getOrNull(list.lastIndex) ?: "Не найдено"
        buttonSecondTask.setOnClickListener {
            val car: Car = Car("Ford","scorpio",500, Driver(), Engine("Ford",200))
            textView.text = car.toString()
            Log.e("!!!",car.printInfo(car))
        }

    }
}