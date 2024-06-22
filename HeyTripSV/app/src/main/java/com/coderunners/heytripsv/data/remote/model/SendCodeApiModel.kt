package com.coderunners.heytripsv.data.remote.model

import com.google.gson.annotations.SerializedName

data class SendCodeBody(
    @SerializedName("email")
    val email: String = ""
)
