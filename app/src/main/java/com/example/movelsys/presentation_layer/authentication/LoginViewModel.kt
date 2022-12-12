package com.example.movelsys.presentation_layer.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.authentication.Errors
import com.example.movelsys.data_layer.authentication.OnLogin
import com.example.movelsys.domain_layer.use_cases.*
import com.example.movelsys.presentation_layer.states.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateCredentials: ValidateCredentials,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var emailError: String by mutableStateOf("Enter a valid email")
    var emailErrorCheck: Boolean = false
    var passwordError: String by mutableStateOf("Enter a valid password")
    var passwordErrorCheck: Boolean = false
    private val _uiStateFlow = MutableStateFlow<LoginUIState>(LoginUIState.Initial)
    val uiStateFlow: StateFlow<LoginUIState> = _uiStateFlow

    fun errorCheckEmail() {
        val error: Errors? = validateCredentials.IsEmailValid(email)
        if (error != null) {
            emailError = error.Message
            emailErrorCheck = true
        } else {
            emailError = Errors.VALID.Message
            emailErrorCheck = false
        }
    }

    fun errorCheckPassword() {
        val error: Errors? = validateCredentials.IsPasswordValid(password)
        if (error != null) {
            passwordError = error.Message
            passwordErrorCheck = true
        } else {
            passwordError = Errors.VALID.Message
            passwordErrorCheck = false
        }
    }

    var areCredentialsRight: Boolean = false
    fun enableButton() {
        areCredentialsRight =
            passwordError == Errors.VALID.Message && emailError == Errors.VALID.Message
    }

    fun login() {
        viewModelScope.launch {
            _uiStateFlow.emit(LoginUIState.Loading)
        }
        loginUseCase.StartBusinessLogic(
            email = email,
            password = password,
            onLogin = object : OnLogin {
                override fun onSuccess() {
                    viewModelScope.launch {
                        _uiStateFlow.emit(LoginUIState.Success)
                    }
                }
                override fun onError(string: String?) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(LoginUIState.Error(string))
                    }
                }
            })
    }
}