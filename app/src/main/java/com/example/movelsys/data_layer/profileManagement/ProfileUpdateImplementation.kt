package com.example.movelsys.data_layer.profileManagement

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class ProfileUpdateImplementation() : ProfileUpdate{

    private val user = Firebase.auth.currentUser

    override fun updateUserName(name: String, onUpdate: OnUpdate){
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                    onUpdate.onSuccess()
                }
                else{
                    onUpdate.onError(task.exception.toString())
                }
            }
    }

    override fun updateUserProfilePicture(url: String, onUpdate: OnUpdate){
        val profileUpdates = userProfileChangeRequest {
            photoUri = Uri.parse(url)
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                    onUpdate.onSuccess()
                }
                else{
                    onUpdate.onError(task.exception.toString())
                }
            }
    }

    override fun updateUserEmail(email: String, onUpdate: OnUpdate){
        user!!.updateEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                    onUpdate.onSuccess()
                }
                else{
                    onUpdate.onError(task.exception.toString())
                }
            }
    }

    override fun updateUserPassword(newPassword: String, onUpdate: OnUpdate){
        user!!.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                    onUpdate.onSuccess()
                }
                else{
                    onUpdate.onError(task.exception.toString())
                }
            }
    }
}