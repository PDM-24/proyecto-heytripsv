package com.coderunners.heytripsv.data.remote.model

import android.net.Uri

data class PostApiModel(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val meeting: String = "",
    val category: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val price: Double = 0.0,
    val includes: List<String> = listOf(),
    val image: Uri = Uri.parse("https://res.cloudinary.com/dlmtei8cc/image/upload/v1718430757/zjyr4khxybczk6hjibw9.jpg"),
    val itinerary: List<ItineraryApi> = listOf()
)

