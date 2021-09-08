package com.example.kotlinmyaplicationtasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey(autoGenerate = true) var id: Long? ,
                var name: String,
                var age: Int,
                var gender:String?) {
}