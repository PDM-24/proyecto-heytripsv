package com.coderunners.heytripsv.utils

sealed class UiState {
    object Idle : UiState()
    data object Loading : UiState()
    data object Ready : UiState()
    data class Success(val msg: String) : UiState()
    data class Error(val msg: String) : UiState()
}
