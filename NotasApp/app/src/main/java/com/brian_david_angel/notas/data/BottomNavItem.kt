package com.brian_david_angel.notas.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Task
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val nombre: String,
    val ruta: String,
    val icono: ImageVector,
    val color: Color = Color.White
)

val bottomNavItems = listOf(
    BottomNavItem(
        nombre = "Notas",
        ruta = "home",
        icono = Icons.Filled.Description,
    ),
    BottomNavItem(
        nombre = "Tareas",
        ruta = "hometask",
        icono = Icons.Filled.Task,
    ),
)