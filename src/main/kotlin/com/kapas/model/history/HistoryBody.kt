package com.kapas.model.history

import com.google.gson.annotations.SerializedName

data class HistoryBody(
    @field:SerializedName("uid")
    val uid: String,

    @field:SerializedName("job_id")
    val jobId: String,
)