package com.coderunners.heytripsv.data.remote.model

import com.coderunners.heytripsv.model.Itinerary
import com.google.gson.annotations.SerializedName

data class ApiReportResponse(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("message")
    val message: String = "",
    @SerializedName("data")
    val data: List<ReportApiModel> = listOf()
)

data class ReportApiModel(
    @SerializedName("_id")
    val id: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("itinerary")
    val itinerary: MutableList<Itinerary> = mutableListOf(),
    @SerializedName("reports")
    val reports: MutableList<Report> = mutableListOf(),
    @SerializedName("agency")
    val agency: Agency = Agency(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("meeting")
    val meeting: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("long")
    val long: Double = 0.0,
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("updatedAt")
    val updatedAt: String = ""
)

data class Report(
    @SerializedName("_id")
    val id: String = "",
    @SerializedName("user")
    val user: User = User(),
    @SerializedName("content")
    val content: String = ""
)

data class User(
    @SerializedName("_id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
)

data class Agency(
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

data class ReportedAgency(
    @SerializedName("agency")
    val agency: Agency = Agency(),
    @SerializedName("reports")
    val report: MutableList<Report> = mutableListOf()
)
