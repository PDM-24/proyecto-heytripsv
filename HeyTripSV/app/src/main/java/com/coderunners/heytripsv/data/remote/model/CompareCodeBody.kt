package com.coderunners.heytripsv.data.remote.model

import com.google.gson.annotations.SerializedName

data class CompareCodeBody(

    @SerializedName(value = "code")
    val code : String = "",

    @SerializedName(value = "email")
    val email : String = ""
)
