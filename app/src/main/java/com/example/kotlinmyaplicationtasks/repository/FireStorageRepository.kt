package com.example.kotlinmyaplicationtasks.repository

import android.net.Uri
import com.example.kotlinmyaplicationtasks.model.Message
import com.example.kotlinmyaplicationtasks.model.Song
import com.google.firebase.storage.UploadTask


interface FireStorageRepository {
    suspend fun upLoadAudio(id: String, uri: Uri): UploadTask.TaskSnapshot?
    suspend fun downloadAudioUrl(song: Song): Uri?
}