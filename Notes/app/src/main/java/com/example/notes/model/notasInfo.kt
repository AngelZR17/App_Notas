package com.example.notes.model
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.notes.model.Nota
import java.time.Instant
import java.util.Date

object notasInfo {
    @RequiresApi(Build.VERSION_CODES.O)
    val notas = listOf(
        Nota(
            nombre="nota 1",
            descripcion="esta es la nota de ejemplo 1",
            fecha=Date.from(Instant.now())
        ),
        Nota(
            nombre="nota 2",
            descripcion="esta es la nota de ejemplo 2",
            fecha=Date.from(Instant.now())
        ),
    )
}