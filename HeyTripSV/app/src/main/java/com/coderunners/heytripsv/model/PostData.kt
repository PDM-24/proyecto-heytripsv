package com.coderunners.heytripsv.model

import com.coderunners.heytripsv.R

data class PostDataModel(
    val id: Int = 0,
    val title: String = "",
    val image: Int = R.drawable.default_image,
    val date: String = "",
    val price: Float = 0f,
    val agency: String = "",
    val phone: String = "",
    val description: String = "",
    val meeting: String = "",
    val itinerary: List<Itinerary> = listOf(),
    val includes: List<String> = listOf(),
    val position: Position = Position(0.0, 0.0)
)

data class Position(
    val lat: Double,
    val long: Double
)

data class Itinerary(
    val time: String,
    val event: String
)