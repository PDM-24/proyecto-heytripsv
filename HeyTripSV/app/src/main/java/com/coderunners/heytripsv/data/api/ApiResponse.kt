package com.coderunners.heytripsv.data.api

import com.coderunners.heytripsv.utils.Constants
import com.google.gson.annotations.SerializedName

data class APIResponseSuccesful(
    @SerializedName(value = Constants.RESPONSE_SUCCESFUL)
    val result : String
)

data class APIResponseError(
    @SerializedName(value = Constants.RESPONSE_ERROR)
    val error : String
)