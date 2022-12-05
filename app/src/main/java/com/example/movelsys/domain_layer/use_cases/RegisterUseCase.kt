package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.AuthDataInt

class RegisterUseCase(val authDataInt: AuthDataInt) {

    fun execute(email: String, pass: String, fname: String, lname: String, confpass: String, onRegister: AuthDataInt.OnRegister){
        authDataInt.register(email, pass, fname, lname, confpass, onRegister)
    }
}