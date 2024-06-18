package com.coderunners.heytripsv.data.remote.api

import com.coderunners.heytripsv.utils.Constants
import com.google.gson.annotations.SerializedName

data class AgencyApi(
    @SerializedName(value = Constants.POST_NAME)
    val name: String = "",
    @SerializedName(value = Constants.POST_EMAIL)
    val email: String = "",
    @SerializedName(value = Constants.POST_DUI)
    val dui: String = "",
    @SerializedName(value = Constants.POST_DESCRIPTION)
    val description: String = "",
    @SerializedName(value = Constants.POST_NUMBER)
    val number: Int = 0 ,
    @SerializedName(value = Constants.POST_INSTAGRAM)
    val instagram:String ="",
    @SerializedName(value = Constants.POST_FACEBOOK)
    val facebook: String ="",
    // @SerializedName(value = "image")
    // val image: String ="",
    @SerializedName(value = Constants.POST_PASSWORD)
    val password: String ="",
)

data class UserApi(
    @SerializedName(value = Constants.POST_USER_NAME)
    val name: String ="",
    @SerializedName(value = Constants.POST_USER_EMAIL)
    val email : String ="",
    @SerializedName(value = Constants.POST_USER_SAVED)
    val saved :String ="",
    @SerializedName(value = Constants.POST_USER_PASSWORD)
    val password: String ="",
)
data class RegisterUser(
    @SerializedName(value = "name")
    val name: String ="",
    @SerializedName(value = "email")
    val email: String ="",
    @SerializedName(value = "password")
    val password: String =""
)

data class PostAgencyApi(
    @SerializedName(value = "tittle")
    val title: String ="",
    @SerializedName(value = "description")
    val description: String ="",
    @SerializedName(value = "date")
    val date: String ="",
    @SerializedName(value = "meeting")
    val meeting: String ="",
    @SerializedName(value = "itinerary")
    val itinerary: String ="",
    @SerializedName(value = "includes")
    val includes : String ="",
    @SerializedName(value = "category")
    val category :String ="",
    @SerializedName(value = "lat")
    val lat : String ="",
    @SerializedName(value = "long")
    val long : String ="",
    @SerializedName(value = "price")
    val price :String =""
)