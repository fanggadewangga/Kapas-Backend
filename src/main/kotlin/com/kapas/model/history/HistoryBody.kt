package com.kapas.model.history

import com.google.gson.annotations.SerializedName

data class HistoryBody(
    // not yet, pay attention to UI and query
    @field:SerializedName("uid")
    val uid: String,

    @field:SerializedName("job_id")
    val jobId: String,
)