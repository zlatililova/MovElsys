package com.example.movelsys.presentation_layer.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movelsys.domain_layer.use_cases.ProfileUpdateUseCase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileViewModel(
    val profileUpdateUseCase: ProfileUpdateUseCase
): ViewModel() {
    private val user = Firebase.auth.currentUser
    val name = user!!.displayName
    val profilePicture = user!!.photoUrl
    val email = user!!.email
    var nameError: String? by mutableStateOf("Update your name")





}