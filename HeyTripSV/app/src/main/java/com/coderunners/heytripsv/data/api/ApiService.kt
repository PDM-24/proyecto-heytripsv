package com.coderunners.heytripsv.data.api

import com.coderunners.heytripsv.utils.Constants
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers(value = ["Content-Type: application/json "])
    @POST(value= Constants.API_PATH + Constants.POST_REGISTER_AGENCY)
    suspend fun addAgency(@Body agencyApi: AgencyApi ): APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.POST_REGISTER_USER)
    suspend fun addUser(@Body userApi: UserApi) : APIResponseSuccesful



}