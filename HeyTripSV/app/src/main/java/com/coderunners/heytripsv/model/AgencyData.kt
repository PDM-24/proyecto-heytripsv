package com.coderunners.heytripsv.model

import com.coderunners.heytripsv.R

data class AgencyDataModel(
    val id: String = "",
    val name: String = "",
    val desc: String = "",
    val email: String = "",
    val dui: String = "",
    val image: String = "",
    val number: String = "",
    val instagram: String = "",
    val facebook: String = "",
    val postList: MutableList<PostDataModel> = mutableListOf()
)

val agencyData = AgencyDataModel(
    id = "1",
    name = "SivarTrips",
    email = "sivarTrips@gmail.com",
    dui = "06700421-9",
    desc = "Llevando experiencias de viaje dentro y fuera de El Salvador\uD83C\uDF0D✨\uFE0F\u2028-Viajes privados.\u2028-Empresariales\u2028- Expresos.\u2028Cotiza con nosotros tu próxima aventura \uD83D\uDE90",
    image = "R.drawable.default_image",
    number = "7974-7018",
    instagram = "sivartripssv",
    facebook = "sivarrtrips",
    postList = PostList
)