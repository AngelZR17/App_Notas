package com.brian_david_angel.notas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddNoteViewModel : ViewModel() {

    init {

    }
    var textTitulo by mutableStateOf("")
        private set
    var textDescripcion by mutableStateOf("")
        private set

    fun actualizarTitulo(textoTitulo: String){
        textTitulo = textoTitulo
    }

    fun actualizarDescripcion(textoDescripcion: String){
        textDescripcion = textoDescripcion
    }
}