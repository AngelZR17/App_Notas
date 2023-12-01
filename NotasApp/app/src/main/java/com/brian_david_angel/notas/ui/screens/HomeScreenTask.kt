package com.brian_david_angel.notas.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brian_david_angel.notas.Constants
import com.brian_david_angel.notas.Constants.orPlaceHolderListTask
import com.brian_david_angel.notas.R
import com.brian_david_angel.notas.TaskViewModel
import com.brian_david_angel.notas.data.Task
import com.brian_david_angel.notas.data.bottomNavItems
import com.brian_david_angel.notas.ui.theme.NotasTheme
import com.brian_david_angel.notas.ui.utils.NotesAppNavigationType
import kotlinx.coroutines.launch

@Composable
fun HomeScreenTaskUI(taskViewModel: TaskViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ContentHomeScreenTaskUI(viewModel=taskViewModel, navController=navController, navigationType = navigationType)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentHomeScreenTaskUI(viewModel: TaskViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    val tasks = viewModel.task.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = { Text("Tareas", color = Color.White) },
                /*actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.notification),
                            contentDescription = "Recordatorio",
                            tint = Color.White)
                    }
                }*/
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.Description,
                            contentDescription = "Notas",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = {  }) {
                        Icon(
                            Icons.Filled.Task,
                            contentDescription = "Tareas",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("addtask")
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
                    }
                }
            )
        },
        content = {
            contenidoPrincipalTask(contentPadding = it, task = tasks.value.orPlaceHolderListTask(), viewModel=viewModel, navController=navController, navigationType=navigationType)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun contenidoPrincipalTask(contentPadding: PaddingValues = PaddingValues(0.dp), task: List<Task>, viewModel: TaskViewModel, navController: NavController, navigationType: NotesAppNavigationType){
    if(navigationType == NotesAppNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.fillMaxWidth(0.2f).padding(top=62.dp)
                ) {
                    var selectedItem by remember { mutableStateOf(2) }
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                navController.navigate(item.ruta)
                            },
                            label = { Text(text = item.nombre) },
                            icon = { Icon(item.icono, contentDescription = "${item.nombre} Icon") }
                        )
                    }
                }
            }
        ) {
            if(task.get(0).id!=0){
                LazyColumn(
                    modifier = Modifier.padding(top = 12.dp),
                    contentPadding = contentPadding
                ){
                    itemsIndexed(task){index, task ->
                        tarjetaTarea(task, navController, viewModel)
                    }
                }
            }else{
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(contentPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No hay ninguna tarea agregada",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }else{
        if(task.get(0).id!=0){
            LazyColumn(
                modifier = Modifier.padding(top = 12.dp),
                contentPadding = contentPadding
            ){
                itemsIndexed(task){index, task ->
                    tarjetaTarea(task, navController, viewModel) //MODIFICAR
                }
            }
        }else{
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No hay ninguna tarea agregada",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}


@Composable
fun tarjetaTarea(task: Task,navController: NavController, viewModel: TaskViewModel) {
    var eliminarConfirmaciónrequerida by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                Constants.NOTE_EDIT = task.id?:0
                navController.navigate("edittask")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    overflow= TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = task.task,
                    overflow= TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = task.dateUpdated,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                IconButton(
                    onClick = {
                        eliminarConfirmaciónrequerida = true
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Icon(painter = painterResource(id = R.drawable.trash),
                        contentDescription = "Borrar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            if (eliminarConfirmaciónrequerida) {
                mostrarDialogoEliminacion(
                    confirmarEliminacion = {
                        eliminarConfirmaciónrequerida = false
                        viewModel.deleteTask(task)
                    },
                    cancelarEliminacion = { eliminarConfirmaciónrequerida = false },
                )
            }
        }
    }
}

@Composable
private fun mostrarDialogoEliminacion(
    confirmarEliminacion: () -> Unit,
    cancelarEliminacion: () -> Unit,
) {
    AlertDialog(onDismissRequest = {  },
        containerColor = MaterialTheme.colorScheme.background,
        title = { Text("Confirmar eliminación") },
        text = { Text("¿Estás seguro de que deseas eliminar esta tarea?") },
        modifier = Modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = cancelarEliminacion  ) {
                Text(text = "Cancelar")
            }
        },
        confirmButton = {
            TextButton(onClick = confirmarEliminacion ) {
                Text(text = "Si, eliminar")
            }
        })
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview6() {
    NotasTheme {
        /*val itemNota = Item(
            1,"Nota prueba","esta es una nota"
        )
        tarjetaNota(item = itemNota)*/
    }
}
