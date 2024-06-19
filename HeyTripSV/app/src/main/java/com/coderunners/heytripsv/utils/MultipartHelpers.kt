package com.coderunners.heytripsv.utils

import android.content.ContentResolver
import android.net.Uri
import com.coderunners.heytripsv.data.remote.model.ItineraryApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException

fun createPartFromString(descriptionString: String): RequestBody {
    return descriptionString.toRequestBody("multipart/form-data".toMediaTypeOrNull())
}

fun createFilePart(partName: String, fileUri: Uri, contentResolver: ContentResolver): MultipartBody.Part? {
    return try {
        val inputStream = contentResolver.openInputStream(fileUri)
        val fileBytes = inputStream?.readBytes() ?: return null
        val mimeType = contentResolver.getType(fileUri)
        val requestFile = fileBytes.toRequestBody(mimeType?.toMediaTypeOrNull())
        MultipartBody.Part.createFormData(partName, fileUri.lastPathSegment, requestFile)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun createPartFromList(partName: String, values: List<String>): List<MultipartBody.Part> {
    return values.map {
        MultipartBody.Part.createFormData(partName, it)
    }
}

fun createItineraryParts(itineraries: List<ItineraryApi>): List<MultipartBody.Part> {
    val parts = mutableListOf<MultipartBody.Part>()
    itineraries.forEachIndexed { index, itinerary ->
        parts.add(MultipartBody.Part.createFormData("itinerary[$index][time]", itinerary.time))
        parts.add(MultipartBody.Part.createFormData("itinerary[$index][event]", itinerary.event))
    }
    return parts
}