package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.AuthData
import com.example.movelsys.data_layer.authentication.OnLogin

class LoginUseCase(private val authData: AuthData) {
    fun StartBusinessLogic(
        email: String, pass: String, onLogin: OnLogin
    ) {
        authData.login(email, pass, onLogin)
    }
}