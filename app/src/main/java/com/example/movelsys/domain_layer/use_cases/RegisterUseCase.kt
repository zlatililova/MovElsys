package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.AuthData
import com.example.movelsys.data_layer.authentication.OnRegister

class RegisterUseCase(private val authData: AuthData) {
    fun startBusinessLogic(
        email: String,
        password: String,
        name: String,
        profilePictureURL: String,
        onRegister: OnRegister
    ) {
        authData.register(email = email, password = password, name = name, profilePictureURL = profilePictureURL, onRegister = onRegister)
    }
}