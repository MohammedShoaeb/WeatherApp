package com.example.wheatherapp.weatherapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wheatherapp.weatherapp.R
import com.example.wheatherapp.weatherapp.ui.theme.dialogueColorBg
import com.example.wheatherapp.weatherapp.ui.theme.transparentColor
import com.example.wheatherapp.weatherapp.ui.theme.whiteColor

@Composable
fun WelcomeMsg(modifier: Modifier = Modifier){


    Column(modifier = modifier.padding(10.dp).wrapContentSize()
        .clip(shape = RoundedCornerShape(10.dp))
        .background(dialogueColorBg)
        .border(width = (1.5).dp, color = transparentColor,shape = RoundedCornerShape(10.dp))
        .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(painter = painterResource(id = R.drawable.search_vector_emoji), contentDescription = null)

        Text(text = stringResource(id = R.string.suggestion_search),
            fontSize = 14.sp,
            color = whiteColor,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
            , textAlign = TextAlign.Center)

    }


}