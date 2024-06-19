package com.coderunners.heytripsv.data.remote.model

import com.google.gson.annotations.SerializedName

data class AgencyResponse(
    @SerializedName(value = "agency")
    val agency: AgencyApi = AgencyApi(),
    @SerializedName(value = "posts")
    val posts: MutableList<PostApi> = mutableListOf()
)

data class AgencyApi(
    @SerializedName(value = "_id")
    val id: String = "",
    @SerializedName(value = "name")
    val name: String = "",
    @SerializedName(value = "dui")
    val dui: String = "",
    @SerializedName(value = "email")
    val email: String = "",
    @SerializedName(value = "number")
    val number: String = "",
    @SerializedName(value = "description")
    val description: String = "",
    @SerializedName(value = "instagram")
    val instagram: String = "",
    @SerializedName(value = "facebook")
    val facebook: String = "",
    @SerializedName(value = "image")
    val image: String = ""
)