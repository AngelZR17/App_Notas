package com.brian_david_angel.notas.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.brian_david_angel.notas.Constants
import com.brian_david_angel.notas.R
import com.brian_david_angel.notas.ui.theme.NotasTheme
import com.brian_david_angel.notas.ui.utils.NotesAppNavigationType
import kotlinx.coroutines.launch
import com.brian_david_angel.notas.NotesViewModel
import com.brian_david_angel.notas.app.NotesApplication
import com.brian_david_angel.notas.data.Note
import com.brian_david_angel.notas.others_codes.ComposeFileProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView


@Composable
fun AddNoteScreenUI(navController: NavController, notesViewModel: NotesViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ContentAddNoteScreenUI(navController = navController, viewModel = notesViewModel)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContentAddNoteScreenUI(viewModel: NotesViewModel, navController: NavController){
    val context = LocalContext.current
    var uriCamara : Uri? = null
    val currentNote = rememberSaveable { mutableStateOf("") }
    val currentTitle = rememberSaveable { mutableStateOf("") }
    var urisPhotos by remember { mutableStateOf(listOf<String>()) }


    var permisosRequeridos by rememberSaveable { mutableStateOf(false) }
    val recordAudioPermissionState = rememberPermissionState(
        Manifest.permission.RECORD_AUDIO
    )


    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            urisPhotos = urisPhotos.plus(uri!!.toString()+"|IMG")
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if(success){
                urisPhotos = urisPhotos.plus(uriCamara!!.toString()+"|IMG")
            }
        }
    )

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if(success){
                urisPhotos = urisPhotos.plus(uriCamara!!.toString()+"|VID")
            }
        }
    )

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
                    },
                    label = { Text("Descripcion") }
                )
                Spacer(modifier = Modifier.height(3.dp))
                LazyColumn(
                    modifier = Modifier.padding(top = 0.dp),
                    contentPadding = PaddingValues(0.dp),
                ){
                    itemsIndexed(urisPhotos){index, uri ->
                        tarjetaMedia(uri = uri)
                    }
                }
                if (permisosRequeridos) {
                    mostrarDialogoPermisos(
                        confirmarPermisos = {
                            permisosRequeridos = false
                            recordAudioPermissionState.launchPermissionRequest()
                        },
                        cancelarPermisos = { permisosRequeridos = false },
                    )
                }
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
                            uriCamara = ComposeFileProvider.getImageUri(context)
                            cameraLauncher.launch(uriCamara)
                        }
                    ) {
                        Icon(
                            Icons.Filled.Camera,
                            contentDescription = "Tomar foto",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {
                            val uri = ComposeFileProvider.getImageUri(context)
                            videoLauncher.launch(uri)
                            uriCamara = uri
                        }
                    ) {
                        Icon(
                            Icons.Filled.Camera,
                            contentDescription = "Tomar video",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { permisosRequeridos=true }) {
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
                                viewModel.createNote(
                                    currentTitle.value,
                                    currentNote.value,
                                    urisPhotos.joinToString()
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

@Composable
private fun tarjetaMedia(uri: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                var arreglo = uri.split("|")
                if (arreglo.get(1).equals("IMG")) {
                    AsyncImage(
                        model = Uri.parse(arreglo.get(0)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(150.dp),
                        contentDescription = "Selected image",
                    )
                } else if (arreglo.get(1).equals("VID")) {
                    VideoPlayer(Uri.parse(arreglo.get(0)))
                }
            }

        }
    }
}

@Composable
private fun VideoPlayer(videoUri: Uri, modifier: Modifier = Modifier
    .fillMaxWidth()
    .size(150.dp)) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
        }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = modifier
    )
}

@Composable
private fun mostrarDialogoPermisos(
    confirmarPermisos: () -> Unit,
    cancelarPermisos: () -> Unit,
) {
    AlertDialog(onDismissRequest = {  },
        containerColor = MaterialTheme.colorScheme.background,
        title = { Text("Se requiere permiso para grabar audio") },
        text = { Text("Para grabar un audio se requiere el permiso de Audio, pulse permitir para habilitar permitir los permisos") },
        modifier = Modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = cancelarPermisos ) {
                Text(text = "Cancelar")
            }
        },
        confirmButton = {
            TextButton(onClick = confirmarPermisos ) {
                Text(text = "Permitir")
            }
        })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    NotasTheme {
    }
}