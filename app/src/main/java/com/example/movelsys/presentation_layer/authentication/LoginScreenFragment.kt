package com.example.movelsys.presentation_layer.authentication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.movelsys.R
import com.example.movelsys.Screen
import com.example.movelsys.presentation_layer.states.LoginUIState
import kotlinx.coroutines.flow.collectLatest

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@Composable
fun LoginScreenFragment(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.padding(20.dp),
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.running_person),
                contentDescription = "MovElsys Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp)
            )
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = {
                    viewModel.email = it
                    viewModel.errorCheckEmail()
                    viewModel.enableButton()
                },
                label = { Text(text = viewModel.emailError) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    errorBorderColor = Color.Red
                ),
                isError = viewModel.emailErrorCheck,

                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 10.dp),
                placeholder = { Text("Enter your email") },

                )
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = {
                    viewModel.password = it
                    viewModel.errorCheckPassword()
                    viewModel.enableButton()
                },
                label = { Text(text = viewModel.passwordError) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    errorBorderColor = Color.Red
                ),
                isError = viewModel.passwordErrorCheck,
                leadingIcon = {
                    Icon(Icons.Default.Info, contentDescription = "Password")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 10.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedButton(
                onClick = {
                    viewModel.login()
                    viewModel.enableButton()

                },
                enabled = viewModel.areCredentialsRight,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, bottom = 50.dp)
            ) {
                Text(text = "Login", textAlign = TextAlign.Center, color = Color.White)
            }
            Text(
                text = "Don't have an account? Sign up here!",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier
                    .clickable { navController.navigate(Screen.Register.route) }
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(300.dp))
        }

    }
    val lifecycleOwner = LocalLifecycleOwner.current
    observeViewModel(lifecycleOwner, viewModel, context, navController)
}

private fun observeViewModel(
    lifecycleOwner: LifecycleOwner,
    viewModel: LoginViewModel,
    context: Context,
    navController: NavController
) {
    lifecycleOwner.lifecycleScope.launch {
        viewModel.uiStateFlow.collectLatest {
            viewModel.uiStateFlow.onEach {
                when (it) {
                    is LoginUIState.Success -> {
                        Toast.makeText(
                            context,
                            "Success",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(Screen.Main.route)
                    }
                    is LoginUIState.Error -> {
                        navController.navigate(Screen.Login.route)
                        Toast.makeText(
                            context,
                            "Error: " + it.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is LoginUIState.Loading -> {
                        navController.navigate(Screen.Load.route)
                    }
                    else -> {}
                }
            }.launchIn(this)
        }
    }
}


