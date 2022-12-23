package com.example.movelsys.presentation_layer

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movelsys.MainActivity
import com.example.movelsys.data_layer.google_fit.GoogleFitFetchData
import com.example.movelsys.data_layer.google_fit.GoogleFitPermissions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun MainScreenFragment() {
    val context = LocalContext.current
    val activity  = context as Activity


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        GoogleFitPermissions(appContext = context, activity = activity).detectIfPermissionIsGiven()
    }


    val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()
    val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)
    Log.i(ContentValues.TAG, GoogleSignIn.hasPermissions(account, fitnessOptions).toString())


    Handler().postDelayed({
        GoogleFitFetchData(context = context, activity = activity).subscribeToStepsListener()
        GoogleFitFetchData(context = context, activity = activity).listActiveSubscriptions()
    }, 3000)




    val user = Firebase.auth.currentUser
    var text = ""
    if (user != null) {
        text = user.uid
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            fontSize = 20.sp
        )
    }
}
