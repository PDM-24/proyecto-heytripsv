package com.coderunners.heytripsv.data.remote.model

import androidx.compose.ui.semantics.Role
import com.google.gson.annotations.SerializedName

data class LogInBody(
    @SerializedName(value = "email")
    val email: String = "",
    @SerializedName(value = "password")
    val password: String = ""
)

data class LogInResponse(
    @SerializedName(value = "token")
    val token: String="",
    @SerializedName(value = "role")
    val role: String="",
    @SerializedName(value = "saved")
    val saved: MutableList<String> = mutableListOf()
)
