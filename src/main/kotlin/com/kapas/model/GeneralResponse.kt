package com.kapas.model

import com.google.gson.annotations.SerializedName

data class GeneralResponse<T>(
    @SerializedName("status")
    val status: String = "",

    @SerializedName("message")
    val message: String = "",

    @SerializedName("data")
    val data: T?,
)