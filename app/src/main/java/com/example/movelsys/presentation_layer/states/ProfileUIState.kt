package com.example.movelsys.presentation_layer.states

sealed class ProfileUIState{
    object Initial: ProfileUIState()
    object Success: ProfileUIState()
    class Error(val error: String?): ProfileUIState()
}
