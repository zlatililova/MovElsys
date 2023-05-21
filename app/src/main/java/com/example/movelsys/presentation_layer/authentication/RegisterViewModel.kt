package com.example.movelsys.presentation_layer.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.authentication.Errors
import com.example.movelsys.data_layer.authentication.OnRegister
import com.example.movelsys.data_layer.models.UserAccount
import com.example.movelsys.domain_layer.use_cases.*
import com.example.movelsys.presentation_layer.states.RegisterUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    val validateCredentials: ValidateCredentials,
    val registerUseCase: RegisterUseCase
) : ViewModel() {

    var userAccount = UserAccount()
    private val _uiStateFlow = MutableStateFlow<RegisterUIState>(RegisterUIState.Initial)
    val uiStateFlow: StateFlow<RegisterUIState> = _uiStateFlow

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

    fun errorCheckName() {
        val error: Errors? = validateCredentials.nameErrorCheck(userAccount.getUserName())
        if (error != null) {
            userAccount.setUserNameError(error.Message)
            userAccount.setIsNameWrong(true)
        } else {
            userAccount.setUserNameError(null)
            userAccount.setIsNameWrong(false)
        }
    }

    fun errorCheckProfilePicture() {
        val error: Errors? = validateCredentials.profilePictureErrorCheck(userAccount.getUserProfilePicture())
        if (error != null) {
            userAccount.setUserProfilePictureError(error.Message)
            userAccount.setIsProfilePictureWrong(true)
        } else {
            userAccount.setUserProfilePictureError(null)
            userAccount.setIsProfilePictureWrong(false)
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

    fun errorCheckConfirmationPassword() {
        val error: Errors? = validateCredentials.confirmationPasswordErrorCheck(
            password = userAccount.getUserPassword(),
            confirmPassword = userAccount.getUserConfirmationPassword()
        )
        if (error != null) {
            userAccount.setUserConfirmationPassword(error.Message)
            userAccount.setIsConfirmaitonPasswordWrong(true)
        } else {
            userAccount.setUserConfirmationPasswordError(null)
            userAccount.setIsConfirmaitonPasswordWrong(false)
        }
    }

    var areCredentialsRight: Boolean = false
    fun enableButton() {
        areCredentialsRight = userAccount.getUserEmailError() == null
                    && userAccount.getUserNameError() == null
                    && userAccount.getUserPasswordError() == null
                    && userAccount.getUserConfirmationPasswordError() == null
    }

    fun register() {
        viewModelScope.launch {
            _uiStateFlow.emit(RegisterUIState.Loading)
        }
        registerUseCase.startBusinessLogic(
            email = userAccount.getUserEmail(),
            password = userAccount.getUserPassword(),
            name = userAccount.getUserName(),
            profilePictureURL = userAccount.getUserProfilePicture(),
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

    fun setPlaceholders(
        email: String,
        password: String,
        confirmPass: String,
        name: String,
        profilePic: String
    ) {
        userAccount.setPlaceholders(email, password, confirmPass, name, profilePic)
    }
}