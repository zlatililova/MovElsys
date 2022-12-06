package com.example.movelsys.data_layer.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthDataImplementation: AuthDataInt{

    private lateinit var auth: FirebaseAuth

    override fun login(
        email: String, pass: String, onLogin: AuthDataInt.OnLogin
    ) {
        auth = Firebase.auth
        /*runBlocking { launch {
            onLogin.onSuccess()
            //onLogin.onError("This user is not in the database")
        } }*/
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
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
        onRegister: AuthDataInt.OnRegister

    ) {
        /*
        runBlocking { launch {
            onRegister.onSuccess()
            //onRegister.onError("Cannot create the user!")
        } }*/
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    onRegister.onSuccess()
                } else {
                    // If sign in fails, display a message to the user.
                    onRegister.onError(task.exception.toString());
                    //onRegister.onError("Firebase: Cannot create the user!")
                }
            }
    }

}