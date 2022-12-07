package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.AuthDataInt
import com.example.movelsys.data_layer.authentication.OnLogin

class LoginUseCase(private val authDataInt: AuthDataInt) {
    fun execute(email: String, pass: String,  onLogin: OnLogin
    ) {
        authDataInt.login(email, pass, onLogin)

    }
}