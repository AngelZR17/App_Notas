package com.brian_david_angel.notas.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.brian_david_angel.notas.AppViewModelProvider
import com.brian_david_angel.notas.R
import com.brian_david_angel.notas.ui.utils.NotesAppNavigationType
import kotlinx.coroutines.launch
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import android.content.Intent

object EditNoteDestination {
    const val itemIdArg = "itemId"
    val routeWithArgs = "editnote/{$itemIdArg}"
}

@Composable
fun EditNoteScreenUI(navController: NavController, navigationType: NotesAppNavigationType){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ContentEditNoteScreenUI(navController = navController, navigationType = navigationType)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentEditNoteScreenUI(viewModel: EditNoteViewModel = viewModel(factory = AppViewModelProvider.Factory), navController: NavController, navigationType: NotesAppNavigationType){
    val openGallery = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {

        }
    }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = { Text("Editar Nota", color = Color.White) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Botón de Retroceso", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.notification), contentDescription = "Menú", tint = Color.White)
                    }
                }
            )
        },
        content = {
            CamposTextoCuerpo(
                contentPadding = it,
                itemUiState = viewModel.itemUiState,
                onItemValueChange = viewModel::updateUiState
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                actions = {
                    IconButton(onClick = { openGallery.launch("image/*") }) {
                        Icon(painter = painterResource(id = R.drawable.attach_file),
                            contentDescription = "Adjuntar archivo",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(painter = painterResource(id = R.drawable.checklist),
                            contentDescription = "Tarea",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(painter = painterResource(id = R.drawable.microphone),
                            contentDescription = "Audio",
                            tint = Color.White
                        )
                    }
                },
                floatingActionButton = {
                    if(viewModel.itemUiState.isEntryValid){
                        FloatingActionButton(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.updateItem()
                                    navController.popBackStack()
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.primary,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                        ) {
                            Icon(painter = painterResource(id = R.drawable.save), "Localized description", tint = Color.White)
                        }
                    }
                }
            )
        }
    )
}

