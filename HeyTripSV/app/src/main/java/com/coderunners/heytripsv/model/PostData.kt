package com.coderunners.heytripsv.model

data class PostDataModel(
    val id: String = "",
    val title: String = "",
    val image: String = "",
    val date: String = "",
    val price: Double = 0.0,
    val agency: String = "",
    val agencyId: String = "",
    val phone: String = "",
    val description: String = "",
    val meeting: String = "",
    val itinerary: List<Itinerary> = mutableListOf(),
    val includes: MutableList<String> = mutableListOf(),
    val category: String = "",
    val position: Position = Position(0.0, 0.0),
    val user: String = ""
)

data class Position(
    val lat: Double,
    val long: Double
)

data class Itinerary(
    var time: String,
    var event: String
)