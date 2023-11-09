package com.brian_david_angel.notas.navegation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brian_david_angel.notas.ui.screens.AddNoteScreenUI
import com.brian_david_angel.notas.ui.screens.EditNoteDestination
import com.brian_david_angel.notas.ui.screens.EditNoteScreenUI
import com.brian_david_angel.notas.ui.screens.HomeScreenUI
import com.brian_david_angel.notas.ui.utils.NotesAppNavigationType

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppNavigation(
    windowSize: WindowWidthSizeClass
){
    val navigationType: NotesAppNavigationType
    val navController = rememberNavController()

    when(windowSize){
        WindowWidthSizeClass.Compact -> {
            navigationType = NotesAppNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NotesAppNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = NotesAppNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            navigationType = NotesAppNavigationType.BOTTOM_NAVIGATION
        }
    }

    NavHost(navController = navController, startDestination = "home"){
        composable(route = "home") {
            HomeScreenUI(navController, navigationType)
        }
        composable("addnote"){
            AddNoteScreenUI(navController, navigationType)
        }
        composable(
            route = EditNoteDestination.routeWithArgs,
            arguments = listOf(navArgument(EditNoteDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            EditNoteScreenUI(
                navController = navController, navigationType
            )
        }
    }
}