package com.example.movelsys.presentation_layer.authentication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
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
    Column(
        modifier = Modifier.padding(20.dp),
    ) {
        Text(
            text = "Login today!",
            fontFamily = FontFamily.Default,
            textAlign = TextAlign.Center,
            fontSize = 48.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Enter your email") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.primary
            ),
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, top = 10.dp)

        )
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Enter your password") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.primary
            ),
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
                var error = viewModel.checkValues()
                if(error.isNotEmpty()){
                    Toast.makeText(
                        context,
                        error,
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    viewModel.login()
                }
            },
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
                .clickable {
                navController.navigate(Screen.Register.route)
            }
                .fillMaxWidth()
        )


    }



    val lifecycleOwner = LocalLifecycleOwner.current
    observeViewModel(lifecycleOwner, viewModel, context)
}

private fun observeViewModel(lifecycleOwner: LifecycleOwner, viewModel: LoginViewModel, context: Context) {


    lifecycleOwner.lifecycleScope.launch {
        viewModel.uiStateFlow.collectLatest{
            viewModel.uiStateFlow.onEach {
                when (it) {
                    is LoginUIState.Success -> {
                        Toast.makeText(
                            context,
                            "Success",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                    is LoginUIState.Error -> {
                        Toast.makeText(
                            context,
                            "Error: " + it.error,
                            Toast.LENGTH_LONG
                        ).show()

                    }
                    is LoginUIState.Loading -> {


                    }
                    else ->{}
                }
            }.launchIn(this)
        }

    }
}
