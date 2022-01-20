package com.example.kotlinmyaplicationtasks.firebaseDownloadUseCase

import com.example.kotlinmyaplicationtasks.common.Resource
import com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase.parseErrorBody
import com.example.kotlinmyaplicationtasks.model.Song
import com.example.kotlinmyaplicationtasks.repository.FirestoreRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class ReceiveSongsUseCase(val repository: FirestoreRepositoryImpl) {
    operator fun invoke(): Flow<Resource<List<Song>>> = flow {
        emit(Resource.Loading())
        try {
            val list = repository.receiveSongs()
            emit(Resource.Success(list))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.toString()))
            }else {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }
}