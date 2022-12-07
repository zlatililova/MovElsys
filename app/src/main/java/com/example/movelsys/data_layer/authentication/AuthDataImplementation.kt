package com.example.movelsys.data_layer.authentication

import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthDataImplementation: AuthDataInt{

    private lateinit var auth: FirebaseAuth

    override fun login(
        email: String, pass: String, onLogin: OnLogin
    ) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    onLogin.onSuccess()
                } else {
                    // If sign in fails, display a message to the user.
                    onLogin.onError("This user is not in the database")
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
                    // Sign in success, update UI with the signed-in user's information
                    onRegister.onSuccess()
                } else {
                    // If sign in fails, display a message to the user.
                    println(task.exception.toString())
                    onRegister.onError(task.exception.toString())
                    //onRegister.onError("Firebase: Cannot create the user!")
                }
            }
    }

}