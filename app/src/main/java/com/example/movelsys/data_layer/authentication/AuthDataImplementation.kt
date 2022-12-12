package com.example.movelsys.data_layer.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthDataImplementation : AuthData {

    private lateinit var auth: FirebaseAuth

    override fun login(
        email: String, pass: String, onLogin: OnLogin
    ) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, pass)
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
        pass: String,
        fname: String,
        lname: String,
        confpass: String,
        onRegister: OnRegister

    ) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onRegister.onSuccess()
                } else {
                    onRegister.onError(task.exception.toString())
                }
            }
    }

}