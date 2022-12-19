package com.example.movelsys.presentation_layer.states

sealed class RegisterUIState{
    object Initial: RegisterUIState()
    object Success: RegisterUIState()
    object Loading: RegisterUIState()
    class Error(val error: String?): RegisterUIState()
}

