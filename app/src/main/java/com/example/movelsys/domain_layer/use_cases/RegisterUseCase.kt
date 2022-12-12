package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.AuthData
import com.example.movelsys.data_layer.authentication.OnRegister

class RegisterUseCase(private val authData: AuthData) {
    fun StartBusinessLogic(
        email: String,
        pass: String,
        fname: String,
        lname: String,
        confpass: String,
        onRegister: OnRegister
    ) {
        authData.register(email, pass, fname, lname, confpass, onRegister)
    }
}