package com.example.movelsys.presentation_layer.profile

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movelsys.R
import com.example.movelsys.Screen
import com.example.movelsys.presentation_layer.activity_tracking.BottomBarFragment
import com.example.movelsys.presentation_layer.activity_tracking.TopBarFragment
import com.example.movelsys.presentation_layer.states.ProfileUIState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun ProfileScreenFragment(navController: NavController, viewModel: ProfileViewModel) {
    Column {
        TopBarFragment(navController, true)
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            item {
                viewModel.updateUI()
                val context = LocalContext.current
                val activity = context as Activity
                viewModel.getActivityAndContext(context = context, activity = activity)
                Text(
                    text = stringResource(R.string.Profile_main_text),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(20.dp)
                )
                Image(
                    painter = rememberAsyncImagePainter(viewModel.profilePicture.value),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "${viewModel.name.value}",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(30.dp)
                )
                OutlinedButton(
                    onClick = {
                        viewModel.signOut()
                        navController.navigate(Screen.Welcome.route)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    modifier = Modifier
                        .size(width = 150.dp, height = 100.dp)
                        .padding(bottom = 50.dp)
                ) {
                    Text(text = "Sign out", textAlign = TextAlign.Center, color = Color.White)
                }
                Divider(
                    color = MaterialTheme.colors.primary,
                    thickness = 2.dp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Text(
                    text = stringResource(R.string.Update_profile),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(20.dp, bottom = 20.dp)
                )
                Text(
                    text = "Current name: ${viewModel.name.value} ",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(20.dp, bottom = 10.dp)
                )
                OutlinedTextField(
                    value = viewModel.userAccount.getUserName(),
                    onValueChange = {
                        viewModel.userAccount.setUserName(it)
                        viewModel.errorCheckName()
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
                    placeholder = { Text(stringResource(R.string.update_name)) },
                )
                OutlinedButton(
                    onClick = {
                        viewModel.updateName()
                        if (viewModel.isChangeMade) {
                            viewModel.updateUI()
                        }
                    },
                    enabled = viewModel.enableButton("name"),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    modifier = Modifier
                        .size(width = 150.dp, height = 100.dp)
                        .padding(bottom = 50.dp)
                ) {
                    Text(text = "Update name", textAlign = TextAlign.Center, color = Color.White)
                }
                Text(
                    text = "Current email: ${viewModel.email.value} ",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(20.dp, bottom = 10.dp)
                )
                OutlinedTextField(
                    value = viewModel.userAccount.getUserEmail(),
                    onValueChange = {
                        viewModel.userAccount.setUserEmail(it)
                        viewModel.errorCheckEmail()
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
                    placeholder = { Text(stringResource(R.string.update_email)) },

                    )
                OutlinedButton(
                    onClick = {
                        viewModel.updateEmail()
                        if (viewModel.isChangeMade) {
                            viewModel.updateUI()
                        }
                    },
                    enabled = viewModel.enableButton("email"),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    modifier = Modifier
                        .size(width = 150.dp, height = 100.dp)
                        .padding(bottom = 50.dp)
                ) {
                    Text(text = "Update email", textAlign = TextAlign.Center, color = Color.White)
                }
                Text(
                    text = stringResource(R.string.update_profile_pic),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(20.dp, bottom = 10.dp)
                )
                OutlinedTextField(
                    value = viewModel.userAccount.getUserProfilePicture(),
                    onValueChange = {
                        viewModel.userAccount.setUserProfilePicture(it)
                        viewModel.errorCheckProfilePicture()
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
                    placeholder = { Text(stringResource(R.string.update_profile_pic)) },
                )
                OutlinedButton(
                    onClick = {
                        viewModel.updateProfilePicture()
                        if (viewModel.isChangeMade) {
                            viewModel.updateUI()
                        }
                    },
                    enabled = viewModel.enableButton("profile_picture"),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    modifier = Modifier
                        .size(width = 150.dp, height = 100.dp)
                        .padding(bottom = 50.dp)
                ) {
                    Text(
                        text = "Update profile Pic",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Text(
                    text = stringResource(R.string.update_password),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(20.dp, bottom = 10.dp)
                )
                OutlinedTextField(
                    value = viewModel.userAccount.getUserPassword(),
                    onValueChange = {
                        viewModel.userAccount.setUserPassword(it)
                        viewModel.errorCheckPassword()
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
                    placeholder = { Text(stringResource(R.string.update_password)) },
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
                OutlinedButton(
                    onClick = {
                        viewModel.updatePassword()
                    },
                    enabled = viewModel.enableButton("password"),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    modifier = Modifier
                        .size(width = 180.dp, height = 80.dp)
                        .padding(bottom = 30.dp)
                ) {
                    Text(
                        text = "Update password",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Text(
                    text = "Change your step goal",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(20.dp, bottom = 10.dp)
                )
                OutlinedTextField(
                    value = viewModel.newGoal,
                    onValueChange = {
                        if (it.isNotEmpty()) {
                            viewModel.newGoal = it
                        }
                    },
                    label = { Text("Current goal: ${viewModel.goalSteps}") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.primary,
                    ),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.EmojiPeople,
                            contentDescription = "Step Goal"
                        )
                    },
                    modifier = Modifier
                        .size(width = 300.dp, height = 80.dp)
                        .padding(bottom = 20.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedButton(
                    onClick = { viewModel.setNewGoalSteps() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    enabled = viewModel.enableButton("steps"),
                    modifier = Modifier
                        .size(width = 180.dp, height = 80.dp)
                        .padding(bottom = 30.dp)
                ) {
                    Text(
                        text = "Change step goal",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.padding(120.dp))
            }
        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }
    observeViewModel(
        lifecycleOwner = LocalLifecycleOwner.current,
        viewModel = viewModel,
        context = LocalContext.current
    )
}

private fun observeViewModel(
    lifecycleOwner: LifecycleOwner,
    viewModel: ProfileViewModel,
    context: Context,
) {
    lifecycleOwner.lifecycleScope.launch {
        viewModel.uiStateFlow.collectLatest {
            viewModel.uiStateFlow.onEach {
                when (it) {
                    is ProfileUIState.Success -> {
                        viewModel.updateUI()
                        Toast.makeText(
                            context,
                            "Successfully updated",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is ProfileUIState.Error -> {
                        Toast.makeText(
                            context,
                            "Error: " + it.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {}
                }
            }.launchIn(this)
        }
    }
}
