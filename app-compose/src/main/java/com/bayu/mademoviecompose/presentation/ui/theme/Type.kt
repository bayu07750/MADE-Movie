package com.bayu.mademoviecompose.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.bayu.mademoviecompose.R


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)


val fontPoppins = GoogleFont("Poppins")
val fontFamily = FontFamily(
    Font(googleFont = fontPoppins, fontProvider = provider, weight = FontWeight.W300),
    Font(googleFont = fontPoppins, fontProvider = provider, weight = FontWeight.W400),
    Font(googleFont = fontPoppins, fontProvider = provider, weight = FontWeight.W500),
    Font(googleFont = fontPoppins, fontProvider = provider, weight = FontWeight.W600),
    Font(googleFont = fontPoppins, fontProvider = provider, weight = FontWeight.W700),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = fontFamily,
)