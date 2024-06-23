package com.coderunners.heytripsv.data.remote.model

import com.google.gson.annotations.SerializedName

data class Own(
    @SerializedName(value = "_id")
    val id : String = "",

    @SerializedName(value = "email")
    val email : String = "",

    @SerializedName(value = "name")
    val name : String = ""
)

data class editOwnBody(
    @SerializedName(value = "_id")
    val id : String = "",

    @SerializedName(value = "email")
    val email : String,

    @SerializedName(value = "name")
    val name : String
)