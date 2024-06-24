package com.coderunners.heytripsv.data.remote.api

import android.net.Uri
import com.coderunners.heytripsv.data.remote.model.ItineraryApi
import com.coderunners.heytripsv.data.remote.model.AgencyResponse
import com.coderunners.heytripsv.data.remote.model.ApiAgencyReportModel
import com.coderunners.heytripsv.data.remote.model.ApiReportResponse
import com.coderunners.heytripsv.data.remote.model.ChangePassBody
import com.coderunners.heytripsv.data.remote.model.CompareCodeBody
import com.coderunners.heytripsv.data.remote.model.LogInBody
import com.coderunners.heytripsv.data.remote.model.LogInResponse
import com.coderunners.heytripsv.data.remote.model.Own
import com.coderunners.heytripsv.data.remote.model.PostListResponse
import com.coderunners.heytripsv.data.remote.model.ReportApiModel
import com.coderunners.heytripsv.data.remote.model.ReportedAgency
import com.coderunners.heytripsv.data.remote.model.SendCodeBody
import com.coderunners.heytripsv.data.remote.model.editOwnBody
import com.coderunners.heytripsv.data.remote.model.savedPosts
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
    @Multipart
    @POST(value= Constants.API_PATH + Constants.POST_REGISTER_AGENCY)
    suspend fun addAgency(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("dui") dui: RequestBody,
        @Part("description") description: RequestBody,
        @Part("number") number: RequestBody,
        @Part("instagram") instagram: RequestBody?,
        @Part("facebook") facebook: RequestBody?,
        @Part("password") password: RequestBody,
        @Part image: MultipartBody.Part?
    ): APIResponseSuccesful

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
    @DELETE(value = Constants.API_PATH + Constants.DELETE_POST)
    suspend fun deletePost(
        @Header("Authorization") authHeader: String,
        @Path("postId") postId: String
    ): PostListResponse

    @Headers(value = ["Content-Type: application/json"])
    @PATCH(value = Constants.API_PATH + Constants.PATCH_REPORT_POST + "{postId}")
    suspend fun reportPost(
        @Header("Authorization") authHeader: String,
        @Path("postId") postId: String,
        @Body reportApiModel: ReportApiModel
    ):APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @POST(value= Constants.API_PATH + Constants.POST_RECOVER_PASSWORD)
    suspend fun sendCode(@Body email : SendCodeBody): APIResponseSuccesful
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.GET_REPORTED_POST)
    suspend fun getReportedPosts(@Header("Authorization") authHeader: String): List<ReportApiModel>

    @Headers(value = ["Content-Type: application/json"])
    @PATCH(value = Constants.API_PATH + Constants.PATCH_REPORTED_POST + "{id}")
    suspend fun patchReportedPost(
        @Header("Authorization") authHeader: String,
        @Path("id") postId: String
    ): APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @GET(Constants.API_PATH + Constants.GET_REPORTED_AGENCY)
    suspend fun getReportedAgencies(
        @Header("Authorization") authHeader: String
    ): List<ReportedAgency>

    @Headers(value = ["Content-Type: application/json"])
    @PATCH(Constants.API_PATH + Constants.PATCH_REPORT_AGENCY + "{id}")
    suspend fun deleteReportedAgency(
        @Header("Authorization") authHeader: String,
        @Path("id") agencyId: String
    ): APIResponseSuccesful
  
    @PATCH(value = Constants.API_PATH + Constants.PATCH_REPORTED_AGENCY + "{agencyId}")
    suspend fun reportAgency(
        @Header("Authorization") authHeader: String,
        @Path("agencyId") agencyId: String,
        @Body reportApiModel: ReportApiModel
    ):APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @PATCH(value = Constants.API_PATH + Constants.SAVE_POST + "{id}")
    suspend fun savePost(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ):savedPosts

    @Headers(value = ["Content-Type: application/json"])
    @POST(value= Constants.API_PATH + Constants.POST_CONFIRM_CODE)
    suspend fun compareCode(@Body compareCode : CompareCodeBody): APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @POST(value= Constants.API_PATH + Constants.POST_CHANGE_PASSWORD)
    suspend fun changePassword(@Body changePass : ChangePassBody): APIResponseSuccesful

    @Headers(value = ["Content-Type: application/json"])
    @GET(value= Constants.API_PATH + Constants.GET_OWN_USER)
    suspend fun getUser(@Header("Authorization") authHeader: String): Own

    @Headers(value = ["Content-Type: application/json"])
    @POST(value= Constants.API_PATH + Constants.POST_EDIT_PROFILE)
    suspend fun editUser(
        @Header("Authorization") authHeader: String,
        @Body editOwn : editOwnBody
    ): Own
}