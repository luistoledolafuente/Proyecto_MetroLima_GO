package com.metrolimago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.metrolimago.ui.screens.HomeScreen
import com.metrolimago.ui.theme.MetroLimaGOTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroLimaGOTheme {
                HomeScreen()
            }
        }
    }
}
