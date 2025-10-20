package com.metrolimago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.metrolimago.ui.MainScreen // <- CAMBIO: Importamos MainScreen
import com.metrolimago.ui.theme.MetroLimaGOTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroLimaGOTheme {
                MainScreen() // <- CAMBIO: Llamamos a MainScreen, que controla toda la app
            }
        }
    }
}
