package com.brian_david_angel.notas.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.brian_david_angel.notas.AppViewModelProvider
import com.brian_david_angel.notas.R
import com.brian_david_angel.notas.ui.theme.NotasTheme
import com.brian_david_angel.notas.ui.utils.NotesAppNavigationType
import kotlinx.coroutines.launch

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import android.content.Intent


@Composable
fun AddNoteScreenUI(navController: NavController, navigationType: NotesAppNavigationType){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ContentAddNoteScreenUI(navController = navController, navigationType = navigationType)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentAddNoteScreenUI(viewModel: AddNoteViewModel = viewModel(factory = AppViewModelProvider.Factory), navController: NavController, navigationType: NotesAppNavigationType){
    val openGallery = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {

        }
    }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = { Text("Agregar Nota", color = Color.White) },
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
                                    viewModel.saveItem()
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

@Composable
fun CamposTextoCuerpo(
    contentPadding: PaddingValues = PaddingValues(dimensionResource(R.dimen.padding_values)),
    itemUiState: ItemUiState,
    onItemValueChange: (ItemDetails) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(R.dimen.padding_top_column)),
    ) {
        ItemInputForm(
            itemDetails = itemUiState.itemDetails,
            onValueChange = onItemValueChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemInputForm(
    itemDetails: ItemDetails,
    onValueChange: (ItemDetails) -> Unit = {},
){
    TextField(
        modifier = Modifier.fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_start_textField), end=dimensionResource(R.dimen.padding_end_textField)),
        value = itemDetails.titulo,
        onValueChange = { onValueChange(itemDetails.copy(titulo = it)) },
        label = { Text("Titulo de la nota") }
    )
    Spacer(modifier = Modifier.height(10.dp))
    TextField(
        modifier = Modifier.fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_start_textField), end=dimensionResource(R.dimen.padding_end_textField))
            .height(400.dp),
        value = itemDetails.contenido,
        onValueChange = { onValueChange(itemDetails.copy(contenido = it)) },
        label = { Text("Descripcion") }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    NotasTheme {
    }
}