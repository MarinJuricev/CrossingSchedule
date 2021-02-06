package com.example.crossingschedule.core.ui

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.example.crossingschedule.R

private val FinkHeavy = FontFamily(
    Font(R.font.fink_heavy)
)

// Set of Material typography styles to start with
val crossingTypography = Typography(
    h1 = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W500,
        fontSize = 48.sp,
    ),
    h2 = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
    ),
    h3 = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
    ),
    h4 = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
    ),
    h5 = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
    ),
    h6 = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    body1 = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FinkHeavy,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = Color.White
    ),
    caption = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = FinkHeavy,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )
)
