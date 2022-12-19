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
    var firstName: String by mutableStateOf("")
    var lastName: String by mutableStateOf("")
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

    var firstNameError: String? by mutableStateOf("Enter your first name")
    var isFirstNameWrong: Boolean = false
    fun errorCheckFirstName() {
        val error: Errors? = validateCredentials.nameErrorCheck(firstName)
        if (error != null) {
            firstNameError = error.Message
            isFirstNameWrong = true
        } else {
            firstNameError = null
            isFirstNameWrong = false
        }
    }

    var lastNameError: String? by mutableStateOf("Enter your last name")
    var isLastNameWrong: Boolean = false
    fun errorCheckLastName() {
        val error: Errors? = validateCredentials.nameErrorCheck(lastName)
        if (error != null) {
            lastNameError = error.Message
            isLastNameWrong = true
        } else {
            lastNameError = null
            isLastNameWrong = false
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

    var confirmationPasswordError: String? by mutableStateOf("Enter your password again")
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
            emailError == null && firstNameError == null && lastNameError == null && passwordError == null && confirmationPasswordError == null
    }

    fun register() {
        viewModelScope.launch {
            _uiStateFlow.emit(RegisterUIState.Loading)
        }
        registerUseCase.startBusinessLogic(
            email = email,
            password = password,
            firstName = firstName,
            lastName = lastName,
            confirmationPassword = confirmationPass,
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