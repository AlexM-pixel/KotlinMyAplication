package com.example.kotlinmyaplicationtasks.repository


import com.example.kotlinmyaplicationtasks.model.Song
import com.google.firebase.firestore.DocumentReference



interface FirestoreRepository {
    suspend fun saveSong(song: Song): Void?
    suspend fun receiveSongs(): List<Song>

}