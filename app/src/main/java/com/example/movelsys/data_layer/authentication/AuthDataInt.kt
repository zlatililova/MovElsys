package com.example.movelsys.data_layer.authentication

interface AuthDataInt {
    fun login(email: String, pass: String,  onLogin: OnLogin)
    fun register(email: String, pass: String, fname: String, lname: String, confpass: String, onRegister: OnRegister)
}