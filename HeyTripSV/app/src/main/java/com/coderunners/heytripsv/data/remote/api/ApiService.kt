package com.coderunners.heytripsv.data.remote.api

import android.net.Uri
import com.coderunners.heytripsv.data.remote.model.ItineraryApi
import com.coderunners.heytripsv.data.remote.model.PostListResponse
import com.coderunners.heytripsv.utils.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Headers(value = ["Content-Type: application/json "])
    @POST(value= Constants.API_PATH + Constants.POST_REGISTER_AGENCY)
    suspend fun addAgency(@Body agencyApi: AgencyApi): APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.POST_REGISTER_USER)
    suspend fun addUser(@Body userApi: UserApi) : APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.GET_TRIP_UPCOMING)
    suspend fun getUpcoming(): PostListResponse

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.GET_TRIP_RECENT)
    suspend fun getRecent(): PostListResponse

    @Multipart
    @POST(value = Constants.API_PATH + Constants.POST_CREATE)
    suspend fun addPost(
        @Header("Authorization") authHeader: String,
        @Part("title") title : String,
        @Part("description") description: String,
        @Part("date") date: String,
        @Part("meeting") meeting: String,
        @Part("category") category: String,
        @Part("lat") lat: Double,
        @Part("long") long: Double,
        @Part("price") price: Double,
        @Part("includes") includes: List<String>,
        @Part("image") image: Uri?,
        @Part("itinerary") itinerary: List<ItineraryApi>
        ) : APIResponseSuccesful
}