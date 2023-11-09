package com.brian_david_angel.notas

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brian_david_angel.notas.navegation.AppNavigation
import com.brian_david_angel.notas.ui.screens.AddNoteScreenUI
import com.brian_david_angel.notas.ui.screens.EditNoteDestination
import com.brian_david_angel.notas.ui.screens.EditNoteScreenUI
import com.brian_david_angel.notas.ui.screens.HomeScreenUI
import com.brian_david_angel.notas.ui.theme.NotasTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotasTheme {
                val windowSize = calculateWindowSizeClass(this)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(windowSize.widthSizeClass)
                }
            }
        }
    }
}

