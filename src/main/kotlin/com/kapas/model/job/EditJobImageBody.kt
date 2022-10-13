package com.kapas.model.job

import com.google.gson.annotations.SerializedName

data class EditJobImageBody(
    @field:SerializedName("image_url")
    val imageUrl: String
)