package com.example.wheatherapp.weatherapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import com.example.wheatherapp.weatherapp.ui.theme.containerColor2
import com.example.wheatherapp.weatherapp.ui.theme.splashScreenBgFirst

@Composable
fun DefaultBackgroundTheme(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(splashScreenBgFirst, containerColor2),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY,
                    tileMode = TileMode.Clamp
                )
            )
    ) {
        content()
    }
}