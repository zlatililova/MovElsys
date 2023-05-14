package com.example.movelsys.presentation_layer.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.authentication.Errors
import com.example.movelsys.data_layer.authentication.OnRegister
import com.example.movelsys.domain_layer.use_cases.*
import com.example.movelsys.presentation_layer.states.RegisterUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    val validateCredentials: ValidateCredentials,
    val registerUseCase: RegisterUseCase
) : ViewModel() {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var name: String by mutableStateOf("")
    var profilePicture: String by mutableStateOf("")
    var confirmationPass: String by mutableStateOf("")
    private val _uiStateFlow = MutableStateFlow<RegisterUIState>(RegisterUIState.Initial)
    val uiStateFlow: StateFlow<RegisterUIState> = _uiStateFlow

    var emailError: String? by mutableStateOf("Enter your email")
    var isEmailWrong: Boolean = false
    fun errorCheckEmail() {
        val error: Errors? = validateCredentials.emailErrorCheck(email)
        if (error != null) {
            emailError = error.Message
            isEmailWrong = true
        } else {
            emailError = null
            isEmailWrong = false
        }
    }

    var nameError: String? by mutableStateOf("Enter your first name")
    var isNameWrong: Boolean = false
    fun errorCheckFirstName() {
        val error: Errors? = validateCredentials.nameErrorCheck(name)
        if (error != null) {
            nameError = error.Message
            isNameWrong = true
        } else {
            nameError = null
            isNameWrong = false
        }
    }

    var profilePictureError: String? by mutableStateOf("Enter profile picture URL")
    var isProfilePictureURLWrong: Boolean = false
    fun errorCheckLastName() {
        val error: Errors? = validateCredentials.nameErrorCheck(profilePicture)
        if (error != null) {
            profilePictureError = error.Message
            isProfilePictureURLWrong = true
        } else {
            profilePictureError = null
            isProfilePictureURLWrong = false
        }
    }

    var passwordError: String? by mutableStateOf("Enter your password")
    var isPasswordWrong: Boolean = false
    fun errorCheckPassword() {
        val error: Errors? = validateCredentials.passwordErrorCheck(password)
        if (error != null) {
            passwordError = error.Message
            isPasswordWrong = true
        } else {
            passwordError = null
            isPasswordWrong = false
        }
    }

    var confirmationPasswordError: String? by mutableStateOf("Confirm your password")
    var isConfirmationPasswordWrong: Boolean = false
    fun errorCheckConfirmationPassword() {
        val error: Errors? = validateCredentials.confirmationPasswordErrorCheck(
            password = password,
            confirmPassword = confirmationPass
        )
        if (error != null) {
            confirmationPasswordError = error.Message
            isConfirmationPasswordWrong = true
        } else {
            confirmationPasswordError = null
            isConfirmationPasswordWrong = false
        }
    }

    var areCredentialsRight: Boolean = false
    fun enableButton() {
        areCredentialsRight =
            emailError == null && nameError == null && passwordError == null && confirmationPasswordError == null
    }

    fun register() {
        viewModelScope.launch {
            _uiStateFlow.emit(RegisterUIState.Loading)
        }
        registerUseCase.startBusinessLogic(
            email = email,
            password = password,
            name = name,
            profilePictureURL = profilePicture,
            onRegister = object : OnRegister {
                override fun onSuccess() {
                    viewModelScope.launch {
                        _uiStateFlow.emit(RegisterUIState.Success)
                    }
                }

                override fun onError(string: String?) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(RegisterUIState.Error(string))
                    }
                }
            })
    }
}