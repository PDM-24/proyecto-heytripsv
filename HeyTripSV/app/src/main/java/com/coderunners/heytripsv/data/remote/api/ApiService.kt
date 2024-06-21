package com.coderunners.heytripsv.data.remote.api

import android.net.Uri
import com.coderunners.heytripsv.data.remote.model.ItineraryApi
import com.coderunners.heytripsv.data.remote.model.AgencyResponse
import com.coderunners.heytripsv.data.remote.model.LogInBody
import com.coderunners.heytripsv.data.remote.model.LogInResponse
import com.coderunners.heytripsv.data.remote.model.PostListResponse
import com.coderunners.heytripsv.data.remote.model.ReportApiModel
import com.coderunners.heytripsv.utils.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("date") date: RequestBody,
        @Part("meeting") meeting: RequestBody,
        @Part("category") category: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("long") long: RequestBody,
        @Part("price") price: RequestBody,
        @Part includes: List<MultipartBody.Part>,
        @Part image: MultipartBody.Part?,
        @Part itinerary: List<MultipartBody.Part>
        ) : APIResponseSuccesful
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.GET_AGENCY + "{id}")
    suspend fun getAgency(@Path("id") id : String): AgencyResponse

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.GET_SAVED_USER)
    suspend fun getSaved(@Header("Authorization") authHeader: String): PostListResponse

    @Headers(value = ["Content-Type: application/json "])
    @POST(value= Constants.API_PATH + Constants.POST_LOGIN)
    suspend fun logIn(@Body logInBody: LogInBody): LogInResponse

    @Headers(value = ["Content-Type: application/json"])
    @DELETE(value = Constants.API_PATH + Constants.DELETE_POST )
    suspend fun deletePost(
        @Header("Authorization") authHeader: String,
        @Path("postId") postId: String): APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @PATCH(value = Constants.API_PATH + Constants.PATCH_REPORT_POST + "{postId}")
    suspend fun reportPost(
        @Header("Authorization") authHeader: String,
        @Path("postId") postId: String,
        @Body reportApiModel: ReportApiModel
    ):APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.GET_REPORTED_POST)
    suspend fun getReportedPosts(@Header("Authorization") authHeader: String): APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @DELETE(value = Constants.API_PATH + Constants.DELETE_REPORTED_POST + "{_id}")
    suspend fun deleteReportedPost(
        @Header("Authorization") authHeader: String,
        @Path("postId") postId: String
    ): APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @GET(Constants.API_PATH + Constants.GET_REPORTED_AGENCY)
    suspend fun getReportedAgencies(
        @Header("Authorization") authHeader: String
    ): APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @DELETE(Constants.API_PATH + Constants.DELETE_REPORTED_AGENCY + "{_id}")
    suspend fun deleteReportedAgency(
        @Header("Authorization") authHeader: String,
        @Path("agencyId") agencyId: String
    ): APIResponseSuccesful
  
    @PATCH(value = Constants.API_PATH + Constants.PATCH_REPORTED_AGENCY + "{agencyId}")
    suspend fun reportAgency(
        @Header("Authorization") authHeader: String,
        @Path("agencyId") agencyId: String,
        @Body reportApiModel: ReportApiModel
    ):APIResponseSuccesful
  
}