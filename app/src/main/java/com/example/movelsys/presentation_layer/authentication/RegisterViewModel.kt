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

    var emailError: String by mutableStateOf("Enter your email")
    var isEmailWrong: Boolean = false
    fun errorCheckEmail() {
        val error: Errors? = validateCredentials.IsEmailValid(email)
        if (error != null) {
            emailError = error.Message
            isEmailWrong = true
        } else {
            emailError = Errors.VALID.Message
            isEmailWrong = false
        }
    }

    var firstNameError: String by mutableStateOf("Enter your first name")
    var isFirstNameWrong: Boolean = false
    fun errorCheckFirstName() {
        val error: Errors? = validateCredentials.IsNameValid(firstName)
        if (error != null) {
            firstNameError = error.Message
            isFirstNameWrong = true
        } else {
            firstNameError = Errors.VALID.Message
            isFirstNameWrong = false
        }
    }

    var lastNameError: String by mutableStateOf("Enter your last name")
    var isLastNameWrong: Boolean = false
    fun errorCheckLastName() {
        val error: Errors? = validateCredentials.IsNameValid(lastName)
        if (error != null) {
            lastNameError = error.Message
            isLastNameWrong = true
        } else {
            lastNameError = Errors.VALID.Message
            isLastNameWrong = false
        }
    }

    var passwordError: String by mutableStateOf("Enter your password")
    var isPasswordWrong: Boolean = false
    fun errorCheckPassword() {
        val error: Errors? = validateCredentials.IsPasswordValid(password)
        if (error != null) {
            passwordError = error.Message
            isPasswordWrong = true
        } else {
            passwordError = Errors.VALID.Message
            isPasswordWrong = false
        }
    }

    var confirmationPasswordError: String by mutableStateOf("Enter your password again")
    var isConfirmationPasswordWrong: Boolean = false
    fun errorCheckConfirmationPassword() {
        val error: Errors? = validateCredentials.IsConfirmationPasswordValid(
            password = password,
            confirmPassword = confirmationPass
        )
        if (error != null) {
            confirmationPasswordError = error.Message
            isConfirmationPasswordWrong = true
        } else {
            confirmationPasswordError = Errors.VALID.Message
            isConfirmationPasswordWrong = false
        }
    }

    var areCredentialsRight: Boolean = false
    fun enableButton() {
        areCredentialsRight =
            emailError == Errors.VALID.Message && firstNameError == Errors.VALID.Message && lastNameError == Errors.VALID.Message && passwordError == Errors.VALID.Message && confirmationPasswordError == Errors.VALID.Message
    }

    fun register() {
        viewModelScope.launch {
            _uiStateFlow.emit(RegisterUIState.Loading)
        }
        registerUseCase.StartBusinessLogic(
            email = email,
            pass = password,
            fname = firstName,
            lname = lastName,
            confpass = confirmationPass,
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