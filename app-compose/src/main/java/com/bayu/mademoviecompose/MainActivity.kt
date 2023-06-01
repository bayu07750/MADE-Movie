package com.bayu.mademoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.core.view.WindowCompat
import com.bayu.mademoviecompose.ui.theme.MADEMovieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MADEMovieTheme {
                Text(text = "Hello World!")
            }
        }
    }
}
