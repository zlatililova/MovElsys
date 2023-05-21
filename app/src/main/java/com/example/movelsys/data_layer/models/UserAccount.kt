package com.example.movelsys.data_layer.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UserAccount {

    private var email: String by mutableStateOf("")
    private var emailError: String? by mutableStateOf("Enter your email")
    private var isEmailWrong: Boolean = false

    private var password: String by mutableStateOf("")
    private var passwordError: String? by mutableStateOf("Enter your password")
    private var isPasswordWrong: Boolean = false

    private var name: String by mutableStateOf("")
    private var nameError: String? by mutableStateOf("Enter your password")
    private var isNameWrong: Boolean = false

    private var profilePicture: String by mutableStateOf("")
    private var profilePictureError: String? by mutableStateOf("Enter your password")
    private var isProfilePictureWrong: Boolean = false


    private var confirmationPass: String by mutableStateOf("")
    private var confirmationPasswordError: String? by mutableStateOf("Enter your password")
    private var isConfirmationPasswordWrong: Boolean = false




    fun getUserEmail(): String {
        return this.email
    }
    fun getUserPassword(): String {
        return this.password
    }
    fun getUserName(): String {
        return this.name
    }
    fun getUserProfilePicture(): String {
        return this.profilePicture
    }
    fun getUserConfirmationPassword(): String {
        return this.confirmationPass
    }

    fun setUserEmail(email: String){
        this.email = email
    }
    fun setUserPassword(password: String){
        this.password = password
    }
    fun setUserName(name: String){
        this.name = name
    }
    fun setUserProfilePicture(profilePicture: String){
        this.profilePicture = profilePicture
    }
    fun setUserConfirmationPassword(confirmationPass: String){
        this.confirmationPass = confirmationPass
    }

    fun getUserEmailError(): String? {
        return this.emailError
    }
    fun getUserPasswordError(): String? {
        return this.passwordError
    }
    fun getUserNameError(): String? {
        return this.nameError
    }
    fun getUserProfilePictureError(): String? {
        return this.profilePictureError
    }
    fun getUserConfirmationPasswordError(): String? {
        return this.confirmationPasswordError
    }

    fun setUserEmailError(emailError: String?){
        this.emailError = emailError
    }
    fun setUserPasswordError(passwordError: String?){
        this.passwordError = passwordError
    }
    fun setUserNameError(nameError: String?){
        this.nameError = nameError
    }
    fun setUserProfilePictureError(profilePictureError: String?){
        this.profilePictureError = profilePictureError
    }
    fun setUserConfirmationPasswordError(confirmationPassError: String?){
        this.confirmationPasswordError = confirmationPassError
    }

    fun getIsEmailWrong(): Boolean {
        return this.isEmailWrong
    }
    fun getIsPasswordWrong(): Boolean {
        return this.isPasswordWrong
    }
    fun getIsNameWrong(): Boolean {
        return this.isNameWrong
    }
    fun getIsProfilePictureWrong(): Boolean {
        return this.isProfilePictureWrong
    }
    fun getIsConfirmationPasswordWrong(): Boolean {
        return this.isConfirmationPasswordWrong
    }

    fun setIsEmailWrong(isWrong: Boolean){
        this.isEmailWrong = isWrong
    }
    fun setIsPasswordWrong(isWrong: Boolean){
        this.isPasswordWrong = isWrong
    }
    fun setIsNameWrong(isWrong: Boolean){
        this.isNameWrong = isWrong
    }
    fun setIsProfilePictureWrong(isWrong: Boolean){
        this.isProfilePictureWrong = isWrong
    }
    fun setIsConfirmaitonPasswordWrong(isWrong: Boolean){
        this.isConfirmationPasswordWrong = isWrong
    }

}