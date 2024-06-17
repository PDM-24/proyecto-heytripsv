package com.coderunners.heytripsv.data.api

import com.coderunners.heytripsv.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient{
    val apiService : ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}