package com.coderunners.heytripsv.model

val PostList = mutableListOf<PostDataModel>(
    PostDataModel(
        "1",
        "Volcan de Santa Ana",
        "R.drawable.default_image",
        "12/05/2024",
        12.4,
        "Sivartrips",
        "",
        "76504265",
        "\uD83D\uDCAA\uD83C\uDFFC Dificultad: Intermedia 06/10 -- 12 Kilómetros\n" +
                "\u2028⌚ Tiempo estimado de recorrido: 3 horas de ascenso // 2 horas y media de descenso",
        "Gasolinera Uno Los Héroes",
        mutableListOf(Itinerary("3:00am", "Salida"), Itinerary("6:00pm", "Regreso")),
        mutableListOf("Transporte", "Entrada"),
        "Montañas",
        Position(13.8499995, -89.6397656)
    ),PostDataModel(
        "2",
        "Playa los cobanos",
        "R.drawable.default_image",
        "25/05/2024",
        15.0,
        "Sivartrips",
        "1",
        "76504265",
        "Ven a disfrutar a la playa los cobanos",
        "Gasolinera Dos Los Héroes",
        mutableListOf(Itinerary("7:00am", "Salida"), Itinerary("9:00pm", "Regreso")),
        mutableListOf("Transporte", "Entrada"),
        "Playas",
        Position(13.5254277, -89.8147406)
    ),PostDataModel(
        "3",
        "Ruta de las flores",
        "R.drawable.default_image",
        "30/05/2024",
        11.0,
        "Sivartrips",
        "1",
        "76504265",
        "\uD83D\uDCAA\uD83C\uDFFC Dificultad: Intermedia 06/10 -- 12 Kilómetros\n" +
                "\u2028⌚ Tiempo estimado de recorrido: 3 horas de ascenso // 2 horas y media de descenso",
        "Gasolinera Uno Los Héroes",
        mutableListOf(Itinerary("5:00am", "Salida"), Itinerary("7:00am", "Salcoatitan"), Itinerary("9:00am", "Apaneca")),
        mutableListOf("Transporte"),
        "Rutas",
        Position(13.8270692, -89.7604426)
    )
)