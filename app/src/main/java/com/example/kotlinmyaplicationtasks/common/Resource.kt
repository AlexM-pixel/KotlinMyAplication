package com.example.kotlinmyaplicationtasks.common

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val uploadSongTask: UploadTask.TaskSnapshot? = null
) {
    class Success<T>(data: T, uploadSongTask: UploadTask.TaskSnapshot? = null) :
        Resource<T>(data = data, uploadSongTask = uploadSongTask)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class AccessDenied<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
