package com.coderunners.heytripsv.model

import com.coderunners.heytripsv.R

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
    val itinerary: List<Itinerary> = listOf(),
    val includes: List<String> = listOf(),
    val category: String = "",
    val position: Position = Position(0.0, 0.0),
    val user: String = ""
)

data class Position(
    val lat: Double,
    val long: Double
)

data class Itinerary(
    val time: String,
    val event: String
)