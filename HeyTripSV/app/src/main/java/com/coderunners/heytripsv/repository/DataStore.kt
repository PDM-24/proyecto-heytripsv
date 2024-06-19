package com.coderunners.heytripsv.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val data_store_name = "HEYTRIPSV"
val Context.datastore : DataStore<Preferences>
        by preferencesDataStore(name = data_store_name)

class DataStore (private val context: Context){

    val token_key = stringPreferencesKey("token")
    val role_key = stringPreferencesKey("role")


    suspend fun saveToken(value: String){
        context.datastore.edit { datastore ->
            datastore[token_key] = value
        }
    }
    suspend fun saveRole(value: String){
        context.datastore.edit { datastore ->
            datastore[role_key] = value
        }
    }


    fun getToken() = context.datastore.data.map {
            datastore ->
        datastore[token_key]
    }
    fun getRole() = context.datastore.data.map {
            datastore ->
        datastore[role_key]
    }


}