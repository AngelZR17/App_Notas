package com.brian_david_angel.notas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.brian_david_angel.notas.AppViewModelProvider
import com.brian_david_angel.notas.R
import com.brian_david_angel.notas.data.Item
import com.brian_david_angel.notas.ui.theme.NotasTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory), navController: NavController){
    //val context = LocalContext.current
    val homeUiState by viewModel.homeUiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = { Text("Notas", color = Color.White) },
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.search), contentDescription = "Buscar", tint = Color.White)
                    }

                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.notification), contentDescription = "Recordatorio", tint = Color.White)
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("addnote")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
            }
        },
        content = {
            contenidoPrincipal(contentPadding = it, itemList = homeUiState.itemList, viewModel=viewModel, navController=navController)
        }
    )
}

@Composable
fun contenidoPrincipal(contentPadding: PaddingValues = PaddingValues(0.dp), itemList: List<Item>, viewModel: HomeViewModel, navController: NavController){
    val itemList: List<Item> = itemList
    if (itemList.isEmpty()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No hay ninguna nota agregada",
                style = MaterialTheme.typography.titleLarge,
                //textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.padding(top = 12.dp),
            contentPadding = contentPadding
        ){
            items(items = itemList, key = { it.id }) {
                    item -> tarjetaNota(item, viewModel, navController)
            }
        }
    }
}

@Composable
fun tarjetaNota(item: Item, viewModel: HomeViewModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController.navigate("editnote/${item.id}") }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.titulo,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = item.contenido,
                    style = MaterialTheme.typography.bodyLarge
                )
                /*Text(
                    text = item.fecha,
                    style = MaterialTheme.typography.bodyLarge
                )*/
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.eliminarNota(item)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Icon(painter = painterResource(id = R.drawable.trash),
                        contentDescription = "Borrar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotasTheme {
        /*val itemNota = Item(
            1,"Nota prueba","esta es una nota"
        )
        tarjetaNota(item = itemNota)*/
    }
}