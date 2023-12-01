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
import com.brian_david_angel.notas.app.NotesApplication
import com.brian_david_angel.notas.navegation.AppNavigation
import com.brian_david_angel.notas.ui.theme.NotasTheme

class MainActivity : ComponentActivity() {

    private lateinit var notesViewModel : NotesViewModel
    private lateinit var taskViewModel : TaskViewModel

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesViewModel =  NotesViewModelFactory(NotesApplication.getDao()).create(NotesViewModel::class.java)
        taskViewModel =  TaskViewModelFactory(NotesApplication.getDaoTask()).create(TaskViewModel::class.java)

        setContent {
            NotasTheme {
                val windowSize = calculateWindowSizeClass(this)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(windowSize.widthSizeClass, notesViewModel, taskViewModel)
                }
            }
        }
    }
}

