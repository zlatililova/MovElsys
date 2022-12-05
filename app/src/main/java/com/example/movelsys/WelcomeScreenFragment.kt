package com.example.movelsys

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movelsys.data_layer.authentication.AuthDataImplementation
import com.example.movelsys.domain_layer.use_cases.*


@Composable
fun WelcomeScreenFragment(
    navController: NavController,
    ) {
    Column(
        modifier = Modifier.padding(20.dp),
    ) {

        Image(
            painter = painterResource(id = R.drawable.movelsys_logo),
            contentDescription = "MovElsys Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .size(450.dp)


        )

        OutlinedButton(
            onClick = {
                navController.navigate(Screen.Login.route)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)
        ) {
            Text(text = "Login", textAlign = TextAlign.Center, color = Color.White, fontSize = 20.sp)
        }
        OutlinedButton(
            onClick = {
                navController.navigate(Screen.Register.route)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)
        ) {
            Text(text = "Register", textAlign = TextAlign.Center, color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeScreenFragmentPreview() {
    RegisterScreenFragment(navController = rememberNavController(), viewModel = RegisterViewModel(validationName = ValidateName(), validationEmail = ValidateEmail(), validationPassword = ValidatePassword(), validationConfirmPassword = ValidateConfPass(), registerUseCase = RegisterUseCase(
        AuthDataImplementation()
    )
    ))
}