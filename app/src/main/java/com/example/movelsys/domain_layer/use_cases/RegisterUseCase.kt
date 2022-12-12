package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.AuthData
import com.example.movelsys.data_layer.authentication.OnRegister

class RegisterUseCase(private val authData: AuthData) {
    fun StartBusinessLogic(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        confirmationPassword: String,
        onRegister: OnRegister
    ) {
        authData.register(email, password, firstName, lastName, confirmationPassword, onRegister)
    }
}