package com.example.movelsys.data_layer.authentication

interface AuthData {
    fun login(
        email: String,
        password: String,
        onLogin: OnLogin
    )

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        confirmationPassword: String,
        onRegister: OnRegister
    )
}