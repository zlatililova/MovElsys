package com.example.movelsys.presentation_layer.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.authentication.AuthDataInt
import com.example.movelsys.domain_layer.use_cases.*
import com.example.movelsys.presentation_layer.states.LoginUIState
import com.example.movelsys.presentation_layer.states.RegisterUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validationEmail: ValidateEmail,
    private val validationPassword: ValidatePassword,
    private val loginUseCase: LoginUseCase
): ViewModel() {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")


    private val _uiStateFlow = MutableStateFlow<LoginUIState>(LoginUIState.Initial)
    val uiStateFlow : StateFlow<LoginUIState> = _uiStateFlow

    fun checkValues(): String{
        var errors = ""
        if(errors.isEmpty()){
            errors += validationEmail.execute(email).errors
        }
        if(errors.isEmpty()){
            errors += validationPassword.execute(password).errors
        }
        return errors
    }

    fun login() {
        viewModelScope.launch {
            _uiStateFlow.emit(LoginUIState.Loading)
        }

        loginUseCase.execute(
            email = email,
            pass = password,
            onLogin = object : AuthDataInt.OnLogin {
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