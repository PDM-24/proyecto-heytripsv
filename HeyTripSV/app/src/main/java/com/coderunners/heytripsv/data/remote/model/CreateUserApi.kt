package com.coderunners.heytripsv.data.remote.model

import com.google.gson.annotations.SerializedName

data class CreateUserBody(
    @SerializedName(value = "name")
    val name: String = "",
    @SerializedName(value = "email")
    val email: String = "",
    @SerializedName(value = "password")
    val password: String = "",

)

