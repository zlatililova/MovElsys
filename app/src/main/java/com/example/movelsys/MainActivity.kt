package com.example.movelsys

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movelsys.data_layer.google_fit.GoogleFitPermissions
import com.example.movelsys.ui.theme.MovelsysTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovelsysTheme {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    GoogleFitPermissions(appContext = this, activity = this).detectIfPermissionIsGiven()
                }
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}

