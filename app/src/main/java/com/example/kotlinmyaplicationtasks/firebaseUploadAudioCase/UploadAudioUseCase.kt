package com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase

import android.net.Uri
import android.util.Log
import com.example.kotlinmyaplicationtasks.common.Resource
import com.example.kotlinmyaplicationtasks.model.Song
import com.example.kotlinmyaplicationtasks.repository.FireStorageRepository
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class UploadAudioUseCase(private val repository: FireStorageRepository) {
    operator fun invoke(song: Song, uri: Uri): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        try {
            val response = repository.upLoadAudio(
                id = song.id,
                uri = uri
            )
            if (response != null) {
                emit(Resource.Success(data = true, uploadSongTask = response))
            } else {
                emit(Resource.Error<Boolean>(message = "response is null"))
            }

        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = e.parseErrorBody()
                    emit(Resource.Error(message = message.toString()))
                }
                is StorageException -> {
                    val m = e.message
                    emit(Resource.AccessDenied(message = m.toString()))
                }

            }
        }
    }
}