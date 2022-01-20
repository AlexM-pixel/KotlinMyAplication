package com.example.kotlinmyaplicationtasks.repository

import com.example.kotlinmyaplicationtasks.model.Song
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await


class FirestoreRepositoryImpl(private val firestore: FirebaseFirestore) : FirestoreRepository {
    override suspend fun saveSong(song: Song): Void? {
        return firestore.collection("audio")
            .document(song.id)
            .set(song)
            .await()
    }

   override suspend fun receiveSongs(): List<Song> {
        return firestore
            .collection("audio")
            .get()
            .await()
            .documents
            .map { doc ->
                doc?.let {
                    doc.toObject<Song>()
                } ?: kotlin.run {
                    Song(
                        id = "empty",
                        songsName = "empty",
                        songsPath = "_",
                        imageUri = 0,
                        songsDuration = 1,
                        storageLink = "null"
                    )
                }
            }
    }


}