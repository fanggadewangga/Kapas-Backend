package com.kapas.model.user

import com.google.gson.annotations.SerializedName

data class EditEmailBody(

    @field:SerializedName("email")
    val email: String

    )
