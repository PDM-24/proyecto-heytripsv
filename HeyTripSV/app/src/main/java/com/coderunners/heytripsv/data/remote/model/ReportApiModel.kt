package com.coderunners.heytripsv.data.remote.model

import com.coderunners.heytripsv.model.Itinerary
import com.google.gson.annotations.SerializedName

data class ReportApiModel(
    @SerializedName(value="_id")
    val id: String = "",
    @SerializedName(value="content")
    val content: String = "",
    //@SerializedName("image")
    //val image : xd = ""
    @SerializedName(value="itinerary")
    val itinerary: MutableList<Itinerary> = mutableListOf(),
    @SerializedName(value="reports")
    val reports : MutableList<Reports> = mutableListOf(),
    @SerializedName(value = "agency")
    val agency : MutableList<Agency> = mutableListOf(),
    @SerializedName(value = "title")
    val title: String ="",
    @SerializedName(value ="description")
    val description : String ="",
    @SerializedName(value="date")
    val date: String ="",
    @SerializedName(value = "meeting")
    val meeting : String = "",
    @SerializedName(value = "category")
    val category: String ="",
    @SerializedName(value = "lat")
    val lat: Double = 0.0,
    @SerializedName(value = "long")
    val long: Double = 0.0,
    @SerializedName(value = "price")
    val price: Double = 0.0,
    @SerializedName(value="createdAt")
    val createdAt : String ="",
    @SerializedName(value= "updatedAt")
    val updatedAt : String =""
) {
}

data class  Reports(
    @SerializedName(value = "_id")
    val id: String = "",
    @SerializedName(value = "user")
    val name: MutableList<User> = mutableListOf() ,
    @SerializedName(value= "content")
    val content:String =""
)

data class User(
    @SerializedName(value = "_id")
    val id : String = "",
    @SerializedName(value = "name")
    val name: String =""
)

data class Agency(
    @SerializedName(value = "_id")
    val id :String = "",
    @SerializedName(value= "name")
    val name : String = "",
    @SerializedName(value="email")
    val email:String =""
)

data class ReportedAgency(
    @SerializedName(value = "agency")
    val agency: MutableList<Agency> = mutableListOf(),
    @SerializedName(value = "reports")
    val report: MutableList<Reports> = mutableListOf()
)