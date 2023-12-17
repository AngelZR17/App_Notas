package com.brian_david_angel.notas.navegation

sealed class AppScreens (val route: String){
    object HomeScreenUI: AppScreens("home")
    object HomeScreenTaskUI: AppScreens("hometask")
    object AddNoteScreenUI: AppScreens("addnote")
    object AddTaskScreenUI: AppScreens("addtask")
    object EditNoteScreenUI: AppScreens("editnote")
    object EditTaskScreenUI: AppScreens("edittask")


}
