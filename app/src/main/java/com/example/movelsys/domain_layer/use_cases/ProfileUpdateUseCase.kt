package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.authentication.Errors
import com.example.movelsys.data_layer.profileManagement.ProfileUpdate

class ProfileUpdateUseCase(
    private val profileUpdate: ProfileUpdate
) {
    fun updateUserName(name: String) = profileUpdate.updateUserName(name)
    fun updateUserProfilePic(url: String) = profileUpdate.updateUserProfilePicture(url)
    fun updateUserEmail(email: String) = profileUpdate.updateUserEmail(email)
    fun updateUserPassword(password: String, confirmationPassword: String): Errors?{
        if(password == confirmationPassword){
            profileUpdate.updateUserPassword(password)
            return null
        }else{
            return Errors.PASSWORDS_NOT_MATCHING
        }
    }



}