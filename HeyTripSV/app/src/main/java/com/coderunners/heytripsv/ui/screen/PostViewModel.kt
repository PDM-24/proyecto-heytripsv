package com.coderunners.heytripsv.ui.screen

import androidx.lifecycle.ViewModel
import com.coderunners.heytripsv.model.PostDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostViewModel: ViewModel() {
    private val _selectedPost = MutableStateFlow(PostDataModel())
    val selectedPost = _selectedPost.asStateFlow()

    fun saveSelectedPost(selectPost: PostDataModel) {
        _selectedPost.value = selectPost
    }
}