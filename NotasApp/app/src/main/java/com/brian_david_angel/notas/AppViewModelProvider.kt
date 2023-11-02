package com.brian_david_angel.notas

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.brian_david_angel.notas.app.NotesApplication
import com.brian_david_angel.notas.ui.screens.AddNoteViewModel
import com.brian_david_angel.notas.ui.screens.MainModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            AddNoteViewModel(NotesApplication().container.itemsRepository)
        }

        initializer {
            MainModel(NotesApplication().container.itemsRepository)
        }

    }
}

fun CreationExtras.NotesApplication(): NotesApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)