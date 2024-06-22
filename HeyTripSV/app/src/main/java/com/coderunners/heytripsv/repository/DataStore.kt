package com.coderunners.heytripsv.repository

import android.content.Context
import android.util.Log
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
    val notification_key = stringPreferencesKey("notif_id")


    suspend fun saveToken(value: String){
        context.datastore.edit { datastore ->
            datastore[token_key] = value
        }
    }
    suspend fun addNotif(value: String){
        context.datastore.edit { datastore ->
            Log.i("datasoter key", datastore[notification_key].toString())
            if (datastore[notification_key] == null || datastore[notification_key] == ""){
                datastore[notification_key] = value
            }else {
                datastore[notification_key] = datastore[notification_key] + "/$value"
            }
        }
    }

    suspend fun removeNotif(value: String){
        context.datastore.edit { datastore ->
            var notifs = datastore[notification_key]?.split("/")
            if (notifs != null) {
                notifs = notifs.filter {
                    Log.i("filter", it + " " + value)
                    it != value }
                Log.i("filter", notifs.toString())
                datastore[notification_key] = notifs.joinToString("/")
            }else{
                datastore[notification_key] = ""
            }
        }
    }

    suspend fun resetNotif(){
        context.datastore.edit {
            datastore ->
            datastore[notification_key] = ""
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
    fun getNotifs() = context.datastore.data.map {
            datastore ->
        if (datastore[notification_key] != null && datastore[notification_key] != ""){
            Log.i("datasoter", datastore[notification_key].toString())
            val intList = datastore[notification_key]?.split("/")?.map {
                Log.i("datasoter map", it)
                it.toInt() }
            Log.i("datasoter", intList.toString())
            intList
        }   else {
            listOf()
        }

    }


}