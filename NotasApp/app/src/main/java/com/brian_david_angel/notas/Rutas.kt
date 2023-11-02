package com.brian_david_angel.notas

sealed class Rutas(val ruta: String){
    object HomeScreen: Rutas("home")
    object NoteScreen: Rutas("note")
}
