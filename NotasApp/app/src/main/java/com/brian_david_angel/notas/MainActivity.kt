package com.brian_david_angel.notas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brian_david_angel.notas.ui.screens.AddNoteScreenUI
import com.brian_david_angel.notas.ui.screens.EditNoteDestination
import com.brian_david_angel.notas.ui.screens.EditNoteScreenUI
import com.brian_david_angel.notas.ui.screens.HomeScreenUI
import com.brian_david_angel.notas.ui.theme.NotasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home"){
                        composable(route = "home") {
                            HomeScreenUI(navController = navController)
                        }
                        composable("addnote"){
                            AddNoteScreenUI(navController = navController)
                        }
                        composable(
                            route = EditNoteDestination.routeWithArgs,
                            arguments = listOf(navArgument(EditNoteDestination.itemIdArg) {
                                type = NavType.IntType
                            })
                        ) {
                            EditNoteScreenUI(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

