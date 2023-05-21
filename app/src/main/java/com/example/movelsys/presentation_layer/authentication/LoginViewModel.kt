package com.example.movelsys.presentation_layer.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.authentication.Errors
import com.example.movelsys.data_layer.authentication.OnLogin
import com.example.movelsys.data_layer.models.UserAccount
import com.example.movelsys.domain_layer.use_cases.*
import com.example.movelsys.presentation_layer.states.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateCredentials: ValidateCredentials,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    var userAccount = UserAccount()

    private val _uiStateFlow = MutableStateFlow<LoginUIState>(LoginUIState.Initial)
    val uiStateFlow: StateFlow<LoginUIState> = _uiStateFlow

    fun errorCheckEmail() {
        val error: Errors? = validateCredentials.emailErrorCheck(userAccount.getUserEmail())
        if (error != null) {
            userAccount.setUserEmailError(error.Message)
            userAccount.setIsEmailWrong(true)
        } else {
            userAccount.setUserEmailError(null)
            userAccount.setIsEmailWrong(false)
        }
    }

    fun errorCheckPassword() {
        val error: Errors? = validateCredentials.passwordErrorCheck(userAccount.getUserPassword())
        if (error != null) {
            userAccount.setUserPasswordError(error.Message)
            userAccount.setIsPasswordWrong(true)
        } else {
            userAccount.setUserPasswordError(null)
            userAccount.setIsPasswordWrong(false)
        }
    }

    var areCredentialsRight: Boolean = false
    fun enableButton() {
        areCredentialsRight =
            userAccount.getUserPasswordError() == null && userAccount.getUserEmailError() == null
    }

    fun login() {
        viewModelScope.launch {
            _uiStateFlow.emit(LoginUIState.Loading)
        }
        loginUseCase.startBusinessLogic(
            email = userAccount.getUserEmail(),
            password = userAccount.getUserPassword(),
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

    fun setPlaceholders(
        email: String,
        password: String,
    ) {
        userAccount.setPlaceholders(email, password, null, null, null)
    }
}