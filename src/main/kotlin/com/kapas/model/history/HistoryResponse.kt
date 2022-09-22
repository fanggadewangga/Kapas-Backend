package com.kapas.model.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @field:SerializedName("transaction_id")
    val transactionId: String,

    @field:SerializedName("job_id")
    val jobId: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("wage")
    val wage: Double
)