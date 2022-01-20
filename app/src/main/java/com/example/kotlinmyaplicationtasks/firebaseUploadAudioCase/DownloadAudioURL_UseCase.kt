package com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase

import android.net.Uri
import com.example.kotlinmyaplicationtasks.common.Resource
import com.example.kotlinmyaplicationtasks.model.Song
import com.example.kotlinmyaplicationtasks.repository.FireStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class DownloadAudioURL_UseCase(private val repository: FireStorageRepository) {
    operator fun invoke(song: Song): Flow<Resource<Uri>> = flow {
        emit(Resource.Loading<Uri>())
        try {
            val response = repository
                .downloadAudioUrl(song = song)
            if (response != null) {
                emit(Resource.Success(data = response))
            } else {
                emit(Resource.Error<Uri>(message = "response is null!"))
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.toString()))
            }
        }
    }
}