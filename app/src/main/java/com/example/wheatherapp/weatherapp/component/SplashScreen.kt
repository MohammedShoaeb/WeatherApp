package com.example.wheatherapp.weatherapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wheatherapp.weatherapp.R
import com.example.wheatherapp.weatherapp.ui.theme.splashScreenBgFirst
import com.example.wheatherapp.weatherapp.ui.theme.splashScreenBgSecond
import com.example.wheatherapp.weatherapp.ui.theme.whiteColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize()

            .background(brush=Brush.verticalGradient(
                colors = listOf(splashScreenBgFirst, splashScreenBgSecond),
                startY = 0f, endY = Float.POSITIVE_INFINITY,
                tileMode = TileMode.Clamp
            )

            ),
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment =Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center ) {
            Image(painter = painterResource(R.drawable.logo), contentDescription = null)
            Text(text = stringResource(id = R.string.app_name),
                fontSize = 26.sp,
                color = whiteColor,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold)
        }
    }
    LaunchedEffect(Unit) {
        delay(1000L)
        navController.navigate("WeatherPage") {
            popUpTo("SplashScreen") { inclusive = true }
        }
    }
}