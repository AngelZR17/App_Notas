package com.example.notes.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

object tareasInfo {
    @RequiresApi(Build.VERSION_CODES.O)
    val tareas = listOf(
        Tarea(
            realizada=false,
            nombre="tarea 1",
            descripcion="esta es la tarea de ejemplo 1",
            fecha= Date.from(Instant.now())
        ),
        Tarea(
            realizada = false,
            nombre="tarea 2",
            descripcion="esta es la tarea de ejemplo 2",
            fecha= Date.from(Instant.now())
        ),
    )
}