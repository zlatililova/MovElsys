package com.example.movelsys.data_layer.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthDataImplementation : AuthData {

    private lateinit var auth: FirebaseAuth

    override fun login(
        email: String, password: String, onLogin: OnLogin
    ) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onLogin.onSuccess()
                } else {
                    onLogin.onError(Errors.INVALID_CREDENTIALS.Message)
                }
            }
    }

    override fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        confirmationPassword: String,
        onRegister: OnRegister
    ) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onRegister.onSuccess()
                } else {
                    onRegister.onError(task.exception.toString())
                }
            }
    }

}