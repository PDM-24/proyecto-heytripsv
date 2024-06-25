package com.coderunners.heytripsv.data.remote.model

import com.coderunners.heytripsv.model.Itinerary
import com.google.gson.annotations.SerializedName

data class ApiReportResponse(
    @SerializedName(value = "data")
    val data: List<ReportApiModel> = listOf()
)

data class SendReportModel(
    @SerializedName(value = "content")
    val content: String = ""
)

data class ReportApiModel(
    @SerializedName(value = "_id")
    val id: String = "",
    @SerializedName(value = "image")
    val image: String = "",
    @SerializedName(value = "itinerary")
    val itinerary: MutableList<Itinerary> = mutableListOf(),
    @SerializedName(value = "reports")
    val reports: MutableList<Report> = mutableListOf(),
    @SerializedName(value = "agency")
    val agency: Agency = Agency(),
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
    @SerializedName(value = "createdAt")
    val createdAt: String = "",
    @SerializedName(value = "updatedAt")
    val updatedAt: String = ""
)

data class Report(
    @SerializedName(value = "_id")
    val id: String = "",
    @SerializedName(value = "user")
    val user: User = User(),
    @SerializedName(value = "content")
    val content: String = ""
)

data class User(
    @SerializedName(value = "_id")
    val id: String = "",
    @SerializedName(value = "name")
    val name: String = ""
)

data class Agency(
    @SerializedName(value = "_id")
    val id: String = "",
    @SerializedName(value = "name")
    val name: String = "",
    @SerializedName(value = "email")
    val email: String = ""
)

data class ApiAgencyReportModel(
    @SerializedName(value = "data")
    val data : List<ReportedAgency> = listOf()
)

data class ReportedAgency(
    @SerializedName(value = "_id")
    val id : String = "",
    @SerializedName(value = "name")
    val name :String ="",
    @SerializedName(value = "email")
    val email : String = "",
    @SerializedName(value = "dui")
    val dui : String ="",
    @SerializedName(value = "description")
    val description: String ="",
    @SerializedName(value = "instagram")
    val instragram : String ="",
    @SerializedName(value = "facebook")
    val facebook : String ="",
    @SerializedName(value = "image")
    val image : String ="",
    @SerializedName(value = "reports")
    val reports: MutableList<AgencyReports>
)

data class AgencyReports(
    @SerializedName(value = "user")
    val user: UserAgencyReport = UserAgencyReport(),
    @SerializedName(value ="content")
    val content: String ="",
    @SerializedName(value = "_id")
    val id :String = ""
)
data class  UserAgencyReport(
    @SerializedName(value = "_id")
    val id: String = "",
    @SerializedName(value = "name")
    val name: String = ""
)