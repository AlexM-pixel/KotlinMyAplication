package com.example.kotlinmyaplicationtasks.task5

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.kotlinmyaplicationtasks.R
import kotlinx.android.synthetic.main.activity_main5.*
import java.util.concurrent.TimeUnit

/*
Теперь мы познакомимся с WorkManager. Что бы понимать зачем он нужен вам надо разбираться в понятии DozeMode. Задача:
на экране представлено две кнопки.
 1. “Напомнить через …” - устанавливает время через которое пользователю надо напомнить
  посмотреть инстаграмчик(при условии того, что девайс подключен к интернету),
  соответственно через какое-то время(можно считать с эдиттекста) надо отобразить нотификацию с соответствующим текстом.
 2. Следить за процессом зарядки - Необходимо присылать нотификацию каждые скажем 5 минут
 или без разницы сколько минут с типом зарядки(USB или 220) и уровнем батареи. По поводу  инфы о батарее не уверен,
  может не получится сделать, тогда просто каждые 5 минут присылать нотификашку что идет зарядка.
 */


class MainActivity5 : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        reminder_button.setOnClickListener {
            val delay = input_time_text.text.toString().toLongOrNull()
            if (delay != null) {
                val Netconstraints: Constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
                val myWorkRequest: WorkRequest =
                    OneTimeWorkRequestBuilder<MyAlarmMenedger>()
                        .setInitialDelay(delay, TimeUnit.SECONDS)
                        .setConstraints(Netconstraints)
                        .build()
                WorkManager
                    .getInstance(this)
                    .enqueue(myWorkRequest)
            } else {
                input_time_text.setText("")
                input_time_text.hint = "error"
            }
        }
        charge_check_button.setOnClickListener {
            val chargeConstr: Constraints=Constraints.Builder()
                .setRequiresCharging(true)
                .build()
            val myPeriodRequest: PeriodicWorkRequest =
                PeriodicWorkRequestBuilder<MyChargeManager>(15, TimeUnit.SECONDS)
                    .setConstraints(chargeConstr)
                    .build()
            WorkManager
                .getInstance(this)
                .enqueue(myPeriodRequest)
        }
    }
}