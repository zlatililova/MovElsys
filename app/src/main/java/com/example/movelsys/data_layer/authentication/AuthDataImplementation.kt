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
        name: String,
        profilePictureURL: String,
        onRegister: OnRegister
    ) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                        photoUri = if(profilePictureURL == ""){
                            Uri.parse("https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg")
                        }else{
                            Uri.parse(profilePictureURL)
                        }
                    }
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task1 ->
                            if (task1.isSuccessful) {
                                Log.d(TAG, "User profile created.")
                            }
                        }
                    onRegister.onSuccess()
                } else {
                    onRegister.onError(task.exception.toString())
                }
            }
    }

}