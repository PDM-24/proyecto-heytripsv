package com.coderunners.heytripsv

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.coderunners.heytripsv.data.remote.api.ApiClient
import com.coderunners.heytripsv.data.remote.model.ChangePassBody
import com.coderunners.heytripsv.data.remote.model.CompareCodeBody
import com.coderunners.heytripsv.data.remote.model.ItineraryApi
import com.coderunners.heytripsv.data.remote.model.LogInBody
import com.coderunners.heytripsv.data.remote.model.PostListResponse
import com.coderunners.heytripsv.data.remote.model.ReportApiModel
import com.coderunners.heytripsv.data.remote.model.ReportedAgency
import com.coderunners.heytripsv.data.remote.model.SendCodeBody
import com.coderunners.heytripsv.model.AgencyDataModel
import com.coderunners.heytripsv.model.EmailAccount
import com.coderunners.heytripsv.model.Itinerary
import com.coderunners.heytripsv.model.LogInData
import com.coderunners.heytripsv.model.Position
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.model.PostList
import com.coderunners.heytripsv.repository.DataStore
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
    val uiState: StateFlow<UiState> = _uiState
    private val _stateSaved = MutableStateFlow<UiState>(UiState.Ready)
    val stateSaved: StateFlow<UiState> = _stateSaved

    //VARIABLE DATASTORE
    private val viewModelContext = getApplication<Application>()
        .applicationContext
    val datastore = DataStore(viewModelContext)

    //VARIABLES API Y ROOM
    private val api = ApiClient.apiService

    //FLOWS
    private val _notifications = MutableStateFlow(mutableListOf<Int>())
    val notifications = _notifications.asStateFlow()

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

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _savedIDs = MutableStateFlow(mutableListOf(""))
    val savedIDs = _savedIDs.asStateFlow()


    private val _savedPostList = MutableStateFlow(mutableListOf<PostDataModel>())
    val savedPostList = _savedPostList.asStateFlow()

    private val _upcomingPosts = MutableStateFlow(mutableListOf<PostDataModel>())
    val upcomingPosts = _upcomingPosts.asStateFlow()

    private val _recentPosts = MutableStateFlow(mutableListOf<PostDataModel>())
    val recentPosts = _recentPosts.asStateFlow()

    //Flows para obtener el token y el rol en la vista
    private val _userToken = MutableStateFlow("")
    val userToken = _userToken.asStateFlow()

    //Roles: agency, admin, user
    private val _userRole = MutableStateFlow("")
    val userRole = _userRole.asStateFlow()

    // Variable para el rol de administrador
    private val _isAdmin = MutableStateFlow(false)
    val isAdmin = _isAdmin.asStateFlow()

    //Variable para obtener post reportados
    private val _reportedPosts = MutableStateFlow<List<ReportApiModel>>(emptyList())
    val reportedPosts: StateFlow<List<ReportApiModel>> = _reportedPosts.asStateFlow()
    private val _reportedPostsState = MutableStateFlow<UiState>(UiState.Ready)

    //Variable para obtener agencias reportadas
    private val _reportedAgencies = MutableStateFlow<List<ReportedAgency>>(emptyList())
    val reportedAgencies: StateFlow<List<ReportedAgency>> = _reportedAgencies.asStateFlow()
    private val _reportedAgenciesState = MutableStateFlow<UiState>(UiState.Ready)

    //Función para parsear el formato que devuelve la API a dd/MM/yyyy o HH:mm
    private fun isoDateFormat(dateToFormat: String, time: Boolean = false): String {
        try {
            val dateStr = dateToFormat.removeSuffix("Z")
            val dateTime = LocalDateTime.parse(dateStr)

            val formatter = DateTimeFormatter.ofPattern(if (!time) "dd/MM/yyyy" else "hh:mm a")
            return dateTime.format(formatter)
        } catch (e: Exception) {
            Log.i("MainViewModel", e.toString())
            return if (time) "HH-mm a" else "dd/MM/yyyy"
        }
    }

    fun saveSelectedPost(selectPost: PostDataModel) {
        _selectedPost.value = selectPost
    }

    fun setAgencyId(id: String) {
        _agencyId.value = id
    }

    fun saveSelectedCategory(category: String) {
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

    fun setOwnAgency(agency: AgencyDataModel) {
        _ownAgency.value = agency
    }

    //Función que setea las listas de posts de la pantalla de inicio
    fun getHomePostList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = UiState.Loading
                getUpcomingList()
                getRecentList()
                _uiState.value = UiState.Success("Posts retrieved correctly")
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error retrieving posts")
            }
        }
    }

    //Función a ejecutar para obtener los post próximos
    suspend fun getUpcomingList() {
        return withContext(Dispatchers.IO) {
            var apiPostList = PostListResponse()
            try {
                _uiState.value = UiState.Loading
                apiPostList = api.getUpcoming()
                _upcomingPosts.value = mutableListOf<PostDataModel>()
                for (post in apiPostList.posts) {
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
                            itinerary = post.itinerary.map { it ->
                                Itinerary(
                                    isoDateFormat(
                                        it.time,
                                        true
                                    ), it.event
                                )
                            }
                                .toMutableList(),
                            includes = post.includes,
                            category = post.category,
                            position = Position(post.lat, post.long)
                        )
                    )
                }
                //_uiState.value = UiState.Success("Posts retrieved correctly")
                Log.i("MainViewModel", "Posts retrieved correctly")

            } catch (e: Exception) {
                Log.i("MainViewModel", e.toString())
                _uiState.value = UiState.Error("There was an error connecting to the database")
            }
        }
    }

    //Función a ejecutar para obtener los post recientes
    suspend fun getRecentList() {
        return withContext(Dispatchers.IO) {
            var apiPostList = PostListResponse()
            try {
                //_uiState.value = UiState.Loading
                apiPostList = api.getRecent()
                _recentPosts.value = mutableListOf<PostDataModel>()
                for (post in apiPostList.posts) {
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
                            itinerary = post.itinerary.map { it ->
                                Itinerary(
                                    isoDateFormat(
                                        it.time,
                                        true
                                    ), it.event
                                )
                            }
                                .toMutableList(),
                            includes = post.includes,
                            category = post.category,
                            position = Position(post.lat, post.long)
                        )
                    )
                }
                //_uiState.value = UiState.Success("Posts retrieved correctly")
                Log.i("MainViewModel", "Posts retrieved correctly")

            } catch (e: Exception) {
                Log.i("MainViewModel", e.toString())
                _uiState.value = UiState.Error("There was an error connecting to the database")
            }
        }
    }

    //Función para obtener la información de la agencia
    fun getAgencyData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
                    postList = obtainedAgency.posts.map { post ->
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
                            itinerary = post.itinerary.map { it ->
                                Itinerary(
                                    isoDateFormat(
                                        it.time,
                                        true
                                    ), it.event
                                )
                            }
                                .toMutableList(),
                            includes = post.includes,
                            category = post.category,
                            position = Position(post.lat, post.long))
                    }.toMutableList()
                )
                Log.i("MainVewModel", "Agency retrieved correctly")
                _uiState.value = UiState.Success("Agency retrieved correctly")
            } catch (e: Exception) {
                Log.i("MainVewModel", e.toString())
                _uiState.value = UiState.Error("There was an error retrieving the agency data")
            }

        }
    }

    //Función para obtener los posts guardados de un usuario usando el token
    fun getSavedPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                datastore.getToken().collect() { token ->
                    _stateSaved.value = UiState.Loading
                    val apiList =
                        api.getSaved("Bearer $token")
                    _savedPostList.value = mutableListOf()
                    for (post in apiList.posts) {
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
                                itinerary = post.itinerary.map { it ->
                                    Itinerary(
                                        isoDateFormat(
                                            it.time,
                                            true
                                        ), it.event
                                    )
                                }
                                    .toMutableList(),
                                includes = post.includes,
                                category = post.category,
                                position = Position(post.lat, post.long)
                            )
                        )
                    }
                    Log.i("MainVewModel", "Posts retrieved correctly")
                    _stateSaved.value = UiState.Success("Posts retrieved correctly")
                }
            } catch (e: Exception) {
                Log.i("MainVewModel", e.toString())
                _stateSaved.value = UiState.Error("Error getting the saved posts")
            }
        }
    }

    fun addPost(
        context: Context,
        postDataModel: PostDataModel,
        image: Uri? = Uri.parse("https://res.cloudinary.com/dlmtei8cc/image/upload/v1718430757/zjyr4khxybczk6hjibw9.jpg")
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                datastore.getToken().collect() { token ->
                    _uiState.value = UiState.Loading
                    val response = api.addPost(
                        authHeader = "Bearer $token",
                        title = createPartFromString(postDataModel.title),
                        description = createPartFromString(postDataModel.description),
                        date = createPartFromString(postDataModel.date),
                        meeting = createPartFromString(postDataModel.meeting),
                        category = createPartFromString(postDataModel.category),
                        lat = createPartFromString(postDataModel.position.lat.toString()),
                        long = createPartFromString(postDataModel.position.long.toString()),
                        price = createPartFromString(postDataModel.price.toString()),
                        includes = createPartFromList("includes", postDataModel.includes),
                        image = image?.let {
                            createFilePart(
                                "image",
                                it,
                                contentResolver = context.contentResolver
                            )
                        },
                        itinerary = createItineraryParts(postDataModel.itinerary.map { it ->
                            ItineraryApi(
                                it.time,
                                it.event
                            )
                        })
                    )

                    _uiState.value = UiState.Success(response.result)
                }
            } catch (e: Exception) {
                Log.i("ViewModel", e.toString())
                _uiState.value = UiState.Error("Error saving the post")
            }
        }
    }

    //Funcion para el LogIn
    fun LogIn(logInData: LogInData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = UiState.Loading
                val response = api.logIn(
                    LogInBody(
                        email = logInData.email,
                        password = logInData.password
                    )
                )
                datastore.saveRole(response.role)
                datastore.saveToken(response.token)
                _userRole.value = response.role
                _userToken.value = response.token
                datastore.resetNotif()
                _notifications.value = mutableListOf()

                if (response.role == "user") {
                    _savedIDs.value = response.saved
                }

                _uiState.value = UiState.Success("Logged in correctly")
            } catch (e: Exception) {
                Log.i("ViewModel", e.toString())
                _uiState.value = UiState.Error("Error Logging in")
            }
        }
    }

    fun SendCode(email: EmailAccount) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = UiState.Loading
                val response = api.sendCode(
                    SendCodeBody(
                        email = email.email
                    )
                )
                Log.i("MainViewModel", response.toString())
                _uiState.value = UiState.Success("Succesful")
                _email.value = email.email
            } catch (e: Exception) {
                Log.i("ViewModel", e.toString())
                _uiState.value = UiState.Error("Code Not Sent")
            }
        }
    }

    fun compareCode(code : String){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _uiState.value = UiState.Loading
                val response = api.compareCode(
                    CompareCodeBody(
                        email = _email.value,
                        code = code
                    )
                )
                Log.i("MainViewModel", response.toString())
                _uiState.value = UiState.Success("Code verified")
            }catch(e : Exception){
                Log.i("ViewModel", e.toString())
                _uiState.value = UiState.Error("Code couldn't be compared")
            }
        }
    }

    fun changePassword(password : String){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _uiState.value = UiState.Loading
                Log.d("ViewModel",_email.value)
                val response = api.changePassword(
                    ChangePassBody(
                        email = _email.value,
                        password = password
                    )
                )
                Log.i("MainViewModel", response.toString())
                _uiState.value = UiState.Success("Password Changed Succesfully")
            }catch(e : Exception){
                Log.i("ViewModel", e.toString())
                Log.d("ViewModel",_email.value)
                _uiState.value = UiState.Error("Password couldn't be changed")
            }
        }
    }

    fun reportContent(id: String, content: String, post: Boolean = true){
        viewModelScope.launch(Dispatchers.IO){
            try {
                datastore.getToken().collect(){
                        token->
                    _uiState.value = UiState.Loading
                    val authHeader = "Bearer $token"
                    val response = if (post){
                        api.reportPost(authHeader, id, ReportApiModel(content))
                    }else{
                        api.reportAgency(authHeader, id, ReportApiModel(content))
                    }
                    _uiState.value = UiState.Success(response.result)
                }
            }catch (e: Exception){
                Log.i("ViewModel", e.toString())
                _uiState.value = UiState.Error("Error reporting the post")
            }
        }
    }

    //Función para cerrar sesión (Resetea el DataStore)
    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = UiState.Loading
                datastore.saveRole("")
                datastore.saveToken("")
                datastore.resetNotif()
                _notifications.value = mutableListOf()
                _userToken.value = ""
                _userRole.value = ""
                _uiState.value = UiState.Success("Logged out correctly")
            } catch (e: Exception) {
                Log.i("ViewModel", e.toString())
                _uiState.value = UiState.Error("Error logging out")
            }
        }
    }

    //Función para guardar los posts
    fun savePost(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                datastore.getToken().collect() { token ->
                    _uiState.value = UiState.Loading
                    val authHeader = "Bearer $token"
                    val response = api.savePost(authHeader, id)
                    _savedIDs.value = response.saved
                    _uiState.value = UiState.Success("Post saved")
                }
            } catch (e: Exception) {
                Log.i("ViewModel", e.toString())
                _uiState.value = UiState.Error("Error reporting the post")
            }
        }
    }

    //Función para obtener los ids de notificaciones
    fun getNotifs(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                datastore.getNotifs().collect() { notifs ->
                    _stateSaved.value = UiState.Loading
                    Log.i("Notifs", notifs.toString())
                    _notifications.value = notifs as MutableList<Int>
                    Log.i("Notifs", _notifications.value.toString())
                    _stateSaved.value = UiState.Success("")
                }
            }catch (e: Exception){
                Log.i("MainViewModel", e.toString())
                _stateSaved.value = UiState.Error("")
            }
        }
    }

    fun saveNotif(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {

                    _stateSaved.value = UiState.Loading
                    datastore.addNotif(id)
                    _stateSaved.value = UiState.Success("")
            }catch (e: Exception){
                Log.i("MainViewModel", e.toString())
                _stateSaved.value = UiState.Error("")
            }
        }
    }

    fun removeNotif(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {

                _stateSaved.value = UiState.Loading
                datastore.removeNotif(id)
                _notifications.value.remove(id.toInt())
                _stateSaved.value = UiState.Success("")
            }catch (e: Exception){
                Log.i("MainViewModel", e.toString())
                _stateSaved.value = UiState.Error("")
            }
        }
    }

    fun setSavedStateToReady(){
        _stateSaved.value = UiState.Ready
    }
    fun setStateToReady() {
        _uiState.value = UiState.Ready
    }

    //Funcion para ordenar de forma alfabetica la lista de post reportados
    fun sortReportedPostsAlphabetically() {
        val sortedList = _reportedPosts.value.sortedBy { it.title }
        _reportedPosts.value = sortedList
    }


    //Funcion para eliminar post
    fun deletePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = UiState.Loading
                val response = api.deletePost("Bearer " + datastore.getToken(), postId)
                // Actualizar la lista de posts eliminando el post con el postId
                val updatedPosts = _upcomingPosts.value.filter { it.id != postId }
                _upcomingPosts.value = updatedPosts.toMutableList()
                _uiState.value = UiState.Success("El post ha sido eliminado exitosamente!")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error eliminando post: $e")
                _uiState.value = UiState.Error("Error eliminando post")
            }
        }
    }

    //Funcion para obtener post reportados
    fun getReportedPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _reportedPostsState.value = UiState.Loading
                datastore.getToken().collect { token ->
                    val authHeader = "Bearer $token"
                    val response = api.getReportedPosts(authHeader)

                    _reportedPosts.value = response.reportedPosts ?: emptyList()
                    _reportedPostsState.value = UiState.Success("Posts reportados obtenidos correctamente")
                }
            } catch (e: Exception) {
                Log.i("Mainviewmodel", e.toString())
                _reportedPostsState.value = UiState.Error("Error al obtener posts reportados: ${e.message ?: "Error desconocido"}")
            }
        }
    }

    //Funcion eliminar post de la lista de reportes
    fun deleteReportedPost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Eliminar el post del backend
                val response = api.deleteReportedPost("Bearer " + datastore.getToken(), postId)

                // Eliminar el post de la lista de posts reportados
                val updatedReportedPosts = _reportedPosts.value.filter { it.id != postId }
                _reportedPosts.value = updatedReportedPosts

                // Eliminar el post de la lista de posts
                val updatedPosts = _upcomingPosts.value.filter { it.id != postId }
                _upcomingPosts.value = updatedPosts.toMutableList()

                _uiState.value = UiState.Success("El post ha sido eliminado exitosamente!")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error eliminando post reportado: $e")
                _uiState.value = UiState.Error("Error eliminando post reportado")
            }
        }
    }

    //Funcion para obtener agencias reportadas
    fun getReportedAgency() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _reportedAgenciesState.value = UiState.Loading
                datastore.getToken().collect { token ->
                    val authHeader = "Bearer $token"
                    val response = api.getReportedAgencies(authHeader)

                    _reportedAgencies.value = response.reportedAgencies ?: emptyList()
                    _reportedAgenciesState.value = UiState.Success("Agencias reportadas obtenidas correctamente")
                }
            } catch (e: Exception) {
                _reportedAgenciesState.value = UiState.Error("Error al obtener agencias reportadas: ${e.message ?: "Error desconocido"}")
            }
        }
    }

    //Funcion para eliminar agencias reportadas
    fun deleteReportedAgency(agencyId: String) {
        viewModelScope.launch {
            try {
                val token = datastore.getToken()
                val authHeader = "Bearer $token"
                api.deleteReportedAgency(authHeader, agencyId)

                // Eliminar la agencia reportada de la lista de agencias reportadas

                val updatedReportedAgencies = _reportedAgencies.value.map { reportedAgency ->
                    val updatedAgencyList = reportedAgency.agency.filter { agency ->
                        agency.id != agencyId
                    }
                    ReportedAgency(updatedAgencyList.toMutableList(), reportedAgency.report)
                }

                _reportedAgencies.value = updatedReportedAgencies

                _uiState.value = UiState.Success("Agencia reportada eliminada correctamente")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error eliminando agencia reportada: $e")
                _uiState.value = UiState.Error("Error eliminando la agencia reportada")
            }
        }
    }

}

