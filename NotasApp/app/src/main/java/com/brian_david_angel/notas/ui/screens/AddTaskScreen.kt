package com.brian_david_angel.notas.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.brian_david_angel.notas.Constants
import com.brian_david_angel.notas.TaskViewModel
import com.brian_david_angel.notas.R
import com.brian_david_angel.notas.others_codes.AndroidAudioPlayer
import com.brian_david_angel.notas.others_codes.AndroidAudioRecorder
import com.brian_david_angel.notas.others_codes.ComposeFileProvider
import com.brian_david_angel.notas.others_codes.Notificaciones
import com.brian_david_angel.notas.ui.theme.NotasTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import java.io.File
import java.net.URI
import java.util.Calendar

private var audioFile: File? = null

@Composable
fun AddTaskScreenUI(navController: NavController, taskViewModel: TaskViewModel, ctx: Context){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ContentAddTaskScreenUI(navController = navController, viewModel = taskViewModel, ctx=ctx)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContentAddTaskScreenUI(viewModel: TaskViewModel, navController: NavController, ctx: Context){
    val note = remember {
        mutableStateOf(Constants.noteDetailPlaceHolder)
    }

    val recorder by lazy {
        AndroidAudioRecorder(ctx)
    }

    val player by lazy {
        AndroidAudioPlayer(ctx)
    }

    val ctx = LocalContext.current
    val currentTask = rememberSaveable { mutableStateOf("") }
    val currentTitle = rememberSaveable { mutableStateOf("") }
    val currentFecha = rememberSaveable { mutableStateOf("") }
    val currentHora = rememberSaveable { mutableStateOf("") }
    val currentPhotos = rememberSaveable { mutableStateOf("") }
    val saveButtonState = rememberSaveable { mutableStateOf(false) }
    var urisPhotos by remember { mutableStateOf(listOf<String>()) }
    var uriCamara : Uri? = null

    var isGrabacion = rememberSaveable { mutableStateOf(false) }


    var permisosRequeridos by rememberSaveable { mutableStateOf(false) }
    val recordAudioPermissionState = rememberPermissionState(
        Manifest.permission.RECORD_AUDIO
    )

    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri -> if (uri != null) {
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
                title = { Text("Agregar Tarea", color = Color.White) },
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.notification),
                            contentDescription = "Recordatorio",
                            tint = Color.White)
                    }
                },
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
                    .padding(top = dimensionResource(R.dimen.padding_top_column), bottom = 80.dp),
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
                        saveButtonState.value =
                            currentTitle.value != "" && currentTask.value != ""
                    },
                    label = { Text("Titulo de la tarea") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            dimensionResource(R.dimen.padding_start_textField),
                            end = dimensionResource(R.dimen.padding_end_textField)
                        )
                        .height(150.dp),
                    value = currentTask.value,
                    onValueChange = { value ->
                        currentTask.value = value
                        saveButtonState.value =
                            currentTitle.value != "" && currentTask.value != ""
                    },
                    label = { Text("Descripcion") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                DatePicker(currentFecha)
                Spacer(modifier = Modifier.height(10.dp))
                TimePicker(currentHora)
                LazyColumn(
                    modifier = Modifier.padding(top = 0.dp),
                    contentPadding = PaddingValues(0.dp),
                ){
                    itemsIndexed(urisPhotos){index, uri ->
                        tarjetaMedia(uri = uri, player= player)
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
                        Icon(Icons.Filled.AttachFile,
                            contentDescription = "Adjuntar archivo",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {
                            uriCamara = ComposeFileProvider.getImageUri(ctx)
                            cameraLauncher.launch(uriCamara)
                        }
                    ) {
                        Icon(
                            Icons.Filled.CameraAlt,
                            contentDescription = "Tomar foto",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {
                            val uri = ComposeFileProvider.getImageUri(ctx)
                            videoLauncher.launch(uri)
                            uriCamara = uri
                        }
                    ) {
                        Icon(
                            Icons.Filled.Videocam,
                            contentDescription = "Tomar video",
                            tint = Color.White
                        )
                    }
                    if(!isGrabacion.value){
                        IconButton(
                            onClick = {
                                if(!recordAudioPermissionState.status.isGranted){
                                    permisosRequeridos=true
                                } else {
                                    var i : Int=0
                                    var outputFile: File
                                    do {
                                        i++
                                        outputFile = File(ctx.cacheDir, "audio"+i+".mp3")
                                    }while(outputFile.exists())
                                    outputFile.also {
                                        recorder.start(it)
                                        audioFile = it
                                    }
                                    isGrabacion.value=true
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Mic,
                                contentDescription = "Grabar Audio",
                                tint = Color.White
                            )
                        }
                    }else{
                        IconButton(
                            onClick = {
                                recorder.stop()
                                urisPhotos = urisPhotos.plus(Uri.fromFile(audioFile)!!.toString()+"|AUD")
                                isGrabacion.value=false
                            }
                        ) {
                            Icon(Icons.Filled.MicOff,
                                contentDescription = "Detener Grabacion",
                                tint = Color.White
                            )
                        }
                    }
                },
                floatingActionButton = {
                    if(currentTitle.value.isNotBlank() && currentTask.value.isNotBlank()){
                        FloatingActionButton(
                            onClick = {
                                viewModel.createTask(
                                    currentTitle.value,
                                    currentTask.value,
                                    currentFecha.value,
                                    currentHora.value,
                                    urisPhotos.joinToString()
                                )
                                var hora=currentHora.value.split(":")
                                val millis = conversion(hora.get(0).toLong(), hora.get(1).toLong())
                                Notificaciones().scheduleNotification(ctx, currentTitle.value+"|"+currentTask.value+"|"+currentTask.value, millis)
                                navController.popBackStack()
                            },
                            containerColor = MaterialTheme.colorScheme.primary,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                        ) {
                            Icon(Icons.Filled.Save, "Localized description", tint = Color.White)
                        }
                    }
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePicker(
    currentFecha: MutableState<String>
){
    var fecha by rememberSaveable {
        mutableStateOf("")
    }

    val year: Int
    val month: Int
    val day: Int
    val nCalendar = Calendar.getInstance()
    year = nCalendar.get(Calendar.YEAR)
    month = nCalendar.get(Calendar.MONTH)
    day = nCalendar.get(Calendar.DAY_OF_MONTH)

    val nDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year: Int, month: Int, day: Int ->
            fecha = "$day/$month/$year"
            currentFecha.value = fecha
        },
        year, month, day
    )

    TextField(
        modifier = Modifier.fillMaxWidth()
            .padding(
                dimensionResource(R.dimen.padding_start_textField),
                end = dimensionResource(R.dimen.padding_end_textField)
            ),
        value = currentFecha.value,
        onValueChange = { value ->
            currentFecha.value = value
        },
        readOnly = true,
        label = { Text("Fecha") },
        placeholder = { Text("Selecciona la fecha") },
        leadingIcon = {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.clickable { nDatePickerDialog.show() }
            )
        }
    )
}

private fun getCurrentTime(): Pair<Int, Int>{
    val cal = Calendar.getInstance()
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)
    return Pair(hour,minute)
}

private fun conversion(hora: Long, minuto: Long): Long {
    val dupla = getCurrentTime()
    val millis = (((hora-dupla.first)*60) + (minuto-dupla.second)) * 60 * 1000
    return millis
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePicker(
    currentHour: MutableState<String>
) {
    var hora by rememberSaveable {
        mutableStateOf("")
    }

    val hour: Int
    val minute: Int
    val nCalendar = Calendar.getInstance()
    hour = nCalendar.get(Calendar.HOUR_OF_DAY)
    minute = nCalendar.get(Calendar.MINUTE)

    val nTimePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hourOfDay: Int, minute: Int ->
            hora = String.format("%02d:%02d", hourOfDay, minute)
            currentHour.value = hora
        },
        hour, minute, true
    )

    TextField(
        modifier = Modifier.fillMaxWidth()
            .padding(
                dimensionResource(R.dimen.padding_start_textField),
                end = dimensionResource(R.dimen.padding_end_textField)
            ),
        value = currentHour.value,
        onValueChange = { value ->
            currentHour.value = value
        },
        readOnly = true,
        label = { Text("Hora") },
        placeholder = { Text("Selecciona la hora") },
        leadingIcon = {
            Icon(
                Icons.Default.AccessTime,
                contentDescription = null,
                modifier = Modifier.clickable { nTimePickerDialog.show() }
            )
        }
    )
}

@Composable
private fun tarjetaMedia(uri: String, player: AndroidAudioPlayer) {
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
                    Text(
                        text = "Imagen",
                    )
                    AsyncImage(
                        model = Uri.parse(arreglo.get(0)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(150.dp),
                        contentDescription = "Selected image",
                    )
                } else if (arreglo.get(1).equals("VID")) {
                    Text(
                        text = "Video",
                    )
                    VideoPlayer(Uri.parse(arreglo.get(0)))
                } else if (arreglo.get(1).equals("AUD")) {
                    Text(
                        text = "Audio",
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = {
                                    val uriString = arreglo.get(0)
                                    val uri = URI(uriString)
                                    val file = File(uri)
                                    player.start(file)
                                }
                            ) {
                                Icon(
                                    Icons.Filled.PlayArrow,
                                    contentDescription = "Audio",
                                    tint = Color.White
                                )
                            }
                            IconButton(
                                onClick = {
                                    player.stop()
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Stop,
                                    contentDescription = "Audio",
                                    tint = Color.White
                                )
                            }
                        }
                    }
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
fun GreetingPreview3() {
    NotasTheme {
    }
}