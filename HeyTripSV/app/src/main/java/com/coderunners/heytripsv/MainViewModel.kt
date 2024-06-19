package com.coderunners.heytripsv

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.coderunners.heytripsv.data.remote.api.ApiClient
import com.coderunners.heytripsv.data.remote.model.ItineraryApi
import com.coderunners.heytripsv.data.remote.model.PostListResponse
import com.coderunners.heytripsv.model.AgencyDataModel
import com.coderunners.heytripsv.model.Itinerary
import com.coderunners.heytripsv.model.Position
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.model.PostList
import com.coderunners.heytripsv.model.agencyData
import com.coderunners.heytripsv.utils.UiState
import com.coderunners.heytripsv.utils.createFilePart
import com.coderunners.heytripsv.utils.createItineraryParts
import com.coderunners.heytripsv.utils.createPartFromList
import com.coderunners.heytripsv.utils.createPartFromString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //ESTADO DE APP (LOADING)
    private val _uiState = MutableStateFlow<UiState>(UiState.Ready)
    val uiState : StateFlow<UiState> = _uiState

    //VARIABLES API Y ROOM
    private val api = ApiClient.apiService

    //FLOWS
    private val _selectedPost = MutableStateFlow(PostDataModel())
    val selectedPost = _selectedPost.asStateFlow()

    private val _agencyId = MutableStateFlow("")
    val agencyId = _agencyId.asStateFlow()

    private val _categoryList = MutableStateFlow(mutableListOf<PostDataModel>())
    val categoryList = _categoryList.asStateFlow()

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _selectedAgency = MutableStateFlow(AgencyDataModel())
    val selectedAgency = _selectedAgency.asStateFlow()

    private val _ownAgency = MutableStateFlow(AgencyDataModel())
    val ownAgency = _ownAgency.asStateFlow()

    private val _savedPostList = MutableStateFlow(mutableListOf<PostDataModel>())
    val savedPostList = _savedPostList.asStateFlow()

    private val _upcomingPosts = MutableStateFlow(mutableListOf<PostDataModel>())
    val upcomingPosts = _upcomingPosts.asStateFlow()

    private val _recentPosts = MutableStateFlow(mutableListOf<PostDataModel>())
    val recentPosts = _recentPosts.asStateFlow()

    //Función para parsear el formato que devuelve la API a dd/MM/yyyy o HH:mm
    private fun isoDateFormat(dateToFormat: String, time: Boolean = false): String{
        try {
            val dateStr = dateToFormat.removeSuffix("Z")
            val dateTime = LocalDateTime.parse(dateStr)

            val formatter = DateTimeFormatter.ofPattern(if (!time) "dd/MM/yyyy" else "hh:mm a")
            return dateTime.format(formatter)
        }catch(e: Exception){
            Log.i("MainViewModel", e.toString())
            return if (time) "HH-mm a" else "dd/MM/yyyy"
        }
    }
    fun saveSelectedPost(selectPost: PostDataModel) {
        _selectedPost.value = selectPost
    }

    fun setAgencyId(id: String){
        _agencyId.value = id
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
                            itinerary = post.itinerary.map { it -> Itinerary(isoDateFormat(it.time, true), it.event) }
                                .toMutableList(),
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
                            itinerary = post.itinerary.map { it -> Itinerary(isoDateFormat(it.time, true), it.event) }
                                .toMutableList(),
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

    //Función para obtener la información de la agencia
    fun getAgencyData(id: String){
        viewModelScope.launch (Dispatchers.IO){
            try {
                _uiState.value = UiState.Loading
                val obtainedAgency = api.getAgency(id)
                _selectedAgency.value = AgencyDataModel(
                    id = obtainedAgency.agency.id,
                    name = obtainedAgency.agency.name,
                    desc = obtainedAgency.agency.description,
                    email = obtainedAgency.agency.email,
                    dui = obtainedAgency.agency.dui,
                    image = obtainedAgency.agency.image,
                    number = obtainedAgency.agency.number,
                    instagram = obtainedAgency.agency.instagram,
                    facebook = obtainedAgency.agency.facebook,
                    postList = obtainedAgency.posts.map { post->
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
                            itinerary = post.itinerary.map { it -> Itinerary(isoDateFormat(it.time, true), it.event) }
                                .toMutableList(),
                            includes = post.includes,
                            category = post.category,
                            position = Position(post.lat, post.long))
                    }.toMutableList()
                )
                Log.i("MainVewModel", "Agency retrieved correctly")
                _uiState.value = UiState.Success("Agency retrieved correctly")
            }catch (e: Exception){
                Log.i("MainVewModel", e.toString())
                _uiState.value = UiState.Error(e.toString())
            }

        }
    }

    //Función para obtener los posts guardados de un usuario usando el token
    fun getSavedPosts(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _uiState.value = UiState.Loading
                //TODO: obtener el token del datastore (login)
                val apiList = api.getSaved("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2NjZkZjg2YzdhOWMyN2NmM2M4ODBkMjMiLCJleHAiOjE3MTk4MDE5MTYsImlhdCI6MTcxODUwNTkxNn0.Lb45PXk_UXFoFPDVcA-yROLNr4Ljm-7plqkqIFbAiLA")
                _savedPostList.value = mutableListOf()
                for (post in apiList.posts){
                    _savedPostList.value.add(
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
                            itinerary = post.itinerary.map { it -> Itinerary(isoDateFormat(it.time, true), it.event) }
                                .toMutableList(),
                            includes = post.includes,
                            category = post.category,
                            position = Position(post.lat, post.long)
                        )
                    )
                }
                Log.i("MainVewModel", "Posts retrieved correctly")
                _uiState.value = UiState.Success("Posts retrieved correctly")
            }catch (e: Exception){
                Log.i("MainVewModel", e.toString())
                _uiState.value = UiState.Error(e.toString())
            }
        }
    }

    fun addPost(
        context: Context,
        postDataModel: PostDataModel,
        image: Uri? = Uri.parse("https://res.cloudinary.com/dlmtei8cc/image/upload/v1718430757/zjyr4khxybczk6hjibw9.jpg")){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _uiState.value = UiState.Loading
                val response = api.addPost(
                    authHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2NjZlMDM5OGNjYWYxYTFlNGEwMGIzZTAiLCJleHAiOjE3MTk3OTg5OTMsImlhdCI6MTcxODUwMjk5M30.MVkhyzGELqiQIhrIQoWJCVgXzXJKVvZVj30yYaCmwt0",
                    title = createPartFromString(postDataModel.title),
                    description = createPartFromString(postDataModel.description),
                    date = createPartFromString(postDataModel.date),
                    meeting = createPartFromString(postDataModel.meeting),
                    category = createPartFromString(postDataModel.category),
                    lat = createPartFromString(postDataModel.position.lat.toString()),
                    long = createPartFromString(postDataModel.position.long.toString()),
                    price = createPartFromString(postDataModel.price.toString()),
                    includes = createPartFromList("includes",postDataModel.includes),
                    image= image?.let { createFilePart("image", it, contentResolver = context.contentResolver) },
                    itinerary = createItineraryParts(postDataModel.itinerary.map{ it -> ItineraryApi(it.time, it.event)})
                )

                _uiState.value = UiState.Success(response.toString())
            }catch (e: Exception){
                Log.i("ViewModel", e.toString())
                _uiState.value = UiState.Error("Error saving the post")
            }
        }
    }

    fun setStateToReady() {
        _uiState.value = UiState.Ready
    }

}