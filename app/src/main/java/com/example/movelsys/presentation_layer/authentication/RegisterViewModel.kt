package com.example.movelsys.presentation_layer.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.authentication.AuthDataInt
import com.example.movelsys.data_layer.authentication.OnRegister
import com.example.movelsys.domain_layer.use_cases.*
import com.example.movelsys.presentation_layer.states.RegisterUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    val validationEmail: ValidateEmail,
    val validationPassword: ValidatePassword,
    val validationName: ValidateName,
    val validationConfirmPassword: ValidateConfPass,
    val registerUseCase: RegisterUseCase
): ViewModel() {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var fname: String by mutableStateOf("")
    var lname: String by mutableStateOf("")
    var confpass: String by mutableStateOf("")


    private val _uiStateFlow = MutableStateFlow<RegisterUIState>(RegisterUIState.Initial)
    val uiStateFlow : StateFlow<RegisterUIState> = _uiStateFlow

    fun errorCheck(): String{
        var errors = ""
        errors += validationName.execute(fname).errors
        if(errors.isEmpty()){
            errors += validationName.execute(lname).errors
        }
        if(errors.isEmpty()){
            errors += validationEmail.execute(email).errors
        }
        if(errors.isEmpty()){
            errors += validationPassword.execute(password).errors
        }
        if(errors.isEmpty()){
            errors += validationConfirmPassword.execute(password = password, confirmPassword = confpass).errors
        }
        return errors
    }

    fun register(){
        viewModelScope.launch {
            _uiStateFlow.emit(RegisterUIState.Loading)
        }

        registerUseCase.execute(email = email, pass = password, fname = fname, lname = lname, confpass = confpass, onRegister = object : OnRegister {
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