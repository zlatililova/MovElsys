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
        name: String,
        profilePictureURL: String,
        onRegister: OnRegister
    )
}