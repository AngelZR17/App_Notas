package com.brian_david_angel.notas.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.navigation.NavController
import com.brian_david_angel.notas.R
import com.brian_david_angel.notas.ui.utils.NotesAppNavigationType
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.brian_david_angel.notas.Constants
import com.brian_david_angel.notas.NotesViewModel
import com.brian_david_angel.notas.app.NotesApplication
import com.brian_david_angel.notas.data.Note
import kotlinx.coroutines.Dispatchers

@Composable
fun EditNoteScreenUI(viewModel: NotesViewModel, navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val noteId = Constants.NOTE_EDIT
            ContentEditNoteScreenUI(noteId = noteId, viewModel = viewModel, navController=navController)
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentEditNoteScreenUI(noteId: Int,viewModel: NotesViewModel, navController: NavController){
    val coroutineScope = rememberCoroutineScope()
    val note = remember {
        mutableStateOf(Constants.noteDetailPlaceHolder)
    }
    val currentNote = rememberSaveable { mutableStateOf("") }
    val currentTitle = rememberSaveable { mutableStateOf("") }
    val currentPhotos = rememberSaveable { mutableStateOf("") }
    val saveButtonState = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(true) {
        coroutineScope.launch(Dispatchers.IO) {
            note.value = viewModel.getNote(noteId) ?: Constants.noteDetailPlaceHolder
            currentNote.value = note.value.note
            currentTitle.value = note.value.title
            currentPhotos.value = note.value.imageUri.toString()
        }
    }

    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->

        if (uri != null) {
            NotesApplication.getUriPermission(uri)
        }
        currentPhotos.value = uri.toString()
        if (currentPhotos.value != note.value.imageUri) {
            saveButtonState.value = true
        }
    }

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
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(R.dimen.padding_top_column)),
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            dimensionResource(R.dimen.padding_start_textField),
                            end = dimensionResource(R.dimen.padding_end_textField)
                        ),
                    value = currentTitle.value,
                    onValueChange = { value ->
                        currentTitle.value = value
                        if (currentTitle.value != note.value.title) {
                            saveButtonState.value = true
                        } else if (currentNote.value == note.value.note &&
                            currentTitle.value == note.value.title
                        ) {
                            saveButtonState.value = false
                        }
                    },
                    label = { Text("Titulo de la nota") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            dimensionResource(R.dimen.padding_start_textField),
                            end = dimensionResource(R.dimen.padding_end_textField)
                        )
                        .height(400.dp),
                    value = currentNote.value,
                    onValueChange = { value ->
                        currentNote.value = value
                        if (currentNote.value != note.value.note) {
                            saveButtonState.value = true
                        } else if (currentNote.value == note.value.note &&
                            currentTitle.value == note.value.title
                        ) {
                            saveButtonState.value = false
                        }
                    },
                    label = { Text("Descripcion") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                AsyncImage(
                    model = Uri.parse(currentPhotos.value),
                    modifier = Modifier.fillMaxWidth().size(150.dp),
                    contentDescription = "Selected image",
                )
                /*if (currentPhotos.value != null && currentPhotos.value!!.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = Uri.parse(currentPhotos.value))
                                .build()
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.3f)
                            .padding(6.dp),
                        contentScale = ContentScale.Crop
                    )
                }*/
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                actions = {
                    IconButton(onClick = {
                            getImageRequest.launch(arrayOf("image/*"))
                        }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.attach_file),
                            contentDescription = "Adjuntar archivo",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            Icons.Filled.Camera,
                            contentDescription = "Tomar foto",
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
                    if(currentTitle.value.isNotBlank() && currentNote.value.isNotBlank()){
                        FloatingActionButton(
                            onClick = {
                                viewModel.updateNote(
                                    Note(
                                        id = note.value.id,
                                        note = currentNote.value,
                                        title = currentTitle.value,
                                        imageUri = currentPhotos.value
                                    )
                                )
                                navController.popBackStack()
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

