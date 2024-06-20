package com.coderunners.heytripsv.data.remote.model

import com.google.gson.annotations.SerializedName

data class ReportApiModel(
    @SerializedName("content")
    val content: String = ""
)