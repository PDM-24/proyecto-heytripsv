package com.coderunners.heytripsv

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderunners.heytripsv.data.remote.api.ApiClient
import com.coderunners.heytripsv.data.remote.model.PostListResponse
import com.coderunners.heytripsv.model.AgencyDataModel
import com.coderunners.heytripsv.model.Itinerary
import com.coderunners.heytripsv.model.Position
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.model.PostList
import com.coderunners.heytripsv.model.agencyData
import com.coderunners.heytripsv.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //ESTADO DE APP (LOADING)
    private val _uiState = MutableStateFlow<UiState>(UiState.Ready)
    val uiState : StateFlow<UiState> = _uiState

    //VARIABLES API Y ROOM
    private val api = ApiClient.apiService

    //FLOWS
    private val _selectedPost = MutableStateFlow(PostDataModel())
    val selectedPost = _selectedPost.asStateFlow()

    private val _categoryList = MutableStateFlow(mutableListOf<PostDataModel>())
    val categoryList = _categoryList.asStateFlow()

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _selectedAgency = MutableStateFlow(AgencyDataModel())
    val selectedAgency = _selectedAgency.asStateFlow()

    private val _ownAgency = MutableStateFlow(AgencyDataModel())
    val ownAgency = _ownAgency.asStateFlow()

    //TODO: Vaciar el valor por defecto (Se va a obtener de la base)
    private val _savedPostList = MutableStateFlow(PostList)
    val savedPostList = _savedPostList.asStateFlow()

    private val _upcomingPosts = MutableStateFlow(mutableListOf<PostDataModel>())
    val upcomingPosts = _upcomingPosts.asStateFlow()

    private val _recentPosts = MutableStateFlow(mutableListOf<PostDataModel>())
    val recentPosts = _recentPosts.asStateFlow()

    //Función para parsear el formato que devuelve la API a dd/MM/yyyy o HH:mm
    private fun isoDateFormat(dateToFormat: String, time: Boolean = false): String{
        val dateStr = dateToFormat.removeSuffix("Z")
        val dateTime = LocalDateTime.parse(dateStr)

        val formatter = DateTimeFormatter.ofPattern(if (!time) "dd/MM/yyyy" else "hh:mm a")
        return dateTime.format(formatter)
    }
    fun saveSelectedPost(selectPost: PostDataModel) {
        _selectedPost.value = selectPost
    }

    fun saveSelectedCategory(category: String){
        _selectedCategory.value = category
        _categoryList.value = PostList.filter {
            when (category) {
                "Montañas", "Mountains" -> it.category == "Montañas"
                "Beaches", "Playas" -> it.category == "Playas"
                "Towns", "Pueblos" -> it.category == "Pueblos"
                "Others", "Otros" -> it.category == "Otros"
                "Routes", "Rutas" -> it.category == "Rutas"
                else -> false
            }
        }.toMutableList()
    }


    fun saveSelectedAgency(agency: String){
        //TODO: Obtener el id de la agencia y obtener la info de esa agenccia
        _selectedAgency.value = agencyData
    }

    fun getSavedPosts(){
        //TODO: Obtener la lista de posts guardados del usuario loggeado

    }

    fun setOwnAgency(agency: AgencyDataModel){
        _ownAgency.value = agency
    }

    //Función que setea las listas de posts de la pantalla de inicio
    fun getHomePostList(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _uiState.value = UiState.Loading
                getUpcomingList()
                getRecentList()
                _uiState.value = UiState.Success("Posts retrieved correctly")
            }catch (e: Exception){
                _uiState.value = UiState.Error("Error retrieving posts")
            }
        }
    }

    //Función a ejecutar para obtener los post próximos
    suspend fun getUpcomingList(){
        return withContext(Dispatchers.IO){
            var apiPostList = PostListResponse()
            try {
                _uiState.value = UiState.Loading
                apiPostList = api.getUpcoming()
                _upcomingPosts.value = mutableListOf<PostDataModel>()
                for (post in apiPostList.posts){
                    _upcomingPosts.value.add(
                        PostDataModel(
                            id = post.id,
                            title = post.title,
                            image = post.image,
                            date = isoDateFormat(post.date),
                            price = post.price,
                            agencyId = post.agency.id,
                            agency = post.agency.name,
                            phone = post.agency.number,
                            description = post.description,
                            meeting = post.meeting,
                            itinerary = post.itinerary.map { it -> Itinerary(isoDateFormat(it.time, true), it.event) },
                            includes = post.includes,
                            category = post.category,
                            position = Position(post.lat, post.long)
                        )
                    )
                }
                //_uiState.value = UiState.Success("Posts retrieved correctly")
                Log.i("MainViewModel", "Posts retrieved correctly")

            }catch (e: Exception){
                Log.i("MainViewModel", e.toString())
                _uiState.value = UiState.Error("There was an error connecting to the database")
            }
        }
    }

    //Función a ejecutar para obtener los post recientes
    suspend fun getRecentList(){
        return withContext(Dispatchers.IO){
            var apiPostList = PostListResponse()
            try {
                //_uiState.value = UiState.Loading
                apiPostList = api.getRecent()
                _recentPosts.value = mutableListOf<PostDataModel>()
                for (post in apiPostList.posts){
                    _recentPosts.value.add(
                        PostDataModel(
                            id = post.id,
                            title = post.title,
                            image = post.image,
                            date = isoDateFormat(post.date),
                            price = post.price,
                            agencyId = post.agency.id,
                            agency = post.agency.name,
                            phone = post.agency.number,
                            description = post.description,
                            meeting = post.meeting,
                            itinerary = post.itinerary.map { it -> Itinerary(isoDateFormat(it.time, true), it.event) },
                            includes = post.includes,
                            category = post.category,
                            position = Position(post.lat, post.long)
                        )
                    )
                }
                //_uiState.value = UiState.Success("Posts retrieved correctly")
                Log.i("MainViewModel", "Posts retrieved correctly")

            }catch (e: Exception){
                Log.i("MainViewModel", e.toString())
                _uiState.value = UiState.Error("There was an error connecting to the database")
            }
        }
    }

    fun setStateToReady() {
        _uiState.value = UiState.Ready
    }

}