package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.Errors
import com.example.movelsys.data_layer.profileManagement.OnUpdate
import com.example.movelsys.data_layer.profileManagement.ProfileUpdate

class ProfileUpdateUseCase(
    private val profileUpdate: ProfileUpdate
) {
    fun updateUserName(name: String, onUpdate: OnUpdate) = profileUpdate.updateUserName(name, onUpdate)
    fun updateUserProfilePic(url: String, onUpdate: OnUpdate) = profileUpdate.updateUserProfilePicture(url, onUpdate)
    fun updateUserEmail(email: String, onUpdate: OnUpdate) = profileUpdate.updateUserEmail(email, onUpdate)
    fun updateUserPassword(password: String, confirmationPassword: String, onUpdate: OnUpdate): Errors?{
        if(password == confirmationPassword){
            profileUpdate.updateUserPassword(password, onUpdate)
            return null
        }else{
            return Errors.PASSWORDS_NOT_MATCHING
        }
    }



}