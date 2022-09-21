package com.kapas.model.user

import com.google.gson.annotations.SerializedName

data class EditProfileBody(

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

)
