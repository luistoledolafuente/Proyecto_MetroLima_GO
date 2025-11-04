package com.metrolimago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// 1. Asegúrate de que importa MainScreen
import com.metrolimago.ui.MainScreen
import com.metrolimago.ui.theme.MetroLimaGOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroLimaGOTheme {
                // 2. Asegúrate de que llama a MainScreen()
                MainScreen()
            }
        }
    }
}