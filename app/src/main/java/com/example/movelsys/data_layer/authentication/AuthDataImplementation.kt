package com.example.movelsys.data_layer.authentication

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
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
                    val user = Firebase.auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = "Test User"
                        photoUri = Uri.parse("https://www.google.com/url?sa=i&url=https%3A%2F%2Fpfpmaker.com%2F&psig=AOvVaw0WE4nb_DSEG3X0-CldB6rL&ust=1673353256898000&source=images&cd=vfe&ved=0CA8QjRxqFwoTCMjdluW8uvwCFQAAAAAdAAAAABAE")
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task1 ->
                            if (task1.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                            }
                        }
                    onRegister.onSuccess()
                } else {
                    onRegister.onError(task.exception.toString())
                }
            }
    }

}