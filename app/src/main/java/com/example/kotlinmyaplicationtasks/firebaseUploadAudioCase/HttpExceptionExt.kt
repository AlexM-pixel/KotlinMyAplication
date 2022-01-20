package com.example.kotlinmyaplicationtasks.firebaseUploadAudioCase

import android.util.Log
import com.example.kotlinmyaplicationtasks.common.baseResponse.BaseResponse
import org.json.JSONObject
import retrofit2.HttpException

internal fun HttpException.parseErrorBody(): BaseResponse {
    this.response()?.errorBody()?.string()?.let {
        val errorBody = JSONObject(it)
        val code = errorBody.getString("code")
        val message = errorBody.getString("message")
        Log.e("retrofit_tag", errorBody.toString())
        Log.e("retrofit_tag", code)
        Log.e("retrofit_tag", message)
        return BaseResponse(code = code, message = message)
    } ?: kotlin.run {
        return BaseResponse(code = "-1", message = "unknown_error")
    }
}