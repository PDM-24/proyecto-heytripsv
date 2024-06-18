package com.coderunners.heytripsv.data.remote.model

import com.coderunners.heytripsv.model.PostDataModel
import com.google.gson.annotations.SerializedName

data class PostListResponse(
    @SerializedName(value = "posts")
    val posts: List<PostApi> = listOf(),
    @SerializedName(value = "count")
    val count: Int = 0
)

data class PostApi(
    @SerializedName(value = "_id")
    val id: String = "",
    @SerializedName(value = "title")
    val title: String = "",
    @SerializedName(value = "description")
    val description: String = "",
    @SerializedName(value = "date")
    val date: String = "",
    @SerializedName(value = "meeting")
    val meeting: String = "",
    @SerializedName(value = "category")
    val category: String = "",
    @SerializedName(value = "lat")
    val lat: Double = 0.0,
    @SerializedName(value = "long")
    val long: Double = 0.0,
    @SerializedName(value = "price")
    val price: Double = 0.0,
    @SerializedName(value = "includes")
    val includes: List<String> = listOf(),
    @SerializedName(value = "image")
    val image: String = "",
    @SerializedName(value = "itinerary")
    val itinerary: List<ItineraryApi> = listOf(),
    @SerializedName(value = "agency")
    val agency: AgencyPostApi = AgencyPostApi()

)

data class ItineraryApi(
    @SerializedName(value = "time")
    val time: String,
    @SerializedName(value = "event")
    val event: String
)

data class AgencyPostApi(
    @SerializedName(value = "_id")
    val id: String = "",
    @SerializedName(value = "name")
    val name: String = "",
    @SerializedName(value = "number")
    val number: String = "",
)