package com.coderunners.heytripsv.data.remote.api

import com.coderunners.heytripsv.data.remote.model.PostListResponse
import com.coderunners.heytripsv.utils.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
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

}