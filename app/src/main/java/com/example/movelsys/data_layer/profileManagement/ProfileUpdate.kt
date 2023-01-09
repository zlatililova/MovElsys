package com.example.movelsys.data_layer.profileManagement

interface ProfileUpdate {
    fun updateUserName(name: String)
    fun updateUserProfilePicture(url: String)
    fun updateUserEmail(email: String)
    fun updateUserPassword(newPassword: String)
}