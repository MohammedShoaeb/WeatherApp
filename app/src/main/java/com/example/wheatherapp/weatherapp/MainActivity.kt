package com.example.wheatherapp.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wheatherapp.weatherapp.component.DefaultBackgroundTheme
import com.example.wheatherapp.weatherapp.component.SplashScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
 installSplashScreen()
        setContent {
            DefaultBackgroundTheme {
                val navController = rememberNavController()
                val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

                NavHost(navController = navController, startDestination = "SplashScreen") {
                    composable("SplashScreen") { SplashScreen(navController) }
                    composable("WeatherPage") { WeatherPage(weatherViewModel,navController) }
                }
            }
        }
    }
}



@Preview(name = "Nexus 5 - Small", device = "id:Nexus 5", group = "Portrait Mode",
    showSystemUi = false
)
@Preview(name = "Pixel 5 - Medium", device = "id:pixel_5", group = "Portrait Mode",
    showSystemUi = false
)
@Preview(name = "Pixel 7 Pro - Large", device = "id:pixel_5", group = "Portrait Mode",
    showSystemUi = false
)
@Composable
fun GreetingPreview() {

        SplashScreen(rememberNavController())

}
