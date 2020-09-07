package com.example.kotlinmyaplicationtasks.task2

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.kotlinmyaplicationtasks.R
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.FileInputStream
import java.lang.StringBuilder
import java.lang.System.currentTimeMillis
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/*
Найти количество повторений каждого слова в книге и вывести 10 наиболее встречаемых слов
 за исключением артиклев(a и the просто отфильтруйте).
 Пользователь вводит в edittext цифровое значение(нужна проверка на ввод),
 которое задает количество потоков которые используются для поиска.
 По завершению поиска также отобразить время затраченное на поиск.
 */
class MainActivity2 : AppCompatActivity() {
   @Volatile var maxCountMap = mutableMapOf<String,Int>()
    var timeBefor: Long = 0
private var sharedCounter=0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
        1
        )
        val count_thread: EditText = findViewById(R.id.count_thread)
        val buttonStart: Button = findViewById(R.id.buttonStart)
        buttonStart.setOnClickListener {
            maxCountMap= mutableMapOf()
            sharedCounter=0
            val count: Int? = count_thread.text.toString().toIntOrNull()
            if (count != null) {
                timeBefor = currentTimeMillis()
                Thread(Runnable {
                    method(count)
                }).start()
            } else {
                count_thread.setText("")
                count_thread.hint = "insert number"
            }
        }
    }

    @SuppressLint("SetTextI18n", "SdCardPath")
    @WorkerThread
    fun method(count: Int) {
        var myBook = FileInputStream("/sdcard/Download/MyKotlinBook.txt").bufferedReader().use { it.readText() }

        myBook = Regex("""[^a-zA-Z\s]""").replace(myBook, "")         // убрал все знаки препинания кромн пробела
        var list = myBook.toLowerCase().split(" ")                            // список слов
        list = list.filterNot { it.equals("a") || it.equals("the") }
        var  list2: List<String>
        val executor = Executors.newFixedThreadPool(count)
        var n=0
        val size=list.size/count                                                         // получил кусок списка для каждого потока (точнее размер будущего списка)
        var length = size
        val worker = Callable<Map<String,Int>> {
            for (a in 1..10) {
                countincrement()
            }
            Log.e("!!!","Hello this is thread  ${Thread.currentThread().name}" )
            list2= list.slice(n..length-1)                             //получил кусок списка для каждого потока
            val groupMap = list2.groupingBy { it }.eachCount()                            // создал map где ключ - слова а значние это количество повторений
            n+=size
            length+=size
            return@Callable groupMap
        }
        for (i in  1..count) {

            resultMapping(executor.submit(worker).get())

          //  executor.execute(worker)
        }
        executor.shutdown()
        while (!executor.isTerminated) {
        }
        Log.e("!!!","Finished all threads, sharedcounter = $sharedCounter")
        val resultMap  = maxCountMap.toList().sortedByDescending { (_, value) -> value }.toMap()   // отсортироваль по убыванию
        val timeAfter: Long = currentTimeMillis() - timeBefor
        n=0
        val text =StringBuilder()
        for((key,value) in resultMap) {
                text.append("$key - $value \n")
            n++
            if (n==10) {
                break
            }
        }
        list_words.post(Runnable {list_words.text=text.toString()})           // отправил в UI поток значения
        time.post(Runnable { time.text=timeAfter.toString() })                 // отправил в UI поток время выполнения

    }
    @Synchronized
    fun resultMapping( groupMap:Map<String,Int>) {
        for((key,value) in groupMap) {
            if (maxCountMap.get(key) != null) {
                maxCountMap.put(key, maxCountMap.get(key)!! + value)
            }else {
                maxCountMap.put(key,value)
            }
        }
    }
    fun countincrement() {
        sharedCounter++
    }
}