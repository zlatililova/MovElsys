package com.example.movelsys.data_layer.profileManagement

interface ProfileUpdate {
    fun updateUserName(name: String, onUpdate: OnUpdate)
    fun updateUserProfilePicture(url: String, onUpdate: OnUpdate)
    fun updateUserEmail(email: String, onUpdate: OnUpdate)
    fun updateUserPassword(newPassword: String, onUpdate: OnUpdate)
}