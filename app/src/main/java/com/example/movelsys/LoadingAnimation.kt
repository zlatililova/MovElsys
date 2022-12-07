package com.example.movelsys

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(){
    val circleSize = 25.dp
    val circleColor = MaterialTheme.colors.primary
    val spaceBetween = 10.dp
    val travelDistance = 20.dp

    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
    )

    circles.forEachIndexed{ index, animatable ->
        LaunchedEffect(key1 = animatable){
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing

                    },
                    repeatMode = RepeatMode.Restart

                )
            )
        }
    }

    val circlesValue = circles.map { it.value }
    val distance = with(LocalDensity.current){ travelDistance.toPx()}
    val lastCircle = circlesValue.size - 1

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Row(

        ) {
            circlesValue.forEachIndexed { index, value ->
                Box(
                    modifier = Modifier
                        .size(circleSize)
                        .graphicsLayer {
                            translationY = -value * distance
                        }
                        .background(
                            color = circleColor,
                            shape = CircleShape
                        )
                )

                if(index != lastCircle){
                    Spacer(modifier = Modifier.width(spaceBetween))
                }
            }
        }
    }
    }




