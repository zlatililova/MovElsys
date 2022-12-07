package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.AuthDataInt
import com.example.movelsys.data_layer.authentication.OnRegister

class RegisterUseCase(private val authDataInt: AuthDataInt) {

    fun execute(email: String, pass: String, fname: String, lname: String, confpass: String, onRegister: OnRegister){
        authDataInt.register(email, pass, fname, lname, confpass, onRegister)
    }
}