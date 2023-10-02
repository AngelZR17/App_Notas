package com.example.notes

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notes.model.Nota
import com.example.notes.model.Tarea
import com.example.notes.model.notasInfo
import com.example.notes.model.tareasInfo
import com.example.notes.ui.theme.NotesTheme
import java.time.Instant
import java.util.Date


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //notasList(notas=notasInfo.notas.toList());
                    taskList(tareas = tareasInfo.tareas.toList());

                }
            }
        }
    }
}
@Composable
fun notasList(
    modifier: Modifier= Modifier,
    notas:List<Nota>
){
    LazyColumn(){
        itemsIndexed(notas){index,nota->
            notaCard(
                nota = nota,
                modifier =Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}
@Composable
fun taskList(
    modifier: Modifier= Modifier,
    tareas:List<Tarea>
){
    LazyColumn(){
        itemsIndexed(tareas){index,tareas->
            taskCard(
                tarea = tareas,
                modifier =Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}
@Composable
fun notaCard(nota: Nota, modifier: Modifier=Modifier){
    Card (
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier,

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nota.nombre
                )
                Text(
                    text = nota.descripcion
                )
                Text(
                    text = nota.fecha.toString()
                )
            }
        }
    }
}
@Composable
fun taskCard(tarea: Tarea, modifier: Modifier=Modifier){
    Card (
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),

        modifier = modifier,

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarea.nombre
                )
                Text(
                    text = tarea.descripcion
                )
                Text(
                    text = tarea.fecha.toString()
                )
                Text(
                    text = tarea.realizada.toString()
                )
            }
        }
    }
}
/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ShowCard() {
    NotesTheme {
        val nota: Nota=notasInfo.notas[0];
        notaCard(nota)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ShowTask() {
    NotesTheme{
        val tarea: Tarea=tareasInfo.tareas[0];
        notaCard(tarea)
    }
}
*/