package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.AuthDataInt

class LoginUseCase(val authDataInt: AuthDataInt) {
    fun execute(email: String, pass: String,  onLogin: AuthDataInt.OnLogin
    ) {
        authDataInt.login(email, pass, onLogin)

    }
}