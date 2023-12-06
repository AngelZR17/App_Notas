package com.brian_david_angel.notas.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.DateRange
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.brian_david_angel.notas.Constants
import com.brian_david_angel.notas.TaskViewModel
import com.brian_david_angel.notas.R
import com.brian_david_angel.notas.app.NotesApplication
import com.brian_david_angel.notas.others_codes.Notificaciones
import com.brian_david_angel.notas.ui.theme.NotasTheme
import java.util.Calendar

@Composable
fun AddTaskScreenUI(navController: NavController, taskViewModel: TaskViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ContentAddTaskScreenUI(navController = navController, viewModel = taskViewModel)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContentAddTaskScreenUI(viewModel: TaskViewModel, navController: NavController){
    val note = remember {
        mutableStateOf(Constants.noteDetailPlaceHolder)
    }
    val ctx = LocalContext.current
    val currentTask = rememberSaveable { mutableStateOf("") }
    val currentTitle = rememberSaveable { mutableStateOf("") }
    val currentFecha = rememberSaveable { mutableStateOf("") }
    val currentPhotos = rememberSaveable { mutableStateOf("") }
    val saveButtonState = rememberSaveable { mutableStateOf(false) }


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
                AsyncImage(
                    model = Uri.parse(currentPhotos.value),
                    modifier = Modifier.fillMaxWidth().size(150.dp),
                    contentDescription = "Selected image",
                )
                /*
                if (currentPhotos.value.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = Uri.parse(currentPhotos.value))
                                .build()
                        ),
                        contentDescription = null,
                        modifier = Modifier
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
                    if(currentTitle.value.isNotBlank() && currentTask.value.isNotBlank()){
                        FloatingActionButton(
                            onClick = {
                                viewModel.createTask(
                                    currentTitle.value,
                                    currentTask.value,
                                    currentFecha.value,
                                    currentPhotos.value
                                )
                                Notificaciones().scheduleNotification(ctx)
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    NotasTheme {
    }
}