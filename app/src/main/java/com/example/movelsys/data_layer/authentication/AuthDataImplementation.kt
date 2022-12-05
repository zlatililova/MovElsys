package com.example.movelsys.data_layer.authentication

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthDataImplementation: AuthDataInt{
    override fun login(
        email: String, pass: String, onLogin: AuthDataInt.OnLogin
    ) {

        runBlocking { launch {
            onLogin.onSuccess()
            //onLogin.onError("This user is not in the database")
        } }

    }

    override fun register(
        email: String,
        pass: String,
        fname: String,
        lname: String,
        confpass: String,
        onRegister: AuthDataInt.OnRegister

    ) {

        runBlocking { launch {
            onRegister.onSuccess()
            //onRegister.onError("Cannot create the user!")
        } }
    }

}