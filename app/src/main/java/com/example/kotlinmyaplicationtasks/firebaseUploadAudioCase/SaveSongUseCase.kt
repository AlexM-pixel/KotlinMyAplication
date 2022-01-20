package com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase


import com.example.kotlinmyaplicationtasks.common.Resource
import com.example.kotlinmyaplicationtasks.model.Song
import com.example.kotlinmyaplicationtasks.repository.FirestoreRepositoryImpl
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class SaveSongUseCase(private val repository: FirestoreRepositoryImpl) {
    operator fun invoke(song: Song): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            repository.saveSong(song = song)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = e.parseErrorBody()
                    emit(Resource.Error<Unit>(message = message.code.toString(), Unit))
                }
                is StorageException -> {
                    emit(Resource.Error(message = e.message.toString(), Unit))
                }
                else -> {
                    emit(Resource.Error<Unit>(message = e.message.toString(), Unit))
                }
            }
        }

    }
}