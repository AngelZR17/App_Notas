package com.brian_david_angel.notas.navegation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brian_david_angel.notas.NotesViewModel
import com.brian_david_angel.notas.TaskViewModel
import com.brian_david_angel.notas.ui.screens.AddNoteScreenUI
import com.brian_david_angel.notas.ui.screens.AddTaskScreenUI
import com.brian_david_angel.notas.ui.screens.EditNoteScreenUI
import com.brian_david_angel.notas.ui.screens.EditTaskScreenUI
import com.brian_david_angel.notas.ui.screens.HomeScreenTaskUI
import com.brian_david_angel.notas.ui.screens.HomeScreenUI
import com.brian_david_angel.notas.ui.utils.NotesAppNavigationType

@Composable
fun AppNavigation(
    windowSize: WindowWidthSizeClass,
    notesViewModel: NotesViewModel,
    taskViewModel: TaskViewModel
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
            HomeScreenUI(notesViewModel,navController, navigationType)
        }
        composable("hometask"){
            HomeScreenTaskUI(taskViewModel,navController, navigationType)
        }
        composable("addtask"){
            AddTaskScreenUI(navController, taskViewModel)
        }
        composable("edittask"){
            EditTaskScreenUI(taskViewModel, navController)
        }
        composable("addnote"){
            AddNoteScreenUI(navController, notesViewModel)
        }
        composable("editnote"){
            EditNoteScreenUI(notesViewModel, navController)
        }
    }
}