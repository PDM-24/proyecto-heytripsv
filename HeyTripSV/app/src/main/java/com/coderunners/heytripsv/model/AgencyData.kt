package com.coderunners.heytripsv.model

import com.coderunners.heytripsv.R

data class AgencyDataModel(
    //TODO: STRING - MONGODB OBJECTID
    val id: Int = 0,
    val name: String = "",
    val desc: String = "",
    //TODO: STRING - LINK DE IMAGEN
    val image: Int = R.drawable.default_image,
    val number: String = "",
    val instagram: String = "",
    val facebook: String = "",
    val postList: MutableList<PostDataModel> = mutableListOf()
)

val agencyData = AgencyDataModel(
    id = 1,
    name = "SivarTrips",
    desc = "Llevando experiencias de viaje dentro y fuera de El Salvador\uD83C\uDF0D✨\uFE0F\u2028-Viajes privados.\u2028-Empresariales\u2028- Expresos.\u2028Cotiza con nosotros tu próxima aventura \uD83D\uDE90",
    image = R.drawable.default_image,
    number = "7974-7018",
    instagram = "sivartripssv",
    facebook = "sivarrtrips",
    postList = PostList
)