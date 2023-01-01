package com.example.movelsys.presentation_layer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(
    isDisplayed: Boolean
){
    if(isDisplayed){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
    }
}