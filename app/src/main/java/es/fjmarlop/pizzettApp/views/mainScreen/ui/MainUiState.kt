package es.fjmarlop.pizzettApp.views.mainScreen.ui

sealed interface MainUiState {
    object Loading : MainUiState
    data class Error(val throwable: Throwable) : MainUiState
    data class Success(val email: String) : MainUiState
}