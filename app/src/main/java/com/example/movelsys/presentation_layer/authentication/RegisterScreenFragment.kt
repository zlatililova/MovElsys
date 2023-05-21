package com.example.movelsys.presentation_layer.authentication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.movelsys.R
import com.example.movelsys.Screen
import com.example.movelsys.presentation_layer.states.RegisterUIState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun RegisterScreenFragment(
    navController: NavController,
    viewModel: RegisterViewModel
) {

    viewModel.setPlaceholders(email = stringResource(id = R.string.enter_email),
        password = stringResource(id = R.string.enter_password),
        confirmPass = stringResource(id = R.string.confirm_password),
        name = stringResource(id = R.string.enter_first_name),
        profilePic = stringResource(id = R.string.enter_profile_pic))

    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.padding(20.dp),
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.running_person_no_background),
                contentDescription = "MovElsys Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp)
            )
            OutlinedTextField(
                value = viewModel.userAccount.getUserName(),
                onValueChange = {
                    viewModel.userAccount.setUserName(it)
                    viewModel.errorCheckName()
                    viewModel.enableButton()
                },
                singleLine = true,
                label = { viewModel.userAccount.getUserNameError()?.let { Text(it) } },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    errorBorderColor = Color.Red
                ),
                isError = viewModel.userAccount.getIsNameWrong(),
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Person")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 10.dp),
                placeholder = { Text(stringResource(R.string.enter_first_name)) },
                )
            OutlinedTextField(
                value = viewModel.userAccount.getUserEmail(),
                onValueChange = {
                    viewModel.userAccount.setUserEmail(it)
                    viewModel.errorCheckEmail()
                    viewModel.enableButton()
                },
                singleLine = true,
                label = { viewModel.userAccount.getUserEmailError()?.let { Text(text = it) } },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    errorBorderColor = Color.Red
                ),
                isError = viewModel.userAccount.getIsEmailWrong(),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 10.dp),
                placeholder = { Text(stringResource(R.string.enter_email)) },

                )
            OutlinedTextField(
                value = viewModel.userAccount.getUserPassword(),
                onValueChange = {
                    viewModel.userAccount.setUserPassword(it)
                    viewModel.errorCheckPassword()
                    viewModel.enableButton()
                },
                singleLine = true,
                label = { viewModel.userAccount.getUserPasswordError()?.let { Text(it) } },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    errorBorderColor = Color.Red
                ),
                isError = viewModel.userAccount.getIsPasswordWrong(),
                leadingIcon = {
                    Icon(Icons.Default.Info, contentDescription = "Password")
                },
                placeholder = { Text(stringResource(R.string.enter_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 10.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(
                value = viewModel.userAccount.getUserConfirmationPassword(),
                onValueChange = {
                    viewModel.userAccount.setUserConfirmationPassword(it)
                    viewModel.errorCheckConfirmationPassword()
                    viewModel.enableButton()
                },
                singleLine = true,
                label = { viewModel.userAccount.getUserConfirmationPasswordError()?.let { Text(it) } },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    errorBorderColor = Color.Red
                ),
                isError = viewModel.userAccount.getIsConfirmationPasswordWrong(),
                leadingIcon = {
                    Icon(Icons.Default.Info, contentDescription = "Password")
                },
                placeholder = { Text(stringResource(R.string.confirm_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 10.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(
                value = viewModel.userAccount.getUserProfilePicture(),
                onValueChange = {
                    viewModel.userAccount.setUserProfilePicture(it)
                    viewModel.errorCheckProfilePicture()
                    viewModel.enableButton()
                },
                singleLine = true,
                label = { viewModel.userAccount.getUserProfilePictureError()?.let { Text(it) } },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    errorBorderColor = Color.Red
                ),
                isError = viewModel.userAccount.getIsProfilePictureWrong(),
                leadingIcon = {
                    Icon(Icons.Filled.Photo, contentDescription = "Profile_picture")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 10.dp),
                placeholder = { Text(stringResource(R.string.enter_profile_pic)) },
                )
            OutlinedButton(
                onClick = {
                    viewModel.register()
                },
                enabled = viewModel.areCredentialsRight,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Text(text = "Register", textAlign = TextAlign.Center, color = White)
            }
            Text(
                text = stringResource(R.string.sign_in_here),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.Login.route)
                    }
                    .fillMaxWidth()
                    .padding(bottom = 200.dp)
            )
            Spacer(modifier = Modifier.padding(100.dp))
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    observeViewModel(lifecycleOwner, viewModel, context, navController)
}

private fun observeViewModel(
    lifecycleOwner: LifecycleOwner,
    viewModel: RegisterViewModel,
    context: Context,
    navController: NavController
) {
    lifecycleOwner.lifecycleScope.launch {
        viewModel.uiStateFlow.collectLatest {
            viewModel.uiStateFlow.onEach {
                when (it) {
                    is RegisterUIState.Success -> {
                        Toast.makeText(
                            context,
                            "Success",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(Screen.Activity.route)
                    }
                    is RegisterUIState.Error -> {
                        navController.navigate(Screen.Register.route)
                        Toast.makeText(
                            context,
                            "Error: " + it.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is RegisterUIState.Loading -> {
                        navController.navigate(Screen.Load.route)
                    }
                    else -> {}
                }
            }.launchIn(this)
        }
    }
}