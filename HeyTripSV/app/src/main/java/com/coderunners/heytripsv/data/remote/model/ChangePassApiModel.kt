package com.coderunners.heytripsv.data.remote.model

import com.google.gson.annotations.SerializedName

data class ChangePassBody(

    @SerializedName(value = "email")
    val email : String = "",

    @SerializedName(value = "pass")
    val password : String = ""
)
