package com.example.wheatherapp.weatherapp
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.wheatherapp.weatherapp.api.CitySuggestion
import com.example.wheatherapp.weatherapp.api.NetworkResponse
import com.example.wheatherapp.weatherapp.api.WeatherModel
import com.example.wheatherapp.weatherapp.component.WelcomeMsg
import com.example.wheatherapp.weatherapp.ui.theme.buttonColor
import com.example.wheatherapp.weatherapp.ui.theme.dialogueColorBg
import com.example.wheatherapp.weatherapp.ui.theme.greyColor
import com.example.wheatherapp.weatherapp.ui.theme.transparentColor
import com.example.wheatherapp.weatherapp.ui.theme.whiteColor


@Composable
fun WeatherPage(viewModel: WeatherViewModel, navController: NavHostController) {
    var city by remember { mutableStateOf("") }
    var showSuggestions by remember { mutableStateOf(false) }
    val weatherResult = viewModel.weatherResult.observeAsState()
    val suggestions = viewModel.suggestions.observeAsState(NetworkResponse.Success(emptyList()))
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var backPressCount by remember { mutableIntStateOf(0) }

    BackHandler(enabled = true) {
        backPressCount++
        if (backPressCount == 1) {
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        } else {

             (context as? Activity)?.finishAffinity()
        }
    }

    Column(modifier = Modifier.fillMaxSize() .verticalScroll(scrollState)


        , horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .onFocusChanged { focusState ->
                    showSuggestions = focusState.isFocused && city.isNotEmpty()
                }

                ,
            value = city,
            onValueChange = { newValue ->

                if (newValue.all { !it.isDigit() }) {
                    city = newValue
                    if (newValue.length > 2) {
                        viewModel.getCitySuggestions(newValue)
                        showSuggestions = true
                    } else {
                        showSuggestions = false
                    }

                }

            },
            
            label = { Text(text = "Search for any location") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = whiteColor,
                focusedBorderColor = transparentColor,
                focusedTextColor = whiteColor,
                focusedLabelColor = transparentColor,
                unfocusedLabelColor = buttonColor,
                unfocusedBorderColor = buttonColor,
                unfocusedTextColor = buttonColor,
                unfocusedContainerColor = dialogueColorBg
            ),
            shape = RoundedCornerShape(25.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )

        if (showSuggestions && suggestions.value is NetworkResponse.Success) {
            SuggestionsDropdown(
                suggestions = (suggestions.value as NetworkResponse.Success).data,
                onSuggestionSelected = { selectedCity ->
                    city = selectedCity.name
                    viewModel.getData(selectedCity.name)
                    keyboardController?.hide()
                    showSuggestions = false

                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxSize().navigationBarsPadding()
            ,
            verticalArrangement = Arrangement.Center
        ) {
            when (val result = weatherResult.value) {
                is NetworkResponse.Error -> {
//                    InternetErrorPage(modifier = Modifier.fillMaxSize())
                }
                NetworkResponse.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        strokeWidth = 3.dp,
                        color = whiteColor
                    )
                }
                is NetworkResponse.Success -> {
                    WeatherDetails(data = result.data)
                }
                null -> {
                    WelcomeMsg(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun SuggestionsDropdown(
    suggestions: List<CitySuggestion>,
    onSuggestionSelected: (CitySuggestion) -> Unit
) {



    Column(modifier = Modifier.fillMaxWidth()
        .padding(top = 1.dp, start = 10.dp, end = 10.dp)
        .background(color = dialogueColorBg, shape = RoundedCornerShape(10.dp))

        )

             {
        suggestions.forEach { suggestion ->
            Text(
                text = "${suggestion.name}, ${suggestion.country}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSuggestionSelected(suggestion)
                    }
                    .padding(8.dp),
                color = whiteColor
            )
        }
    }
}


@Composable
fun WeatherDetails(data: WeatherModel) {

        Column (modifier = Modifier
            .padding(horizontal = 10.dp)
            .background(color = dialogueColorBg, shape = RoundedCornerShape(10.dp))
            .border(width = (1.5).dp, color = transparentColor,shape = RoundedCornerShape(10.dp))
            .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                painter = painterResource(R.drawable.location_icon),
                contentDescription = "Location icon",
                modifier = Modifier.size(32.dp),
                tint = Color.Unspecified
            )
            Text(text = data.location.name, fontSize = 30.sp, color = whiteColor)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = greyColor)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${data.current.temp_c} °C",
            color = whiteColor,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AsyncImage(
            modifier = Modifier.size(200.dp),
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Condition icon"
        )
        Text(
            text = data.current.condition.text,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = whiteColor
        )}
///////////////////////////////////
        Card(modifier = Modifier.padding(10.dp)
            .border(width = (1.5).dp, color = transparentColor,shape = RoundedCornerShape(10.dp))
            ,
            colors = CardColors(containerColor = dialogueColorBg,
            contentColor = whiteColor,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor =Color.Unspecified )) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherMetrics("Humidity", data.current.humidity, icon = painterResource(R.drawable.humidity_icon))
                    WeatherMetrics("Wind Speed", "${data.current.wind_kph} km/h",
                        icon = painterResource(R.drawable.wind_icon))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherMetrics("UV", data.current.uv
                        ,icon = painterResource(R.drawable.uv_icon))
                    WeatherMetrics("Precipitation", "${data.current.precip_mm} mm",
                        icon = painterResource(R.drawable.precipitation_icon))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherMetrics("Local Time", data.location.localtime.split(" ")[1],
                        icon = painterResource(R.drawable.local_time_icon))
                    WeatherMetrics("Local Date", data.location.localtime.split(" ")[0]
                    ,icon = painterResource(R.drawable.local_date_icon))
                }
            }
        }
    }


@Composable
fun WeatherMetrics(key: String, value: String, icon: Painter) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter =icon, contentDescription ="", tint = Color.Unspecified)
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold)
    }
}
