package com.coderunners.heytripsv.data.remote.api

import com.coderunners.heytripsv.data.remote.model.ReportApiModel
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.utils.Constants
import com.google.gson.annotations.SerializedName

data class APIResponseSuccesful(
    @SerializedName(value = Constants.RESPONSE_SUCCESFUL)
    val result: String,
    val reportedPosts: List<ReportApiModel>? = null,
    val reportedAgencies: List<ReportApiModel>? = null
)



data class APIResponseError(
    @SerializedName(value = Constants.RESPONSE_ERROR)
    val error : String
)