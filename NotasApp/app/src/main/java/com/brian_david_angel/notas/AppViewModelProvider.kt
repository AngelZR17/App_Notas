package com.brian_david_angel.notas

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.brian_david_angel.notas.app.NotesApplication
import com.brian_david_angel.notas.ui.screens.AddNoteViewModel
import com.brian_david_angel.notas.ui.screens.EditNoteViewModel
import com.brian_david_angel.notas.ui.screens.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            AddNoteViewModel(NotesApplication().container.itemsRepository)
        }

        initializer {
            HomeViewModel(NotesApplication().container.itemsRepository)
        }

        initializer {
            EditNoteViewModel(this.createSavedStateHandle(), NotesApplication().container.itemsRepository)
        }

    }
}

fun CreationExtras.NotesApplication(): NotesApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)