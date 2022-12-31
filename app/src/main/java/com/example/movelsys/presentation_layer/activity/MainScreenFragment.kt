package com.example.movelsys.presentation_layer

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movelsys.LoadingAnimation
import com.example.movelsys.data_layer.google_fit.GoogleFetchData
import com.example.movelsys.data_layer.google_fit.GoogleFetchDataImplementation
import com.example.movelsys.data_layer.google_fit.GoogleFitPermissions
import com.example.movelsys.data_layer.google_fit.Responses
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import com.example.movelsys.presentation_layer.activity.HistoryRowFragment
import com.example.movelsys.presentation_layer.activity.MainViewModel
import com.example.movelsys.presentation_layer.activity.TopBarFragment
import com.example.movelsys.presentation_layer.authentication.LoginViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun MainScreenFragment(
    viewModel: MainViewModel
) {
    TopBarFragment()
    viewModel.startGoogleFit(LocalContext.current, LocalContext.current as Activity)
    /*while(viewModel.googleFitHistory.isEmpty()){
        LoadingAnimation()
    }*/
    /*LazyColumn {
        //for (data in steps){
            item{
                HistoryRowFragment(datapoint = viewModel.googleFitHistory[0])
            }


    }*/
}


