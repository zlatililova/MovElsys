package com.example.movelsys.presentation_layer.states

sealed class LoginUIState {
    object Initial: LoginUIState()
    object Success: LoginUIState()
    object Loading: LoginUIState()
    class Error(val error: String?): LoginUIState()
}