package com.example.kotlinmyaplicationtasks.common.baseResponse

import com.google.gson.annotations.SerializedName

class BaseResponse (
    @SerializedName("code")
    val code: String? = "0",
    @SerializedName("message")
    val message: String? = ""
        )