package com.example.movelsys.data_layer.authentication

interface AuthData {
    fun login(email: String, pass: String,  onLogin: OnLogin)
    fun register(email: String, pass: String, fname: String, lname: String, confpass: String, onRegister: OnRegister)
}