package com.example.kotlinmyaplicationtasks.model

import com.google.android.gms.tasks.Task

data class Song(
    val id: String ="_",
    val songsName: String = "-",
    val songsPath: String = "-",
    val imageUri: Int=0,
    val songsDuration: Long =0,
    var storageLink: String = "-"
)
