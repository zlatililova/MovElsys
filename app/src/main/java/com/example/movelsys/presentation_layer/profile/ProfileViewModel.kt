package com.example.movelsys.presentation_layer.profile

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movelsys.data_layer.authentication.Errors
import com.example.movelsys.data_layer.models.UserAccount
import com.example.movelsys.data_layer.profileManagement.OnUpdate
import com.example.movelsys.domain_layer.use_cases.ProfileUpdateUseCase
import com.example.movelsys.domain_layer.use_cases.ValidateCredentials
import com.example.movelsys.presentation_layer.states.ProfileUIState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProfileViewModel(
    val profileUpdateUseCase: ProfileUpdateUseCase,
    private val validateCredentials: ValidateCredentials
) : ViewModel() {
    private val user = Firebase.auth.currentUser
    var name = mutableStateOf(user?.displayName)
    var profilePicture = mutableStateOf(user?.photoUrl)
    var email = mutableStateOf(user?.email)
    var goalSteps by mutableStateOf(0)
    var newGoal by mutableStateOf("")
    var isChangeMade = false

    var userAccount = UserAccount()

    private val _uiStateFlow = MutableStateFlow<ProfileUIState>(ProfileUIState.Initial)
    val uiStateFlow: StateFlow<ProfileUIState> = _uiStateFlow

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

    fun enableButton(code: String): Boolean {
        when (code) {
            "email" -> return userAccount.getUserEmailError() == null
            "name" -> return userAccount.getUserNameError() == null
            "password" -> return userAccount.getUserPasswordError() == null && userAccount.getUserConfirmationPasswordError() == null
            "profile_picture" -> return userAccount.getUserProfilePictureError() == null
            "steps" -> return newGoal.isNotEmpty()
        }
        return false
    }

    fun updateName() {
        profileUpdateUseCase.updateUserName(
            name = userAccount.getUserName(),
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
            email = userAccount.getUserEmail(),
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
            password = userAccount.getUserPassword(),
            confirmationPassword = userAccount.getUserConfirmationPassword(),
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

    fun updateProfilePicture() {
        profileUpdateUseCase.updateUserProfilePic(
            url = userAccount.getUserProfilePicture(),
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

    fun updateUI() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            name.value = user.displayName
            email.value = user.email
            profilePicture.value = user.photoUrl
        }
        isChangeMade = false
    }

    private lateinit var context: Context
    private lateinit var activity: Activity
    fun getActivityAndContext(context: Context, activity: Activity) {
        this.context = context
        this.activity = activity
        fetchLastSavedSteps()
    }

    fun signOut() {
        val auth = Firebase.auth
        auth.signOut()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClient.signOut()
    }

    fun setNewGoalSteps() {
        if (newGoal != "") {
            if (newGoal.toInt() != 0) {
                goalSteps = newGoal.toInt()
                val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()) {
                    putInt("newGoalSteps", goalSteps)
                    apply()
                }

            }
        }
    }

    private fun fetchLastSavedSteps() {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val defaultValue = 0
        if (sharedPref != null) {
            goalSteps = sharedPref.getInt("newGoalSteps", defaultValue)
        }
        Log.i("Steps", goalSteps.toString())
    }
}