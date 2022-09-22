package com.kapas.model.user

import com.google.gson.annotations.SerializedName

data class EditPhoneBody(

    @field:SerializedName("phone")
    val phone: String
)