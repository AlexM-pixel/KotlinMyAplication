package com.example.kotlinmyaplicationtasks.repository

import android.net.Uri
import com.example.kotlinmyaplicationtasks.model.Message
import com.example.kotlinmyaplicationtasks.model.Song
import kotlinx.coroutines.tasks.await
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask

class FirestorageRepositoryImpl(private val firebaseStorage: FirebaseStorage) :
    FireStorageRepository {
    override suspend fun  upLoadAudio(id: String, uri: Uri): UploadTask.TaskSnapshot? {
        val storageMetadata = StorageMetadata.Builder()
            .setContentType("audio/mpeg")
            .build()
        val storageRef = firebaseStorage.getReference("audio")
        return storageRef
            .child(id)
            .putFile(uri, storageMetadata)
            .await()
    }

    override suspend fun downloadAudioUrl(song: Song): Uri? {
        val id = song.id
        return firebaseStorage
            .getReference("audio")
            .child(id)
            .downloadUrl
            .await()
    }

}