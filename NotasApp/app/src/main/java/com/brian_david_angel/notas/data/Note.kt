package com.brian_david_angel.notas.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.brian_david_angel.notas.Constants
import java.text.SimpleDateFormat
import java.util.Date

@Entity(tableName = Constants.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class Note @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)    val id: Int? = null,
    @ColumnInfo(name = "note")          val note: String,
    @ColumnInfo(name = "title")         val title: String,
    @ColumnInfo(name = "dateUpdated")   val dateUpdated: String = fechaHoraActual(),
    @ColumnInfo(name = "imageUri")     val imageUri: String? = null
)

fun fechaHoraActual() : String {
    val date = Date()
    val sdf = SimpleDateFormat("dd/MMM/yyyy hh:mm a")
    return sdf.format(date)
}