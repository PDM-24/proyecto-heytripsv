package com.coderunners.heytripsv

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.model.PostList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class MainViewModel: ViewModel() {
    private val _selectedPost = MutableStateFlow(PostDataModel())
    val selectedPost = _selectedPost.asStateFlow()

    private val _categoryList = MutableStateFlow(mutableListOf<PostDataModel>())
    val categoryList = _categoryList.asStateFlow()

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory = _selectedCategory.asStateFlow()

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



}