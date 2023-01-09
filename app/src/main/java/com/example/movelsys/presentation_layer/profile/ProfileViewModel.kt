package com.example.movelsys.presentation_layer.profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.authentication.Errors
import com.example.movelsys.data_layer.profileManagement.OnUpdate
import com.example.movelsys.domain_layer.use_cases.ProfileUpdateUseCase
import com.example.movelsys.domain_layer.use_cases.ValidateCredentials
import com.example.movelsys.presentation_layer.states.LoginUIState
import com.example.movelsys.presentation_layer.states.ProfileUIState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    val profileUpdateUseCase: ProfileUpdateUseCase,
    private val validateCredentials: ValidateCredentials
): ViewModel() {
    private val user = Firebase.auth.currentUser
    var name = mutableStateOf(user!!.displayName)
    var newName: String by mutableStateOf("")
    var profilePicture = mutableStateOf(user!!.photoUrl)
    var newEmail: String by mutableStateOf("")
    var email = mutableStateOf(user!!.email)
    var password: String by mutableStateOf("")
    var confirmationPass: String by mutableStateOf("")

    var isChangeMade = false

    private val _uiStateFlow = MutableStateFlow<ProfileUIState>(ProfileUIState.Initial)
    val uiStateFlow: StateFlow<ProfileUIState> = _uiStateFlow

    var nameError: String? by mutableStateOf("Update your name")
    var nameErrorCheck: Boolean = false
    fun errorCheckName(){
        val error: Errors? = validateCredentials.nameErrorCheck(newName)
        if (error != null) {
            nameError = error.Message
            nameErrorCheck = true
        } else {
            nameError = null
            nameErrorCheck = false
        }

    }

    var emailError: String? by mutableStateOf("Update your email")
    var emailErrorCheck: Boolean = false
    fun errorCheckEmail() {
        val error: Errors? = validateCredentials.emailErrorCheck(newEmail)
        if (error != null) {
            emailError = error.Message
            emailErrorCheck = true
        } else {
            emailError = null
            emailErrorCheck = false
        }
    }

    var passwordError: String? by mutableStateOf("Enter a valid password")
    var passwordErrorCheck: Boolean = false
    fun errorCheckPassword() {
        val error: Errors? = validateCredentials.passwordErrorCheck(password)
        if (error != null) {
            passwordError = error.Message
            passwordErrorCheck = true
        } else {
            passwordError = null
            passwordErrorCheck = false
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

    fun enableButton(code: String): Boolean {
        when(code){
            "email" -> return emailError == null
            "name" -> return nameError == null
            "password" -> return passwordError == null && confirmationPasswordError == null
        }
        return false
    }

    fun updateName() {
        profileUpdateUseCase.updateUserName(
            name = newName,
            onUpdate = object : OnUpdate {
                override fun onSuccess() {
                    viewModelScope.launch {
                        _uiStateFlow.emit(ProfileUIState.Success)
                    }
                }

                override fun onError(string: String?) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(ProfileUIState.Error(string))
                    }
                }
            })
       isChangeMade = true
    }

    fun updateEmail() {
        profileUpdateUseCase.updateUserEmail(
            email = newEmail,
            onUpdate = object : OnUpdate {
                override fun onSuccess() {
                    viewModelScope.launch {
                        _uiStateFlow.emit(ProfileUIState.Success)
                    }
                }

                override fun onError(string: String?) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(ProfileUIState.Error(string))
                    }
                }
            })
        isChangeMade = true

    }


    fun updatePassword() {
        profileUpdateUseCase.updateUserPassword(
            password = password,
            confirmationPassword = confirmationPass,
            onUpdate = object : OnUpdate {
                override fun onSuccess() {
                    viewModelScope.launch {
                        _uiStateFlow.emit(ProfileUIState.Success)
                    }
                }

                override fun onError(string: String?) {
                    viewModelScope.launch {
                        _uiStateFlow.emit(ProfileUIState.Error(string))
                    }
                }
            })
    }

    fun updateUI() {
        val user = Firebase.auth.currentUser
        Log.i(TAG, user!!.uid)
        name.value = user.displayName
        email.value = user.email
        profilePicture.value = user.photoUrl
        Log.i("Credentials: ", name.value!!)
        isChangeMade = false
    }

    fun signOut(){
        val auth = Firebase.auth
        auth.signOut()
    }

}