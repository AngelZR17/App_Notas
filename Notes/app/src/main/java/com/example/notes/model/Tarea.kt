package com.example.notes.model

import java.util.Date

data class Tarea(
    var realizada:Boolean,
    var nombre:String,
    var descripcion:String,
    var fecha: Date
)
